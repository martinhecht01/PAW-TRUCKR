package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.BasicFilter;
import ar.edu.itba.paw.webapp.auth.JwtTokenFilter;
import ar.edu.itba.paw.webapp.auth.UserDetailsServiceImpl;
import ar.edu.itba.paw.webapp.auth.handlers.TruckrAccessDeniedHandler;
import ar.edu.itba.paw.webapp.auth.handlers.TruckrAuthenticationEntryPoint;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.persistence.Access;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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


@Configuration
@EnableWebSecurity
@ComponentScan("ar.edu.itba.paw.webapp.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Autowired
    private BasicFilter basicFilter;

    private static final String USER_ACCESS_VERIFICATION = "@accessHandler.userAccessVerification(#id)";

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
        cors.setAllowedOrigins(Collections.singletonList("*"));
        cors.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        cors.setAllowedHeaders(Arrays.asList("authorization", "userURL", "content-type", "x-auth-token"));
        cors.setExposedHeaders(Arrays.asList("X-JWT", "X-Content-Type-Options", "X-XSS-Protection", "X-Frame-Options", "authorization", "Location", "Content-Disposition", "Link"));
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
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.PUT, "/users/{id}/**").access(USER_ACCESS_VERIFICATION)
                .antMatchers(HttpMethod.GET, "/trips").authenticated()
//                    .antMatchers("/trips/browse").access("hasRole('PROVIDER') or isAnonymous()")
//                    .antMatchers("/requests/browse").access("hasRole('TRUCKER') or isAnonymous()")
//                    .antMatchers("/login", "/register", "/resetPassword", "/resetPasswordRequest", "/verifyAccount").anonymous()
//                    .antMatchers("/requests/create", "/requests/myRequests" ).hasRole("PROVIDER")
//                    .antMatchers("/trips/create", "/trips/myTrips").hasRole("TRUCKER")
//                    .antMatchers( "/","/trips/details", "/requests/details", "/explore", "/trips/{tripId}/tripPicture", "/user/{userId}/profilePicture").permitAll()
//                    .antMatchers("/**").authenticated()
                .antMatchers("/**").permitAll()
                .and().cors().and().csrf().disable()

                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(basicFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                String errorMessage = "InvalidCredentials";

                if (exception.getMessage() != null && exception.getMessage().equalsIgnoreCase("User is disabled")) {
                    errorMessage = "UserNotVerified";
                }
                String loginPageUrl = request.getContextPath() + "/login?error=" + errorMessage ;
                response.sendRedirect(loginPageUrl);
            }
        };
    }


    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/css/**", "/js/**", "/img/**", " /favicon.ico", "/resources/**", "/errors/**");
    }
}