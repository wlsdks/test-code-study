package sample.cafekiosk.spring.api;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {

    private int code;
    private HttpStatus status;
    private String message;
    private T data; // 성공한 응답을 제네릭으로 받는다.

    public ApiResponse(HttpStatus status, String message, T data) {
        this.code = status.value();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // 메시지를 넣어주는 메서드
    public static <T> ApiResponse<T> of(HttpStatus httpStatus, String message, T data) {
        return new ApiResponse<>(httpStatus, message, data);
    }

    // status에서 상태정보를 꺼내는 메서드
    public static <T> ApiResponse<T> of(HttpStatus httpStatus, T data) {
        return of(httpStatus, httpStatus.name(), data);
    }

    // 성공할때 전용 메서드
    public static <T> ApiResponse<T> ok(T data) {
        return of(HttpStatus.OK, data);
    }


}
