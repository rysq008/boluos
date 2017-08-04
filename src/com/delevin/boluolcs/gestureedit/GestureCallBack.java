package com.delevin.boluolcs.gestureedit;

/**
 *     @author 李红涛  @version 创建时间：2016-12-22 下午4:56:08    类说明 
 */

public interface GestureCallBack {
	/**
	 * 用户设置/输入了手势密码
	 */
	public abstract void onGestureCodeInput(String inputCode);

	/**
	 * 代表用户绘制的密码与传入的密码相同
	 */
	public abstract void checkedSuccess();

	/**
	 * 代表用户绘制的密码与传入的密码不相同
	 */
	public abstract void checkedFail();
}
