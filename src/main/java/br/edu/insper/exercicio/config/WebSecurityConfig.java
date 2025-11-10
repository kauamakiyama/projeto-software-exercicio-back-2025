package br.edu.insper.exercicio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowedHeaders("*");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize

                        .requestMatchers(HttpMethod.GET, "/viagens", "/viagens/**").authenticated()                        // DELETE requests: apenas administradores podem deletar
                        .requestMatchers(HttpMethod.DELETE, "/viagens/**").hasRole("admin")
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                );

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(this::extractAuthorities);
        return converter;
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {

        
        List<String> roles = Stream.of(
                extractRolesFromClaim(jwt, "https://dev-hb5tnbkuyk217lt2.us.auth0.com/roles"),
                extractRolesFromClaim(jwt, "roles"),
                extractPermissions(jwt)
        )
        .flatMap(List::stream)
        .distinct()
        .collect(Collectors.toList());

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private List<String> extractRolesFromClaim(Jwt jwt, String claimName) {
        Object claimValue = jwt.getClaim(claimName);
        if (claimValue instanceof List) {
            return ((List<?>) claimValue).stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    @SuppressWarnings("unchecked")
    private List<String> extractPermissions(Jwt jwt) {
        Object permissions = jwt.getClaim("permissions");
        if (permissions instanceof List) {
            return ((List<?>) permissions).stream()
                    .map(Object::toString)
                    .filter(permission -> permission.equals("delete:viagens") || permission.equals("admin"))
                    .map(permission -> permission.equals("delete:viagens") ? "admin" : permission)
                    .collect(Collectors.toList());
        }
        return List.of();
    }
}