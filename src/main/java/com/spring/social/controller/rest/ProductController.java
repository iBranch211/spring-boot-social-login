package com.spring.social.controller.rest;


import com.spring.social.model.Product;
import com.spring.social.payload.request.ProductCreateRequest;
import com.spring.social.service.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal=true)
@RequiredArgsConstructor
@RequestMapping("/versions/1")
public class ProductController {

    ProductService productService;

    @ApiOperation(value = "Create a Product",response = Product.class)
    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@Valid @RequestBody ProductCreateRequest productCreateRequest) {
        return productService.createProduct(productCreateRequest);
    }
}
