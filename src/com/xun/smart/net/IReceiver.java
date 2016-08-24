package com.xun.smart.net;

public interface IReceiver {

	public void start();
	
	public void receive(OnReceiveListener listener);
	
	public void stop();
	
}
