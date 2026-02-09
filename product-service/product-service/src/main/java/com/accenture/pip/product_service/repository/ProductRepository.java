package com.accenture.pip.product_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accenture.pip.product_service.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
