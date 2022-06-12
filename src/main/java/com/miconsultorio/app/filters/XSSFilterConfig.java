package com.miconsultorio.app.filters;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * @author jxwen
 * @create 2018-10-19 9:14
 * @desc
 **/
@Configuration
public class XSSFilterConfig {
    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(xssFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("xssFilter");
        return registration;
    }

   /**
     * Create a bean
     * @return
     */
    @Bean(name = "xssFilter")
    public Filter xssFilter() {
        return new XssFilter();
    }

}