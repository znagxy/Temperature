package com.yin.common;

public class ResponseUtils {

	public static <T> Result<T> success(T data){
        return new Result<T>(ResponseCode.SUCCESS,"success" , data);
    }
   
 
    public static <T> Result<T> fail(int code, String msg){
        return new Result<T>(code, msg, null);
    }	
}
