package com.yilia.springboot.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	
	@PostMapping(value = "/user/login")
	public String login(@RequestParam("username") String username, 
						@RequestParam("password") String password,
						Map<String, Object> map,
						HttpSession session) {
		//session存放登入的用戶
		if(StringUtils.hasText(username) && "123456".equals(password)) {
			session.setAttribute("loginUser", username);
			return "redirect:/main.html";
		}
		else {
			map.put("msg", "密碼錯誤，請重新登入");
			return "login";
		}
	}
}
