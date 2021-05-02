package ir.navaco.mcb.olivetti.model;

import ir.navaco.mcb.olivetti.model.enums.pr4.FontStyle;
import ir.navaco.mcb.olivetti.model.enums.pr4.LanguageType;
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

public class StringCommand {

    private LanguageType languageType;
    private String text;
    private FontStyle style;

}
