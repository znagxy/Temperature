package com.yin.config;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.yin.common.TemperatureException;


@Service
public class HttpClient {
	
	@Autowired
	public RestTemplate restTemplate;
	
	@ExceptionRetry(times=3)
	public JSONObject doGet(String url , MultiValueMap<String, String> param) {
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		System.out.println("calling....url:" + url);
		if(response.getStatusCode().value()!=200) {
			throw new TemperatureException(1004, "https error");
		}
		JSONObject json = JSONObject.parseObject(response.getBody());
		return json;
	}

}
