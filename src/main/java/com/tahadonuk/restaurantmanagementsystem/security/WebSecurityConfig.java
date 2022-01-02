package com.tahadonuk.restaurantmanagementsystem.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    LoginSuccessHandler loginSuccessHandler;
    @Autowired
    LogoutHandler logoutHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers("/","/tables/**","/users/**","/products/**","/orders/**","/fragments/**", "/user/**")
                    .hasAnyAuthority("EMPLOYEE", "MANAGER", "ADMIN")
                    .and()
                .authorizeRequests()
                    .antMatchers("/disabled","/unauthorized", "/me/**")
                    .authenticated()
                    .and()
                .authorizeRequests()
                    .antMatchers("/api/product/**", "/api/order/**", "/api/table/**")
                    .hasAnyAuthority( "EMPLOYEE", "ADMIN", "MANAGER")
                    .and().exceptionHandling().accessDeniedPage("/unauthorized").and()
                .authorizeRequests()
                    .antMatchers("/insights", "/api/user/**")
                    .hasAnyAuthority("ADMIN", "MANAGER")
                    .and().exceptionHandling().accessDeniedPage("/unauthorized").and()
                .formLogin().loginPage("/login").usernameParameter("email")
                    .successHandler(loginSuccessHandler)
                    .permitAll()
                    .and()
                .formLogin().failureUrl("/login?error")
                    .and()
                .logout().logoutSuccessHandler(logoutHandler)
                    .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetailsService service = new UserDetailsServiceImpl();

        return service;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }
}
