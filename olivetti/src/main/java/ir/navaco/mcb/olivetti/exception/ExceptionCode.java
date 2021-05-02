package ir.navaco.mcb.olivetti.exception;

import com.fasterxml.jackson.annotation.JsonValue;
import ir.navaco.mcb.olivetti.model.enums.response.Convertible;

import java.util.Arrays;

/**
 * @author alireza khatami doost [alireza.khtm@gmail.com]
 */
public enum ExceptionCode implements Convertible<String> {

    NOT_FOUND("not found", 1L),
    ACCESS_DENIED("access denied", 2L),
    INTERNAL_ERROR("internal-error", 3L),
    INPUT_ARGUMENT_ERROR("input argument is not true", 4L),
    CONNECTION_INTERRUPT("connection is not available", 5L),
    NOT_SAME_VERSION("not same version", 6L),
    REQUEST_TIME_OUT("request time out", 7L),
    NOT_READABLE("not redable", 8L),
    BUSINESS_EXCEPTION("business exception", 9L),
    METHOD_NOT_SUPPORTED("method not supported", 10L),
    TYPE_NOT_SUPPORTED("type not supported", 11L),
    TYPE_NOT_ACCEPTABLE("type not acceptable", 12L),
    MISSING_PATH_VARIABLE("missing path variable", 13L),
    MISSING_SERVLET_REQUEST_PARAMETER("missing servlet request parameter", 14L),
    SERVLET_REQUEST_BINDING_EXCEPTION("servlet request binding exception", 15L),
    CONVERSION_NOT_SUPPORTED("conversion not supported", 16L),
    TYPE_MIS_MATCH("type mismatch", 17L),
    HTTP_MESSAGE_NOT_WRITABLE("http message not writable", 18L),
    METHOD_ARGUMENT_NOT_VALID("method argument not valid", 19L),
    MISSING_SERVLET_REQUEST_PART("missing servlet request part", 20L),
    BIND_EXCEPTION("bind exception", 21L),
    NO_HANDLER_FOUND_EXCEPTION("no handler found exception", 22L),
    ASYNC_REQUEST_TIMEOUT_EXCEPTION("async request timeout exception", 23L),
    EXCEPTION_INTERNAL("exception internal", 24L);

    private String value;
    private Long code;

    ExceptionCode(String value, Long code) {
        this.value = value;
        this.code = code;
    }

    public ExceptionCode findByValue(String vlaue) {
        return Arrays.stream(values()).filter(exceptionCode -> exceptionCode.value.equals(value)).findFirst().get();
    }

    @Override
    public String getValue() {
        return value;
    }

    public Long getCode() {
        return code;
    }

    @JsonValue
    public void setCode(Long code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return String.format("code: %d - [%s]", this.code, this.value);
    }
}
