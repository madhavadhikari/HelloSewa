package com.lifevision.HelloSewa.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.lifevision.HelloSewa.exception.FileHandlingException;
import com.lifevision.HelloSewa.exception.LoginFailedException;
import com.lifevision.HelloSewa.model.Login;
import com.lifevision.HelloSewa.repository.LoginRepository;
import com.lifevision.HelloSewa.repository.WishlistRepository;
import com.lifevision.HelloSewa.response.AuthenticationResponse;

@Service
public class CommonService {
	private static final Logger LOG = LoggerFactory.getLogger(CommonService.class); 
	
	@Autowired
	private Environment environment;
	
	@Autowired 
	LoginRepository loginRepository;
	
	@Autowired
	WishlistRepository wishlistRepository;
	
	/**
	 * validates the login id and token.
	 * @param loginId
	 * @param token
	 * @return AuthenticationResponse
	 */
	public AuthenticationResponse isValidToken(Long loginId, String token) {
		
		AuthenticationResponse res = new AuthenticationResponse();
		
		Login login = loginRepository.findByIdAndToken(loginId, token);
		if(login == null) {
			throw new LoginFailedException("Authentication Failed.");
		}
		
		res.setMessage("Authentication Sucess.");
		return res;
	}
	
	/**
	 * Generates the unique name for the images
	 * @return String
	 */
	public String generateImageName() {
		DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("ddmmyyyyhhmmss");
		LocalDateTime rightNow = LocalDateTime.now();
		return dtFormat.format(rightNow)+rightNow.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()+".png";
	}
	
	/**
	 * uploads the encoded in the given directory for the given image name
	 * @param encodedImage
	 * @param imageName
	 * @param folderName
	 * @return String
	 */
	public String uploadImage(String encodedImage, String imageName, String folderName) {
		//LOG.info(encodedImage);
		String resDirectory = environment.getProperty("resource.directory.static");
		String imagebaseDirectory = environment.getProperty("image.directory.base");
		String imageDirectory = resDirectory.concat(imagebaseDirectory);

		String contextPath = environment.getProperty("server.servlet.context-path");
		
		if(!new File(resDirectory).exists()) {
			new File(resDirectory).mkdir();
		}
		if(!new File(imageDirectory).exists()) {
			new File(imageDirectory).mkdir();
		}
		String finalDirectory = imageDirectory.concat(folderName);
		if(!new File(finalDirectory).exists()) {
			new File(finalDirectory).mkdir();
		}
		File actualFile = new File(finalDirectory, imageName);
		try {
			FileOutputStream outputImg = new FileOutputStream(actualFile);
			byte[] imgByte = Base64.decodeBase64(encodedImage);
			try {
				outputImg.write(imgByte);
				outputImg.close();
			} catch (IOException e) {
				throw new FileHandlingException(e.getMessage());
			}
		} catch (FileNotFoundException e) {
			throw new FileHandlingException(e.getMessage());
		}
		LOG.info("Image stored in: "+contextPath+imagebaseDirectory+folderName+"/"+imageName);
		return "http://192.168.137.138:8086" +contextPath+imagebaseDirectory+folderName+"/"+imageName;
		
	}
	
	/**
	 * Deletes image for the given image path
	 * @param imagePath
	 */
	public void deleteImage(String imagePath) {
		//extracting image actual path
		int i = imagePath.indexOf("/image");
		String imgToDeletePath = "src/main/resources/static"+imagePath.substring(i);
		
		if(imgToDeletePath != null) {
			try {
				Files.deleteIfExists(Paths.get(imgToDeletePath));
			} catch (IOException e) {
				throw new FileHandlingException(e.getMessage());
			} 
//			File fileToDelete = new File(imgToDeletePath);
//			LOG.info("File Exist? "+fileToDelete.exists());
//			
//		    boolean stat = fileToDelete.delete();
//		    LOG.info("File deleted? "+stat);
		}
	}
	
	/**
	 * applies discount to the given price and return it
	 * @param price
	 * @param discount
	 * @return float
	 */
	public float getPriceAfterDiscount(float price, float discount) {
		return price-((discount/100)*price);
	}
}
