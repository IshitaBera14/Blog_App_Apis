
package com.blog.project_blog_app_apis.config;

        import io.swagger.v3.oas.annotations.OpenAPIDefinition;
        import io.swagger.v3.oas.annotations.info.Contact;
        import io.swagger.v3.oas.annotations.info.Info;
        import io.swagger.v3.oas.annotations.info.License;
        import io.swagger.v3.oas.annotations.servers.Server;
        import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Blogging Application : Backend",
                version = "1.0",
                description = "API documentation for the Blog App backend built with Spring Boot",
                termsOfService = "Terms of Service",
                contact = @Contact(
                        name = "Ishita",
                        email = "ishbera09@gmail.com",
                        url = "https://learncodewithishita.com"
                ),
                license = @License(
                        name = "License of APIS",
                        url = "API license URL"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Local Dev Server")
        }
)
public class SwaggerConfig {
    // No additional configuration required here unless you're customizing beans
}
