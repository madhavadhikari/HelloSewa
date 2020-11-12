package com.lifevision.HelloSewa.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lifevision.HelloSewa.model.User;
import com.lifevision.HelloSewa.request.EditProfilePicRequest;
import com.lifevision.HelloSewa.request.UserConfirmationRequest;
import com.lifevision.HelloSewa.request.UserEditRequest;
import com.lifevision.HelloSewa.request.UserSignupRequest;
import com.lifevision.HelloSewa.response.SingupResponse;
import com.lifevision.HelloSewa.response.UserResponse;
import com.lifevision.HelloSewa.service.UserService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/api/v1/user/")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/signup")
	@ApiOperation(
			value = "API to User Signup.",
			notes = "This API is used for adding user in our system.",
			response = SingupResponse.class)
	public ResponseEntity<Object> userSignup(@RequestBody UserSignupRequest userSignupRequest){
		SingupResponse response = userService.userSignup(userSignupRequest);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	@PutMapping("/edituser")
	@ApiOperation(
			value = "API for edit user", 
			notes = "This is for editing user.",
			response = User.class)
	public ResponseEntity<Object> userEdit(@RequestHeader Long userId,
			@RequestHeader String token, @RequestBody UserEditRequest userEditRequest){
		User userResponse = userService.editUser(userId, token, userEditRequest);
		Map<Object, Object> responseMap = new HashMap<>();
		responseMap.put("profile updated as:", userResponse);
		return new ResponseEntity<Object>(responseMap,  HttpStatus.OK);
	}

	@PutMapping("/confirmation")
	@ApiOperation(
			value = "API to User Confirmation.",
			notes = "This API is used for confirm the user.")
	public ResponseEntity<Object> userConfirmation(@RequestHeader Long userId, 
			@RequestBody UserConfirmationRequest userConfirmationRequest){
		userService.confirmation(userId, userConfirmationRequest);
		return new ResponseEntity<Object>("Your id has been verified.", HttpStatus.OK);
	}
	
	@GetMapping("/getuser")
	@ApiOperation(
			value = "API for get user",
			notes = "This is for getting particular user details.",
			response = UserResponse.class)
	public ResponseEntity<Object> getUser(@RequestHeader Long userId, @RequestHeader String token){
		UserResponse userResponse = userService.getUser(userId, token);
		return new ResponseEntity<Object>(userResponse, HttpStatus.OK);
	}
	
	@PutMapping("/change-profile-pic")
	@ApiOperation(
			value = "API for changing the profile picture of user.",
			notes = "This is for changing the profile picture of user.")
	public ResponseEntity<Object> changeProfilePicture(@RequestHeader Long loginId, 
			@RequestHeader String token, @RequestBody EditProfilePicRequest editProfilePicRequest){
		String imgPath = userService.changeProfilePic(loginId, token, editProfilePicRequest.getPicture());
		return new ResponseEntity<Object>(imgPath, HttpStatus.OK);
	}
	
	@GetMapping("/getAllUsers")
	@ApiOperation(
			value = "API for getting all users.",
			notes = "This is for getting all users.",
			response = UserResponse.class,
			responseContainer = "List")
	public ResponseEntity<Object> getAllUsers(@RequestHeader Long loginId, @RequestHeader String token){
		List<UserResponse> users = userService.getAllUsers(loginId, token);
		return new ResponseEntity<Object>(users, HttpStatus.OK);
	}
}
