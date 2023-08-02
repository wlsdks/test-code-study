package sample.cafekiosk.spring.api.controller.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.spring.ControllerTestSupport;
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest extends ControllerTestSupport {

    @DisplayName("신규 주문을 등록한다.")
    @Test
    void createProduct() throws Exception {
        //given
        OrderCreateRequest request = OrderCreateRequest.builder()
                .productNumbers(List.of("001"))
                .build();

        //when & then
        mockMvc.perform(
                        post("/api/v1/orders/new")
                                .content(objectMapper.writeValueAsString(request)) // .content()안에 request값을 직렬화시켜서 넣어주면 된다.
                                .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))  // jsonPath를 검증한다.
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @DisplayName("신규 주문을 등록할때 상품번호는 1개 이상이어야 한다.")
    @Test
    void createOrderWithEmptyProductNumbers() throws Exception {
        //given
        OrderCreateRequest request = OrderCreateRequest.builder()
                .productNumbers(List.of())
                .build();

        //when & then
        mockMvc.perform(
                        post("/api/v1/orders/new")
                                .content(objectMapper.writeValueAsString(request)) // .content()안에 request값을 직렬화시켜서 넣어주면 된다.
                                .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400")) // jsonPath를 검증한다.
                .andExpect(jsonPath("$.status").value("BAD_REQUEST")) // jsonPath를 검증한다.
                .andExpect(jsonPath("$.message").value("상품 번호 리스트는 필수입니다.")) // jsonPath를 검증한다.
                .andExpect(jsonPath("$.data").isEmpty()
                );

    }


}