package ir.navaco.mcb.primacy.service.business.impl;

import ir.navaco.mcb.primacy.exception.ExceptionBase;
import ir.navaco.mcb.primacy.model.BitmapCommand;
import ir.navaco.mcb.primacy.model.DownloadTrack;
import ir.navaco.mcb.primacy.model.enums.evolis.*;
import ir.navaco.mcb.primacy.service.business.api.CreditCardBitmap;
import ir.navaco.mcb.primacy.service.business.api.CreditCardPrinter;
import ir.navaco.mcb.primacy.service.business.api.IomemService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author arash nikakhlagh [arash.nikakhlagh@gmail.com]
 */

@Service
public class IomemServiceImpl implements IomemService {

    private static final Logger logger = LoggerFactory.getLogger(IomemServiceImpl.class);

    @Autowired
    private CreditCardBitmap creditCardBitmap ; //= new CreditCardBitmapImpl();

    @Autowired
    private CreditCardPrinter creditCardPrinter ;

    @Value("${printerName:Evolis Primacy}")
    private String printerName ;


    @Override
    public synchronized Long printBitmap(byte[] bitmap) {
        if (bitmap == null || bitmap.length <= 0) {
            logger.error("pic_is_null");
            throw new ExceptionBase("pic_is_null");
        }
        creditCardPrinter.open(printerName)
                .parameterErrorManagement(2)
                .sequenceInsertion()
                .parameterRibbon(ParameterRibbon.TWO_PANEL_RIBBON)
                .sequenceStart()
                .writeClearBitmap(WriteClearBitmap.BLACK_RESIN_BITMAP)
                .writeClearBitmap(WriteClearBitmap.OVERLAY_BITMAP)
                .parameterContrast(ParameterContrast.Black, ParameterContrastOperate.OTHER, 10)
                .downloadingMonochromeBitmap(DownloadingMonochromeBitmap.BLACK_MONOCHROME_BITMAP, 0, 648 , 0, bitmap)
                .sequenceEnd()
                .writeOnDevice()
                .close();
        return 0l;
    }

    @Override
    public synchronized Long printBitmap(String base64Bitmap) {
        byte[] bitmap = Base64.decodeBase64(base64Bitmap);
        return printBitmap(bitmap);
    }

    @Override
    public synchronized Long encodeTracks(DownloadTrack downloadTrack) {
        creditCardPrinter.open(printerName)
                .parameterErrorManagement(2)
                .downloadTrack1(downloadTrack.getTrack1())
                .downloadTrack2(downloadTrack.getTrack2())
                .downloadTrack3(downloadTrack.getTrack3())
                .sequenceWriteMagneticTrack()
                .sequenceStart()
                .sequenceEnd()
                .writeOnDevice()
                .close();
        return 0l;
    }

    @Override
    public byte[] createBitmap(List<BitmapCommand> bitmapCommands) {
        return creditCardBitmap.createBitmap(bitmapCommands);
    }
}
