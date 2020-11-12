package com.lifevision.HelloSewa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lifevision.HelloSewa.model.Product;
import com.lifevision.HelloSewa.utils.ProductStatus;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	List<Product> findByBrandId(int brandId);

	Product findByIdAndStatusNot(Long productId, ProductStatus removed);

}
