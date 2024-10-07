package com.training.greetapi.shared.security.config;

import com.training.greetapi.shared.security.CustomAuthenticationFailureEntryPoint;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
public class AuthorizationConfig {

    private final AuthenticationProvider authenticationProvider;
    private final CsrfTokenRepository csrfTokenRepository;

    public AuthorizationConfig(AuthenticationProvider authenticationProvider, CsrfTokenRepository csrfTokenRepository) {
        this.authenticationProvider = authenticationProvider;
        this.csrfTokenRepository = csrfTokenRepository;
    }

//    @Bean
//    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
//        return new MvcRequestMatcher.Builder(introspector).servletPath("/spring-mvc");
//    }


    @Bean
    CorsConfigurationSource getCorsConfiguration() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("http://localhost:8080"));
                configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                return configuration;
            }
        };

    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic(c ->
        {
            c.authenticationEntryPoint(new CustomAuthenticationFailureEntryPoint());
        });
        http.authorizeHttpRequests(x -> {
                    x

                            /*
                            When using parameter expressions with a regex, make sure to not
                            have a space between the name of the parameter, the colon (:), and the
                            regex

                            .requestMatchers(HttpMethod.GET, "/api/v1/messages/add/{id:^[0-9]*$}").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.POST, "/api/v1/messages/add").permitAll()
                             .requestMatchers(HttpMethod.GET, "/api/v1/messages/bbb").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/v1/messages/ccc").denyAll()
                            .anyRequest().denyAll()
                             */

                            /*If the HandlerMappingIntrospector(included at spring boot web starter) is
                            available in the` classpath,
                            maps to an MvcRequestMatcher that also specifies
                            a specific HttpMethod to match on.
                            This matcher will use the same rules that Spring MVC uses for matching.
                             For example, often times a mapping of the path "/ path" will
                             match on "/ path", "/ path/", "/ path. html", etc. If
                            the HandlerMappingIntrospector is not available,
                            maps to an AntPathRequestMatcher.
                            */
                            .requestMatchers(HttpMethod.GET, "/api/v1/messages/{id}").hasAnyAuthority("ADMIN", "MANAGER", "USER")
                            .requestMatchers(HttpMethod.DELETE, "/api/v1/messages/{id}").hasAnyAuthority("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/api/v1/messages").hasAnyAuthority("ADMIN", "MANAGER", "USER")
                            .requestMatchers(HttpMethod.POST, "/api/v1/messages").hasAnyAuthority("ADMIN", "MANAGER")
                            .requestMatchers(HttpMethod.PUT, "/api/v1/messages").hasAnyAuthority("ADMIN", "MANAGER")

                            .anyRequest().permitAll();
                }
        );
        //specifying that we want this filter get executed before authentication
        //  http.addFilterBefore(new RequestHeaderValidatingFilter(), BasicAuthenticationFilter.class);

        //specifying that we want this filter get executed after authentication
        //  http.addFilterAfter(new AuthenticationEventAuditingFilter(), BasicAuthenticationFilter.class);

        /*
        When adding a filter to the chain, the framework doesn’t guarantee it will be
        called only once per request. OncePerRequestFilter , as the name suggests, imple-
        ments logic to make sure that the filter’s doFilter() method is executed only one
        time per request.

        **/
        //specifying that we want this filter get executed behalf of BasicAuthenticationFilter
        //http.addFilterAt(staticKeyAuthenticationFilter, BasicAuthenticationFilter.class);

        //  http.addFilterAfter(new CSRFTokenLoggerFilter(), CsrfFilter.class);
        http.authenticationProvider(authenticationProvider);

        //read https://www.baeldung.com/csrf-stateless-rest-api
        http.csrf(AbstractHttpConfigurer::disable);

        //we can exclude some paths from csrf protection as follows.
//        http.csrf(c -> {
//            HandlerMappingIntrospector handlerMappingIntrospector = new HandlerMappingIntrospector();
//            MvcRequestMatcher mvcRequestMatcher = new MvcRequestMatcher(handlerMappingIntrospector, "/api/v1/messages/{id}");
//            mvcRequestMatcher.setMethod(HttpMethod.DELETE);
//            c.ignoringRequestMatchers(mvcRequestMatcher);
//        });

        //adding custom csrf token handling mechanism
        //https://www.baeldung.com/csrf-stateless-rest-api
//        http.csrf(c -> {
//            c.csrfTokenRepository(csrfTokenRepository);
//        });

        //adding global cors configuration
        /*
        * CorsConfiguration is the object that states which are the allowed origins, methods,
and headers. If you use this approach, you have to specify at least which are the origins
and the methods. If you only specify the origins, your application won’t allow the
requests. This behavior happens because a CorsConfiguration object doesn’t
define any methods by default.
        *
        * */
        http.cors(c -> {
            c.configurationSource(getCorsConfiguration());
        });

        return http.build();
    }


}