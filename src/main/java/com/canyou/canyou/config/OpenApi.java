/* Created by El Hadji M. NDONGO  */
/* on 06 02 2024 */
/* Project: can-you */

package com.canyou.canyou.config;

import io.swagger.v3.oas.models.info.Contact;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApi {
    @Bean
    public OpenAPI customOpenAPI(@Value("${application-description}") String appDesciption, @Value("${application-version}") String appVersion) {
        return new OpenAPI()
                .info(new Info()
                                .title("Developer webservice API")
                                .version(appVersion)
                                .description(appDesciption)
                                .contact((new Contact()).email("ndongoel@gmail.com").name("El Hadji M. NDONGO")));
//                                .termsOfService("http://swagger.io/terms/")
//                                .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
