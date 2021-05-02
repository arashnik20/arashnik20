package ir.navaco.mcb.olivetti.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author arash nikakhlagh [arash.nikakhlagh@gmail.com]
 */
@Configuration
@PropertySource("classpath:local_messages_fa.properties")
public class PropertiesHandler {

}
