package com.readWise.gateway.config;

import com.readWise.gateway.filter.JweFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import javax.sql.DataSource;
import java.security.AuthProvider;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

/*
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/user/**")
                        .filters(f -> f.addRequestHeader("X-Response-Header", "54745-932Hh-9hGhG-936dh_083Ng"))  // Header entfernen
                        .uri("http://localhost:8080"))
                .build();
    }

 */

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(JweFilter jweFilter, ServerHttpSecurity http) {
        http.csrf(csrf -> csrf.disable())
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/user/login").permitAll()
                        .anyExchange().authenticated()
                )
              //  .addFilterBefore(jweFilter, SecurityWebFiltersOrder.FIRST)
                .httpBasic();
        return http.build();
    }


}
