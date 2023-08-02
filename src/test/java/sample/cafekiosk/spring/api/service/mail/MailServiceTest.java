package sample.cafekiosk.spring.api.service.mail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import sample.cafekiosk.spring.client.mail.MailSendClient;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistory;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    // 이렇게하면 이 객체들을 Mock객체로 만들어준다. 위에 @ExtendWith 어노테이션을 적어줘야 적용된다.
//    @Mock private MailSendClient mailSendClient;
    @Spy private MailSendClient mailSendClient;
    @Mock private MailSendHistoryRepository mailSendHistoryRepository;

    // Mock 객체를 주입받아서 사용하는 다른 객체는 이렇게 Inject로 mock을 주입받도록 @InjectMocks를 선언해 준다.
    @InjectMocks private MailService mailService;

    @DisplayName("메일 전송 테스트")
    @Test
    void sendMail() {
//        Mockito.when(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
//                .thenReturn(true);

        // 위,아래의 방법이 이상해서 알아보기 쉽게 DBBMockito를 만들었음 (Stubbing은 준비단계라 given절이니 위 아래보다 이게 가장 좋은것같다.)
        BDDMockito.given(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
                .willReturn(true);

//        doReturn(true)
//                .when(mailSendClient)
//                .sendEmail(anyString(), anyString(), anyString(), anyString());

        //when
        boolean result = mailService.sendMail("", "", "", "");

        //then
        assertThat(result).isTrue();
        // mock 객체가 1번 불렸는지를 검증하는 메서드다. save는 mailSendHistoryRepository의 save메서드이고 내부의 인자를 넣어준것이다.
        Mockito.verify(mailSendHistoryRepository, Mockito.times(1)).save(any(MailSendHistory.class));

    }

}