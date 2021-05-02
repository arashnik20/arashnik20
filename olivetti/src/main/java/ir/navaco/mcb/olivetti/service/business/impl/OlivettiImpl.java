package ir.navaco.mcb.olivetti.service.business.impl;

import ir.navaco.mcb.olivetti.model.StringCommand;
import ir.navaco.mcb.olivetti.service.business.api.OlivettiService;
import ir.navaco.mcb.olivetti.service.business.api.Pr4Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OlivettiImpl implements OlivettiService {

    @Autowired
    private Pr4Service pr4Service;

    @Override
    public Long print(String str) {
        return pr4Service.print(str);
    }

    @Override
    public Long printSeries(List<StringCommand> list) {
        return pr4Service.printSeries(list);
    }
}
