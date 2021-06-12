package com.myfirststore.storee.product.repository;

import com.myfirststore.storee.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}