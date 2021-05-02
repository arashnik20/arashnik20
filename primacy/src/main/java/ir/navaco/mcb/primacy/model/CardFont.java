package ir.navaco.mcb.primacy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author arash nikakhlagh [arash.nikakhlagh@gmail.com]
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardFont {

    private String name;
    private int style;
    private int size;
}
