package com.zds.biz.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.core.Ordered;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class AfterConfigListener implements SmartApplicationListener, Ordered {

    @Override
    public boolean supportsEventType(@NotNull Class<? extends ApplicationEvent> aClass) {
        return (ApplicationEnvironmentPreparedEvent.class.isAssignableFrom(aClass) ||
                ApplicationPreparedEvent.class.isAssignableFrom(aClass));
    }

    @Override
    public void onApplicationEvent(@NotNull ApplicationEvent applicationEvent) {
        /* ApplicationEnvironmentPreparedEvent 是加载配置文件，初始化日志系统的事件。 */
        if (applicationEvent instanceof ApplicationEnvironmentPreparedEvent) {
            InetAddress localHost = null;
            try {
                localHost = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            assert localHost != null;
            String clientIp = localHost.getHostAddress();
            System.setProperty("spring.cloud.sentinel.transport.clientIp", clientIp);
        }
    }

    @Override
    public int getOrder() {
        /* 设置该监听器 在加载配置文件之后执行 */
        return (ConfigFileApplicationListener.DEFAULT_ORDER + 1);
    }
}
