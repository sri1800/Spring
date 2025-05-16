package com.sri.user.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.sri.user.repo.UserRepo;
import com.sri.user.service.UserService;

@EnableMethodSecurity(securedEnabled = true,prePostEnabled = true)
@Configuration
@EnableWebSecurity
public class WebSecurity {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Bean
	protected  SecurityFilterChain configure(HttpSecurity http)throws Exception{
		AuthenticationManagerBuilder authenticationManagerBuilder=
				http.getSharedObject(AuthenticationManagerBuilder.class);
		
		authenticationManagerBuilder.userDetailsService(userService)
									.passwordEncoder(bCryptPasswordEncoder);
		
		AuthenticationManager authenticationManager=authenticationManagerBuilder.build();
		
		http.csrf((csrf)->csrf.disable())
			.authorizeHttpRequests((authz)->authz
			.requestMatchers(HttpMethod.POST,"/users/createuser")
			.permitAll()
			.requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
			.permitAll()
			.requestMatchers("/todo/data")
			.permitAll()
//			.requestMatchers(HttpMethod.DELETE,"/users/**").hasAnyRole("ADMIN")
			.anyRequest().authenticated())
			.addFilter(new AuthenticationFilter(authenticationManager))
			.addFilter(new AuthorizationFilter(authenticationManager,userRepo))
			.authenticationManager(authenticationManager)
			.sessionManagement((session)->session
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		
		http.headers((headers)->
		headers.frameOptions((frameOptions)->
		frameOptions.sameOrigin()));
		
		return http.build();
	}
}
