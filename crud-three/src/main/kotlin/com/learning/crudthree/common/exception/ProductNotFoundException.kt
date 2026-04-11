package com.learning.crudthree.common.exception

class ProductNotFoundException(productId: Long) :
    RuntimeException("Product not found with id: $productId")
