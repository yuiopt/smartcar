package com.xun.smart.net;

/**
 * 
 * @author hetwen
 * 里面只写了一个方法，onReceive,也可以添加多个回调比如校验错误时回调onError()，等等根据需要自己设定
 */
public interface OnReceiveListener {

	/**
	 * 收到数据的回调
	 * @param data
	 */
	public void onReceive(byte[] data);
	
}
