package ir.navaco.mcb.primacy.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionModel {
    private String message;
    private String code;
    private Object stackTrace;
    private ExceptionCode exceptionCode;
}
