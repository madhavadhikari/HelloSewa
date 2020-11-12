package com.lifevision.HelloSewa.response;

import java.io.Serializable;
import java.util.Date;

import com.lifevision.HelloSewa.utils.LoginStatus;

public class LoginResponse implements Serializable{
	
	private Long loginId;
	private String token;
	
	public Long getLoginId() {
		return loginId;
	}
	
	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}

}
