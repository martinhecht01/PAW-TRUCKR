package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.models.RoleType;
import ar.edu.itba.paw.webapp.auth.BasicFilter;
import ar.edu.itba.paw.webapp.auth.JwtTokenFilter;
import ar.edu.itba.paw.webapp.auth.UserDetailsServiceImpl;
import ar.edu.itba.paw.webapp.auth.handlers.AccessHandler;
import ar.edu.itba.paw.webapp.auth.handlers.TruckrAccessDeniedHandler;
import ar.edu.itba.paw.webapp.auth.handlers.TruckrAuthenticationEntryPoint;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;
import org.springframework.core.io.Resource;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import static org.springframework.web.cors.CorsConfiguration.ALL;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan("ar.edu.itba.paw.webapp.auth")
@Configuration
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Autowired
    private BasicFilter basicFilter;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean(name = "jwtKey")
    public static Key jwtKey(@Value("classpath:jwt.key") Resource jwtKeyResource) throws IOException {
        return Keys.hmacShaKeyFor(FileCopyUtils.copyToString(new InputStreamReader(jwtKeyResource.getInputStream())).getBytes(StandardCharsets.UTF_8));
    }

    @Override @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOrigins(Collections.singletonList(ALL));
        cors.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        cors.addAllowedHeader(ALL);
        cors.setExposedHeaders(Arrays.asList("Authorization", "Link", "Location", "ETag", "Total-Elements", "X-JWT"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);
        return source;
    }

    @Bean
    public TruckrAuthenticationEntryPoint authenticationEntryPoint() {
        return new TruckrAuthenticationEntryPoint();
    }

    @Bean
    public TruckrAccessDeniedHandler accessDeniedHandler() {
        return new TruckrAccessDeniedHandler();
    }


    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        Properties props = new Properties();

        try (InputStream input = getClass().getResourceAsStream("/application.properties")) {
            props.load(input);
        }catch (Exception e){
            e.printStackTrace();
        }

        String MyKey = props.getProperty("KEY");

        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling()
                .authenticationEntryPoint(new TruckrAuthenticationEntryPoint())
                .accessDeniedHandler(new TruckrAccessDeniedHandler())
                .and().headers().cacheControl().disable()
                .and()
                .authorizeRequests()

                .antMatchers(HttpMethod.PUT, "/api/users/{id:\\d+}/**").authenticated()
                .antMatchers(HttpMethod.PATCH, "/api/users/{id:\\d+}").authenticated()

                .antMatchers(HttpMethod.POST, "/api/trips").authenticated()
                .antMatchers(HttpMethod.PATCH, "/api/trips").authenticated()

                .antMatchers(HttpMethod.POST, "/api/reviews").authenticated()

                .antMatchers(HttpMethod.POST, "/api/alerts").hasRole(RoleType.TRUCKER.getRoleName())
                .antMatchers(HttpMethod.DELETE, "/api/alerts").authenticated()
                .antMatchers(HttpMethod.GET, "/api/alerts").authenticated()
                .antMatchers(HttpMethod.GET, "/api/alerts/{id:\\d+}").authenticated()

                .antMatchers(HttpMethod.POST, "/api/offers").authenticated()
                .antMatchers(HttpMethod.PATCH, "/api/offers/{id:\\d+}").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/offers/{id:\\d+}").authenticated()
                .antMatchers(HttpMethod.GET, "/api/offers").authenticated()



//                    .antMatchers("/trips/browse").access("hasRole('PROVIDER') or isAnonymous()")
//                    .antMatchers("/requests/browse").access("hasRole('TRUCKER') or isAnonymous()")
//                    .antMatchers("/login", "/register", "/resetPassword", "/resetPasswordRequest", "/verifyAccount").anonymous()
//                    .antMatchers("/requests/create", "/requests/myRequests" ).hasRole("PROVIDER")
//                    .antMatchers("/trips/create", "/trips/myTrips").hasRole("TRUCKER")
//                    .antMatchers( "/","/trips/details", "/requests/details", "/explore", "/trips/{tripId}/tripPicture", "/user/{userId}/profilePicture").permitAll()
//                    .antMatchers("/**").authenticated()
                .antMatchers("/api/**").permitAll()
                .and().cors().and().csrf().disable()

                .addFilterBefore(basicFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

//    @Bean
//    public AuthenticationFailureHandler authenticationFailureHandler() {
//        return new SimpleUrlAuthenticationFailureHandler() {
//            @Override
//            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//                String errorMessage = "InvalidCredentials";
//
//                if (exception.getMessage() != null && exception.getMessage().equalsIgnoreCase("User is disabled")) {
//                    errorMessage = "UserNotVerified";
//                }
//                String loginPageUrl = request.getContextPath() + "/login?error=" + errorMessage ;
//                response.sendRedirect(loginPageUrl);
//            }
//        };
//    }


    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/static/**","/css/**", "/js/**", "/img/**", " /favicon.ico", "/resources/**", "/errors/**");
    }
}