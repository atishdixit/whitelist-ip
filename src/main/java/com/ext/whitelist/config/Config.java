package com.ext.whitelist.config;

import com.ext.whitelist.filter.WhiteListedIPFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;


@Configuration
public class Config {

    @Autowired
    private WhiteListedIPFilter whiteListedIPFilter;

    @Bean
    @Order(2)
    public FilterRegistrationBean<WhiteListedIPFilter> filterRegistrationBean() {
        FilterRegistrationBean<WhiteListedIPFilter> registrationBean
                = new FilterRegistrationBean<>();
        registrationBean.setFilter(whiteListedIPFilter);
        registrationBean.addUrlPatterns("/white-list/*");
        registrationBean.setOrder(2);

        return registrationBean;
    }
}
