package ir.navaco.mcb.olivetti.model.dto;

import java.util.HashMap;

/**
 * @author arash nikakhlagh [arash.nikakhlagh@gmail.com]
 */

public class Pr4Map extends HashMap<String,byte[]> {

    {
        put("LINE", new byte[]{0x0A}); // Print and line feed
        // Align
        put("LEFT", new byte[]{0x1B, 0x61, 0x00}); // Left justification
        put("CENTER", new byte[]{0x1B, 0x61, 0x01}); // Centering
        put("RIGHT", new byte[]{0x1B, 0x61, 0x02}); // Right justification

        put("DOUBLE_HEIGHT",new byte[]{0x1b, 0x21, 0x10}); //Double height text *
        put("DOUBLE_WIDTH",new byte[]{0x1b, 0x21, 0x20}); //Double width text *
        put("QUAD_AREA",new byte[]{0x1b, 0x21, 0x30}); // Quad area text *
        put("UNDERLINE",new byte[]{0x1b, 0x2d, 0x01}); // Underline font 1-dot ON *
        put("EJECT_PAPER", new byte[]{0x0c}); //Print and eject slip paper
        put("BLANK", new byte[]{0x32}); //space
    }
    private byte[] getByteCode(String key){
        return get(key);
    }
}
