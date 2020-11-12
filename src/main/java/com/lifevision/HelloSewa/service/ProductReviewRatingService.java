package com.lifevision.HelloSewa.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifevision.HelloSewa.model.Brand;
import com.lifevision.HelloSewa.model.Product;
import com.lifevision.HelloSewa.model.ProductReviewRating;
import com.lifevision.HelloSewa.model.User;
import com.lifevision.HelloSewa.repository.BrandRepository;
import com.lifevision.HelloSewa.repository.ProductRepository;
import com.lifevision.HelloSewa.repository.ProductReviewRatingRepository;
import com.lifevision.HelloSewa.repository.UserRepository;
import com.lifevision.HelloSewa.request.ProductReviewRatingCreationRequest;
import com.lifevision.HelloSewa.response.ProductReviewRatingRes;
import com.lifevision.HelloSewa.response.ProductReviewRatingResponse;
import com.lifevision.HelloSewa.response.RatingResponse;
import com.lifevision.HelloSewa.utils.ProductReviewRatingStatus;
import com.lifevision.HelloSewa.utils.ProductStatus;

@Service
public class ProductReviewRatingService {
	
	@Autowired
	ProductReviewRatingRepository productReviewRatingRepository;

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	BrandRepository brandRepository;
	
	@Autowired
	UserRepository userRepository;
	
	/**
	 * API for giving review and rating for product.
	 * @param loginId
	 * @param token
	 * @param productId
	 * @param request
	 */
	public void addProductReviewRating(Long loginId, String token, Long productId,
			ProductReviewRatingCreationRequest request) {
		
		commonService.isValidToken(loginId, token);
		
		ProductReviewRating productReviewRating = new ProductReviewRating();
		
		productReviewRating.setCreatedDate(new Date());
		productReviewRating.setReviewBy(loginId);
		productReviewRating.setReview(request.getReview());
		productReviewRating.setProductId(productId);
		productReviewRating.setRating(request.getRating());
		productReviewRating.setStatus(ProductReviewRatingStatus.ACTIVE);
		productReviewRatingRepository.save(productReviewRating);
		
	}
	
    /**
     * API for removing rating and review.
     * @param loginId
     * @param token
     * @param productReviewRatingId
     */
	public void removeProductReviewRating(Long loginId, String token, Long productReviewRatingId) {
		
		commonService.isValidToken(loginId, token);

		ProductReviewRating productReviewRating = productReviewRatingRepository.getOne(productReviewRatingId);
		
		productReviewRating.setStatus(ProductReviewRatingStatus.REMOVED);
		productReviewRating.setModifiedDate(new Date());
		productReviewRatingRepository.save(productReviewRating);
	}
	
	/**
	* API for getting all reviews and ratings of users.
	* @param loginId
	* @param token
	* @return List<ProductReviewRatingResponse>
	*/
	public List<ProductReviewRatingResponse> getAllProductReviewRating(Long loginId, String token) {
		
		commonService.isValidToken(loginId, token);

		List<ProductReviewRating> productReviewRatings = productReviewRatingRepository.findByReviewByAndStatusNot(loginId, ProductReviewRatingStatus.REMOVED);
		List<ProductReviewRatingResponse> finalResponse = new ArrayList<>();

		productReviewRatings.forEach(p -> {

		ProductReviewRatingResponse res = new ProductReviewRatingResponse();
		Product product = productRepository.findByIdAndStatusNot(p.getProductId(), ProductStatus.REMOVED);
	
		res.setId(p.getId());
		res.setDescription(product.getDescription());

		Brand brand = brandRepository.getOne(product.getBrandId());
		res.setBrandName(brand.getName());

		res.setReview(p.getReview());
		res.setRating(p.getRating());
		res.setReviewDate(p.getCreatedDate());
		res.setName(product.getName());
		res.setThumbnail(product.getThumbnail());
		res.setProductId(p.getProductId());
		res.setStatus(p.getStatus());
		res.setReviewBy(loginId);
		finalResponse.add(res);
		});

		return finalResponse;
	}
	
	/**
	 * API for getting review and rating for particular product.
	 * @param productId
	 * @return ProductReviewRatingRes
	 */
	public ProductReviewRatingRes getReviewRatingForProduct(Long productId) {

		List<ProductReviewRating> results = productReviewRatingRepository.findByProductId(productId);
		
		ProductReviewRatingRes finalResponse = new ProductReviewRatingRes();
		
		finalResponse.setAverageRating(calculateAverage(productId));
		
		List<RatingResponse> ratingResponseList = new ArrayList<>();
		
		results.forEach(r -> {
			
			RatingResponse response = new RatingResponse();
			
			response.setId(r.getId());
			response.setRating(r.getRating());
			response.setReview(r.getReview());
			
			User user = userRepository.getOne(r.getReviewBy());
			response.setUserPic(user.getProfilePicture());
			
			response.setReviewDate(r.getCreatedDate());
			response.setReviewBy(user.getFullName());
			ratingResponseList.add(response);
		});
		
		finalResponse.setRatingResponse(ratingResponseList);
		return finalResponse;
			
	}
	
	/**
	 * for calculating average rating.
	 */
	public float calculateAverage(Long productId) {
		
		List<ProductReviewRating> results = productReviewRatingRepository.findByProductId(productId);
		int number = results.size();
		float average = 0;
		
		for(ProductReviewRating p : results){
			
			average = average + p.getRating();
			
		}
		return average/number;
		
	}

}
