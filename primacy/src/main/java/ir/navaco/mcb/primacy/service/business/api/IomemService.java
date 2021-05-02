package ir.navaco.mcb.primacy.service.business.api;

import ir.navaco.mcb.primacy.model.BitmapCommand;
import ir.navaco.mcb.primacy.model.DownloadTrack;

import java.util.List;

/**
 * @author arash nikakhlagh [arash.nikakhlagh@gmail.com]
 */

public interface IomemService  {
    Long printBitmap(byte[] bitmap);

    Long printBitmap(String base64Bitmap);

    Long encodeTracks(DownloadTrack downloadTrack);

    byte[] createBitmap(List<BitmapCommand> bitmapCommands);
}
