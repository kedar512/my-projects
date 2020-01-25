package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ConfigDto;
import com.example.demo.service.ConfigService;

@RestController
public class ConfigController {
	
	@Autowired
	ConfigService configService;
	
	@GetMapping(value = "uris", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getUriMapping(@RequestHeader Map<String, String> headers) {
		return configService.fetchUriMapping(headers.get("uri-key"));
	}
	
	@PutMapping(value = "uris", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addUriMapping(@RequestBody ConfigDto configDto, @RequestHeader Map<String, String> headers) {
		return configService.addUriMapping(configDto);
	}
}
