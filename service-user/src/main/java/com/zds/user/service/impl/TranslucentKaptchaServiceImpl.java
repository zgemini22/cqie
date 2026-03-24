package com.zds.user.service.impl;

import com.google.code.kaptcha.Producer;
import com.zds.biz.constant.BaseException;
import com.zds.user.service.TranslucentKaptchaService;
import com.zds.user.util.SafeHashGenerator;
import com.zds.user.util.TranslucentKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.TimeUnit;

@Service
public class TranslucentKaptchaServiceImpl implements TranslucentKaptchaService {

    @Autowired
    private TranslucentKaptcha translucentKaptcha;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private String code_prefix = "LC_";

    @Override
    public void getLoginCode(HttpServletResponse response) {
        try {
            // 将生成的验证码保存在session中
            String createText = translucentKaptcha.createText();
            String rightCode = SafeHashGenerator.getStretchedText(createText);
            //存在redis就返回
            Boolean flag = redisTemplate.hasKey(code_prefix + rightCode);
            if (flag) {
                getLoginCode(response);
            }
            redisTemplate.opsForValue().set(code_prefix + rightCode, rightCode, 3, TimeUnit.MINUTES);
            Producer kap1 = translucentKaptcha.translucentKaptchaByModified(translucentKaptcha.getConfig());
            BufferedImage bi = kap1.createImage(createText);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(bi, "png", out);
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("rightCode", rightCode);
            response.setDateHeader("Expires", 0);
            response.setContentType("image/png");
            ServletOutputStream sout = response.getOutputStream();
            sout.write(out.toByteArray());
            sout.flush();
            sout.close();
        } catch (Exception e) {
            throw new BaseException("生成验证码失败");
        }
    }

    @Override
    public boolean isHavLoginCode(String rightCode) {
        return redisTemplate.hasKey(code_prefix + rightCode);
    }

    @Override
    public boolean deleteLoginCode(String rightCode) {
        return redisTemplate.delete(code_prefix + rightCode);
    }
}
