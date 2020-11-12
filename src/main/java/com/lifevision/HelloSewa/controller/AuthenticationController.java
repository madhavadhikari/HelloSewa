package com.lifevision.HelloSewa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lifevision.HelloSewa.response.AuthenticationResponse;
import com.lifevision.HelloSewa.service.CommonService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/api/v1/user")
public class AuthenticationController {
	
	@Autowired
	CommonService commonService; 
	
	@PostMapping("/authentication")
	@ApiOperation(
		value = "Api to validate token",
		notes = "This api gives the token is validate or not.")
	public ResponseEntity<Object> isValidToken(@RequestHeader Long loginId, @RequestHeader String token){
		AuthenticationResponse res = commonService.isValidToken(loginId, token);
		return new ResponseEntity<Object>(res, HttpStatus.OK);
	}
}
