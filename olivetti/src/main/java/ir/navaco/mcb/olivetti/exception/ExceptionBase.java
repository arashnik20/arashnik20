package ir.navaco.mcb.olivetti.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionBase extends RuntimeException {

    private String code;
    private String message;
    private Object[] arguments;
    private ExceptionCode exceptionCode;

    public ExceptionBase(String message) {
        super(message);
        this.message = message;
    }

    public ExceptionBase(String message, Throwable cause){
        super(message, cause);
    }

    public ExceptionBase(Throwable cause){
        super(cause);
    }
}
