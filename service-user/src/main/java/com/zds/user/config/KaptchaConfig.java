package com.zds.user.config;

import com.google.code.kaptcha.util.Config;
import com.zds.user.util.TranslucentKaptcha;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * 谷歌验证码工具配置
 */
@Component
public class KaptchaConfig {

    @Bean
    public TranslucentKaptcha getDDefaultKaptcha() {
        TranslucentKaptcha dk = new TranslucentKaptcha();
        Properties properties = new Properties();
        // 图片边框
        properties.setProperty("kaptcha.border", "no");
        // 边框颜色
        properties.setProperty("kaptcha.border.color", "105,179,90");
        // 干扰线
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
        // 字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "white");
        // 图片宽
        properties.setProperty("kaptcha.image.width", "138");
        // 图片高
        properties.setProperty("kaptcha.image.height", "40");
        // 字体大小
        properties.setProperty("kaptcha.textproducer.font.size", "26");
        // 字体间隔
        properties.setProperty("kaptcha.textproducer.char.space", "3");
        // session key
        properties.setProperty("kaptcha.session.key", "code");
        // 验证码长度
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        // 字体
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        // 样式自定义
        properties.setProperty("kaptcha.obscurificator.impl","com.zds.user.config.NoWaterRipple");
        Config config = new Config(properties);
        dk.setConfig(config);
        return dk;
    }
}
