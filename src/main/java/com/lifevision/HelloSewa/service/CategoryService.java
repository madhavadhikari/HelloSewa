package com.lifevision.HelloSewa.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.lifevision.HelloSewa.model.Category;
import com.lifevision.HelloSewa.repository.CategoryRepository;
import com.lifevision.HelloSewa.request.CategoryCreationRequest;
import com.lifevision.HelloSewa.response.CategoryResponse;
import com.lifevision.HelloSewa.response.MainCategoryResponse;
import com.lifevision.HelloSewa.response.SubCategoryResponse;
import com.lifevision.HelloSewa.response.SubSubCategoryResponse;
import com.lifevision.HelloSewa.response.SubSubSubCategoryResponse;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	CommonService commonService;

	@Autowired
	private Environment environment;
	
	private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

	/**
	 * API for Posting Category.
	 * @param loginId
	 * @param token
	 * @param request
	 */
	public void addCategory(Long loginId, String token, CategoryCreationRequest request) {

		commonService.isValidToken(loginId, token);
		
		Category category = new Category();
		category.setName(request.getName());
		category.setParentCategoryId(request.getParentCategoryId());
		
		if(!request.getImage().equals(null) || !request.getImage().isEmpty() || !request.getImage().equals("")) {
			category.setImage(commonService.uploadImage(
					request.getImage(), 
					commonService.generateImageName(), 
					environment.getProperty("image.directory.icon")));
		}
		LOG.info("Sucessfully added.");
		categoryRepository.save(category);
	}


	
	/**
	 * Get main categories.
	 * @return List<MainCategoryResponse>
	 */
	public List<MainCategoryResponse> getMainCategories() {
		List<MainCategoryResponse> mainCategoryResponse = new ArrayList<>();
		List<Category> mainCategory = categoryRepository.findByParentCategoryId(0);
		
		mainCategory.forEach( mC -> {
			
			MainCategoryResponse temp = new MainCategoryResponse();
			
			temp.setId(mC.getId());
			temp.setImage(mC.getImage());
			temp.setName(mC.getName());
			mainCategoryResponse.add(temp);
		});
		return mainCategoryResponse;
	}

	/**
	 * Returns all level category.
	 * @return List<CategoryResponse>
	 */
	public List<CategoryResponse> getCategories() {
		List<CategoryResponse> finalResponse = new ArrayList<>();
		
		List<Category> mainCategory = categoryRepository.findByParentCategoryId(0);
		
		mainCategory.forEach( mC -> {
			
			CategoryResponse temp = new CategoryResponse();
			
			temp.setId(mC.getId());
			temp.setParentCategoryId(mC.getParentCategoryId());
			temp.setParentCategoryId(0);
			temp.setImage(mC.getImage());
			temp.setName(mC.getName());
			
			temp.setSubCategoryResponseList(getSubCategoryResponseListOf(mC.getId()));
			
			finalResponse.add(temp);
		});
		return finalResponse;
	}

	private List<SubCategoryResponse> getSubCategoryResponseListOf(int id) {
		List<SubCategoryResponse> subResponseList = new ArrayList<>();
		List<Category> subCategory = categoryRepository.findByParentCategoryId(id);
		
		subCategory.forEach(sC ->{
			SubCategoryResponse subResponse = new SubCategoryResponse();
			subResponse.setId(sC.getId());
			subResponse.setImage(sC.getImage());
			subResponse.setParentCategoryId(sC.getParentCategoryId());
			subResponse.setName(sC.getName());
			subResponse.setSubSubCategoryResponseList(getSubSubCategoryResponseListOf(sC.getId()));
			
			subResponseList.add(subResponse);
		});
		
		return subResponseList;
	}

	private List<SubSubCategoryResponse> getSubSubCategoryResponseListOf(int id) {
		List<SubSubCategoryResponse> subSubResponseList = new ArrayList<>();
		
		List<Category> subSubCategoryList = categoryRepository.findByParentCategoryId(id);
		
		subSubCategoryList.forEach(ssC ->{
			
			SubSubCategoryResponse subSubResponse = new SubSubCategoryResponse();
			subSubResponse.setId(ssC.getId());
			subSubResponse.setImage(ssC.getImage());
			subSubResponse.setParentCategoryId(ssC.getParentCategoryId());
			subSubResponse.setName(ssC.getName());
			subSubResponse.setSubSubSubCategoryResponse(getSubSubSubCategoryResponseListOf(ssC.getId()));
			
			subSubResponseList.add(subSubResponse);
		});
		return subSubResponseList;
	}

	private List<SubSubSubCategoryResponse> getSubSubSubCategoryResponseListOf(int id) {
		List<SubSubSubCategoryResponse> subSubSubResponseList = new ArrayList<>();
		
		List<Category> subSubSubCategoryList = categoryRepository.findByParentCategoryId(id);
		
		subSubSubCategoryList.forEach(sssC ->{
			
			SubSubSubCategoryResponse subSubSubResponse = new SubSubSubCategoryResponse();
			subSubSubResponse.setId(sssC.getId());
			subSubSubResponse.setImage(sssC.getImage());
			subSubSubResponse.setParentCategoryId(sssC.getParentCategoryId());
			subSubSubResponse.setName(sssC.getName());
			 
			subSubSubResponseList.add(subSubSubResponse);
		});
		return subSubSubResponseList;
	}
}