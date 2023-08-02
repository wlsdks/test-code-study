package sample.cafekiosk.spring.api.controller.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.spring.ControllerTestSupport;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerTest extends ControllerTestSupport {

    @DisplayName("신규 상품을 등록한다.")
    @Test
    void createProduct() throws Exception {
        //given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("아메리카노")
                .price(4000)
                .build();

        //when & then
        mockMvc.perform(
                post("/api/v1/products/new")
                .content(objectMapper.writeValueAsString(request)) // .content()안에 request값을 직렬화시켜서 넣어주면 된다.
                .contentType(APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("신규 상품을 등록할때 상품타이은 필수값이다.")
    @Test
    void createProductWithoutType() throws Exception {
        //given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("아메리카노")
                .price(4000)
                .build();

        //when & then
        mockMvc.perform(
                        post("/api/v1/products/new")
                                .content(objectMapper.writeValueAsString(request)) // .content()안에 request값을 직렬화시켜서 넣어주면 된다.
                                .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400")) // jsonPath를 검증한다.
                .andExpect(jsonPath("$.status").value("BAD_REQUEST")) // jsonPath를 검증한다.
                .andExpect(jsonPath("$.message").value("상품 타입은 필수입니다.")) // jsonPath를 검증한다.
                .andExpect(jsonPath("$.data").isEmpty()
                );

    }

    @DisplayName("신규 상품을 등록할때 상품판매상태는 필수값이다.")
    @Test
    void createProductSellingStatus() throws Exception {
        //given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(ProductType.HANDMADE)
                .name("아메리카노")
                .price(4000)
                .build();

        //when & then
        mockMvc.perform(
                        post("/api/v1/products/new")
                                .content(objectMapper.writeValueAsString(request)) // .content()안에 request값을 직렬화시켜서 넣어주면 된다.
                                .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400")) // jsonPath를 검증한다.
                .andExpect(jsonPath("$.status").value("BAD_REQUEST")) // jsonPath를 검증한다.
                .andExpect(jsonPath("$.message").value("상품 판매상태는 필수입니다.")) // jsonPath를 검증한다.
                .andExpect(jsonPath("$.data").isEmpty()
                );

    }

    @DisplayName("신규 상품을 등록할때 상품이름은 필수값이다.")
    @Test
    void createProductWithoutName() throws Exception {
        //given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .price(4000)
                .build();

        //when & then
        mockMvc.perform(
                        post("/api/v1/products/new")
                                .content(objectMapper.writeValueAsString(request)) // .content()안에 request값을 직렬화시켜서 넣어주면 된다.
                                .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400")) // jsonPath를 검증한다.
                .andExpect(jsonPath("$.status").value("BAD_REQUEST")) // jsonPath를 검증한다.
                .andExpect(jsonPath("$.message").value("상품 이름은 필수입니다.")) // jsonPath를 검증한다.
                .andExpect(jsonPath("$.data").isEmpty()
                );

    }

    @DisplayName("신규 상품을 등록할때 상품가격은 필수값이다.")
    @Test
    void createProductWithoutPrice() throws Exception {
        //given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("아메리카노")
                .price(0)
                .build();

        //when & then
        mockMvc.perform(
                        post("/api/v1/products/new")
                                .content(objectMapper.writeValueAsString(request)) // .content()안에 request값을 직렬화시켜서 넣어주면 된다.
                                .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400")) // jsonPath를 검증한다.
                .andExpect(jsonPath("$.status").value("BAD_REQUEST")) // jsonPath를 검증한다.
                .andExpect(jsonPath("$.message").value("상품 가격은 양수여야 합니다.")) // jsonPath를 검증한다.
                .andExpect(jsonPath("$.data").isEmpty()
                );

    }

    @DisplayName("판매상품을 조회한다.")
    @Test
    void getSellingProducts() throws Exception {
        //given
        List<ProductResponse> result = List.of();

        when(productService.getSellingProducts()).thenReturn(result);

        //when & then
        mockMvc.perform(
                    get("/api/v1/products/selling")
//                            .queryParam("name", "이름")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))  // jsonPath를 검증한다.
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isArray()
                );

    }


}