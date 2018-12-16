package com.taoyuan.framework.bs.mail.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 短信配置类
 * 
 * @author submail
 *
 */
@Configuration
//@PropertySource("classpath:application.properties")
//@ConfigurationProperties(prefix = "")
//@Component
@Data
public class MessageConfig extends AppConfig {

    public static final String APP_ID = "msg_appid";
    public static final String APP_KEY = "msg_appkey";
    public static final String APP_SIGNTYPE = "msg_signtype";

	@Value("${msg_appid}")
	private String appid;

	@Value("${msg_appkey}")
	private String appkey;

	@Value("${msg_signtype}")
	private String signtype;
}
