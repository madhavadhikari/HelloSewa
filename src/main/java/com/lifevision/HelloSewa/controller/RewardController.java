package com.lifevision.HelloSewa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lifevision.HelloSewa.response.RewardResponse;
import com.lifevision.HelloSewa.service.RewardService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("api/v1/rewards")
public class RewardController {
	
	@Autowired
	RewardService rewardService;
	
	@GetMapping("/getAllRewards")
	@ApiOperation(
			value = "API for getting all rewards.", 
			notes = "This api is for get all rewards.",
			response = RewardResponse.class,
			responseContainer = "List")
	public ResponseEntity<Object> getAllRewards(@RequestHeader Long loginId, @RequestHeader String token){
		List<RewardResponse> response = rewardService.getAllRewards(loginId, token);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}	
}
