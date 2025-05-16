package com.sri.user.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.sri.user.pojo.UserDto;


public interface UserService extends UserDetailsService {
	
	public UserDto createUser(UserDto userDto);
	public UserDto getUserDetailsByEmail(String email);
	public UserDto getUserByUserId(String userId);

}
