package com.lifevision.HelloSewa.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.lifevision.HelloSewa.utils.RandomStringGenerator;

@Entity
public class UserConfirmation extends AbstractEntity{
	
	private String confirmationCode;
	
	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id")
	private User user;
	
	public UserConfirmation() {	
	}
	
	public UserConfirmation(User user) {
		this.user = user;
		new RandomStringGenerator();
		confirmationCode = RandomStringGenerator.randomString(5);
	}

	public String getConfirmationCode() {
		return confirmationCode;
	}

	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
