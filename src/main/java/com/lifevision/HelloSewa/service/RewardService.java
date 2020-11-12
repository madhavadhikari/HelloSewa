package com.lifevision.HelloSewa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifevision.HelloSewa.model.Reward;
import com.lifevision.HelloSewa.repository.RewardRepository;
import com.lifevision.HelloSewa.response.RewardResponse;

@Service
public class RewardService {

	@Autowired
	RewardRepository rewardRepository;
	
	@Autowired
	CommonService commonService;
	
	/**
	 * For getting all rewards for specific user.
	 * @param loginId
	 * @param token
	 * @return List<RewardResponse>
	 */
	public List<RewardResponse> getAllRewards(Long loginId, String token) {
		
		commonService.isValidToken(loginId, token);
		
		List<RewardResponse> finalResponse = new ArrayList<>();
		
		List<Reward> rewards = rewardRepository.findByUserId(loginId);
		
		rewards.forEach(r -> {
			
			RewardResponse res = new RewardResponse();
			
			res.setId(r.getId());
			res.setCreatedDate(r.getCreatedDate());
			res.setModifiedDate(r.getModifiedDate());
			res.setUserId(r.getUserId());
			res.setPoints(r.getPoints());
			res.setProductId(r.getProductId());
			finalResponse.add(res);
		});
		return finalResponse;
	}

}
