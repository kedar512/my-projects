package com.example.demo.controller;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Test;
import com.example.demo.pojo.MyClass;
import com.example.demo.service.TestService;

@RestController
public class TestController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	TestService testService;
	
	@GetMapping("/test")
	public MyClass testGetApi() {
		LOGGER.info("Test log");
		
		MyClass myClass = testService.testPostServiceCall();
		
		return myClass;
	}
	
	@PostMapping("test")
	public String testPostApi(@RequestBody Test test) {
		return "Hello";
	}
	
	@PostMapping("testxss")
	public String testXssFiltering(@RequestBody(required = false) String reqBody) {
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("message", "Success");
		
		return jsonObj.toString();
	}
}
