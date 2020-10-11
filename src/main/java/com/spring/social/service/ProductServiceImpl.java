package com.spring.social.service;

import com.spring.social.exception.production.ProductAlreadyExistsException;
import com.spring.social.model.Product;
import com.spring.social.payload.request.ProductCreateRequest;
import com.spring.social.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;
    ModelMapper modelMapper;

    @Override
    public Product createProduct(ProductCreateRequest productCreateRequest) {

        if (productRepository.existsByName(productCreateRequest.getName())) {
            throw new ProductAlreadyExistsException();
        }
        Product product=modelMapper.map(productCreateRequest, Product.class);

        product=productRepository.save(product);
        return product;
    }
}
