package ir.navaco.mcb.olivetti;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;


@Configuration
@EnableSwagger2
public class OlivettiConfiguration {

    @Value("${version}")
    private String version;
    @Value("${name}")
    private String name;
    @Value("${url}")
    private String url;
    @Value("${email}")
    private String email;


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {


        ApiInfo apiInfo = new ApiInfo("Spring boot olivetti pr4 api",
                null,
                version,
                url,
                new Contact(name,null,email),
                null,
                null,
                new ArrayList<VendorExtension>()
                );

        return apiInfo;
    }

}
