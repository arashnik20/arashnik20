package ir.navaco.mcb.olivetti.service.business.api;

import ir.navaco.mcb.olivetti.model.StringCommand;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author arash nikakhlagh [arash.nikakhlagh@gmail.com]
 */
@Component
public interface Pr4Service {
    Long print(String str);
    Long printSeries(List<StringCommand> list);
}
