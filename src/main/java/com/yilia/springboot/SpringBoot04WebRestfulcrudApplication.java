package com.yilia.springboot;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;

@SpringBootApplication
public class SpringBoot04WebRestfulcrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBoot04WebRestfulcrudApplication.class, args);
	}
	
	@Bean
	public ViewResolver myViewResolver() {
		return new MyViewResolver();
	}
	
	public static class MyViewResolver implements ViewResolver{

		@Override
		public org.springframework.web.servlet.View resolveViewName(String viewName, Locale locale) throws Exception {
			return null;
		}
		
	}
}
