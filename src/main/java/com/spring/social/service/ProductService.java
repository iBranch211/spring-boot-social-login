package com.spring.social.service;

import com.spring.social.model.Product;
import com.spring.social.payload.request.ProductCreateRequest;

public interface ProductService {

    Product createProduct(ProductCreateRequest productCreateRequest);
}
