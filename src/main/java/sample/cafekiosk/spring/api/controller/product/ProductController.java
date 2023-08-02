package sample.cafekiosk.spring.api.controller.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sample.cafekiosk.spring.api.ApiResponse;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.service.product.ProductService;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    /**
     * 상품 생성
     * @param request
     * @return
     */
    @PostMapping("/api/v1/products/new")
    public ApiResponse<ProductResponse> createProduct( // 검증을 위해 @Valid를 달아준다.
           @Valid @RequestBody ProductCreateRequest request
    ) {
        // 검증만하고 serviceDto로 변환해서 넘긴다.
        return ApiResponse.ok(productService.createProduct(request.toServiceRequest()));
    }

    /**
     * 판매상품 조회
     * @return
     */
    @GetMapping("/api/v1/products/selling")
    public ApiResponse<List<ProductResponse>> getSellingProducts() {
        return ApiResponse.ok(productService.getSellingProducts());
    }


}
