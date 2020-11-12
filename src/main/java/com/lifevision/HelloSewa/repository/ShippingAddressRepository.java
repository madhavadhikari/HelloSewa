package com.lifevision.HelloSewa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lifevision.HelloSewa.model.ShippingAddress;
import com.lifevision.HelloSewa.utils.DefaultStatus;
import com.lifevision.HelloSewa.utils.ShippingAddressStatus;

@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {

	ShippingAddress findByIdAndStatusNot(Long shippingAddressId, ShippingAddressStatus removed);

	List<ShippingAddress> findByCreatedByAndStatusNot(Long loginId, ShippingAddressStatus removed);

	ShippingAddress findByCreatedByAndDefaultStatusAndStatusNot(Long loginId, DefaultStatus yes,
			ShippingAddressStatus removed);

	ShippingAddress findByIdAndDefaultStatusAndStatusNot(Long id, DefaultStatus yes, ShippingAddressStatus removed);
}

