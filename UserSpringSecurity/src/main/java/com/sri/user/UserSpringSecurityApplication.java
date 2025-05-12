package com.sri.user;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sri.user.security.AppProperties;

@SpringBootApplication
public class UserSpringSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserSpringSecurityApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper=new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return mapper;
	}

	@Bean
	public SpringApplicationContext springApplicationContext() {
		return new SpringApplicationContext();
	}
	
	@Bean(name="AppProperties")
	public AppProperties getAppProperties() {
		return new AppProperties();
	}
}
