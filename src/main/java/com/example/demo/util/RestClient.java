package com.example.demo.util;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RestClient {

	@Autowired
	RestTemplate restTemplate;

	public <S> S callGetService(S responseType, String baseUri, MultiValueMap<String, String> queryParams,
			HttpHeaders headers) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUri).queryParams(queryParams);
		String uri = builder.build().encode().toUriString();

		HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);

		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);

		return mapJsonToObject(response.getBody(), responseType);
	}

	public <R, S> S callPostService(R request, S responseType, String baseUri,
			MultiValueMap<String, String> queryParams, HttpHeaders headers) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUri).queryParams(queryParams);
		String uri = builder.build().encode().toUriString();

		HttpEntity<R> requestEntity = new HttpEntity<>(request, headers);

		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);

		return mapJsonToObject(response.getBody(), responseType);
	}
	
	public <R, S> S callPutService(R request, S responseType, String baseUri,
			MultiValueMap<String, String> queryParams, HttpHeaders headers) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUri).queryParams(queryParams);
		String uri = builder.build().encode().toUriString();

		HttpEntity<R> requestEntity = new HttpEntity<>(request, headers);

		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.PUT, requestEntity, String.class);

		return mapJsonToObject(response.getBody(), responseType);
	}

	@SuppressWarnings("unchecked")
	private <S> S mapJsonToObject(String jsonString, S s) {
		ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		S obj = null;

		try {
			obj = (S) objectMapper.readValue(jsonString, s.getClass());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return obj;
	}

}
