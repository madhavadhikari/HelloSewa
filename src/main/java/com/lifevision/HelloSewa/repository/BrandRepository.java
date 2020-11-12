package com.lifevision.HelloSewa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lifevision.HelloSewa.model.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer>{

}
