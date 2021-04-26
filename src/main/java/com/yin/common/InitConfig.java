package com.yin.common;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Configuration
public class InitConfig {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		builder.additionalMessageConverters(new StringHttpMessageConverter(StandardCharsets.UTF_8));
		return builder.build();
	}
}
