package com.lifevision.HelloSewa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lifevision.HelloSewa.request.ChangePasswordRequest;
import com.lifevision.HelloSewa.request.PhoneEditRequest;
import com.lifevision.HelloSewa.request.UserLoginRequest;
import com.lifevision.HelloSewa.response.LoginResponse;
import com.lifevision.HelloSewa.service.LoginService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/api/v1/login")
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@PostMapping
	@ApiOperation(
			value = "API to Login.",
			notes = "This api is for login.",
			response = LoginResponse.class)
	public ResponseEntity<Object> login(@RequestBody UserLoginRequest userLoginRequest){
		LoginResponse loginResponse = loginService.userLogin(userLoginRequest);
		return new ResponseEntity<Object>(loginResponse, HttpStatus.OK);	
	}
	
	
	@PutMapping("/logout")
	@ApiOperation(
			value = "API to Logout.",
			notes = "This api is for logout.")
	public ResponseEntity<Object> logout(@RequestHeader Long loginId, @RequestHeader String token){
		loginService.userLogout(loginId, token);
		return new ResponseEntity<Object>("Logout", HttpStatus.OK);
	}
	
	@PutMapping("/changepassword")
	@ApiOperation(
			value = "API to password change.",
			notes = "This api is for password change.")
	public ResponseEntity<Object> changePassword(@RequestHeader Long loginId, @RequestHeader String token,
		@RequestBody ChangePasswordRequest request){
		loginService.changePassword(loginId, token, request);
		return new ResponseEntity<Object>("Password sucessfully changed.",HttpStatus.OK);
	}
	
	@PutMapping("/changePhone")
	@ApiOperation(
			value = "API to change password for user.",
			notes = "This api is for change password for user.")
	public ResponseEntity<Object> changePhone(@RequestHeader Long userId, @RequestHeader String token,
		@RequestBody PhoneEditRequest PhoneEditRequest){
		loginService.changePhone(userId, token, PhoneEditRequest);
		return new ResponseEntity<Object>("Phone number suseccfully changed", HttpStatus.OK);
	}
}

