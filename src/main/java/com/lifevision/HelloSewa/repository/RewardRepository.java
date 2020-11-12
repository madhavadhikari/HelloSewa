package com.lifevision.HelloSewa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lifevision.HelloSewa.model.Reward;

public interface RewardRepository extends JpaRepository<Reward, Integer>{

	List<Reward> findByUserId(Long loginId);

}
