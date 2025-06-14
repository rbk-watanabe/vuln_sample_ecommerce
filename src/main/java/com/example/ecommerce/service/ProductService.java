// src/main/java/com/example/ecommerce/service/ProductService.java
package com.example.ecommerce.service;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct; // @PostConstructをインポート (Java 17以降のSpring Boot 3.xではjakartaパッケージを使用)
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * アプリケーション起動時に初期の商品データをメモリに追加します。
     * InMemoryProductRepositoryを使用しているため、DBアクセスは行いません。
     */
    @PostConstruct // サービスが初期化された後に一度だけ実行される
    public void init() {
        if (productRepository.count() == 0) {
            productRepository.save(new Product("シンプルTシャツ", "コットン100%の肌触りの良いTシャツです。", 2500, "/images/tshirt.png"));
            productRepository.save(new Product("定番ジーンズ", "どんなトップスにも合う、定番のストレートジーンズ。", 6800, "/images/jeans.png"));
            productRepository.save(new Product("レザースニーカー", "普段使いに最適な、高品質なレザースニーカー。", 9900, "/images/sneakers.png"));
            productRepository.save(new Product("軽量バックパック", "通勤・通学に便利な、大容量の軽量バックパック。", 7200, "/images/backpack.png"));
            System.out.println("初期商品データを作成しました。");
        }
    }

    /**
     * 全ての商品を取得します。
     * @return 全商品のリスト
     */
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    /**
     * 指定されたIDの商品を取得します。
     * @param id 商品ID
     * @return 該当する商品（存在しない場合はOptional.empty()）
     */
    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }
}