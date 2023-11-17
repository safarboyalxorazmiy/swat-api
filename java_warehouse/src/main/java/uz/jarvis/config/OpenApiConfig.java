package uz.jarvis.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
        contact = @Contact(
            name = "Safarboy",
            email = "safarboyalxorazmiy@gmail.com",
            url = "http://backall.uz"
        ),
        description = "Open Api documentation for LOGIST WEBSITE",
        title = "OpenApi specification",
        version = "1.1",
        license = @License(
            name = "No Licence",
            url = "https://www.google.com"
        ),
        termsOfService = "Terms of service"
    ),
    servers = {
        @Server(
            description = "Local ENV",
            url = "http://192.168.5.193:1212"
        ),
        @Server(
            description = "PROD ENV",
            url = "http://backall.uz"
        )
    },
    security = {
        @SecurityRequirement(
            name = "bearerAuth"
        )
    }
)

@SecurityScheme(
    name = "bearerAuth",
    description = "JWT auth description",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}