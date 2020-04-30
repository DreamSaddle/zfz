package com.zfz.common.api;

import java.util.HashMap;
import java.util.Map;

/**
 * author: DreamSaddle
 * date: 2020年01月03日
 * time: 16:32
 */
public class Result<T> {
	private int code;
	private String message;
	private T data;

	protected Result() {
	}

	protected Result(int code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	/**
	 * 根据操作结果处理
	 * @param flag 成功标识
	 */
	public static <T> Result<T> smart(boolean flag) {
		if (flag) {
			return success();
		}

		return failed();
	}

	/**
	 * 成功返回结果
	 */
	public static <T> Result<T> success() {
		return new Result<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
	}

	/**
	 * 成功返回结果
	 *
	 * @param data 获取的数据
	 */
	public static <T> Result<T> success(T data) {
		return new Result<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
	}

	/**
	 * 成功返回结果
	 *
	 * @param data 获取的数据
	 * @param  message 提示信息
	 */
	public static <T> Result<T> success(T data, String message) {
		return new Result<T>(ResultCode.SUCCESS.getCode(), message, data);
	}

	/**
	 * 失败返回结果
	 * @param errorCode 错误码
	 */
	public static <T> Result<T> failed(ResultCode errorCode) {
		return new Result<T>(errorCode.getCode(), errorCode.getMessage(), null);
	}

	/**
	 * 失败返回结果
	 * @param message 提示信息
	 */
	public static <T> Result<T> failed(String message) {
		return new Result<T>(ResultCode.FAILED.getCode(), message, null);
	}

	/**
	 * 失败返回结果
	 */
	public static <T> Result<T> failed() {
		return failed(ResultCode.FAILED);
	}

	/**
	 * 参数验证失败返回结果
	 */
	public static <T> Result<T> validateFailed() {
		return failed(ResultCode.VALIDATE_FAILED);
	}

	/**
	 * 参数验证失败返回结果
	 * @param message 提示信息
	 */
	public static <T> Result<T> validateFailed(String message) {
		return new Result<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
	}

	/**
	 * 未登录返回结果
	 */
	public static <T> Result<T> unauthorized(T data) {
		return new Result<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
	}

	/**
	 * 未授权返回结果
	 */
	public static <T> Result<T> forbidden(T data) {
		return new Result<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
	}


	public Map<String, Object> toMap() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", this.getCode());
		resultMap.put("message", this.getMessage());
		resultMap.put("data", this.getData());

		return resultMap;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
