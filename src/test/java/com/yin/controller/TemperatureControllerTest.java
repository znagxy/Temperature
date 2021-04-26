package com.yin.controller;


import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yin.TemperatureApplication;
import com.yin.common.Result;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TemperatureApplication.class)
class TemperatureControllerTest {
	
	@Autowired
	public TemperatureController controller;
	

	@Test
	void testGetTemperature() {
		String province = "10119";
		String city = "04";
		String county = "01";
		Result result = controller.getTemperature(province, city, county);
		//测试正常返回
		Assert.assertEquals(0, result.getCode());
		
		//测试非法省
		province ="9999";
		result = controller.getTemperature(province, city, county);
		Assert.assertEquals(1001, result.getCode());
		
		//测试非法城市
		province ="10119";
		city = "99";
		result = controller.getTemperature(province, city, county);
		Assert.assertEquals(1002, result.getCode());
		
		//测试非法县
		province = "10119";
		city = "04";
		county = "99";
		result = controller.getTemperature(province, city, county);
		Assert.assertEquals(1003, result.getCode());
	}
	
}
