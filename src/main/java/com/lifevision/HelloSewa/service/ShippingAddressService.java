package com.lifevision.HelloSewa.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifevision.HelloSewa.exception.NotFoundException;
import com.lifevision.HelloSewa.exception.ValidationException;
import com.lifevision.HelloSewa.model.ShippingAddress;
import com.lifevision.HelloSewa.repository.ShippingAddressRepository;
import com.lifevision.HelloSewa.request.ShippingAddressEditRequest;
import com.lifevision.HelloSewa.request.ShippingAddressRequest;
import com.lifevision.HelloSewa.response.ShippingAddressResponse;
import com.lifevision.HelloSewa.utils.DefaultStatus;
import com.lifevision.HelloSewa.utils.ShippingAddressStatus;

@Service
public class ShippingAddressService {
	
	@Autowired
	ShippingAddressRepository shippingAddressRepository;

	@Autowired
	CommonService commonService;
	
	/**
	 * Method for creating shipping address.
	 * @param loginId
	 * @param token
	 * @param request
	 * @return id
	 */
	public Long addShippingAddress(Long loginId, String token, ShippingAddressRequest request) {
		
		commonService.isValidToken(loginId, token);
	
		ShippingAddress shippingAddress = new ShippingAddress(); 
		
		shippingAddress.setCreatedBy(loginId);
		shippingAddress.setReceiverName(request.getReceiverName());
		shippingAddress.setReceiverPhone(request.getReceiverPhone());
		shippingAddress.setDistrict(request.getDistrict());
		shippingAddress.setCity(request.getCity());
		
		if(shippingAddressRepository.findByCreatedByAndDefaultStatusAndStatusNot(loginId, DefaultStatus.YES, ShippingAddressStatus.REMOVED) == null)
			shippingAddress.setDefaultStatus(DefaultStatus.YES);
		else 
			shippingAddress.setDefaultStatus(request.getDefaultStatus());
		
		if(request.getDefaultStatus() == DefaultStatus.YES) {
			ShippingAddress check = shippingAddressRepository.findByCreatedByAndDefaultStatusAndStatusNot(loginId, DefaultStatus.YES, ShippingAddressStatus.REMOVED);	
			if(check != null)
				check.setDefaultStatus(DefaultStatus.NO);
		}	
	
		shippingAddress.setAddress(request.getAddress());
		shippingAddress.setStatus(ShippingAddressStatus.ACTIVE);
		shippingAddress.setCreatedDate(new Date());
		shippingAddressRepository.save(shippingAddress);
	
		return shippingAddress.getId();
	}

	/**
	 * API to remove shipping address.
	 * @param loginId
	 * @param token
	 * @param shippingAddressId
	 */
	public void deleteShippingAddress(Long loginId, String token, Long shippingAddressId) {

		commonService.isValidToken(loginId, token);
		
		ShippingAddress shippingAddress = shippingAddressRepository.findByIdAndStatusNot(shippingAddressId, ShippingAddressStatus.REMOVED);
		if(shippingAddress == null)
			throw new NotFoundException("Shipping Address not found.");
			
		shippingAddress.setModifiedDate(new Date());
		shippingAddress.setStatus(ShippingAddressStatus.REMOVED);
		shippingAddressRepository.save(shippingAddress);
	}

	/**
	 * API for getting All shipping addresses of user.
	 * @param loginId
	 * @param token
	 * @return List<ShippingAddressResponse>
	 */
	public List<ShippingAddressResponse> getShippingAddresses(Long loginId, String token) {
		
		List<ShippingAddress> ShippingAddressList = shippingAddressRepository.findByCreatedByAndStatusNot(loginId, ShippingAddressStatus.REMOVED);
		
		List<ShippingAddressResponse> finalResponse = new ArrayList<>();
		
		for(ShippingAddress s : ShippingAddressList) {
			
			ShippingAddressResponse res = new ShippingAddressResponse();
			
			//setting responses
			res.setId(s.getId());	
			res.setCreatedBy(s.getCreatedBy());
			res.setReceiverPhone(s.getReceiverPhone());
			res.setReceiverName(s.getReceiverName());
			res.setDefaultStatus(s.getDefaultStatus());
		    res.setDistrict(s.getDistrict());
		    res.setCity(s.getCity());
		    res.setAddress(s.getAddress());
		    
		    finalResponse.add(res);
		} 
		return finalResponse;
	}

