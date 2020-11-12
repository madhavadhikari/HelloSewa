package com.lifevision.HelloSewa.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.lifevision.HelloSewa.utils.LoginStatus;

@Entity(name = "user_login")
public class Login extends AbstractEntity{
	@NotNull
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id") 
	private User user;
	
	@NotNull
	private String password;
	
	@Lob
	private String token;
	
	private Date  tokenExpiryDate;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private LoginStatus LoginStatus;

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getTokenExpiryDate() {
		return tokenExpiryDate;
	}

	public void setTokenExpiryDate(Date tokenExpiryDate) {
		this.tokenExpiryDate = tokenExpiryDate;
	}

	public LoginStatus getLoginStatus() {
		return LoginStatus;
	}

	public void setLoginStatus(LoginStatus loginStatus) {
		LoginStatus = loginStatus;
	}
	
}
