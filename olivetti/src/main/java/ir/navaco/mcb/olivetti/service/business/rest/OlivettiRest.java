package ir.navaco.mcb.olivetti.service.business.rest;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ir.navaco.mcb.olivetti.model.StringCommand;
import ir.navaco.mcb.olivetti.model.enums.response.ResponseCode;
import ir.navaco.mcb.olivetti.service.Response;
import ir.navaco.mcb.olivetti.service.business.api.OlivettiService;
import ir.navaco.mcb.olivetti.service.view.BaseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author arash nikakhlagh [arash.nikakhlagh@gmail.com]
 */

@RestController
@RequestMapping("/olivetti")
@Api(value = "User Resource REST Endpoint")
public class OlivettiRest {

    @Qualifier("olivettiImpl")
    @Autowired(required = true)
    private OlivettiService service;

    @ApiOperation(value = "${print.value}", notes = "${print.notes}")
    @PostMapping("/print")
    @JsonView(BaseView.ResponseView.class)

    public Response<String ,Long> printString(@RequestBody String str) {
        return new Response<>(null, str,  service.print(str), ResponseCode.GENERAL);
    }

    @ApiOperation(value = "${printSeries.value}", notes = "${printSeries.notes}")
    @PostMapping("/printSeries")
    @JsonView(BaseView.ResponseView.class)
    @ResponseBody
    public Response<List<StringCommand> ,Long> printSeries(@RequestBody List<StringCommand> list) {
        return new Response<>(null, list,  service.printSeries(list), ResponseCode.GENERAL);
    }


}
