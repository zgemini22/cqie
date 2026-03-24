package com.zds.biz.interceptor;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class ReusableHttpServletRequestWrapper extends HttpServletRequestWrapper {
    // 用 volatile 确保多线程下的可见性（可选，视场景而定）
    private volatile byte[] body;

    public ReusableHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        // 初始化时读取原始请求体并缓存
        this.body = readOriginalBody(request);
    }

    // 读取原始请求体为字节数组
    private byte[] readOriginalBody(HttpServletRequest request) throws IOException {
        try (InputStream inputStream = request.getInputStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            return outputStream.toByteArray();
        }
    }

    // 关键：更新缓存的请求体字节数组
    public void setBody(String newBody) {
        if (newBody == null) {
            this.body = new byte[0];
        } else {
            // 将新的请求体字符串转换为字节数组（使用UTF-8编码，与读取时一致）
            this.body = newBody.getBytes(StandardCharsets.UTF_8);
        }
    }

    // 重写输入流方法：基于最新的body字节数组返回
    @Override
    public ServletInputStream getInputStream() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return byteArrayInputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {}

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
    }

    // 重写Reader方法：基于最新的body字节数组返回
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream(), StandardCharsets.UTF_8));
    }

    // 获取当前缓存的请求体字符串
    public String getBody() {
        return new String(body, StandardCharsets.UTF_8);
    }
}
