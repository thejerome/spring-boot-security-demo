package com.example.springbootsecuritydemo.config;

import com.example.springbootsecuritydemo.auth.UserEntityDetailsService;
import com.example.springbootsecuritydemo.repo.UserRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private DataSource dataSource;
    private UserRepo userRepo;

    public WebSecurityConfig(final DataSource dataSource, final UserRepo userRepo) {
        this.dataSource = dataSource;
        this.userRepo = userRepo;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("configure http");
        http.authorizeRequests()
                .antMatchers("/reg").permitAll()
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .loginPage("/login")
                .successForwardUrl("/hello")
                .permitAll()
            .and()
                .logout().permitAll()
            .and()
                .httpBasic();
    }



    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("configure auth");
        auth.userDetailsService(new UserEntityDetailsService(userRepo, passwordEncoder()));
    }

//    @Override
//    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//        System.out.println("configure auth");
//        auth.jdbcAuthentication()
//                .passwordEncoder(passwordEncoder())
//                .dataSource(dataSource)
//                .usersByUsernameQuery("select username, password, 1 from user where username = ?")
//                .authoritiesByUsernameQuery("select username, 'USER' as roles from user where username = ?");
//    }

//    @Override
//    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//        System.out.println("configure auth");
//        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder())
//                .withUser("vasya")
//                .password(passwordEncoder().encode("vasya"))
//                .roles("USER")
//            .and()
//                .withUser("valya")
//                .password(passwordEncoder().encode("valya"))
//                .roles("USER")
//            .and()
//                .withUser("vanya")
//                .password(passwordEncoder().encode("vanya"))
//                .roles("USER");
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}