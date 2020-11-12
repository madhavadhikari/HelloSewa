package com.lifevision.HelloSewa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lifevision.HelloSewa.model.UserConfirmation;

public interface UserConfirmationRepository  extends JpaRepository<UserConfirmation, Long> {

	UserConfirmation findByUserId(Long userId);

}
