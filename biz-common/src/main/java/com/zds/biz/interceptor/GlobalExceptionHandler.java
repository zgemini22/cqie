package com.zds.biz.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zds.biz.constant.BaseException;
import com.zds.biz.constant.ResultValueEnum;
import com.zds.biz.util.CryptoUtils;
import com.zds.biz.util.RequestTypeChecker;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.DataVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * 全局异常拦截器
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @ExceptionHandler({BaseException.class})
    public BaseResult<String> baseException(BaseException ex)  {
        String msg = ex.getMessage();
        StackTraceElement[] stackTrace = ex.getStackTrace();
        StringBuilder log_msg = new StringBuilder();
        log_msg.append(msg).append("\n");
        for (StackTraceElement element : stackTrace) {
            String item = element.toString();
            if (item.contains("com.zds.")) {
                log_msg.append(item).append("\n");
            }
        }
        log.error(log_msg.toString());
        ResultValueEnum resultValueEnum = (ResultValueEnum) ex.getBizExceptionEnum();
        if (resultValueEnum == null) {
            resultValueEnum = ResultValueEnum.SYS_FAILURE;
        }
        BaseResult<String> result = BaseResult.newInstance(resultValueEnum);
        if (msg != null) {
            result.setMsg(msg);
        }
        // 需要加密
        if(!RequestTypeChecker.isExcludedRequest()){
            try {
                String json = JSONObject.toJSONString(result);
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                HttpServletResponse response = attributes.getResponse();
                if (response != null) {
                    response.setContentType("application/json;charset=" + StandardCharsets.UTF_8.name());
                    PrintWriter out = response.getWriter();
                    out.write(toJson(DataVo.builder()
//                            .data(CryptoUtils.encrypt(json))
                            .data(json)
                            .build()));
                    out.flush();
                }
                return null;
            }catch (Throwable e){
                return result;
            }
        }
        return result;
    }

    private String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new BaseException("对象转JSON失败");
        }
    }
}
