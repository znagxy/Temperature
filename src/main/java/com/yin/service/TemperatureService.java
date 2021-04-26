package com.yin.service;

import java.util.Optional;

public interface TemperatureService {

	Optional<String> getTemperature(String province, String city, String county);

}
