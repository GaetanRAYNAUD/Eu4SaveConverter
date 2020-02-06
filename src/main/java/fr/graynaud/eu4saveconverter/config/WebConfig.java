package fr.graynaud.eu4saveconverter.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.switchuser.SwitchUserGrantedAuthority;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "fr.graynaud.eu4saveconverter.repository")
@EnableConfigurationProperties
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    @Bean
    public AuditorAware<String> createAuditorProvider() {
        return new SecurityAuditor();
    }

    public static class SecurityAuditor implements AuditorAware<String> {

        @Override
        public Optional<String> getCurrentAuditor() {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if (auth == null) {
                return Optional.of("anonymous");
            }

            String username = auth.getName();

            Optional<? extends GrantedAuthority> switchUserAuthorities = auth.getAuthorities().stream().filter(a -> a instanceof SwitchUserGrantedAuthority).findAny();

            if (switchUserAuthorities.isPresent()) {
                username = ((SwitchUserGrantedAuthority) switchUserAuthorities.get()).getSource().getName();
            }

            return Optional.of(username);
        }
    }
}
