package com.zds.file.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig {

    /**
     * 是否开启API文档显示
     */
    @Value("${swagger.show}")
    private boolean swaggerShow;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder().title("通用-文件服务").version("1.0").build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zds.file"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(getParameter())
                .enable(swaggerShow);
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
}
