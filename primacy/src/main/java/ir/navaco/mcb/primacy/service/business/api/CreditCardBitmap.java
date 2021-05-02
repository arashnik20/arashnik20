package ir.navaco.mcb.primacy.service.business.api;


import ir.navaco.mcb.primacy.model.BitmapCommand;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author arash nikakhlagh [arash.nikakhlagh@gmail.com]
 */
public interface CreditCardBitmap {
    byte[] createBitmap(List<BitmapCommand> bitmapCommands);
}
