package ir.navaco.mcb.primacy.model.enums.response;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author alireza khatami doost [alireza.khtm@gmail.com]
 */
public enum ResponseCode implements Convertible<Long> {
    GENERAL("GENERAL", 0L),
    EXCEPTION("EXCEPTION", 1L);

    private String value;
    private Long code;

    ResponseCode(String value, Long code){
        this.value = value;
        this.code = code;
    }

    @Override
    @JsonValue
    public Long getValue() {
        return this.code;
    }
}
