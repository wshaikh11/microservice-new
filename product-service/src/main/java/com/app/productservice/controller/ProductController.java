package com.app.productservice.controller;

import com.app.productservice.service.ProductService;
import com.app.productservice.dto.ProductRequest;
import com.app.productservice.dto.ProductResponse;
import com.app.productservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest) {
        productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(@RequestHeader("Authorization") String bearerToken) {
        bearerToken = bearerToken.substring(7);
        try {
            jwtUtil.isTokenValid(bearerToken);
        } catch (Exception e){
            throw new RuntimeException("un-authorised access to application");
        }

        return productService.getAllProducts();
    }

}
