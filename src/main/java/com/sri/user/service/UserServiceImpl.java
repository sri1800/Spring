package com.sri.user.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sri.user.Entity.RoleEntity;
import com.sri.user.Entity.UserEntity;
import com.sri.user.Exception.EmailAlreadyExistException;
import com.sri.user.pojo.UserDto;
import com.sri.user.repo.RoleRepo;
import com.sri.user.repo.UserRepo;
import com.sri.user.security.UserPrinciple;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	ModelMapper mapper;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	RoleRepo roleRepo;
	
	@Override
	public UserDto createUser(UserDto userDto) throws EmailAlreadyExistException {
		
		UserEntity checkEmail = userRepo.findByEmail(userDto.getEmail());
		if(checkEmail != null)
			throw new EmailAlreadyExistException("User with email "+ userDto.getEmail() + " already exist, please use another email");
		
		userDto.setUserId(UUID.randomUUID().toString());
		userDto.setEncrypassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		
//		ModelMapper mapper=new ModelMapper();
//		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserEntity entity=mapper.map(userDto,UserEntity.class); 
		
		Collection<RoleEntity> rolesEntities=new HashSet<>();
		for(String role:userDto.getRoles()) {
			RoleEntity roleEntity=roleRepo.findByName(role);
			rolesEntities.add(roleEntity);
		}
		entity.setRoles(rolesEntities);
		
		userRepo.save(entity);
		UserDto ReturnValue=mapper.map(entity,UserDto.class);
		
		return ReturnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity entity=userRepo.findByEmail(email);
		if(entity==null)throw new UsernameNotFoundException("User not found");
//		return new User(entity.getEmail(),entity.getEncrypassword(),true,true,true,true,new ArrayList<>());
		return new UserPrinciple(entity);
	}

	@Override
	public UserDto getUserDetailsByEmail(String email) {
		UserEntity entity=userRepo.findByEmail(email);
		if(entity==null)throw new UsernameNotFoundException("User not found");
		return mapper.map(entity,UserDto.class);
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserEntity entity=userRepo.findByUserId(userId);
		return mapper.map(entity,UserDto.class);
	}

}
