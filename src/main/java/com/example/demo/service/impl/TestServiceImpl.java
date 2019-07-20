package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.demo.pojo.MyClass;
import com.example.demo.service.TestService;
import com.example.demo.util.RestClient;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	RestClient restClient;

	@Override
	@Retryable(value = {
			Exception.class }, maxAttemptsExpression = "#{${retry.max-attempts}}",
					backoff = @Backoff(delayExpression = "#{${retry.delay}}",
					maxDelayExpression = "#{${retry.max.delay}}",
					multiplierExpression = "#{${retry.multiplier}}"))
	public MyClass testGetServiceCall() {
		final String baseUri = "http://localhost:9055/test";

		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		HttpHeaders headers = new HttpHeaders();
		MyClass myClass = new MyClass();

		queryParams.add("test", "test");
		headers.set("testHeader", "headerValue");

		MyClass myClassRes = restClient.callGetService(myClass, baseUri, queryParams, headers);

		return myClassRes;
	}
	
	@Recover
	public String recoveryMethod() {
		System.out.println("Recovered");
		
		return "recovered";
	}

	@Override
	public MyClass testPostServiceCall() {
		final String baseUri = "http://localhost:9055/test";

		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		HttpHeaders headers = new HttpHeaders();
		MyClass myClass = new MyClass();

		myClass.setMessage("test");
		queryParams.add("test", "test");
		headers.set("testHeader", "headerValue");

		MyClass myClassRes = restClient.callPostService(myClass, myClass, baseUri, queryParams, headers);

		return myClassRes;
	}

}
