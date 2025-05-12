package com.sri.user;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.sri.user.Entity.AuthoritiesEntity;
import com.sri.user.Entity.RoleEntity;
import com.sri.user.Entity.UserEntity;
import com.sri.user.repo.AuthoritiesRepo;
import com.sri.user.repo.RoleRepo;
import com.sri.user.repo.UserRepo;
import com.sri.user.shared.Authority;
import com.sri.user.shared.Role;
import com.sri.user.shared.Utills;

import jakarta.transaction.Transactional;

@Component
public class InitialSetup {

	@Autowired
	RoleRepo roleRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	AuthoritiesRepo authoritiesRepo;
	
	@Autowired
	Utills utills;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional
	@EventListener
	public void onApplicationEvent(ApplicationReadyEvent event) {
		AuthoritiesEntity readAuthority=createAuthority(Authority.READ.name());
		AuthoritiesEntity writeAuthority=createAuthority(Authority.WRITE.name());
		AuthoritiesEntity deleteAuthority=createAuthority(Authority.DELETE.name());
		
		createRole(Role.ROLE_USER.name(),Arrays.asList(readAuthority,writeAuthority));
		RoleEntity adminRole=createRole(Role.ROLE_ADMIN.name(),Arrays.asList(readAuthority,writeAuthority,deleteAuthority));
		
		if(adminRole==null)return;
		
		UserEntity adminUser=new UserEntity();
		adminUser.setFname("Sri");
		adminUser.setLname("hari");
		adminUser.setEmail("abc@com");
		adminUser.setUserId(utills.generateUserId(20));
		adminUser.setEncrypassword(bCryptPasswordEncoder.encode("123456789"));
		adminUser.setRoles(Arrays.asList(adminRole));
		
		UserEntity alreadyStoredUser=userRepo.findByEmail("abc@com");
		if(alreadyStoredUser==null) {
			userRepo.save(adminUser);
		}
		
		}
	
	@Transactional
	private AuthoritiesEntity createAuthority(String name) {
		AuthoritiesEntity entity=authoritiesRepo.findByName(name);
		if(entity==null) {
			entity=new AuthoritiesEntity(name);
			authoritiesRepo.save(entity);
		}
		return entity;
	}
	
	@Transactional
	private RoleEntity createRole(String name,Collection <AuthoritiesEntity> authorities) {
		RoleEntity entity=roleRepo.findByName(name);
		if(entity==null) {
			entity=new RoleEntity(name);
			entity.setAuthorities(authorities);
			roleRepo.save(entity);
		}
		return entity;
	}
}
