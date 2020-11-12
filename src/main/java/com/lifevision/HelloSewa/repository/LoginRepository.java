package com.lifevision.HelloSewa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lifevision.HelloSewa.model.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {
	
	Login findByUserId(Long id);

	Login findByIdAndToken(Long loginId, String token);

}
