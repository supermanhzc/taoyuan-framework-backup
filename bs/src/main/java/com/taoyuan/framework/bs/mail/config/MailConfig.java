package com.taoyuan.framework.bs.mail.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 邮件配置类
 *
 * @author submail
 */
@Configuration
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "")
@Component
@Data
public class MailConfig extends AppConfig {

    public static final String APP_ID = "mail_appid";
    public static final String APP_KEY = "mail_appkey";
    public static final String APP_SIGNTYPE = "mail_signtype";
    @Value("${mail_appid}")
    private String appid;

    @Value("${mail_appkey}")
    private String appkey;

    @Value("${mail_signtype}")
    private String signtype;
}
