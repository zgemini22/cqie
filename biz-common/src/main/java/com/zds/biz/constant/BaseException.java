package com.zds.biz.constant;

/**
 * 自定义基类异常
 */
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 2048249368097972960L;

	private BaseEnum<Integer> baseExceptionEnum;

	public BaseException() {
	}

	public BaseException(String arg0) {
		super(arg0);
		this.baseExceptionEnum = ResultValueEnum.SYS_FAILURE;
	}

	public BaseException(Throwable arg0) {
		super(arg0);
	}

	public BaseException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public BaseException(BaseEnum<Integer> bizExceptionEnum, String message){
		super(message);
		this.baseExceptionEnum = bizExceptionEnum;
	}

	public BaseException(BaseEnum<Integer> bizExceptionEnum){
		super(bizExceptionEnum.getTitle());
		this.baseExceptionEnum = bizExceptionEnum;
	}

	public BaseEnum<Integer> getBizExceptionEnum(){
		return this.baseExceptionEnum;
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
