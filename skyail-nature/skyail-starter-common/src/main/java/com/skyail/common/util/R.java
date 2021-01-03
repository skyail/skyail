
package com.skyail.common.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Optional;

/**
 * Rest接口返回结果封装
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class R<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String DEFAULT_SUCCESS_MESSAGE = "请求成功";
	public static final String DEFAULT_NULL_MESSAGE = "未查询到数据";
	public static final String DEFAULT_FAILURE_MESSAGE = "操作失败";

	private int code;
	private boolean success;
	private T data;
	private String msg;

	private R(IReturnCode returnCode) {
		this(returnCode, null, returnCode.getMessage());
	}

	private R(IReturnCode returnCode, String msg) {
		this(returnCode, null, msg);
	}

	private R(IReturnCode returnCode, T data) {
		this(returnCode, data, returnCode.getMessage());
	}

	private R(IReturnCode returnCode, T data, String msg) {
		this(returnCode.getCode(), data, msg);
	}

	private R(int code, T data, String msg) {
		this.code = code;
		this.data = data;
		this.msg = msg;
		this.success = ReturnCode.SUCCESS.code == code;
	}

	/**
	 * 判断返回是否为成功
	 *
	 * @param result Result
	 * @return 是否成功
	 */
	public static boolean isSuccess(@Nullable R<?> result) {
		return Optional.ofNullable(result)
			.map(x -> ObjectUtils.nullSafeEquals(ReturnCode.SUCCESS.code, x.code))
			.orElse(Boolean.FALSE);
	}

	/**
	 * 判断返回是否为成功
	 *
	 * @param result Result
	 * @return 是否成功
	 */
	public static boolean isNotSuccess(@Nullable R<?> result) {
		return !R.isSuccess(result);
	}

	/**
	 * 返回R
	 *
	 * @param data 数据
	 * @param <T>  T 泛型标记
	 * @return R
	 */
	public static <T> R<T> data(T data) {
		return data(data, DEFAULT_SUCCESS_MESSAGE);
	}

	/**
	 * 返回R
	 *
	 * @param data 数据
	 * @param msg  消息
	 * @param <T>  T 泛型标记
	 * @return R
	 */
	public static <T> R<T> data(T data, String msg) {
		return data(HttpServletResponse.SC_OK, data, msg);
	}

	/**
	 * 返回R
	 *
	 * @param code 状态码
	 * @param data 数据
	 * @param msg  消息
	 * @param <T>  T 泛型标记
	 * @return R
	 */
	public static <T> R<T> data(int code, T data, String msg) {
		return new R<>(code, data, data == null ? DEFAULT_NULL_MESSAGE : msg);
	}

	/**
	 * 返回R
	 *
	 * @param msg 消息
	 * @param <T> T 泛型标记
	 * @return R
	 */
	public static <T> R<T> success(String msg) {
		return new R<>(ReturnCode.SUCCESS, msg);
	}

	/**
	 * 返回R
	 *
	 * @param returnCode 业务代码
	 * @param <T>        T 泛型标记
	 * @return R
	 */
	public static <T> R<T> success(IReturnCode returnCode) {
		return new R<>(returnCode);
	}

	/**
	 * 返回R
	 *
	 * @param returnCode 业务代码
	 * @param msg        消息
	 * @param <T>        T 泛型标记
	 * @return R
	 */
	public static <T> R<T> success(IReturnCode returnCode, String msg) {
		return new R<>(returnCode, msg);
	}

	/**
	 * 返回R
	 *
	 * @param msg 消息
	 * @param <T> T 泛型标记
	 * @return R
	 */
	public static <T> R<T> fail(String msg) {
		return new R<>(ReturnCode.FAILURE, msg);
	}


	/**
	 * 返回R
	 *
	 * @param code 状态码
	 * @param msg  消息
	 * @param <T>  T 泛型标记
	 * @return R
	 */
	public static <T> R<T> fail(int code, String msg) {
		return new R<>(code, null, msg);
	}

	/**
	 * 返回R
	 *
	 * @param returnCode 业务代码
	 * @param <T>        T 泛型标记
	 * @return R
	 */
	public static <T> R<T> fail(IReturnCode returnCode) {
		return new R<>(returnCode);
	}

	/**
	 * 返回R
	 *
	 * @param returnCode 业务代码
	 * @param msg        消息
	 * @param <T>        T 泛型标记
	 * @return R
	 */
	public static <T> R<T> fail(IReturnCode returnCode, String msg) {
		return new R<>(returnCode, msg);
	}

	/**
	 * 返回R
	 *
	 * @param flag 成功状态
	 * @return R
	 */
	public static <T> R<T> status(boolean flag) {
		return flag ? success(DEFAULT_SUCCESS_MESSAGE) : fail(DEFAULT_FAILURE_MESSAGE);
	}

}
