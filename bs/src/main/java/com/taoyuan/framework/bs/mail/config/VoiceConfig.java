package com.taoyuan.framework.bs.mail.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 语音配置文件
 * 
 * @author submail
 *
 */
@Configuration
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "")
@Data
public class VoiceConfig extends AppConfig {
	public static final String APP_ID = "voice_appid";
	public static final String APP_KEY = "voice_appkey";
	public static final String APP_SIGNTYPE = "voice_signtype";

	@Value("${voice_appid}")
	private String appid;

	@Value("${voice_appkey}")
	private String appkey;

	@Value("${voice_signtype}")
	private String signtype;
}
