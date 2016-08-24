package com.xun.smart.net.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.xun.smart.net.IConnector;
import com.xun.smart.net.OnConnectListener;

public class Connector implements IConnector {
	
	private OnConnectListener listener;
	
	private boolean isConnected = false;

	private Socket socket;
	
	private InputStream inStream;
	
	private OutputStream outStream;
	
	public InputStream getInStream() {
		return inStream;
	}

	public OutputStream getOutStream() {
		return outStream;
	}

	@Override
	public void setOnConnectListener(OnConnectListener listener) {
		this.listener = listener;
	}
	
	@Override
	public void connect(final String ip,final int port) {
		//IOca操作放在子线程
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					socket = new Socket(ip,port);
					inStream = socket.getInputStream();
					outStream = socket.getOutputStream();
					isConnected = true;
					if(listener!=null){
						listener.onSuccess();
					}
				} catch (UnknownHostException e) {
					if(listener!=null){
						listener.onError(1, e.getLocalizedMessage());
					}
				} catch (IOException e) {
					if(listener!=null){
						listener.onError(2, e.getLocalizedMessage());
					}
				}
			}
		}).start();
	}

	@Override
	public void disconnect() {
		if(isConnected && socket!=null){
			isConnected = false;
			try {
				socket.close();
			} catch (IOException e) {
				socket = null;
			}
		}
	}
}
