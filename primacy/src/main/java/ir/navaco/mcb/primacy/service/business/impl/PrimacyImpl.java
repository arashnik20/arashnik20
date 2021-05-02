package ir.navaco.mcb.primacy.service.business.impl;

import ir.navaco.mcb.primacy.model.BitmapCommand;
import ir.navaco.mcb.primacy.model.BitmapType;
import ir.navaco.mcb.primacy.model.CardFont;
import ir.navaco.mcb.primacy.model.DownloadTrack;
import ir.navaco.mcb.primacy.model.dto.ComponentDto;
import ir.navaco.mcb.primacy.model.dto.CardInfoDto;
import ir.navaco.mcb.primacy.service.business.api.IomemService;
import ir.navaco.mcb.primacy.service.business.api.PrimacyService;
import ir.navaco.mcb.primacy.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Component
public class PrimacyImpl implements PrimacyService {

    private static final Logger logger = LoggerFactory.getLogger(PrimacyImpl.class);

    @Autowired
    private IomemService iomemService;

    @Override
    public byte[] viewBitmapDefault(CardInfoDto dto) {

        List<BitmapCommand>  bitmapCommands = new ArrayList<>();

        String name = Optional.ofNullable(dto.getName()).orElse("");
        String xName = name.equals("") ? "1" : name ;
        bitmapCommands.add(BitmapCommand.builder()
                .bitmapType(BitmapType.TEXT)
                .value(name)
                .x(870 - (xName.trim().length() * 23))
                .y(460)
                .font(new CardFont("B Nazanin", Font.BOLD, 50))
                .build());

        String expireDate = DateUtils.convertToJalaliString(DateUtils.convertToLocalDate(dto.getExpireDate(), "yyyy-MM-dd")).replaceAll("-", "/");

            bitmapCommands.add(BitmapCommand.builder()
                    .bitmapType(BitmapType.TEXT)
                    .value("تاریخ انقضا : ".concat(Optional.ofNullable(expireDate.substring(0,expireDate.length()-3)).orElse("")))
                    .x(40)
                    .y(605)
                    .font(new CardFont("B Nazanin", Font.BOLD, 40))
                    .build());

                bitmapCommands.add(BitmapCommand.builder()
                        .bitmapType(BitmapType.TEXT)
                        .value("cvv2 : ".concat(Optional.ofNullable(dto.getCvv2()).orElse("")))
                        .x(750)
                        .y(605)
                        .font(new CardFont("Arial", Font.BOLD, 40))
                        .build());

                    bitmapCommands.add(BitmapCommand.builder()
                            .bitmapType(BitmapType.TEXT)
                            .value(digitSeperator(Optional.ofNullable(dto.getCardNo()).orElse("")))
                            .x(140)
                            .y(400)
                            .font(new CardFont("Arial", Font.BOLD, 70))
                            .build());


            bitmapCommands.add(BitmapCommand.builder()
                    .bitmapType(BitmapType.TEXT)
                    .value("IR".concat(Optional.ofNullable(dto.getSheba()).orElse("")))
                    .x(100)
                    .y(200)
                    .font(new CardFont("Arial", Font.BOLD, 50))
                    .build());

        // use picture
       if(dto.getImage() != null) {
            byte[] bitmap = dto.getImage(); //Files.readAllBytes(Paths.get("test.bmp"));
            String base64Bitmap = Base64.getEncoder().encodeToString(bitmap);
           bitmapCommands.add(BitmapCommand.builder()
                    .bitmapType(BitmapType.IMAGE)
                    .value(base64Bitmap)
                    .width(50) //optional
                    .height(52) //optional
                    .x(45)
                    .y(60)
                    .build());
        }
    return iomemService.createBitmap(bitmapCommands);
    }

    @Override
    public Long printBitmapDefault(CardInfoDto dto) {
        return iomemService.printBitmap(viewBitmapDefault(dto));
    }

    @Override
    public String base64BitmapDefault(CardInfoDto dto) {
        return  Base64.getEncoder().encodeToString(viewBitmapDefault(dto));
    }

    @Override
    public byte[] viewBitmap(List<ComponentDto> componentDto) {
        List<BitmapCommand> bitmapCommands = new ArrayList<>();
        for (ComponentDto dto : componentDto) {
            bitmapCommands.add(BitmapCommand.builder()
                    .bitmapType(dto.getBitmapType())
                    .value(dto.getValue())
                    .x(dto.getX())
                    .y(dto.getY())
                    .width(dto.getWidth())
                    .height(dto.getHeight())
                    .font(dto.getFont())
                    .build());
        }
        return iomemService.createBitmap(bitmapCommands);
    }

    @Override
    public String base64Bitmap(List<ComponentDto> dto) {
        return  Base64.getEncoder().encodeToString(viewBitmap(dto));
    }

    @Override
    public Long printBitmap(List<ComponentDto> dto) {
        return iomemService.printBitmap(viewBitmap(dto));
    }

    @Override
    public Long printUploadBitmap(String base64) {
        byte[] bitmap = org.apache.tomcat.util.codec.binary.Base64.decodeBase64(base64);
        return iomemService.printBitmap(bitmap);
    }

    @Override
    public Long encodeTracks(DownloadTrack downloadTrack) {
        return iomemService.encodeTracks(downloadTrack);
    }

    private static String digitSeperator(String str){
        String result = ""  ;
        for (int i = 0 ; i < str.length(); i++) {
            int d = (i+1) % 4;
            result += str.charAt(i);
            if (d == 0) {
                result += "  " ;
            }
        }
        return result;
    }
}
