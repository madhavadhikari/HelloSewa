package com.lifevision.HelloSewa.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.lifevision.HelloSewa.exception.NotFoundException;
import com.lifevision.HelloSewa.exception.ValidationException;
import com.lifevision.HelloSewa.model.Login;
import com.lifevision.HelloSewa.model.User;
import com.lifevision.HelloSewa.model.UserConfirmation;
import com.lifevision.HelloSewa.repository.LoginRepository;
import com.lifevision.HelloSewa.repository.UserConfirmationRepository;
import com.lifevision.HelloSewa.repository.UserRepository;
import com.lifevision.HelloSewa.request.UserConfirmationRequest;
import com.lifevision.HelloSewa.request.UserEditRequest;
import com.lifevision.HelloSewa.request.UserSignupRequest;
import com.lifevision.HelloSewa.response.SingupResponse;
import com.lifevision.HelloSewa.response.UserResponse;
import com.lifevision.HelloSewa.utils.LoginStatus;
import com.lifevision.HelloSewa.utils.RandomStringGenerator;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private UserConfirmationRepository userConfirmationRepository;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private Environment environment;
	
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	
	/**
	 * creates user and sets its login data
	 * @param userSignupRequest
	 * @return SingupResponse
	 */
	public SingupResponse userSignup(UserSignupRequest userSignupRequest) {
		
		//checking phone number duplication
		User checkPhone = userRepository.findByPhone(userSignupRequest.getPhone());
		if(checkPhone != null)
			throw new ValidationException("Phone Number already exist.");
		
		//checking password and confirm password 
	    if(!userSignupRequest.getPassword().equals(userSignupRequest. getConfirmPassword())) { 
	    	throw new ValidationException("Password mis-match.");
		}
		 
		User user = new User();
		user.setFullName(userSignupRequest.getFullName());
		user.setPhone(userSignupRequest.getPhone());
		userRepository.save(user);
		
		Login login = new Login();
		login.setPassword(userSignupRequest.getPassword());
		login.setUser(user);
		login.setLoginStatus(LoginStatus.LOGGED_IN);
		login.setToken(RandomStringGenerator.randomString(500));
		login.setCreatedDate(new Date());
		LOG.info(login.getToken());
		login.setTokenExpiryDate(new Date());
		loginRepository.save(login);
		
		SingupResponse response = new SingupResponse();
		response.setLoginId(login.getId());
		response.setToken(login.getToken());
		
		UserConfirmation userConfirmation = new UserConfirmation(user);
		userConfirmationRepository.save(userConfirmation);
		//send SMS for user signup confirmation
		//sendConfirmationSms(userConfirmation.getConfirmationCode());
		
		return response;
	}
	
	/**
	 * for validating user.
	 * @param userId, userConfirmationRequest
	 * @return boolean true
	 */
	public void confirmation(Long userId, UserConfirmationRequest userConfirmationRequest) {

		UserConfirmation userConfirmation = userConfirmationRepository.findByUserId(userId);
		if(userConfirmation == null) {
			throw new NotFoundException("User not found");
		}
		LOG.debug(">>>>>>>>>>>>>>>>>>Checking>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		if(!userConfirmationRequest.getConfirmationCode().equals(userConfirmation.getConfirmationCode())) {
			throw new ValidationException("Confirmation Code not valid. please try again later.");
		}
		User user = userRepository.getOne(userId);
		user.setVerified(true);
		userRepository.save(user);
	}
	
	/**
	 * Setting users updated data in table
	 * @param userId, token, userEditRequest
	 */
	public User editUser(Long userId, String token, UserEditRequest userEditRequest) {
		
		commonService.isValidToken(userId, token);
		
		User user = userRepository.getOne(userId);
		if(user == null) {
			throw new NotFoundException("User not found associated with this id.");
		}
		
		user.setFullName(userEditRequest.getFullName());
		user.setGender(userEditRequest.getGender());
		user.setEmail(userEditRequest.getEmail());
		user.setDob(userEditRequest.getDob());
		user.setModifiedDate(new Date());
		
		return user = userRepository.save(user);
	}
	
	/**
	 * for returning information of particular user.
	 * @param userId, token
	 */
	public UserResponse getUser(Long userId, String token) {
		
		commonService.isValidToken(userId, token);
		
		User user = userRepository.getOne(userId);
		if(user == null) {
			throw new NotFoundException("User not found associated with this id.");
		}
		UserResponse userResponse = new UserResponse();
		
		//setting response
		userResponse.setId(user.getId());
		userResponse.setCreatedDate(user.getCreatedDate());
		userResponse.setModifiedDate(user.getModifiedDate());
		userResponse.setFullName(user.getFullName());
		userResponse.setEmail(user.getEmail());
		userResponse.setDob(user.getDob());
		userResponse.setGender(user.getGender());
		userResponse.setPhone(user.getPhone());
		userResponse.setProfilePicture(user.getProfilePicture());
		
		return userResponse;
	}
	
	/**
	 * for changing profile picture of user
	 * @param loginId, token, profilePic
	 */
	public String changeProfilePic(Long loginId, String token, String profilePic) {
		//LOG.info("encoded PP :==: "+ profilePic);
		commonService.isValidToken(loginId, token);
		Login login = loginRepository.getOne(loginId);
		
		//delete old profile picture 
		String oldPic = login.getUser().getProfilePicture();
		if(oldPic != null) {
		LOG.info("Old pp path: "+oldPic);
		commonService.deleteImage(oldPic);
		}
		//saving new profile picture
		String newPic = commonService.uploadImage(profilePic, 
				commonService.generateImageName(),
				environment.getProperty("image.directory.profile"));
		
		User user = login.getUser();
		user.setModifiedDate(new Date());
		user.setProfilePicture(newPic);
		
		userRepository.save(user);
		
		return newPic;
	}
	
	/**
	 * for returning information of all user.
	 * @param loginId, token
	 * @return List<UserResponse>
	 */
	public List<UserResponse> getAllUsers(Long loginId, String token) {
		
		List<UserResponse> allUsers = new ArrayList<>();
		
		commonService.isValidToken(loginId, token);
		
		List<User> users = userRepository.findByIdNot(loginId);
		
		users.forEach(u -> {
			
			UserResponse userResponse = new UserResponse();
			
			//setting response.
			userResponse.setId(u.getId());
			userResponse.setDob(u.getDob());
			userResponse.setModifiedDate(u.getModifiedDate());
			userResponse.setEmail(u.getEmail());
			userResponse.setGender(u.getGender());
			userResponse.setProfilePicture(u.getProfilePicture());
			userResponse.setCreatedDate(u.getCreatedDate());
			userResponse.setFullName(u.getFullName());
			userResponse.setDob(u.getDob());
			userResponse.setPhone(u.getPhone());
			allUsers.add(userResponse);
		});
		return allUsers;
	}
}