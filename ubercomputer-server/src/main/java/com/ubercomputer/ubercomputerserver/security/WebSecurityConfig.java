package com.ubercomputer.ubercomputerserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable();

	    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	    
	    http.authorizeRequests()
        .antMatchers("/api/users/login").permitAll()
        .antMatchers("/api/users/signup").permitAll()
        .antMatchers("/api/parts").permitAll()
        .antMatchers("/api/users/forgot").permitAll()
        .antMatchers("/api/users/reset/{tokenId}/{token}").permitAll()
        .anyRequest().authenticated();

	    http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder(12);
	}
}