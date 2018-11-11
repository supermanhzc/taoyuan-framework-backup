package com.taoyuan.framework.common.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.text.DateFormat;

/**
 * 暂时使用springboot自带日期格式处理，此类屏蔽，否则会冲突
 */
@Slf4j
//@Configuration
public class TyDateConfig {

    @Autowired
    private Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder;

    @Bean
    public MappingJackson2HttpMessageConverter MappingJsonpHttpMessageConverter() {
        log.info("开始注入日期转换bean");
        ObjectMapper mapper = jackson2ObjectMapperBuilder.build();

        // ObjectMapper为了保障线程安全性，里面的配置类都是一个不可变的对象
        // 所以这里的setDateFormat的内部原理其实是创建了一个新的配置类
        DateFormat dateFormat = mapper.getDateFormat();
        mapper.setDateFormat(new TyDateFormat(dateFormat));

        MappingJackson2HttpMessageConverter mappingJsonpHttpMessageConverter = new MappingJackson2HttpMessageConverter(
                mapper);
        return mappingJsonpHttpMessageConverter;
    }
}
