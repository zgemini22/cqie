package com.zds.biz.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.zds.biz.constant.BaseException;
import com.zds.biz.constant.ResultValueEnum;
import com.zds.biz.feign.UserLogService;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import com.zds.biz.util.*;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.DataVo;
import com.zds.biz.vo.LogVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;


/**
 * API统一拦截处理类
 * 校验接口入参字段-@ApiModelPropertyCheck
 * 自动记录API调用日志到user服务-tbl_log
 */
@Aspect
@Component
@Slf4j
@Order(2)
public class ApiAspect {


    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final String OPT_TYPE = "optType";
    private static final String SYSTEM_MODEL = "systemModel";
    private static final String SUCCESS = "SUCCESS";
    private static final String FAIL = "FAIL";

    @Autowired
    private UserLogService userService;

    @Autowired
    private ThreadLocalUtil threadLocalUtil;

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;


    /**
     * Controller层切点
     */
    @Pointcut("execution(* com.zds..*.*Controller.*(..))")
    public void controllerAspect() {
    }

    /**
     * 环绕切面统一处理日志保存和更新
     */
    @Around("controllerAspect()")
    public Object aroundController(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 获取入参校验
        Object[] args = joinPoint.getArgs();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        if (args.length >=1) {
            //已经解密的参数
            //需要解密的接口
            if (!RequestTypeChecker.isExcludedRequest()) {
                String parameters = parametersDecrypt(request);
                args = recreateRequestBodyArgs(method, args, parameters);
            }
            apiRequestAspect(args[0]);
        }

        // 记录开始时间
        Date startTime = new Date();
        //前置保存记录日志
        Long logId = logFrontRecord(joinPoint, request, args, startTime);

        //进入controller类处理业务
        Object result = joinPoint.proceed(args);

        //后置修改记录日志
        logPositionRecord(result, logId, startTime);

        //后置数据加密
        //需要加密的接口
        if (!RequestTypeChecker.isExcludedRequest()) {
            parametersPositionEncrypt(result);
            return null;
        }
        return result;
    }

    /**
     * 参数解密
     */
    private String parametersDecrypt(HttpServletRequest request) {
        //1.获取入参
        ReusableHttpServletRequestWrapper wrapper = (ReusableHttpServletRequestWrapper) request;
        //获取
        cloneHandler(wrapper);
        String body = wrapper.getBody();
        return body;
//        if (!StringUtils.isEmpty(body)) {
//            JSONObject jsonObject = JSONObject.parseObject(body);
//            String key = jsonObject.getString("data");
//            String parameter = CryptoUtils.decrypt(key);
//            return parameter;
//        }
//        return "";
    }

    private void cloneHandler(ReusableHttpServletRequestWrapper request) {
        try {
            // 1. 获取处理器方法
            HandlerExecutionChain handler = requestMappingHandlerMapping.getHandler(request);
            if (handler != null && handler.getHandler() instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler.getHandler();
                // 2. 查找 @RequestBody 参数
                MethodParameter requestBodyParam = findRequestBodyParameter(handlerMethod);
                if (requestBodyParam != null) {
                    // 判断是否为数组类型
                    if (requestBodyParam.getParameterType().equals(List.class)) {
                        //获取入参
                        String body = request.getBody();
                        body = body.replace("[", "").replace("]", "");
                        //编辑入参
                        request.setBody(body);
                    }
                }

            }
        } catch (Exception e) {
            log.debug("无法分析请求体类型", e);
        }

    }

    /**
     * 查找 @RequestBody 注解的参数
     */
    private MethodParameter findRequestBodyParameter(HandlerMethod handlerMethod) {
        for (int i = 0; i < handlerMethod.getMethodParameters().length; i++) {
            MethodParameter parameter = handlerMethod.getMethodParameters()[i];
            if (parameter.hasParameterAnnotation(RequestBody.class)) {
                return parameter;
            }
        }
        return null;
    }

