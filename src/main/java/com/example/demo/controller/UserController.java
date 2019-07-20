package com.example.demo.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.pojo.UserDto;
import com.example.demo.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
	public String addUser(@RequestBody UserDto userDto) {
		userService.addUser(userDto);
		
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("message", "User added successfully");
		
		return jsonObj.toString();
	}
}
