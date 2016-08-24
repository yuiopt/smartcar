package com.xun.smart.net;

public interface IConnector {

	public void connect(String connect,int port);
	
	public void setOnConnectListener(OnConnectListener listener);
	
	public void disconnect();
	
}
