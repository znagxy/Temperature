package com.yin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yin.TemperatureApplication;
import com.yin.config.HttpClient;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TemperatureApplication.class)
class HttpClientTest {

	@Autowired
	public HttpClient httpClient;

	@Mock
	public RestTemplate restTemplate;

	@Test
	void testDoGetRetryWithNullPointException() {
		ReflectionTestUtils.setField(httpClient, "restTemplate", restTemplate );
		JSONObject json = JSONObject.parseObject("{\"10101\":\"北京\",\"10102\":\"上海\",\"10103\":\"天津\",\"10104\":\"重庆\",\"10105\":\"黑龙江\",\"10106\":\"吉林\",\"10107\":\"辽宁\",\"10108\":\"内蒙古\",\"10109\":\"河北\",\"10110\":\"山西\",\"10111\":\"陕西\",\"10112\":\"山东\",\"10113\":\"新疆\",\"10114\":\"西藏\",\"10115\":\"青海\",\"10116\":\"甘肃\",\"10117\":\"宁夏\",\"10118\":\"河南\",\"10119\":\"江苏\",\"10120\":\"湖北\",\"10121\":\"浙江\",\"10122\":\"安徽\",\"10123\":\"福建\",\"10124\":\"江西\",\"10125\":\"湖南\",\"10126\":\"贵州\",\"10127\":\"四川\",\"10128\":\"广东\",\"10129\":\"云南\",\"10130\":\"广西\",\"10131\":\"海南\",\"10132\":\"香港\",\"10133\":\"澳门\",\"10134\":\"台湾\"}");
		String url = "http://www.weather.com.cn/data/city3jdata/china.html";

		Mockito.lenient().doAnswer(invocation -> {
			throw new NullPointerException();
		}).when(restTemplate).getForEntity(anyString(), any());
		Assertions.assertThrows(NullPointerException.class, ()->httpClient.doGet(url,any(MultiValueMap.class)));
	}
	@Test
	void testDoGetRetryWithNullPointExceptionAndThenSuccess() {
		ReflectionTestUtils.setField(httpClient, "restTemplate", restTemplate );
		JSONObject json = JSONObject.parseObject("{\"10101\":\"北京\",\"10102\":\"上海\",\"10103\":\"天津\",\"10104\":\"重庆\",\"10105\":\"黑龙江\",\"10106\":\"吉林\",\"10107\":\"辽宁\",\"10108\":\"内蒙古\",\"10109\":\"河北\",\"10110\":\"山西\",\"10111\":\"陕西\",\"10112\":\"山东\",\"10113\":\"新疆\",\"10114\":\"西藏\",\"10115\":\"青海\",\"10116\":\"甘肃\",\"10117\":\"宁夏\",\"10118\":\"河南\",\"10119\":\"江苏\",\"10120\":\"湖北\",\"10121\":\"浙江\",\"10122\":\"安徽\",\"10123\":\"福建\",\"10124\":\"江西\",\"10125\":\"湖南\",\"10126\":\"贵州\",\"10127\":\"四川\",\"10128\":\"广东\",\"10129\":\"云南\",\"10130\":\"广西\",\"10131\":\"海南\",\"10132\":\"香港\",\"10133\":\"澳门\",\"10134\":\"台湾\"}");
		String url = "http://www.weather.com.cn/data/city3jdata/china.html";
		ResponseEntity<String> responseEntity = ResponseEntity.ok(json.toJSONString());
		Mockito.lenient().doAnswer(invocation -> {
			throw new NullPointerException();
		}).doReturn(responseEntity).when(restTemplate).getForEntity(anyString(), any());
		JSONObject jsonObject = httpClient.doGet(url, any(MultiValueMap.class));
		Assertions.assertEquals(json.toJSONString(), jsonObject.toJSONString());
	}
}
