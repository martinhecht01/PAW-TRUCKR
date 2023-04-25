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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
        http.sessionManagement()
                .invalidSessionUrl("/login")
                .and().authorizeRequests()
                    .antMatchers("/login", "/register").anonymous()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                   // .antMatchers("/","/explore", "/trips/**", "/createTrip", "/browseTrips" ).hasRole("PROVIDER")
                   // .antMatchers("/explore", "/trips/**", "/createTrip", "/browseTrips" ).hasRole("TRUCKER")
                    .antMatchers("/").permitAll()
                    .antMatchers("/**").authenticated()
                .and().formLogin()
                    .usernameParameter("cuit")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/browseTrips", false)
                    .loginPage("/login")
                .and().rememberMe()
                    .rememberMeParameter("rememberme")
                    .userDetailsService(userDetailsService)
                .key("mysupersecretketthatnobodyknowsabout") // no hacer esto, crear una aleatoria segura suficientemente grande y colocarla bajo src/main/resources
                        .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
                    .and().logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                    .and().exceptionHandling()
                        .accessDeniedPage("/403")
                    .and().csrf().disable();
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/css/**", "/js/**", "/img/**", " /favicon.ico", "/resources/**", "/403");
    }
}
