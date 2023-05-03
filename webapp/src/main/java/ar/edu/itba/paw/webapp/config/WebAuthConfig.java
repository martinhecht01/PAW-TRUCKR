package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
                    .antMatchers("/login", "/register", "/resetPassword", "/resetPasswordRequest").anonymous()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                //TODO: revisar y especificar permisos
                    .antMatchers("/requests/create", "/trips/browse", "/requests/myRequests", "/requests/manageRequest" ).hasRole("PROVIDER")
                    .antMatchers("/trips/create", "/requests/browse", "/trips/myTrips" , "/trips/manageTrip" ).hasRole("TRUCKER")
                    .antMatchers("/").permitAll()
                    .antMatchers("/**").authenticated()
                .and().formLogin()
                    .usernameParameter("cuit")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/", false)
                    .loginPage("/login")
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



    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/css/**", "/js/**", "/img/**", " /favicon.ico", "/resources/**", "/errors/**");
    }
}
