package insper.collie.company;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import insper.collie.company.exceptions.CompanyNotFoundException;

@ControllerAdvice
public class CompanyControllerAdvice extends ResponseEntityExceptionHandler{

    @ExceptionHandler({CompanyNotFoundException.class})
    private ResponseEntity<String> notFoundHandler(RuntimeException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
