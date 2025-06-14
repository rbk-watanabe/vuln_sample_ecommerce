// src/main/java/com/example/ecommerce/service/OrderService.java
package com.example.ecommerce.service;

import com.example.ecommerce.entity.Cart;
import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.Order; // 後で作成
import com.example.ecommerce.entity.OrderItem; // 後で作成
import com.example.ecommerce.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// TODO: 後でOrder, OrderItemエンティティとリポジトリを作成し、永続化処理を追加する
@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartService cartService;
    private final UserService userService;
    private final PointService pointService; // ポイントサービスを注入
    private final PaymentService paymentService; // 決済サービスを注入

    /**
     * 注文を確定し、決済処理、ポイント付与/利用を行います。
     * @param userId 注文を行うユーザーのID
     * @param usedPoints 使用するポイント数
     * @return 注文成功のメッセージ
     * @throws IllegalArgumentException ポイント不足、決済失敗など
     */
    @Transactional // 複数の操作をまとめてトランザクション管理
    public String placeOrder(Long userId, int usedPoints) {
        Cart cart = cartService.getCart();
        if (cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("カートが空です。");
        }

        User user = userService.findUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("ユーザーが見つかりません。"));

        int totalAmount = cart.getTotalPrice(); // カートの合計金額

        // ポイント利用の処理
        if (usedPoints > 0) {
            if (user.getPoints() < usedPoints) {
                throw new IllegalArgumentException("利用できるポイントが不足しています。現在のポイント: " + user.getPoints());
            }
            // ポイントを使用
            pointService.usePoints(userId, usedPoints, "注文時のポイント利用");
            totalAmount -= usedPoints; // 合計金額から利用ポイントを減算
            if (totalAmount < 0) {
                totalAmount = 0; // 合計金額はマイナスにならない
            }
        }

        // 疑似決済処理 (ここでは常に成功と仮定)
        boolean paymentSuccess = paymentService.processPayment(totalAmount);
        if (!paymentSuccess) {
            // 実際には決済プロバイダからのエラーに応じて具体的なエラーを返す
            throw new RuntimeException("決済に失敗しました。");
        }

        // TODO: ここでOrderエンティティを生成し、DBに保存する処理を追加する
        // 現時点ではログ出力のみ
        System.out.println("----- 注文が確定されました -----");
        System.out.println("ユーザーID: " + userId);
        System.out.println("支払金額 (ポイント利用後): " + totalAmount + "円");
        System.out.println("利用ポイント: " + usedPoints + "ポイント");
        System.out.println("商品:");
        for (CartItem item : cart.getItems()) {
            System.out.println("  - " + item.getProductName() + " (単価: " + item.getPrice() + "円, 数量: " + item.getQuantity() + ")");
        }
        System.out.println("--------------------------");


        // 注文完了後、ポイント付与（例: 支払い金額の1%を付与）
        int earnedPoints = (int) (totalAmount * 0.01); // 1% を計算 (端数切り捨て)
        if (earnedPoints > 0) {
            pointService.addPoints(userId, earnedPoints, "商品購入によるポイント獲得");
            System.out.println("ポイントを " + earnedPoints + " 付与しました。");
        }


        cartService.clearCart(); // カートを空にする
        return "注文が完了しました。";
    }
}