package com.zds.biz.config;

import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import springfox.documentation.schema.Annotations;
import springfox.documentation.schema.ModelProperty;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.spring.web.DescriptionResolver;
import springfox.documentation.swagger.schema.ApiModelPropertyPropertyBuilder;

import java.util.Optional;

@Configuration
@Order(-19999)
public class ApiModelPropertyPropertyBuilderConfig extends ApiModelPropertyPropertyBuilder {

    public ApiModelPropertyPropertyBuilderConfig(DescriptionResolver descriptions) {
        super(descriptions);
    }

    @Override
    public void apply(ModelPropertyContext context) {
        super.apply(context);
        Optional<ApiModelPropertyCheck> annotation = Optional.empty();
        if (context.getBeanPropertyDefinition().isPresent()) {
            annotation = (Optional)annotation.map(Optional::of).orElse(Annotations.findPropertyAnnotation((BeanPropertyDefinition)context.getBeanPropertyDefinition().get(), ApiModelPropertyCheck.class));
            if (annotation.isPresent()) {
                ApiModelPropertyCheck check = annotation.get();
                ModelProperty modelProperty = context.getBuilder().build();
                StringBuilder str = new StringBuilder();
                if (!"".equals(check.notes())) {
                    str.append(",").append(check.notes());
                }
                str.append(",限制:");
                if (check.length() > 0) {
                    str.append("长度").append(check.length()).append("位");
                } else {
                    str.append("长度").append(check.min()).append("-").append(check.max()).append("位");
                }
                String msg = str.toString().replace(",限制:长度0-0位", "");
                if (!"".equals(check.value())) {
                    context.getBuilder().description(check.value() + msg);
                } else {
                    context.getBuilder().description(modelProperty.getDescription() + msg);
                }
                context.getBuilder().required(check.required());
                context.getBuilder().isHidden(check.hidden());
                context.getBuilder().example(check.example());
                context.getBuilder().position(check.position());
            }
        }
    }
}
