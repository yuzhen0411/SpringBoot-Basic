package com.yilia.springboot.component;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

public class MyLocaleResolver implements LocaleResolver{

	//透過Request URL帶的參數l為en_US或zh_TW，判斷語言環境
	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		String l = request.getParameter("l");
		Locale locale = Locale.getDefault();
		if(StringUtils.hasText(l)) {
			String[] split = l.split("_");
			locale = new Locale(split[0], split [1]);
		}
		return locale;
	}

	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
	}

}
