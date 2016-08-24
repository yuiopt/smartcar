package com.xun.smart.net.impl;

import java.io.IOException;
import java.io.InputStream;

import com.xun.smart.net.IReceiver;
import com.xun.smart.net.OnReceiveListener;

import android.util.Log;

public class Receiver implements IReceiver,Runnable {
	
	private static final String TAG = "Receiver";

	private Thread mReceiveThread;
	
	private OnReceiveListener listsner;
	
	private boolean isRunning;
	
	private InputStream inStream;
	
	public Receiver() {
		isRunning = false;
	}

	@Override
	public void setInputInputStream(InputStream inStream) {
		this.inStream = inStream;
	}
	
	@Override
	public void start() {
		if(!isRunning){
			Log.d(TAG,"Receiver Starting...");
			isRunning = true;
			if(mReceiveThread == null){
				mReceiveThread = new Thread(this);
				mReceiveThread.setName("Receiver");
				mReceiveThread.start();
				Log.d(TAG,"Receiver Started...");
			}
		}
	}

	/**
	 * 设置回调
	 */
	@Override
	public void receive(OnReceiveListener listener) {
		this.listsner = listener;
	}

	@Override
	public void stop() {
		if(isRunning){
			Log.d(TAG,"Receiver Stopping...");
			if(inStream!=null){
				try {
					inStream.close();
				} catch (IOException e) {
					inStream = null;
				}
			}
			isRunning = false;
			Log.d(TAG,"Receiver Stopped...");
		}
	}

	@Override
	public void run() {
		Log.d(TAG, "Receiver Thread Started");
		while(isRunning){
			//TODO 收数据，自己实现，校验都在这里处理
			if(inStream!=null){
				try {
					int len = inStream.available();
					if(len > 0){
						byte[] data = new byte[len];
						//数据校验无误，回调onReceive，传回数据
						inStream.read(data, 0, len);
						if (data!=null) {
							if(listsner!=null){
								listsner.onReceive(data);
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		Log.d(TAG, "Receiver Thread Exited");
	}

}
