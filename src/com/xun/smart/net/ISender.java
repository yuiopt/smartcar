package com.xun.smart.net;

import java.io.OutputStream;

public interface ISender {
	
	public void setOutputStream(OutputStream outStream);
	
	public void start();
	
	public void send(byte[] data);
	
	public void send(String data);
	
	public void stop();
	
}
