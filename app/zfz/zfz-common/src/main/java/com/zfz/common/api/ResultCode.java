package com.zfz.common.api;

/**
 * author: DreamSaddle
 * date: 2020年01月03日
 * time: 16:33
 */
public enum ResultCode {

	SUCCESS(200, "操作成功"),
	FAILED(500, "操作失败"),
	VALIDATE_FAILED(801, "参数校验失败"),
	UNAUTHORIZED(902, "未登录"),
	FORBIDDEN(903, "未授权");

	private int code;
	private String message;


	ResultCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
