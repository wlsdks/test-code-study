package sample.cafekiosk.spring.api.controller.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sample.cafekiosk.spring.api.ApiResponse;
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import sample.cafekiosk.spring.api.service.order.OrderService;
import sample.cafekiosk.spring.api.service.order.response.OrderResponse;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    /**
     * 컨트롤러에서는 컨트롤러전용 dto만 사용하고 서비스로 넘길때는 서비스전용 dto로 변환해서 넘긴다.
     * @param request
     * @return
     */
    @PostMapping("/api/v1/orders/new")
    public ApiResponse<OrderResponse> createOrder(
            @Valid @RequestBody OrderCreateRequest request
    ) {
        LocalDateTime registeredDateTime = LocalDateTime.now();
        return ApiResponse.ok(orderService.createOrder(request.toServiceRequest(), registeredDateTime));
    }

}
