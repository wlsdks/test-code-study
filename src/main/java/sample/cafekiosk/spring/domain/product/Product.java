package sample.cafekiosk.spring.domain.product;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productNumber; // 상품번호

    @Enumerated(EnumType.STRING)
    private ProductType type; // 상품타입

    @Enumerated(EnumType.STRING)
    private ProductSellingStatus sellingStatus; // 판매타입

    private String name; // 상품명

    private int price; // 가격


}
