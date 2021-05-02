package ir.navaco.mcb.primacy.service.business.impl;


import ij.ImagePlus;
import ij.process.ImageProcessor;
import ir.navaco.mcb.primacy.exception.ExceptionBase;
import ir.navaco.mcb.primacy.model.BitmapCommand;
import ir.navaco.mcb.primacy.model.BitmapType;
import ir.navaco.mcb.primacy.service.business.api.CreditCardBitmap;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author arash nikakhlagh [arash.nikakhlagh@gmail.com]
 */
@Component
public class CreditCardBitmapImpl implements CreditCardBitmap {
    private static final int cardWidth = 1016;
    private static final int cardHeight = 648;

    @Override
    public byte[] createBitmap(List<BitmapCommand> bitmapCommands) {
        BufferedImage creditCardImage = new BufferedImage(cardWidth, cardHeight, BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D graphicsCreditCardImage = creditCardImage.createGraphics();
        /* todo change fore and back color
        Color background = new Color(255, 255, 255);
        Color foreground = new Color(0, 0, 0);
        graphicsCreditCardImage.setColor(background);
        graphicsCreditCardImage.fillRect(0, 0, cardWidth, cardHeight);
        graphicsCreditCardImage.setColor(foreground);*/

        for (BitmapCommand bitmapCommand : bitmapCommands) {
            if (bitmapCommand != null) {
                if (bitmapCommand.getBitmapType().equals(BitmapType.TEXT)) {

                    Font font = bitmapCommand.getFont() != null
                            ? new Font(bitmapCommand.getFont().getName(), bitmapCommand.getFont().getStyle(), bitmapCommand.getFont().getSize())
                            : null;
                    graphicsCreditCardImage.setFont(font);

                    graphicsCreditCardImage.drawString(bitmapCommand.getValue(), bitmapCommand.getX(), bitmapCommand.getY());

                } else {
                    BufferedImage image = createByteBinary(bitmapCommand.getValue());
                    int width = bitmapCommand.getWidth() == 0 ? image.getWidth() : bitmapCommand.getWidth();
                    int height = bitmapCommand.getHeight() == 0 ? image.getHeight() : bitmapCommand.getHeight();

                    graphicsCreditCardImage.drawImage(image, bitmapCommand.getX(), bitmapCommand.getY(), width, height, null);
                }
            }
        }

        graphicsCreditCardImage.dispose();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {

            ImageIO.write(creditCardImage, "bmp", byteArrayOutputStream);

        } catch (IOException e) {
            e.printStackTrace();
            throw new ExceptionBase("can_not_create_pic");
        }

        return byteArrayOutputStream.toByteArray();
    }

    private BufferedImage createByteBinary(String base64Bitmap) {
        byte[] bitmap = Base64.decodeBase64(base64Bitmap);

        return createByteBinary(bitmap);
    }

    private BufferedImage createByteBinary(byte[] byteArrayBitmap) {
        Image byteBinaryImage = null;
        try {
            BufferedImage byteImage = ImageIO.read(new ByteArrayInputStream(byteArrayBitmap));
            if (byteImage == null) {
                throw new ExceptionBase("pic_is_null");
            }

            byteBinaryImage = new BufferedImage(byteImage.getWidth(), byteImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY);

            Graphics2D graphicsByteBinaryImage = ((BufferedImage) byteBinaryImage).createGraphics();
            graphicsByteBinaryImage.drawImage(byteImage, 0, 0, byteImage.getWidth(), byteImage.getHeight(), null);
            graphicsByteBinaryImage.dispose();

        } catch (IOException e) {
            e.printStackTrace();
            throw new ExceptionBase("can_not_create_pic");
        }

        ImagePlus imagePlus = new ImagePlus();
        imagePlus.setImage(byteBinaryImage);
        ImageProcessor processor = imagePlus.getProcessor();

        processor.invertLut();

        return processor.getBufferedImage();
    }
}
