package com.taoyuan.framework.bs.mail.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 国际短信配置类
 * 
 * @author submail
 *
 */
@Configuration
@PropertySource("classpath:application.properties")
@Data
public class InternationalsmsConfig extends AppConfig {

	public static final String APP_ID = "internationalsms_appid";
	public static final String APP_KEY = "internationalsms_appkey";
	public static final String APP_SIGNTYPE = "internationalsms_signtype";

	@Value("${internationalsms_appid}")
	private String appid;

	@Value("${internationalsms_appkey}")
	private String appkey;

	@Value("${internationalsms_signtype}")
	private String signtype;
}
