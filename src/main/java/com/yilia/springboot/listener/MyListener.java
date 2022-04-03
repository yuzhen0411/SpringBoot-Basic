package com.yilia.springboot.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyListener implements ServletContextListener{
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("contextInitialized...web專案啟動");
	}
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("contextDestroyed...web專案銷毀");
	}

}
