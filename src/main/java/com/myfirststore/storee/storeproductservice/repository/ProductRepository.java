package com.myfirststore.storee.storeproductservice.repository;

import com.myfirststore.storee.storeproductservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}