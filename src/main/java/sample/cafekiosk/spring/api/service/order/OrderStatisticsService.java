package sample.cafekiosk.spring.api.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.cafekiosk.spring.api.service.mail.MailService;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.order.OrderRepository;
import sample.cafekiosk.spring.domain.order.OrderStatus;

import java.time.LocalDate;
import java.util.List;

/**
 * 메일전송같은 긴 작업의 서비스에서는 @Transactional을 걸지않는것이 좋다.
 */
@RequiredArgsConstructor
@Service
public class OrderStatisticsService {

    private final OrderRepository orderRepository;
    private final MailService mailService;

    /**
     * 해달 날짜의 주문 통계를 이메일로 보낸다.
     * @param orderDate
     * @param email
     */
    public boolean sendOrderStatisticsMail(LocalDate orderDate, String email) {
        // 해당 일자에 결제완료된 주문들을 가져와서
        List<Order> orders = orderRepository.findOrdersBy(
            orderDate.atStartOfDay(), //17일 0시부터
            orderDate.plusDays(1).atStartOfDay(), //18일 0시까지
            OrderStatus.PAYMENT_COMPLETED
        );

        // 총 매출합계를 계산하고
        int totalAmount = orders.stream()
            .mapToInt(Order::getTotalPrice)
            .sum();

        // 메일 전송을 한다.
        boolean result = mailService.sendMail(
            "no-reply@cafekiosk.com",
            email,
            String.format("[매출통계] %s", orderDate),
            String.format("총 매출 합계는 %s원 입니다.", totalAmount)
        );

        if (!result) {
            throw new IllegalArgumentException("매출 통계 메일 전송에 실패했습니다.");
        }

        return true;
    }

}
