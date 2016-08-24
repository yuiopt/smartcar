package com.xun.smart.net;

public interface ISender {
	
	public void start();
	
	public void send(byte[] data);
	
	public void send(String data);
	
	public void stop();
	
}
