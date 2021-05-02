package ir.navaco.core.primacy;

import ir.navaco.mcb.primacy.model.BitmapCommand;
import ir.navaco.mcb.primacy.model.BitmapType;
import ir.navaco.mcb.primacy.model.CardFont;
import ir.navaco.mcb.primacy.model.DownloadTrack;
import ir.navaco.mcb.primacy.service.business.api.IomemService;
import ir.navaco.mcb.primacy.service.business.impl.CreditCardBitmapImpl;
import ir.navaco.mcb.primacy.service.business.impl.IomemJNIImpl;
import ir.navaco.mcb.primacy.service.business.impl.IomemServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
        IomemServiceImpl.class,
        IomemJNIImpl.class,
        CreditCardBitmapImpl.class
})
public class IomemServiceImplTest {
    @Autowired
    private IomemService iomemService;

    private DownloadTrack downloadTrack;

    @Before
    public void setup() {
        downloadTrack = DownloadTrack.builder()
                .track1("arash")
                .track2("5558423=5558423")
                .track3("1066")
                .build();
    }

    @Test
    public void printBitmap() throws IOException {
        byte[] bitmap = Files.readAllBytes(Paths.get("credit-card.bmp"));
        iomemService.printBitmap(bitmap);
    }

    @Test
    public void testPrintBitmap() throws IOException {
        byte[] bitmap = Files.readAllBytes(Paths.get("credit-card.bmp"));
        String base64Bitmap = Base64.getEncoder().encodeToString(bitmap);

        iomemService.printBitmap(base64Bitmap);
    }

    @Test
    public void encodeTracks() {
        iomemService.encodeTracks(downloadTrack);
    }

    @Test
    public void createBitmap() throws IOException {
        List<BitmapCommand> bitmapCommands = new ArrayList<>();

        bitmapCommands.add(BitmapCommand.builder()
                .bitmapType(BitmapType.TEXT)
                .value("آرش نیک اخلاق")
                .x(600)
                .y(460)
                .font(new CardFont("B Nazanin", Font.BOLD, 50))
                .build());


        bitmapCommands.add(BitmapCommand.builder()
                .bitmapType(BitmapType.TEXT)
                .value( "تاریخ انقضا : ".concat("1399/12/30"))
                .x(40)
                .y(628)
                .font(new CardFont("B Nazanin", Font.BOLD, 40))
                .build());

        bitmapCommands.add(BitmapCommand.builder()
                .bitmapType(BitmapType.TEXT)
                .value("cvv")
                .x(750)
                .y(628)
                .font(new CardFont("Arial", Font.BOLD, 40))
                .build());

        bitmapCommands.add(BitmapCommand.builder()
                .bitmapType(BitmapType.TEXT)
                .value("2 : 2579")
                .x(820)
                .y(628)
                .font(new CardFont("B Nazanin", Font.BOLD, 40))
                .build());

        bitmapCommands.add(BitmapCommand.builder()
                .bitmapType(BitmapType.TEXT)
                .value(digitSeperator("1245526814527895"))
                .x(190)
                .y(400)
                .font(new CardFont("B Nazanin", Font.BOLD, 70))
                .build());

        bitmapCommands.add(BitmapCommand.builder()
                .bitmapType(BitmapType.TEXT)
                .value("IR")
                .x(130)
                .y(200)
                .font(new CardFont("Arial", Font.BOLD, 50))
                .build());

        bitmapCommands.add(BitmapCommand.builder()
                .bitmapType(BitmapType.TEXT)
                .value(digitSeperator("123450000003456789123456"))
                .x(180)
                .y(200)
                .font(new CardFont("B Nazanin", Font.BOLD, 55))
                .build());

        BufferedImage imag = ImageIO.read(new ByteArrayInputStream(iomemService.createBitmap(bitmapCommands)));
        ImageIO.write(imag, "jpg", new File("snap.jpg"));
    }

    private static String digitSeperator(String str) {
        String result = ""  ;
        for (int i = 0 ; i < str.length(  ); i++) {
            int d = (i+1) % 4;
            result += str.charAt(i);
            if (d == 0) {
                result += "  " ;
            }
        }
        return result;
    }
}
