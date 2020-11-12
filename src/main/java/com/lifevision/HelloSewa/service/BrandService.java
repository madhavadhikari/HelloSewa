package com.lifevision.HelloSewa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.lifevision.HelloSewa.model.Brand;
import com.lifevision.HelloSewa.repository.BrandRepository;
import com.lifevision.HelloSewa.request.BrandCreationRequest;
import com.lifevision.HelloSewa.response.BrandResponse;
import com.lifevision.HelloSewa.utils.BrandStatus;

@Service
public class BrandService {
	
	@Autowired
	BrandRepository brandRepository;

	@Autowired 
	CommonService commonService;
	
	@Autowired
	private Environment environment;
	
	/**
	 * Get all brands for our system.
	 * @return List<BrandResponse>
	 */
	public List<BrandResponse> getBrands() {
		List<Brand> brands = brandRepository.findAll();
		List<BrandResponse> response = new ArrayList<>();
		
		for(Brand b : brands) {
			BrandResponse bres = new BrandResponse();
			bres.setId(b.getId());
			bres.setName(b.getName());
			bres.setImage(b.getImage());
			response.add(bres);
		}
		return response;
	}
	
	/**
	 * Add brands for our system.
	 * @param loginId
	 * @param token
	 * @param request
	 */
	public void addBrands(Long loginId, String token, BrandCreationRequest request) {

		commonService.isValidToken(loginId, token);
		
		Brand brand = new Brand();
		brand.setName(request.getName());
		brand.setImage(commonService.uploadImage(
				request.getImage(), 
				commonService.generateImageName(),
				environment.getProperty("image.directory.icon")));
		brand.setStatus(BrandStatus.ACTIVE);
		
		brandRepository.save(brand);
	}
}
