package com.yilia.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.yilia.springboot.component.MyLocaleResolver;


//@EnableWebMvc
@Configuration
public class MyMvcConfig implements WebMvcConfigurer{
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// TODO Auto-generated method stub
//		super.addViewControllers(registry);
		registry.addViewController("/yilia").setViewName("success");
	}
	
	@Bean
	public WebMvcConfigurer webMvcConfigurer() {
		WebMvcConfigurer configurer = new WebMvcConfigurer() {
			@Override
			public void addViewControllers(ViewControllerRegistry registry) {
				// TODO Auto-generated method stub
//				WebMvcConfigurer.super.addViewControllers(registry);
				registry.addViewController("/").setViewName("login");
				registry.addViewController("/index.html").setViewName("login");
				registry.addViewController("/main.html").setViewName("dashboard");
			}
			
			@Override
			public void addInterceptors(InterceptorRegistry registry) {
//				registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").
//						excludePathPatterns("/index.html", "/", "/asserts/**", "/webjars/**", "/user/login");
			}
		};
		return configurer;
	}
	@Bean
	public LocaleResolver localeResolver() {
		return new MyLocaleResolver();
	}
	
	
}
