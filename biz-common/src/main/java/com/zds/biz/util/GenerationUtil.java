package com.zds.biz.util;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

public class GenerationUtil {

    /**
     * po,mapper快捷生成
     */
    public static void quickGeneration(GenerateNeed vo) {
        //获取目录
        String projectUrl = System.getProperty("user.dir");
        //模块名
        String moduleName = vo.getModuleName();
        String src = moduleName + "\\src\\main\\java\\";
        String[] split = moduleName.split("-");
        String pageName = split[1];
        //填写数据库信息
        FastAutoGenerator.create("jdbc:mysql://" + vo.getIp() + ":3306/" + vo.getDataBaseName() + "?autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=GMT%2B8&allowMultiQueries=true", vo.getUserName(), vo.getUserPassword())
                .globalConfig(builder -> {
                    builder.enableSwagger() // 开启 swagger 模式
                            .dateType(DateType.ONLY_DATE)
                            .disableOpenDir()
                            .outputDir(projectUrl + "\\" + src); // 指定输出目录
                })
                //填写生成包信息
                .packageConfig(builder -> {
                    builder.parent("com.zds")
                            .entity("po")
                            .mapper("dao")
                            .xml("mapper.xml")
                            // 设置父包名
                            .moduleName(pageName) // 设置父包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, moduleName + "\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .templateConfig(builder -> {
                    builder.disable(TemplateType.SERVICE, TemplateType.SERVICEIMPL, TemplateType.CONTROLLER); // 不生成service等
                })
                //开启lombok
                .strategyConfig(builder -> {
                    builder.entityBuilder().fileOverride().enableLombok()
                            .mapperBuilder()
                            .convertMapperFileName(entityName -> entityName + "Dao")
                            .convertXmlFileName(entityName -> entityName + "Dao");
                    if (vo.getTablePrefixs() != null && vo.getTablePrefixs().size() > 0) {
                        for (String tablePrefix : vo.getTablePrefixs()) {
                            builder.addExclude("^" + tablePrefix + ".*"); // 增加表排除匹配
                        }
                    }
                    if (vo.getCtablePrefixs() != null && vo.getCtablePrefixs().size() > 0) {
                        for (String tablePrefix : vo.getCtablePrefixs()) {
                            builder.addInclude(tablePrefix); // 增加表匹配
                        }
                    }
                })
                //自定义生成
                .templateConfig(builder -> {
                    // 实体类使用我们自定义模板 -- 模板位置
                    builder.entity("templates/myEntity.java")
                            .mapper("templates/myMapper.java");
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    @ApiModel(description = "生成实体需要的字段")
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GenerateNeed {

        @ApiModelPropertyCheck(value = "模块名", example = "service-file", required = true)
        private String moduleName;

        @ApiModelPropertyCheck(value = "ip地址", example = "192.168.39.6", required = true)
        private String ip;

        @ApiModelPropertyCheck(value = "数据库名", required = true)
        private String dataBaseName;

        @ApiModelPropertyCheck(value = "数据库用户名", required = true)
        private String userName;

        @ApiModelPropertyCheck(value = "数据库密码", required = true)
        private String userPassword;

        @ApiModelPropertyCheck(value = "不生成的表的前缀集合")
        private List<String> tablePrefixs;

        @ApiModelPropertyCheck(value = "生成的表的前缀集合")
        private List<String> ctablePrefixs;
    }

    public static void main(String[] args) {
        GenerationUtil.GenerateNeed generateNeed = GenerateNeed.builder().moduleName("service-user").ip("192.168.39.6").userName("root").userPassword("123456").dataBaseName("edu_data_test").build();
        GenerationUtil.quickGeneration(generateNeed);
    }
}
