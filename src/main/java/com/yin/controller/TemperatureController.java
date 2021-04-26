package com.yin.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yin.common.ResponseUtils;
import com.yin.common.Result;
import com.yin.common.TemperatureException;
import com.yin.config.MyLimit;
import com.yin.service.TemperatureService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TemperatureController {
	
	@Autowired
	public TemperatureService temperatureService;
	
	
	@PostMapping("/getTemperature")
	@MyLimit(limitNum = 100)
	public Result getTemperature(@RequestParam String province, @RequestParam String city, @RequestParam String county) {
		try {
			Optional<String> temperature = temperatureService.getTemperature(province , city,county);
			System.out.println("called...");
			return ResponseUtils.success(temperature);
		} catch (TemperatureException e) {
			return ResponseUtils.fail(e.getCode(), e.getMessage());
		}
		
	}

}
