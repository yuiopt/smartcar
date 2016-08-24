package com.xun.smart.net;

public interface OnConnectListener {

	public void onSuccess();
	
	public void onError(int code,String msg);
	
}
