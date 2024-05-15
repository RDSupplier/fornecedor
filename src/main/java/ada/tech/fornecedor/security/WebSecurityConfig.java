package ada.tech.fornecedor.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/fornecedor/produto").allowedOrigins("*");
        registry.addMapping("/produto").allowedOrigins("*");
        registry.addMapping("/loja").allowedOrigins("*");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(r -> r.requestMatchers(new OrRequestMatcher(List.of(
                                new AntPathRequestMatcher("/swagger-ui"),
                                new AntPathRequestMatcher("/swagger-ui/**"),
                                new AntPathRequestMatcher("/v3/api-docs/**"),
                                new AntPathRequestMatcher("/h2-console/**")
                        ))).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/fornecedor/**")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/produto/**")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.POST, "/produto/**")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.POST, "/fornecedor/**")).permitAll())

                .authorizeHttpRequests(r -> r.requestMatchers(antMatcher(HttpMethod.POST, "/aluno/**")).hasAnyRole("ADMIN")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        return httpSecurity.build();
    }
}
