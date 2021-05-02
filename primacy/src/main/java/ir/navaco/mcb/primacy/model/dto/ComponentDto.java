package ir.navaco.mcb.primacy.model.dto;

import ir.navaco.mcb.primacy.exception.ExceptionBase;
import ir.navaco.mcb.primacy.model.BitmapType;
import ir.navaco.mcb.primacy.model.CardFont;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComponentDto {

    private BitmapType bitmapType;
    private String value;
    private int x;
    private int y;
    private int width;
    private int height;
    private CardFont font;
    // todo set Exception
    public void setX(int x) {
        if(x > 1016 || x < 0 )
            throw new ExceptionBase("x_not_range");
        this.x = x;
    }

    public void setY(int y) {
        if(y > 648 || y < 0 )
            throw new ExceptionBase("y_not_range");
        this.y = y;
    }

    public void setWidth(int width) {
        if(width > 1016 || width < 0 )
            throw new ExceptionBase("width_not_range");
        this.width = width;
    }

    public void setHeight(int height) {
        if(height > 648 || height < 0 )
            throw new ExceptionBase("height_not_range");
        this.height = height;
    }
}
