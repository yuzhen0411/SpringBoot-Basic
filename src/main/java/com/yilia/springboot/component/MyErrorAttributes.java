package com.yilia.springboot.component;

import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

@Component
public class MyErrorAttributes extends DefaultErrorAttributes{
	
	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions includeStackTrace) {
		Map<String, Object> map = super.getErrorAttributes(webRequest, includeStackTrace);
		map.put("person", "Yilia");
		
		@SuppressWarnings("unchecked")
		Map<String, Object> ext = (Map<String, Object>)webRequest.getAttribute("ext", 0);
		map.put("ext", ext);
		return map;
	}
}
