package sample.cafekiosk.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import sample.cafekiosk.spring.api.controller.order.OrderController;
import sample.cafekiosk.spring.api.controller.product.ProductController;
import sample.cafekiosk.spring.api.service.order.OrderService;
import sample.cafekiosk.spring.api.service.product.ProductService;

/**
 * 이렇게 컨트롤러 테스트도 환경을 통합해서 tomcat이 여러번 돌아가지 않도록 해준다.
 * 새로운 컨트롤러 테스트가 추가될때마다 여기에 주입받을 내용들을 넣어주면 된다.
 */
@WebMvcTest(controllers = {
        OrderController.class,
        ProductController.class
})
public abstract class ControllerTestSupport {

    @Autowired protected MockMvc mockMvc;
    @MockBean protected OrderService orderService;
    @Autowired protected ObjectMapper objectMapper;       // json과 object간의 직렬/역직렬화를 위해 선언한다.
    @MockBean protected ProductService productService;    // @MockBean은 container에 mock객체를 넣어주는 역할이다.

}
