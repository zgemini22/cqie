package com.zds.user.config;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig {

    /**
     * 是否开启API文档显示
     */
    @Value("${swagger.show}")
    private boolean swaggerShow;

    /**
     * 定义分隔符
     */
    private static final String splitor = ";";

    private String serviceName = "用户服务";

    private String basePackageUrl = "com.zds.user.controller";

    @Bean
    public Docket createBackApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("后台-" + serviceName)
                .apiInfo(apiInfo())
                .select()
                .apis(basePackage(basePackageUrl + ".admin"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(getParameter())
                .enable(swaggerShow);
    }

    @Bean
    public Docket createBiApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("大屏-" + serviceName)
                .apiInfo(apiInfo())
                .select()
                .apis(basePackage(basePackageUrl + ".bi"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(getParameter())
                .enable(swaggerShow);
    }

    @Bean
    public Docket createAppApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("APP-" + serviceName)
                .apiInfo(apiInfo())
                .select()
                .apis(basePackage(basePackageUrl + ".client"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(getParameter())
                .enable(swaggerShow);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("")
                .description("")
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }

    public static List<Parameter> getParameter() {
        List<Parameter> list = new ArrayList<>();
        ParameterBuilder ticketPar = new ParameterBuilder();
        ticketPar.name("Authorization")
                .description("user token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false).build(); //header中的ticket参数非必填，传空也可以
        list.add(ticketPar.build());
        //新增是否加密标记
        ParameterBuilder ticketPar1 = new ParameterBuilder();
        ticketPar1.name("X-Request-Source")
                .description("请求来源标识")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .defaultValue("SWAGGER_UI")
                .build(); //header中的ticket参数非必填，传空也可以
        list.add(ticketPar1.build());
        return list;
    }

    public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return input -> declaringClass(input).transform(handlerPackage(basePackage)).or(true);
    }

    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
        return input -> {
            // 循环判断匹配
            for (String strPackage : basePackage.split(splitor)) {
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
    }
}
