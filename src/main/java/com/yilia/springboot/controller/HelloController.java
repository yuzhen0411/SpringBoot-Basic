package com.yilia.springboot.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
	
	@ResponseBody
	@RequestMapping("/hello")
	public String hello(){
		return "hello world";
	}
	
	@RequestMapping("/success")
	public String success(Map<String, Object> map) {
		map.put("hello","<h1>你好<h1>");
		map.put("users", Arrays.asList("zz","ll","ww"));
		//classpth:/templates/success.html
		return "success";
	}
	
}