	/**
	 * API for edit shipping addresses of user.
	 * @param loginId
	 * @param token
	 */
	public void editShippingAddress(Long loginId, String token, ShippingAddressEditRequest request) {

	commonService.isValidToken(loginId, token);
		
	ShippingAddress shippingAddress = shippingAddressRepository.getOne(request.getId());
	if(shippingAddress == null) {
		throw new NotFoundException("Shipping Address not found associated with this id.");
	}
	
	if(request.getDefaultStatus() == DefaultStatus.YES) {
		
		ShippingAddress check = shippingAddressRepository.findByCreatedByAndDefaultStatusAndStatusNot(loginId, DefaultStatus.YES, ShippingAddressStatus.REMOVED);	
		if(check != null)
			check.setDefaultStatus(DefaultStatus.NO);
	}
	
	if(request.getDefaultStatus() == DefaultStatus.NO) {
		ShippingAddress check = shippingAddressRepository.findByIdAndDefaultStatusAndStatusNot(request.getId(), DefaultStatus.YES, ShippingAddressStatus.REMOVED);	
		if(check != null)
			throw new ValidationException("You must have atleast one default shipping address");
	}
	
	shippingAddress.setModifiedDate(new Date());
	shippingAddress.setReceiverName(request.getReceiverName());
	shippingAddress.setReceiverPhone(request.getReceiverPhone());
	shippingAddress.setCity(request.getCity());
	shippingAddress.setDistrict(request.getDistrict());
	shippingAddress.setAddress(request.getAddress());
	shippingAddress.setDefaultStatus(request.getDefaultStatus());
	
	shippingAddressRepository.save(shippingAddress);
	}
	
	/**
	* Get particular shipping address.
	* @param loginId
	* @param token
	* @param shippingAddressId
	* @return
	*/
	public ShippingAddressResponse getShippingAddress(Long loginId, String token, Long shippingAddressId) {

	ShippingAddress shippingAddress = shippingAddressRepository.getOne(shippingAddressId);

	ShippingAddressResponse res = new ShippingAddressResponse();

		res.setId(shippingAddress.getId());
		res.setCreatedBy(shippingAddress.getCreatedBy());
		res.setReceiverPhone(shippingAddress.getReceiverPhone());
		res.setReceiverName(shippingAddress.getReceiverName());
		res.setDistrict(shippingAddress.getDistrict());
		res.setCity(shippingAddress.getCity());
		res.setDefaultStatus(shippingAddress.getDefaultStatus());
		res.setAddress(shippingAddress.getAddress());
	   
		return res;

	}
	
	/**
	 * for get default shipping address.
	 * @param loginId
	 * @param token
	 * @return
	 */

	public ShippingAddressResponse getDefaultShippingAddress(Long loginId, String token) {
		
		ShippingAddress shippingAddress = shippingAddressRepository.findByCreatedByAndDefaultStatusAndStatusNot(loginId, DefaultStatus.YES, ShippingAddressStatus.REMOVED);
		if(shippingAddress == null) {
			throw new NotFoundException("Not Default address found.");
		}
		
		ShippingAddressResponse res = new ShippingAddressResponse();
		
		res.setId(shippingAddress.getId());
		res.setCreatedBy(shippingAddress.getCreatedBy());
		res.setReceiverPhone(shippingAddress.getReceiverPhone());
		res.setReceiverName(shippingAddress.getReceiverName());
		res.setDistrict(shippingAddress.getDistrict());
		res.setCity(shippingAddress.getCity());
		res.setDefaultStatus(shippingAddress.getDefaultStatus());
		res.setAddress(shippingAddress.getAddress());
		
		return res;
	}
}
