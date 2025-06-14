// src/main/java/com/example/ecommerce/service/PaymentService.java
package com.example.ecommerce.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    /**
     * 疑似的なクレジットカード決済処理。
     * 現時点では、常に成功を返します。
     * 後に、決済失敗のシナリオや、金額による条件分岐などを追加して脆弱性をシミュレートできます。
     * @param amount 決済金額
     * @return 決済が成功した場合はtrue、失敗した場合はfalse
     */
    public boolean processPayment(int amount) {
        if (amount < 0) {
            // 負の金額は決済失敗
            System.out.println("支払い金額が負の数です。決済失敗。");
            return false;
        }
        System.out.println("疑似決済処理: 金額 " + amount + "円 の決済が完了しました。");
        // 実際には外部決済プロバイダへのAPI呼び出しなどが行われる
        return true; // とりあえず常に成功と仮定
    }
}