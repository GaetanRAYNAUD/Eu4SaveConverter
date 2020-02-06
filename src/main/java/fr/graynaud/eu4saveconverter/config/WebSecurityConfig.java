package fr.graynaud.eu4saveconverter.config;

import fr.graynaud.eu4saveconverter.config.authentication.TokenFilter;
import fr.graynaud.eu4saveconverter.config.properties.WebProperties;
import fr.graynaud.eu4saveconverter.service.SecurityService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Order(99)
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    private final WebProperties webProperties;

    public WebSecurityConfig(WebProperties webProperties) {
        this.webProperties = webProperties;
    }

    @Order(1)
    @Configuration
    @EnableWebSecurity
    public static class ActuatorSecurityConfig extends WebSecurityConfigurerAdapter {

        private final WebProperties webProperties;

        public ActuatorSecurityConfig(WebProperties webProperties) {
            this.webProperties = webProperties;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.antMatcher("/actuator/**")
                .authorizeRequests()
                .anyRequest().hasRole("ACTUATOR")
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication()
                .withUser(webProperties.getActuatorUser())
                .password("{noop}" + webProperties.getActuatorPassword())
                .roles("ACTUATOR");
        }
    }

    @Order(2)
    @Configuration
    @EnableWebSecurity
    public static class GeneralSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.cors()
                .and()
                .authorizeRequests()
                .antMatchers("/public/**", "/swagger-ui.html", "/v2/api-docs", "/swagger-resources/**", "/webjars/**")
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS)
                .permitAll()
                .and()
                .csrf()
                .disable();

            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(webProperties.getCorsOrigin());
        configuration.setAllowCredentials(true);
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public FilterRegistrationBean<TokenFilter> tokenFilterFilterRegistrationBean(SecurityService securityService){
        FilterRegistrationBean<TokenFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TokenFilter(securityService));
        registrationBean.addUrlPatterns("/api/*");

        return registrationBean;
    }
}
