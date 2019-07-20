package com.example.demo.config;

import java.nio.charset.Charset;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

	@Bean(name = "restTemplate")
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		restTemplate.setRequestFactory(configureRequestFactory());

		return restTemplate;
	}

	private ClientHttpRequestFactory configureRequestFactory() {
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();

		// Increase max total connection to 200
		connManager.setMaxTotal(200);
		// Increase default max connection per route to 20
		connManager.setDefaultMaxPerRoute(20);
		// Increase max connections for localhost:9045 to 50
		HttpHost host = new HttpHost("http://localhost", 9045);
		connManager.setMaxPerRoute(new HttpRoute(host), 50);

		CloseableHttpClient httpClient = HttpClients.custom()
				.setConnectionManager(connManager)
				.setDefaultCredentialsProvider(provider())
				.build();

		return new HttpComponentsClientHttpRequestFactory(httpClient);
	}
	
	private CredentialsProvider provider() {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = 
          new UsernamePasswordCredentials("user1", "user1Pass");
        provider.setCredentials(AuthScope.ANY, credentials);
        return provider;
    }

}
