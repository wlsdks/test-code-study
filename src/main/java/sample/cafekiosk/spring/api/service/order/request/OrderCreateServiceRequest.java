package sample.cafekiosk.spring.api.service.order.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 서비스 레이어에서는 @NotEmpty같은걸 검증하지 않기때문에 빼줘도 된다.
 * 검증은 @Valid가 달린 컨트롤러에서 처리하는것이다.
 */
@Getter
@NoArgsConstructor
public class OrderCreateServiceRequest {

//    @NotEmpty(message = "상품 번호 리스트는 필수입니다.")
    private List<String> productNumbers;

    @Builder
    private OrderCreateServiceRequest(List<String> productNumbers) {
        this.productNumbers = productNumbers;
    }
}
