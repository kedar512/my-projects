package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.pojo.UserDto;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepo;

	@Override
	public void addUser(UserDto userDto) {
		userRepo.save(convertToUserModel(userDto));
	}
	
	private User convertToUserModel(UserDto userDto) {
		User user = new User();
		
		user.setUsername(userDto.getUserName());
		
		return user;
	}

}
