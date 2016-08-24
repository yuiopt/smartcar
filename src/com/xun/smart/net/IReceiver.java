package com.xun.smart.net;

import java.io.InputStream;

public interface IReceiver {

	public void setInputInputStream(InputStream inStream);
	
	public void start();
	
	public void receive(OnReceiveListener listener);
	
	public void stop();
	
}
