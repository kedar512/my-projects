package com.example.demo.service;

import org.springframework.http.ResponseEntity;

import com.example.demo.dto.ConfigDto;

public interface ConfigService {
	String fetchUriMapping(String uriKey);
	ResponseEntity<String> addUriMapping(ConfigDto configDto);
}
