// src/main/java/com/example/ecommerce/service/CartService.java
package com.example.ecommerce.service;

import com.example.ecommerce.entity.Cart;
import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope; // セッションスコープのBeanとして定義

import java.util.Optional;

@Service
@SessionScope // このサービスをセッションスコープのBeanとして定義
@RequiredArgsConstructor
public class CartService {

    private final ProductService productService; // 商品情報を取得するため

    private Cart cart = new Cart(); // セッションごとに新しいカートインスタンスが生成される

    /**
     * カートを取得します。
     * @return 現在のカート
     */
    public Cart getCart() {
        return cart;
    }

    /**
     * 商品をカートに追加します。
     * @param productId 追加する商品のID
     * @param quantity 追加する数量
     * @return 追加に成功したかどうか
     */
    public boolean addProductToCart(Long productId, int quantity) {
        if (quantity <= 0) {
            return false; // 0以下の数量は追加しない
        }

        Optional<Product> productOpt = productService.findProductById(productId);
        if (productOpt.isEmpty()) {
            return false; // 商品が見つからない
        }
        Product product = productOpt.get();

        // 既存のカートアイテムを検索
        Optional<CartItem> existingItemOpt = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();

        if (existingItemOpt.isPresent()) {
            // 既存の商品があれば数量を更新
            CartItem existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            // 新しい商品であれば追加
            CartItem newItem = new CartItem(
                product.getId(),
                product.getName(),
                product.getPrice(),
                quantity,
                product.getImageUrl()
            );
            cart.getItems().add(newItem);
        }
        return true;
    }

    /**
     * カート内の商品の数量を更新します。
     * @param productId 更新する商品のID
     * @param newQuantity 新しい数量
     * @return 更新に成功したかどうか
     */
    public boolean updateProductQuantity(Long productId, int newQuantity) {
        if (newQuantity < 0) {
            return false; // 負の数量は許可しない
        }

        Optional<CartItem> itemOpt = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();

        if (itemOpt.isPresent()) {
            CartItem item = itemOpt.get();
            if (newQuantity == 0) {
                // 数量が0ならカートから削除
                cart.getItems().remove(item);
            } else {
                // 数量を更新
                item.setQuantity(newQuantity);
            }
            return true;
        }
        return false; // 商品がカートに見つからない
    }

    /**
     * カートから商品を削除します。
     * @param productId 削除する商品のID
     * @return 削除に成功したかどうか
     */
    public boolean removeProductFromCart(Long productId) {
        return cart.getItems().removeIf(item -> item.getProductId().equals(productId));
    }

    /**
     * カートをクリアします。
     */
    public void clearCart() {
        cart.getItems().clear();
    }
}