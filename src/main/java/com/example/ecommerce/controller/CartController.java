// src/main/java/com/example/ecommerce/controller/CartController.java
package com.example.ecommerce.controller;

import com.example.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // リダイレクト時にメッセージを渡すため

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // カート一覧画面の表示
    @GetMapping
    public String viewCart(Model model) {
        model.addAttribute("cart", cartService.getCart());
        return "cart/view"; // src/main/resources/templates/cart/view.html を表示
    }

    // カートに商品を追加
    @PostMapping("/add")
    public String addProductToCart(@RequestParam("productId") Long productId,
                                   @RequestParam(value = "quantity", defaultValue = "1") int quantity,
                                   RedirectAttributes redirectAttributes) {
        if (cartService.addProductToCart(productId, quantity)) {
            redirectAttributes.addFlashAttribute("message", "商品がカートに追加されました。");
        } else {
            redirectAttributes.addFlashAttribute("error", "商品の追加に失敗しました。");
        }
        return "redirect:/cart"; // カート一覧ページへリダイレクト
    }

    // カート内の商品の数量を更新
    @PostMapping("/update")
    public String updateCartItem(@RequestParam("productId") Long productId,
                                 @RequestParam("quantity") int quantity,
                                 RedirectAttributes redirectAttributes) {
        if (cartService.updateProductQuantity(productId, quantity)) {
            redirectAttributes.addFlashAttribute("message", "カートの商品数量が更新されました。");
        } else {
            redirectAttributes.addFlashAttribute("error", "数量の更新に失敗しました。");
        }
        return "redirect:/cart";
    }

    // カートから商品を削除
    @PostMapping("/remove")
    public String removeCartItem(@RequestParam("productId") Long productId,
                                 RedirectAttributes redirectAttributes) {
        if (cartService.removeProductFromCart(productId)) {
            redirectAttributes.addFlashAttribute("message", "商品がカートから削除されました。");
        } else {
            redirectAttributes.addFlashAttribute("error", "商品の削除に失敗しました。");
        }
        return "redirect:/cart";
    }

    // カートを空にする
    @PostMapping("/clear")
    public String clearCart(RedirectAttributes redirectAttributes) {
        cartService.clearCart();
        redirectAttributes.addFlashAttribute("message", "カートが空になりました。");
        return "redirect:/cart";
    }
}