package com.yilia.springboot.exception;

public class UserNotExistException extends RuntimeException{
	
	public UserNotExistException() {
		super("該使用者不存在");
	}
	
}
