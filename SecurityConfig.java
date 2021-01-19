package com.luv2code.configurespringsecurity;

import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //set the configuration ... either jdbcAuthentication() or inMemoryAuthentication()
        auth.inMemoryAuthentication()
                .withUser("user")
                .password("pass")
                .roles("ROLE_USER");

    }

    //authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.authorizeRequests()
               //allow static files
               .antMatchers("/", "static/css", "static/js").permitAll()
               //you go from the most restrictive , to the least restrictive
               .antMatchers("/admin").hasRole("ROLE_ADMIN")
               .antMatchers("/user").hasAnyRole("ROLE_ADMIN", "ROLE_USER")
               .antMatchers("/**").permitAll()
           .and()
               .formLogin();
    }

    //password encoder
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        //in production ... use Bcrypt Encryption
        return NoOpPasswordEncoder.getInstance();
    }

}
