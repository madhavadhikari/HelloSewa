package com.lifevision.HelloSewa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lifevision.HelloSewa.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByPhone(String phone);

	List<User> findByIdNot(Long loginId);

}
