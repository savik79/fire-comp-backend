package pl.jtrend.firecomp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Disable CSRF for simplicity of testing APIs via Swagger UI
        http.csrf(AbstractHttpConfigurer::disable);

        // Permit access to Swagger UI and OpenAPI endpoints
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html"
                ).permitAll()
                // You can further restrict your API here. For now, allow all endpoints.
                .anyRequest().permitAll()
        );

        // Use stateless defaults; no login form/basic auth needed for now
        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
