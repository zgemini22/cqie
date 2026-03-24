package com.zds.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 聚合系统接口
 */
@Component
public class SwaggerProvider implements SwaggerResourcesProvider {

    /**
     * swagger2默认的url后缀
     */
    private static final String SWAGGER2URL = "/v2/api-docs";

    /**
     * 网关路由
     */
    private final RouteLocator routeLocator;

    /**
     * 网关应用名称
     */
    @Value("${spring.application.name}")
    private String self;

    @Autowired
    public SwaggerProvider(RouteLocator routeLocator) {
        this.routeLocator = routeLocator;
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<String> routeHosts = new ArrayList<>();
//        routeLocator.getRoutes().filter(route -> route.getUri().getHost() != null)
//                .filter(route -> !self.equals(route.getUri().getHost()))
//                .subscribe(route -> routeHosts.add(route.getUri().getHost()));
        routeLocator.getRoutes()
                .filter(route -> route.getUri().getHost() != null)
                .filter(route -> !self.equals(route.getUri().getHost()))
                .subscribe(route -> {
                    String host = route.getUri().getHost();
                    String targetHost = "localhost".equals(host) ? "service-user" : host;
                    routeHosts.add(targetHost);
                });
        Set<String> dealed = new HashSet<>();
        routeHosts.forEach(instance -> {
            String url = "/gasapi/" + instance + SWAGGER2URL;
            List<String> nameList = getNameList(instance);
            for (String name : nameList) {
                String apiUrl;
                if (name.contains("APP-") || name.contains("后台-") || name.contains("大屏-") || name.contains("开放-")) {
                    apiUrl = url + "?group=" + name;
                } else {
                    apiUrl = url;
                }
                if (!dealed.contains(apiUrl)) {
                    dealed.add(apiUrl);
                    SwaggerResource swaggerResource = new SwaggerResource();
                    swaggerResource.setUrl(apiUrl);
                    swaggerResource.setName(name);
                    swaggerResource.setSwaggerVersion("2.0");
                    resources.add(swaggerResource);
                }
            }
        });
        return resources.stream().distinct().sorted(Comparator.comparing(SwaggerResource::getName)).collect(Collectors.toList());
    }

    private List<String> getNameList(String instance) {
        ServiceEnum serviceName = ServiceEnum.query(instance);
        List<String> list = new ArrayList<>();
        if (serviceName == null) {
            return list;
        }
        switch (serviceName) {
            case service_user:
                list.add("APP-用户服务");
                list.add("后台-用户服务");
                list.add("大屏-用户服务");
                break;
            case service_file:
                list.add("通用-文件服务");
                break;
            case service_flow:
                list.add("通用-流程引擎服务");
                break;
            default:
                list.add(instance);
        }
        return list;
    }
}
