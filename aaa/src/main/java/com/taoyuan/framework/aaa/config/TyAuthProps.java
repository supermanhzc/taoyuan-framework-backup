package com.taoyuan.framework.aaa.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix="taoyuan")
public class TyAuthProps {

    private List<String> unAuthUrls;
    private List<String> permissions;

}
