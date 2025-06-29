package com.sri.user.security;

import java.util.Arrays;

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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
		
		http.cors(cors->cors.configurationSource(corsConfigurationSource()))
			.csrf((csrf)->csrf.disable())
			.authorizeHttpRequests((authz)->authz
			.requestMatchers(HttpMethod.POST,"/users/createuser")
			.permitAll()
			.requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
			.permitAll()
			.requestMatchers("/todo/**")
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
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config=new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("http://localhost:4200", "https://sri-todo.netlify.app"));
		config.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
		config.setAllowedHeaders(Arrays.asList("*"));
		config.setExposedHeaders(Arrays.asList("*"));
		config.setAllowCredentials(true);
		
		UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		
		return  source;
		
		
	}
}
