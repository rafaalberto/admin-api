package br.com.api.admin.exception;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public class ErrorResponse {

    private final int statusCode;
    private final List<ApiError> errors;

    public ErrorResponse(int statusCode, List<ApiError> errors) {
        this.statusCode = statusCode;
        this.errors = errors;
    }

    static ErrorResponse of(HttpStatus httpStatus, List<ApiError> errors) {
        return new ErrorResponse(httpStatus.value(), errors);
    }

    static ErrorResponse of(HttpStatus status, ApiError error){
        return of(status, Collections.singletonList(error));
    }

    @JsonAutoDetect(fieldVisibility = ANY)
    static class ApiError {

        private final String code;
        private final String message;

        public ApiError(String code, String message) {
            this.code = code;
            this.message = message;
        }
    }

}
