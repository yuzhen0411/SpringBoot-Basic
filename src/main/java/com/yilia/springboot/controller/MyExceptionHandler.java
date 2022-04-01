package com.yilia.springboot.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yilia.springboot.exception.UserNotExistException;

@ControllerAdvice
public class MyExceptionHandler {
	
//	@ResponseBody
//	@ExceptionHandler(UserNotExistException.class)
//	public Map<String, Object> handleException(Exception e){
//		Map<String, Object> map = new HashMap<>();
//		map.put("code", "user.notexist");
//		map.put("message", e.getMessage());
//		return map;
//	}
	
	@ExceptionHandler(UserNotExistException.class)
	public String handleException(Exception e, HttpServletRequest request){
		Map<String, Object> map = new HashMap<>();
		request.setAttribute("javax.servlet.error.status_code", 500);
		map.put("code", "user.notexist");
		map.put("message", "使用者名稱錯誤!");
		
		request.setAttribute("ext", map);
		return "forward:/error";
	}
}
