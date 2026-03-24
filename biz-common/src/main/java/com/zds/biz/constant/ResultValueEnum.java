package com.zds.biz.constant;

/**
 * API返回枚举类
 */
public enum ResultValueEnum implements BaseEnum<Integer> {

	SYS_OK(1,"SUCCESS"),
	SYS_FAILURE(2,"操作失败,请重试"),
	SYS_ERROR(3,"系统异常,请联系管理员"),
	NO_LOGIN(4,"未登录"),
	WAIT_AUDIT(5,"账号待审核中"),
	NO_POWER(6,"权限不足"),
	;

	private int key;

	private String msg;

	ResultValueEnum(int key, String msg){
		this.key = key;
		this.msg = msg;
	}

    @Override
	public Integer getKey() {
		return this.key;
	}

	@Override
	public String getTitle() {
		return this.msg;
	}

	/**
	 * 根据枚举编号查询具体枚举类型
	 * @param key 枚举编号
	 * @return {@link ResultValueEnum}
	 */
	public static ResultValueEnum queryByKey(int key){
		ResultValueEnum result = query(key);
		if (null == result) {
			result = ResultValueEnum.SYS_ERROR;
		}
		return result;
	}

	private static ResultValueEnum query(int key){
		if (key > 0) {
			ResultValueEnum[] values = ResultValueEnum.values();
			for (ResultValueEnum result : values) {
				if (result.getKey() == key) {
					return result;
				}
			}
		}
		return null;
	}
}
