package com.yin.common;

public class TemperatureException extends RuntimeException{

	private Integer code;//自定义异常码


	 public Integer getCode() {
		 return code;
	 }


	 public void setCode(Integer code) {
		 this.code = code;
	 }


	 public TemperatureException(Integer code , String message) {
		 super(message);
		 this.code = code;
	 }
	
}
