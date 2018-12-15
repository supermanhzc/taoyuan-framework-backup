package com.taoyuan.framework.bs.mail.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 手机流量配置文件
 * 
 * @author submail
 *
 */
@Configuration
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "")
@Data
public class MobiledataConfig extends AppConfig {
	public static final String APP_ID = "mobiledata_appid";
	public static final String APP_KEY = "mobiledata_appkey";
	public static final String APP_SIGNTYPE = "mobiledata_signtype";

	@Value("${mobiledata_appid}")
	private String appid;

	@Value("${mobiledata_appkey}")
	private String appkey;

	@Value("${mobiledata_signtype}")
	private String signtype;

}
