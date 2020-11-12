package com.lifevision.HelloSewa.utils;

import java.security.SecureRandom;

public class RandomStringGenerator {
	private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%^&*()_+=-";
	private static final String NUMS = "0123456789";
	private static SecureRandom sr = new SecureRandom();
	
	/**
	 * Generates a random string containing 0-9, a-z, A-Z & few symbols for the given size
	 * @param size
	 * @return string
	 */
	public static String randomString(int size) {
		StringBuilder sb = new StringBuilder(size);
		for(int i = 0; i < size; i++) {
			sb.append(AB.charAt(sr.nextInt(AB.length())));
		}
		return sb.toString();
	}
}
