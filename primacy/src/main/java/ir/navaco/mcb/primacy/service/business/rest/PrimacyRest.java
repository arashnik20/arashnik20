package ir.navaco.mcb.primacy.service.business.rest;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ir.navaco.mcb.primacy.model.DownloadTrack;
import ir.navaco.mcb.primacy.model.dto.ComponentDto;
import ir.navaco.mcb.primacy.model.dto.CardInfoDto;
import ir.navaco.mcb.primacy.model.enums.response.ResponseCode;
import ir.navaco.mcb.primacy.service.Response;
import ir.navaco.mcb.primacy.service.business.api.PrimacyService;
import ir.navaco.mcb.primacy.service.view.BaseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/primacy")
@Api(value = "User Resource REST Endpoint")
public class PrimacyRest {

    @Autowired
    private PrimacyService primacyService;

    @ApiOperation(value = "${printUploadBitmap.value}", notes = "${printUploadBitmap.notes}")
    @PostMapping("/printUploadBitmap")
    @JsonView(BaseView.ResponseView.class)
    @ResponseBody
    public Response<String ,Long> printUploadBitmap(String base64) {
        return new Response<>(null, base64,  primacyService.printUploadBitmap(base64), ResponseCode.GENERAL);
    }

    @ApiOperation(value = "${encodeTracks.value}", notes = "${encodeTracks.notes}")
    @PostMapping("/encodeTracks")
    @JsonView(BaseView.ResponseView.class)
    @ResponseBody
    public Response<DownloadTrack ,Long> encodeTracks(@RequestBody DownloadTrack downloadTrack) {
        return new Response<>(null, downloadTrack, primacyService.encodeTracks(downloadTrack), ResponseCode.GENERAL);


    }

    @ApiOperation(value = "${base64BitmapDefault.value}", notes = "${base64BitmapDefault.notes}")
    @PostMapping(value = "/base64BitmapDefault")
    @JsonView(BaseView.ResponseView.class)
    @ResponseBody
    public Response<CardInfoDto,String> base64BitmapDefault(@RequestBody CardInfoDto dto) {
        return new Response<>(null,dto,primacyService.base64BitmapDefault(dto),ResponseCode.GENERAL);
    }

    @ApiOperation(value = "${viewBitmapDefault.value}", notes = "${viewBitmapDefault.notes}")
    @PostMapping(value = "/viewBitmapDefault" ,produces = MediaType.IMAGE_JPEG_VALUE)
    @JsonView(BaseView.ResponseView.class)
    @ResponseBody
    public byte[] viewBitmapDefault (@RequestBody CardInfoDto dto) {
           return primacyService.viewBitmapDefault(dto);
    }

    @ApiOperation(value = "${printBitmapDefault.value}", notes = "${printBitmapDefault.notes}")
    @PostMapping(value = "/printBitmapDefault" )
    @JsonView(BaseView.ResponseView.class)
    @ResponseBody
    public Response<CardInfoDto,Long> printBitmapDefault(@RequestBody CardInfoDto dto) {
        return new Response<>(null,dto,primacyService.printBitmapDefault(dto),ResponseCode.GENERAL);

    }

    @ApiOperation(value = "${viewBitmap.value}", notes = "${viewBitmap.notes}")
    @PostMapping(value = "/viewBitmap"  ,produces = MediaType.IMAGE_JPEG_VALUE)
    @JsonView(BaseView.ResponseView.class)
    @ResponseBody
    public  byte[] viewBitmap(@RequestBody List<ComponentDto> dto) {
        return primacyService.viewBitmap(dto);
    }

    @ApiOperation(value = "${base64Bitmap.value}", notes = "${base64Bitmap.notes}")
    @PostMapping(value = "/base64Bitmap")
    @JsonView(BaseView.ResponseView.class)
    @ResponseBody
    public Response<List<ComponentDto> ,String> base64Bitmap(@RequestBody List<ComponentDto> dto) {
        return new Response<>(null,dto,primacyService.base64Bitmap(dto),ResponseCode.GENERAL);
    }

    @ApiOperation(value = "${printBitmap.value}", notes = "${printBitmap.notes}")
    @PostMapping(value = "/printBitmap")
    @JsonView(BaseView.ResponseView.class)
    @ResponseBody
    public Response<List<ComponentDto> ,Long> printBitmap(@RequestBody List<ComponentDto> dto) {
        return new Response<>(null,dto,primacyService.printBitmap(dto),ResponseCode.GENERAL);
    }


}
