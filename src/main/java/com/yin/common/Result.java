package com.yin.common;

import java.io.Serializable;

import lombok.Data;

public class Result<T> implements Serializable{
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	private static final long serialVersionUID = 3728877563912075885L;

    private int code;
    private String msg;
    private T data;

    public Result(int code, String message, T data) {
        this.code = code;
        this.msg = message;
        this.data = data;
    }
 


}
