// src/main/java/com/example/ecommerce/controller/ProductController.java
package com.example.ecommerce.controller;

import com.example.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products") // このコントローラは /products 以下のパスを処理
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 商品一覧画面の表示
    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.findAllProducts());
        return "product/list"; // src/main/resources/templates/product/list.html を表示
    }

    // 商品詳細画面の表示
    @GetMapping("/{id}")
    public String showProductDetail(@PathVariable("id") Long id, Model model) {
        return productService.findProductById(id)
                .map(product -> {
                    model.addAttribute("product", product);
                    return "product/detail"; // src/main/resources/templates/product/detail.html を表示
                })
                .orElse("redirect:/products"); // 商品が見つからない場合は一覧へリダイレクト
    }
}