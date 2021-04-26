package com.yin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.yin.bean.CityVO;
import com.yin.common.TemperatureException;
import com.yin.config.ExceptionRetry;
import com.yin.config.HttpClient;
import com.yin.service.TemperatureService;

@Service
public class TemperatureServiceImpl implements TemperatureService{
	
	@Autowired
	public HttpClient httpClient;

	@Override
	public Optional<String> getTemperature(String province, String city, String county) {
		Map<String , String> proviceMap = getProvices();
		if(!proviceMap.containsKey(province)) {
			throw new TemperatureException(1001, "Invalid province.");
		}
		Map<String , String> cityMap = getCitys(province);
		if(!cityMap.containsKey(city)){
			throw new TemperatureException(1002, "Invalid city.");
		}
		Map<String , String> countyMap = getCountys(province , city);
		if(!countyMap.containsKey(county)) {
			throw new TemperatureException(1003, "Invalid county.");
		}
		CityVO vo = getCountys(province, city, county);
		return Optional.ofNullable(vo.getTemp());
	}
	
	
	private Map<String , String> getProvices(){
		Map<String , String> result = new HashMap<String, String>();
		JSONObject json = httpClient.doGet("http://www.weather.com.cn/data/city3jdata/china.html", null);
		result = JSONObject.parseObject(json.toJSONString(), Map.class);
		return result;
	}
	
	
	private Map<String , String> getCitys(String province){
		Map<String , String> result = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		sb.append("http://www.weather.com.cn/data/city3jdata/provshi/").append(province).append(".html");
		JSONObject json = httpClient.doGet(sb.toString(), null);
		result = JSONObject.parseObject(json.toJSONString(), Map.class);
		return result;
	}
	
	private Map<String , String> getCountys(String province , String city){
		Map<String , String> result = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		sb.append("http://www.weather.com.cn/data/city3jdata/station/").append(province).append(city).append(".html");
		JSONObject json = httpClient.doGet(sb.toString(), null);
		result = JSONObject.parseObject(json.toJSONString(), Map.class);
		return result;
	}
	
	private CityVO getCountys(String province , String city , String county){
		StringBuffer sb = new StringBuffer();
		Map<String , String> result = new HashMap<String, String>();
		sb.append("http://www.weather.com.cn/data/sk/").append(province).append(city).append(county).append(".html");
		JSONObject json = httpClient.doGet(sb.toString(), null);
		CityVO vo = null;
		if(json!=null) {
			vo = JSONObject.parseObject(json.get("weatherinfo").toString(), CityVO.class);
		}
		return vo;
	}
	
}
