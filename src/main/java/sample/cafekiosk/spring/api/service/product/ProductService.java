package sample.cafekiosk.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.api.service.product.request.ProductServiceCreateRequest;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;

import java.util.List;
import java.util.stream.Collectors;

/**
 * readOnly = true : 읽기전용이다.
 * CRUD에서 CUD는 동작X / only Read
 * JPA : CUD 스냅샷 저장, 변경감지X (성능이 향상됨)
 *
 * CQRS - Command / Query -> Command Query Responsibility seperate 서로의 책임분리
 */
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductNumberFactory productNumberFactory;

    /** 상품 추가 - 동시성 문제 해결이 필요함 */
    @Transactional
    public ProductResponse createProduct(ProductServiceCreateRequest request) {
        // DB에서 마지막 저장된 Product의 상품번호를 읽어와서 +1한다.  009 -> 010
        String nextProductNumber = productNumberFactory.createNextProductNumber();

        Product product = request.toEntity(nextProductNumber);
        Product savedProduct = productRepository.save(product);

        return ProductResponse.of(savedProduct);
    }

    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

        return products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

}
