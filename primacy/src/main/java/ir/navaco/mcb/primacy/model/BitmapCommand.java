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
public class BitmapCommand {

    private BitmapType bitmapType;
    private String value;
    private int x;
    private int y;
    private int width;
    private int height;
    private CardFont font;
}
