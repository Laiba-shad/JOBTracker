//package com.example.jobtrackr.exception;
//
//import com.jobtrackr.server.response.dto.jobtrackr.LoginResponse;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
//    public ResponseEntity<LoginResponse> handleAuthException(Exception ex) {
//        LoginResponse response = new LoginResponse(
//                401,
//                "Invalid email or password"
//        );
//        return ResponseEntity.status(401).body(response);
//    }
//}
