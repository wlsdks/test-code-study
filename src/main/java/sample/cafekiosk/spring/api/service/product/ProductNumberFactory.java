package sample.cafekiosk.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sample.cafekiosk.spring.domain.product.ProductRepository;

@RequiredArgsConstructor
@Component
public class ProductNumberFactory {

    private final ProductRepository productRepository;

    /**
     * 저장할 다음 상품번호 생성 - factory로 extract 메소드를 따로 클래스로 뽑아냄 (테스트 진행을 위함)
     * 이렇게 책임을 분리(객체를 따로 생성)하면 테스트를 진행할수 있다.
     * 그리고 로직을 만든 후 extract로 추출해낸 private 메서드는 억지로 테스트할 필요가 없다. 그래도 하려면 지금처럼 객체를 따로 분리해서 진행해야 한다.
     * 그전에 우선 메서드를 따로 객체에 분리하는게 맞을지 먼저 고민하고 이런식으로 factory 클래스를 만들어서 기능을 위임하고 단독으로 테스트를 진행해라.
     */
    public String createNextProductNumber() {
        String latestProductNumber = productRepository.findLatestProductNumber();
        if (latestProductNumber == null) {
            return "001";
        }

        int latestProductNumberInt = Integer.parseInt(latestProductNumber);
        int nextProductNumberInt = latestProductNumberInt + 1;

        // 9 -> 009, 10 -> 010
        return String.format("%03d", nextProductNumberInt);
    }

}
