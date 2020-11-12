package com.lifevision.HelloSewa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lifevision.HelloSewa.request.BrandCreationRequest;
import com.lifevision.HelloSewa.response.BrandResponse;
import com.lifevision.HelloSewa.service.BrandService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/api/v1/brand")
public class BrandController {
	
	@Autowired
	BrandService brandService;
	
	@GetMapping("/all")
	@ApiOperation(
			value = "API to get Brands",
			notes = "There are different brands that are provided by the API",
			response = BrandResponse.class,		
			responseContainer = "List")
	public ResponseEntity<Object> getBrands(){
		List<BrandResponse> response = brandService.getBrands();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/add")
	@ApiOperation(
			value = "API to post Brands",
			notes = "This API is used for adding Brands.")
	public ResponseEntity<Object> addBrands(@RequestHeader Long loginId,
			@RequestHeader String token, @RequestBody BrandCreationRequest request){
		brandService.addBrands(loginId, token, request);
		return new ResponseEntity<Object>("Brand added Sucessfully.", HttpStatus.OK);
	}
}
