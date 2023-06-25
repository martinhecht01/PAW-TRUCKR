package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@ComponentScan("ar.edu.itba.paw.webapp.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
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

        http.sessionManagement()
                .invalidSessionUrl("/login")
                .and().authorizeRequests()
                    .antMatchers("/trips/browse").access("hasRole('PROVIDER') or isAnonymous()")
                    .antMatchers("/requests/browse").access("hasRole('TRUCKER') or isAnonymous()")
                    .antMatchers("/login", "/register", "/resetPassword", "/resetPasswordRequest", "/verifyAccount").anonymous()
                    .antMatchers("/requests/create", "/requests/myRequests" ).hasRole("PROVIDER")
                    .antMatchers("/trips/create", "/trips/myTrips").hasRole("TRUCKER")
                    .antMatchers( "/","/trips/details", "/requests/details", "/explore", "/trips/{tripId}/tripPicture", "/user/{userId}/profilePicture").permitAll()
                    .antMatchers("/**").authenticated()
                .and().formLogin()
                    .usernameParameter("cuit")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/explore", false)
                    .loginPage("/login")
                    .failureHandler(authenticationFailureHandler())
                .and().rememberMe()
                    .rememberMeParameter("rememberme")
                    .userDetailsService(userDetailsService)
                .key(MyKey)
                        .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
                    .and().logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                .and().exceptionHandling().accessDeniedPage("/errors/403")
                    .and().csrf().disable();
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
