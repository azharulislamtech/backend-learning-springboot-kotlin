package com.learning.crudthree.repository

import com.learning.crudthree.model.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long>