    /**
     * 替换解密后的数据
     */
    private Object[] recreateRequestBodyArgs(Method method, Object[] originalArgs, String modifiedJson) {
        Object[] newArgs = Arrays.copyOf(originalArgs, originalArgs.length);

        try {
            Parameter[] parameters = method.getParameters();
            ObjectMapper mapper = new ObjectMapper();

            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i].isAnnotationPresent(RequestBody.class)) {
                    // 使用原始参数的类型信息重新解析JSON
                    Class<?> paramType = parameters[i].getType();
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    // 过滤掉实体类中不存在的字段
                    String filteredJson = filterUnknownFields(modifiedJson, paramType);
                    if (paramType.equals(List.class)) {
                        // 3. 关键：获取List的真实泛型元素类型
                        Type genericType = parameters[i].getParameterizedType();
                        if (genericType instanceof ParameterizedType) {
                            ParameterizedType parameterizedType = (ParameterizedType) genericType;
                            // 获取List的泛型参数（如List<User>中的User.class）
                            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                            if (actualTypeArguments.length > 0) {
                                Type listElementType = actualTypeArguments[0];
                                // 4. 构造List<实体类>的泛型类型并反序列化
                                CollectionType targetType = TypeFactory.defaultInstance()
                                        .constructCollectionType(List.class, (Class<?>) listElementType);
                                Object newValue = mapper.readValue(modifiedJson, targetType);
                                newArgs[i] = newValue;
                                break;
                            }
                        }
                        // 兜底：若无法获取泛型类型，仍按原逻辑（List<Map>）
                        Object newValue = mapper.readValue(modifiedJson, paramType);
                        newArgs[i] = newValue;
                        break;
                    } else {
                        Object newValue = mapper.readValue(filteredJson, paramType);
                        newArgs[i] = newValue;
                        break; // 通常只有一个@RequestBody参数
                    }

                }
            }
        } catch (Exception e) {
            // 解析失败，返回原始参数
            return originalArgs;
        }

        return newArgs;
    }

    private String filterUnknownFields(String json, Class<?> targetClass) throws Exception {
        JsonNode rootNode = objectMapper.readTree(json);
        ObjectNode filteredNode = objectMapper.createObjectNode();

        // 获取目标类的所有字段
        Set<String> validFields = getValidFields(targetClass);

        // 只保留目标类中存在的字段
        rootNode.fieldNames().forEachRemaining(fieldName -> {
            if (validFields.contains(fieldName)) {
                filteredNode.set(fieldName, rootNode.get(fieldName));
            }
        });

        return objectMapper.writeValueAsString(filteredNode);
    }

    private Set<String> getValidFields(Class<?> clazz) {
        Set<String> validFields = new HashSet<>();
        // 获取所有字段（含父类）
        while (clazz != null && !clazz.equals(Object.class)) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                // 布尔类型 isXXX 字段：同时添加 isXXX 和 commit（适配入参无 is 场景）
                if (fieldName.startsWith("is")
                        && (field.getType() == boolean.class || field.getType() == Boolean.class)) {
                    // 入参是 commit → 加入有效字段
                    String mappedName = fieldName.substring(2, 3).toLowerCase() + fieldName.substring(3);
                    validFields.add(mappedName); // commit
                    validFields.add(fieldName); // isCommit（兜底）
                } else {
                    validFields.add(fieldName); // 普通字段直接添加
                }
            }
            clazz = clazz.getSuperclass();
        }
        return validFields;
    }


    /**
     * 前置保存记录日志
     */
    private Long logFrontRecord(ProceedingJoinPoint joinPoint, HttpServletRequest request, Object[] args, Date startTime) throws Throwable {
        //排除日志接口不记录
        //获取uri 需要排除的接口
        String uri = request.getRequestURI();
        String packageName = joinPoint.getSignature().getDeclaringTypeName();
        // 排除 user 服务日志接口
        if (uri.startsWith("/inner/log") || packageName.startsWith("com.zds.user.controller")) {
            return -1L;
        }
        if (args.length >=1) {
            apiRequestAspect(args[0]);
        }

        // 保存日志
        LogVo logVo = buildLog(joinPoint, request, startTime, args);
        BaseResult<Long> saveResult;
        Long logId = null;
        try {
            saveResult = userService.save(logVo);
            logId = saveResult.getData();
        } catch (Exception e) {
            log.error("ApiAspect - 保存日志失败", e);
        }
        return logId;
    }

    /**
     * 后置加密
     */
    private void parametersPositionEncrypt(Object originalResult) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        if (response != null) {
            String download = response.getHeader("X-SOURCE");
            if (StringUtils.isEmpty(download)) {
                String jsonData = toJson(originalResult);
                // 2. 对返回结果进行加密
                String encryptedResult = CryptoUtils.encrypt(jsonData);
                String data = toJson(DataVo.builder()
                        .data(encryptedResult)
                        .build());
                // 3. 将加密后的结果写入响应体（覆盖原始返回值）
                response.setContentType("application/json;charset=" + StandardCharsets.UTF_8.name());
                PrintWriter out = response.getWriter();
//                out.write(data);
                out.write(jsonData);
                out.flush();
            }
        }
    }

    /**
     * 后置修改记录日志
     */
    private void logPositionRecord(Object result, Long logId, Date startTime) {
        if (logId == -1L) {
            return;
        }
        String status = SUCCESS;
        try {
            //后置流程是否成功
            if (result instanceof BaseResult) {
                status = ((BaseResult<?>) result).getCode() == ResultValueEnum.SYS_OK.getKey() ? SUCCESS : FAIL;
            }
        } catch (Throwable ex) {
            status = FAIL;
        }
        if (logId != null) {
            Date endTime = new Date();
            LogVo updateLog = LogVo.builder()
                    .id(logId)
                    .status(status)
                    .updateTime(endTime)
                    .duration(endTime.getTime() - startTime.getTime()) // 毫秒
                    .build();
            try {
                userService.update(updateLog);
            } catch (Exception e) {
                log.error("ApiAspect - 更新日志失败", e);
            }
        }


    }


    /**
     * 构建日志对象
     */
    private LogVo buildLog(ProceedingJoinPoint joinPoint, HttpServletRequest request, Date now, Object[] args) {
        String requestParam = "";
        if (args.length >=1 && !(args[0] instanceof MultipartFile) && !(args[0] instanceof HttpServletResponse)) {
            try {
                requestParam = JSONObject.toJSONString(args[0]);
            } catch (Exception e) {
                log.warn("ApiAspect - requestParam 序列化失败: {}", e.getMessage());
            }
        }

        Map<String, String> desc = getControllerMethodDescription(joinPoint);

        return LogVo.builder()
                .systemModule(desc.get(SYSTEM_MODEL))
                .type(desc.get(OPT_TYPE))
                .status(SUCCESS)
                .date(now)
                .createTime(now)
                .requestUrl(request.getRequestURI())
                .requestMethod(request.getMethod())
                .requestParam(requestParam)
                .organizationId(threadLocalUtil.getOrganizationId())
                .requestIp(threadLocalUtil.getIpAddr())
                .requestPort(threadLocalUtil.getIpPortr())
                .build();
    }

    /**
     * 获取接口描述信息
     */
    private static Map<String, String> getControllerMethodDescription(ProceedingJoinPoint joinPoint) {
        Map<String, String> map = new HashMap<>();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
        if (apiOperation != null) {
            map.put(OPT_TYPE, apiOperation.value());
        }

        Class<?> targetClass = signature.getDeclaringType();
        Api api = targetClass.getAnnotation(Api.class);
        if (api != null && api.tags().length > 0) {
            map.put(SYSTEM_MODEL, api.tags()[0]);
        }

        return map;
    }

    /**
     * 入参校验
     */
    private void apiRequestAspect(Object obj) {
        if (obj == null) return;
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            filedCheck(field, obj);
        }
    }

    /**
     * 单个属性校验
     */
    private void filedCheck(Field field, Object obj) {
        if (!field.isAnnotationPresent(ApiModelPropertyCheck.class)) return;

        ApiModelPropertyCheck check = field.getDeclaredAnnotation(ApiModelPropertyCheck.class);
        String name = check.value();
        field.setAccessible(true);
        Object value = null;
        try {
            value = field.get(obj);
        } catch (Exception e) {
            log.error("ApiAspect - 获取字段值失败: {}", e.getMessage());
        }

        if (check.required() && (value == null || String.valueOf(value).isEmpty())) {
            throw new BaseException(name + "不能为空");
        }

        if (value != null) {
            int size = (value instanceof List) ? ((List<?>) value).stream().mapToInt(v -> String.valueOf(v).length()).max().orElse(0) : String.valueOf(value).length();

            if (check.length() != 0 && size != check.length()) {
                throw new BaseException(name + "长度必须是" + check.length() + "位");
            }

            if (check.min() != 0 && size < check.min()) {
                throw new BaseException(name + "长度不能小于" + check.min() + "位");
            }
            if (check.max() != 0 && size > check.max()) {
                throw new BaseException(name + "长度不能大于" + check.max() + "位");
            }
        }
    }


    /**
     * 组装json
     */
    private String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new BaseException("对象转JSON失败");
        }
    }

}


