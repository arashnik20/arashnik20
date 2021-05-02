package ir.navaco.mcb.primacy.config;

import ir.navaco.mcb.primacy.PrimacyConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * @author arash nikakhlagh [arash.nikakhlagh@gmail.com]
 */
@Configuration
@PropertySource("classpath:local_messages_fa.properties")
public class PropertiesHandler {

}
