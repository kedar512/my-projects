package com.example.demo.service.impl;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ConfigDto;
import com.example.demo.service.ConfigService;
import com.mongodb.client.result.UpdateResult;

@Service
public class ConfigServiceImpl implements ConfigService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigServiceImpl.class);
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	private CacheManager cacheManager;
	
	@Value("${config.col}")
	private String configCol;
	
	@Value("${config.doc.id}")
	private String configDocId;

	@Override
	@Cacheable(cacheNames = "uriCache",
			   key = "#uriKey",
			   unless = "#result == null")
	public String fetchUriMapping(String uriKey) {
		JSONObject resp = new JSONObject();
		
		Query query = new Query(Criteria.where("_id").is(configDocId));
		
		query.fields().include(uriKey);
		String result = null;
		
		try {
			result = mongoTemplate.findOne(query, String.class, configCol);
		} catch (Exception e) {
			LOGGER.error("Error in fetching URI mapping", e);
			resp.put("message", "Error in fetching URI mapping");
			return resp.toString();
		}
		
		JSONObject config = new JSONObject(result);
		
		if (config.has(uriKey)) {
			resp.put("uriValue", config.getString(uriKey));
			return resp.toString();
		} else {
			return null;
		}
	}

	@Override
	public ResponseEntity<String> addUriMapping(ConfigDto configDto) {
		JSONObject resp = new JSONObject();
		
		if (StringUtils.isBlank(configDto.getKey()) || StringUtils.isBlank(configDto.getValue())) {
			resp.put("message", "Please provide both key and value in request body");
			return new ResponseEntity<>(resp.toString(), HttpStatus.OK);
		}
		
		Query query = new Query(Criteria.where("_id").is(configDocId));
		
		Update update = new Update();
		UpdateResult updateResult = null;
		update.set(configDto.getKey(), configDto.getValue());
		
		try {
			updateResult = mongoTemplate.upsert(query, update, configCol);
		} catch (Exception e) {
			LOGGER.error("Error in adding/updating URI config", e);
			resp.put("message", "Error in adding/updating URI config");
			return new ResponseEntity<>(resp.toString(), HttpStatus.OK);
		}
		
		if (1 == updateResult.getModifiedCount()) {
			Cache cache = cacheManager.getCache("uriCache");
			
			cache.put(configDto.getKey(), configDto.getValue());
			
			resp.put("message", "URI mapping added/updated successfully");
			return new ResponseEntity<>(resp.toString(), HttpStatus.OK);
		} else {
			resp.put("message", "URI mapping is not added/updated");
			return new ResponseEntity<>(resp.toString(), HttpStatus.OK);
		}
	}

}
