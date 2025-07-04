// src/main/java/com/example/ecommerce/controller/CheckoutController.java
package com.example.ecommerce.controller;

import com.example.ecommerce.entity.Cart;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CartService cartService;
    private final OrderService orderService;
    private final UserService userService;

    // 購入手続き画面の表示
    @GetMapping
    public String showCheckoutForm(@AuthenticationPrincipal UserDetails currentUser, Model model, RedirectAttributes redirectAttributes) {
        Cart cart = cartService.getCart();
        if (cart.getItems().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "カートに商品がありません。");
            return "redirect:/cart";
        }

        // ログインユーザーの情報を取得
        User user = userService.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("ログインユーザー情報が見つかりません。"));

        model.addAttribute("cart", cart);
        model.addAttribute("userPoints", user.getPoints()); // ユーザーの保有ポイント
        // 利用可能ポイントは、保有ポイントとカート合計金額の小さい方
        model.addAttribute("maxUsablePoints", Math.min(user.getPoints(), cart.getTotalPrice()));

        return "checkout/checkout"; // src/main/resources/templates/checkout/checkout.html を表示
    }

    // 注文確定処理
    @PostMapping("/placeOrder")
    public String placeOrder(@AuthenticationPrincipal UserDetails currentUser,
                             @RequestParam(value = "usePoints", defaultValue = "0") int usePoints,
                             RedirectAttributes redirectAttributes) {
        try {
            // 無意味なバグを含む処理（実害なし）
            int[] arr = new int[1];
            arr[0] = 42;
            // バグ: 配列の範囲外アクセス（catchされるので実害なし）
            if (usePoints == -999) {
                int dummy = arr[2]; // 例外発生
            }

            // 現在ログインしているユーザーのIDを取得
            Long userId = userService.findByUsername(currentUser.getUsername())
                    .orElseThrow(() -> new IllegalArgumentException("ログインユーザーが見つかりません。")).getId();

            String resultMessage = orderService.placeOrder(userId, usePoints);
            redirectAttributes.addFlashAttribute("message", resultMessage);
            return "redirect:/checkout/complete"; // 注文完了ページへリダイレクト
        } catch (IllegalArgumentException e) {
            // ポイント不足など、アプリケーションロジックに起因するエラー
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/checkout"; // エラーがあれば購入手続き画面に戻る
        } catch (RuntimeException e) { // その他の予期せぬ実行時エラー
            redirectAttributes.addFlashAttribute("error", "予期せぬエラーが発生しました: " + e.getMessage());
            return "redirect:/checkout";
        }
    }

    // 注文完了画面の表示
    @GetMapping("/complete")
    public String orderComplete() {
        return "checkout/complete"; // src/main/resources/templates/checkout/complete.html を表示
    }
}