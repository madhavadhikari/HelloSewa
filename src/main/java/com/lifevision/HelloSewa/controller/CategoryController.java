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

import com.lifevision.HelloSewa.request.CategoryCreationRequest;
import com.lifevision.HelloSewa.response.CategoryResponse;
import com.lifevision.HelloSewa.response.MainCategoryResponse;
import com.lifevision.HelloSewa.service.CategoryService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/api/v1/category")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;

	@PostMapping("/add")
	@ApiOperation(
			value = "API to posting categories.",
			notes = "This api is for posting categories.")
	public ResponseEntity<Object> addCategory(@RequestHeader Long loginId,
			@RequestHeader String token, @RequestBody CategoryCreationRequest  request){
		categoryService.addCategory(loginId, token, request);
		return new ResponseEntity<>("Category added.", HttpStatus.OK);
	}

	@GetMapping("/all/main-categories")
	@ApiOperation(
			value = "API to getting main categories.",
			notes = "This api is for getting only main categories.")
	public ResponseEntity<Object> getMainCategories(){
		List<MainCategoryResponse> response = categoryService.getMainCategories();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	@ApiOperation(
			value = "API to get all Categories and sub-categories.",
			notes = "There are three level categories. All three level categories are provided by the API.",
			response = CategoryResponse.class,
			responseContainer = "List")
	public ResponseEntity<Object> getCategories(){
		List<CategoryResponse> response = categoryService.getCategories();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
