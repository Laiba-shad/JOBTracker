package com.jobtrackr.server.handler;
import com.jobtrackr.server.dto.response.ApiResponse;
import com.jobtrackr.server.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//public class GlobalExceptionHandler {
////    package com.jobtrackr.server.exception;

    @RestControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(UserAlreadyExistsException.class)
        public ResponseEntity<ApiResponse<Void>> handleUserExists(UserAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponse.error(409, ex.getMessage()));
        }

        @ExceptionHandler(UserNotVerifiedException.class)
        public ResponseEntity<ApiResponse<Void>> handleNotVerified(UserNotVerifiedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.error(403, ex.getMessage()));
        }

        @ExceptionHandler(OtpExpiredException.class)
        public ResponseEntity<ApiResponse<Void>> handleOtpExpired(OtpExpiredException ex) {
            return ResponseEntity.status(HttpStatus.GONE)
                    .body(ApiResponse.error(410, ex.getMessage()));
        }

        @ExceptionHandler(OtpInvalidException.class)
        public ResponseEntity<ApiResponse<Void>> handleOtpInvalid(OtpInvalidException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(400, ex.getMessage()));
        }

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ApiResponse<Void>> handleNotFound(ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(404, ex.getMessage()));
        }

        // Handles @Valid failures — wrong/missing fields in request body
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiResponse<Void>> handleValidation(MethodArgumentNotValidException ex) {
            String message = ex.getBindingResult().getFieldErrors().stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .findFirst().orElse("Validation failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(400, message));
        }

        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<ApiResponse<Void>> handleGeneral(RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(500, ex.getMessage()));
        }
    }
