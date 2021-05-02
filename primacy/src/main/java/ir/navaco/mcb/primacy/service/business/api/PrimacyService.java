package ir.navaco.mcb.primacy.service.business.api;

import ir.navaco.mcb.primacy.model.DownloadTrack;
import ir.navaco.mcb.primacy.model.dto.ComponentDto;
import ir.navaco.mcb.primacy.model.dto.CardInfoDto;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public interface PrimacyService {
    //todo method return byte[] for test
    Long printBitmapDefault(CardInfoDto dto);
    byte[] viewBitmapDefault(CardInfoDto dto);
    String base64BitmapDefault(CardInfoDto dto);

    String base64Bitmap(List<ComponentDto> dto);
    byte[] viewBitmap(List<ComponentDto> dto);
    Long printBitmap(List<ComponentDto> dto);

    Long printUploadBitmap(String base64);
    Long encodeTracks(DownloadTrack downloadTrack);
}
