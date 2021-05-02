package ir.navaco.mcb.primacy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardInfoDto {

    private String name ;
    private String expireDate ;
    private String cvv2;
    private String cardNo ;
    private String sheba;
    private byte[] image ;
}
