package ir.navaco.mcb.olivetti.exception;

import ir.navaco.mcb.olivetti.model.enums.response.ResponseCode;
import ir.navaco.mcb.olivetti.service.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.core.env.Environment;
import java.util.Optional;

@ControllerAdvice
public class ExceptionBaseHandler extends ResponseEntityExceptionHandler {

    private Response<Object, Object> response = new Response<>();

    @Value("${unknown}")
    private String unknown;

    @Autowired
    private Environment env;

    @ExceptionHandler(ExceptionBase.class)
    protected ResponseEntity<Object> handleExceptionBase(ExceptionBase ex) {
        //todo need to devlope ExceptionCode
        response.setInputArguments(ex.getArguments());
        response.setResponse(ExceptionModel.builder()
                .message(Optional.ofNullable(env.getProperty(ex.getMessage())).orElse(unknown))
                .code(ex.getCode())
                .exceptionCode(ExceptionCode.BUSINESS_EXCEPTION)
                .build());
       response.setCode(ResponseCode.EXCEPTION);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
