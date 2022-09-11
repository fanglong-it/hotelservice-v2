package fiveman.hotelservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.access.AccessDeniedException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({AppException.class})
    public ResponseEntity<String> AppException(AppException e){
        return  ResponseEntity.status(e.getCode()).body(e.getMessage());
    }

    // Có thêm các @ExceptionHandler khác...
    
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> AccessDeniedException(AccessDeniedException e) {
    	logger.info(e.getMessage());
        return ResponseEntity.status(403).body("Access is Denied");
    }

    // Nên bắt cả Exception.class
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnwantedException(Exception e) {
    	logger.info(e.getMessage());
        return ResponseEntity.status(500).body("Internal Servcer Error");
    }
}

