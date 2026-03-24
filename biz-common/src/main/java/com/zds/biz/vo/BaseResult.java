package com.zds.biz.vo;

import com.zds.biz.constant.ResultValueEnum;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@ApiModel(description = "返回结果基类")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResult<T> implements Serializable {

	private static final long serialVersionUID = -8767713269764707611L;

	@ApiModelPropertyCheck("状态码(1.业务成功,2.业务失败,3.系统异常,4.未登录)")
	private int code;

	@ApiModelPropertyCheck("提示信息")
	private String msg;

	@ApiModelPropertyCheck("业务数据")
	private T data;

	public static <T> BaseResult<T> newInstance() {
		return new BaseResult<T>(ResultValueEnum.SYS_OK);
	}

	public static <T> BaseResult<T> newInstance(ResultValueEnum resultValueEnum) {
		return new BaseResult<T>(resultValueEnum);
	}

	/**
	 * 业务成功
	 */
	public static <T> BaseResult<T> success(T data) {
		BaseResult<T> result = BaseResult.newInstance();
		result.setData(data);
		return result;
	}

	/**
	 * 业务成功
	 */
	public static <T> BaseResult<T> success(String message) {
		BaseResult<T> result = BaseResult.newInstance();
		result.setMsg(message);
		return result;
	}

	/**
	 * 业务成功
	 */
	public static <T> BaseResult<T> success(String message,T data) {
		BaseResult<T> result = BaseResult.newInstance();
		result.setMsg(message);
		result.setData(data);
		return result;
	}

	/**
	 * 业务失败
	 */
	public static <T> BaseResult<T> failure() {
		BaseResult<T> result = BaseResult.newInstance(ResultValueEnum.SYS_FAILURE);
		return result;
	}

	/**
	 * 业务失败
	 */
	public static <T> BaseResult<T> failure(String message) {
		BaseResult<T> result = failure();
		result.setMsg(message);
		return result;
	}

	/**
	 * 系统异常
	 */
	public static <T> BaseResult<T> sysError() {
		BaseResult<T> result = BaseResult.newInstance(ResultValueEnum.SYS_ERROR);
		return result;
	}

	/**
	 * 系统异常
	 */
	public static <T> BaseResult<T> sysError(String message) {
		BaseResult<T> result = sysError();
		result.setMsg(message);
		return result;
	}

	/**
	 * 判断业务操作成功或失败
	 */
	public static <T> BaseResult<T> judgeOperate(boolean flag) {
		return flag ? BaseResult.success("操作成功") : BaseResult.failure("操作失败");
	}

	public void setResultValueEnum(ResultValueEnum resultValueEnum){
		this.code = resultValueEnum.getKey();
		this.msg = resultValueEnum.getTitle();
	}

	private BaseResult(ResultValueEnum resultValueEnum) {
		super();
		this.code = resultValueEnum.getKey();
		this.msg = resultValueEnum.getTitle();
	}
}
