package com.lifevision.HelloSewa.request;

import java.io.Serializable;
import java.util.Date;

import com.lifevision.HelloSewa.utils.Gender;

public class UserEditRequest implements Serializable{
	
	private String fullName;
	private Date dob;
	private String email;
	private Gender gender;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getDob() {
		return dob;
	}
	
	public void setDob(Date dob) {
		this.dob = dob;
	}
	
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getEmail() {
	return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
}