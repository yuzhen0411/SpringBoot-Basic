package com.yilia.springboot.config;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yilia.springboot.filter.MyFilter;
import com.yilia.springboot.listener.MyListener;
import com.yilia.springboot.servlet.MyServlet;

@Configuration
public class MyServerConfig {
	
	@SuppressWarnings("rawtypes")
	@Bean
	public ServletRegistrationBean myServlet() {
		@SuppressWarnings("unchecked")
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(new MyServlet(), "/myServlet");
		registrationBean.setLoadOnStartup(1);
		return registrationBean;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean
	public FilterRegistrationBean myFilter() {
		@SuppressWarnings("rawtypes")
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new MyFilter());
		registrationBean.setUrlPatterns(Arrays.asList("/hello", "/myServlet"));
		return registrationBean;
	}
	
	@SuppressWarnings("rawtypes")
	@Bean
	public ServletListenerRegistrationBean myListener() {
		ServletListenerRegistrationBean<MyListener> registrationBean = new ServletListenerRegistrationBean<>(new MyListener());
		return registrationBean;
	}
	
	//set port=8083
	@Bean
	public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer(){
		return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
			@Override
			public void customize(ConfigurableWebServerFactory factory) {
				factory.setPort(8081);
			}
		};
	}
}
