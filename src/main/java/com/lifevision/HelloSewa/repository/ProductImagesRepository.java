package com.lifevision.HelloSewa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lifevision.HelloSewa.model.ProductImages;

public interface ProductImagesRepository extends JpaRepository<ProductImages, Long> {

	List<ProductImages> findByProductId(Long id);

}
