package ir.navaco.mcb.olivetti.service;

import com.fasterxml.jackson.annotation.JsonView;
import ir.navaco.mcb.olivetti.model.enums.response.ResponseCode;
import ir.navaco.mcb.olivetti.service.view.BaseView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author alireza khatami doost [alireza.khtm@gmail.com]
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Response<INPUT, OUTPUT> {
    @JsonView({BaseView.ResponseView.class})
    private String uniqueId;
    @JsonView({BaseView.ResponseView.class})
    private INPUT inputArguments;
    @JsonView({BaseView.ResponseView.class})
    private OUTPUT response;
    @JsonView({BaseView.ResponseView.class})
    private ResponseCode code = ResponseCode.GENERAL;
}
