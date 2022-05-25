package com.yilia.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.yilia.springboot.component.LoginHandlerInterceptor;
import com.yilia.springboot.component.MyLocaleResolver;


@Configuration
public class MyMvcConfig implements WebMvcConfigurer{
	
	@Bean
	public WebMvcConfigurer webMvcConfigurer() {
		WebMvcConfigurer configurer = new WebMvcConfigurer() {
			
			//進行頁面跳轉，SpringMVC，需寫Controller類及寫方法；SpringBoot則只要重寫並使用此方法即可
			@Override
			public void addViewControllers(ViewControllerRegistry registry) {
				registry.addViewController("/").setViewName("login");
				registry.addViewController("/index.html").setViewName("login");
				registry.addViewController("/main.html").setViewName("dashboard");
			}
			
			//攔截器LoginHandlerInterceptor，攔截除了登入頁面及其他靜態資源以外的請求路徑
			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").
						excludePathPatterns("/index.html", "/", "/asserts/**", "/webjars/**", "/user/login");
			}
		};
		return configurer;
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		return new MyLocaleResolver();
	}
	
}
