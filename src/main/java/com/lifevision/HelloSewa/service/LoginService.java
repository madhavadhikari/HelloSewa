package com.lifevision.HelloSewa.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifevision.HelloSewa.exception.LoginFailedException;
import com.lifevision.HelloSewa.exception.NotFoundException;
import com.lifevision.HelloSewa.exception.ValidationException;
import com.lifevision.HelloSewa.model.Login;
import com.lifevision.HelloSewa.model.User;
import com.lifevision.HelloSewa.model.UserConfirmation;
import com.lifevision.HelloSewa.repository.LoginRepository;
import com.lifevision.HelloSewa.repository.UserConfirmationRepository;
import com.lifevision.HelloSewa.repository.UserRepository;
import com.lifevision.HelloSewa.request.ChangePasswordRequest;
import com.lifevision.HelloSewa.request.PhoneEditRequest;
import com.lifevision.HelloSewa.request.UserLoginRequest;
import com.lifevision.HelloSewa.response.LoginResponse;
import com.lifevision.HelloSewa.utils.LoginStatus;
import com.lifevision.HelloSewa.utils.RandomStringGenerator;

@Service 
public class LoginService {
	
	@Autowired
	LoginRepository loginRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	private UserConfirmationRepository userConfirmationRepository;
	
	/**
	 *  for user login.
	 * @Param userLoginRequest
	 * @Return loginResponse
	 */
	public LoginResponse userLogin(UserLoginRequest userLoginRequest){
		
		User user = userRepository.findByPhone(userLoginRequest.getPhone());
		if(user == null)
			throw new NotFoundException("Phone Number not found.");
		
		LoginResponse loginResponse = new LoginResponse();
		
		Login login = loginRepository.findByUserId(user.getId());
		if(login != null) {
			if(userLoginRequest.getPassword().equals(login.getPassword())) {
				login.setLoginStatus(LoginStatus.LOGGED_IN);
				login.setToken(RandomStringGenerator.randomString(500));
				login.setTokenExpiryDate(new Date());
				login.setModifiedDate(new Date());
				loginRepository.save(login);
				
				//setting Response
				loginResponse.setLoginId(login.getId());
				loginResponse.setToken(login.getToken());
				
			}else {
				throw new LoginFailedException("Login failed!");
			}
		}
		return loginResponse;
	}
	
	/**
	 *  for user logout.
	 * @Param loginId, token.
	*/
	public void userLogout(Long loginId, String token) {
		
		commonService.isValidToken(loginId, token);
		
		Login login = loginRepository.findByIdAndToken(loginId, token);
		if(login == null )
			throw new NotFoundException("Login Id and token didn't match.");
		
		login.setLoginStatus(LoginStatus.LOGGED_OUT);
		login.setModifiedDate(new Date());
		login.setToken(null);
		loginRepository.save(login);
	}
	
	/**
	 *  for password change.
	 * @Param loginId, token.
	 */
	public void changePassword(Long loginId, String token, ChangePasswordRequest request) {
		
		commonService.isValidToken(loginId, token);
		
		Login login = loginRepository.findByIdAndToken(loginId, token);
		
		if (!request.getOldPassword().equals(login.getPassword())){
			throw new ValidationException("Invalid old password!");
		}
		
		if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
			throw new ValidationException(
					"New password and confirm new password did not match.");
		}
		login.setPassword(request.getConfirmNewPassword());
		login.setModifiedDate(new Date());
		loginRepository.save(login);
	}
	
	/**
	 * for changing your phone number.
	 * @Param loginId, token, phoneEditRequest.
	 **/
	public void changePhone(Long userId, String token, PhoneEditRequest phoneEditRequest) {
		
		commonService.isValidToken(userId, token);
		
		User user = userRepository.getOne(userId);
		
		//for update new phone number of user in the database.
		user.setPhone(phoneEditRequest.getPhone());
		userRepository.save(user);
		
		//for update confirmation code in database
		UserConfirmation userConfirmation = userConfirmationRepository.findByUserId(userId);
		userConfirmation.setConfirmationCode(RandomStringGenerator.randomString(5));
		userConfirmation.setModifiedDate(new Date());
		userConfirmationRepository.save(userConfirmation);
		
	}
}
