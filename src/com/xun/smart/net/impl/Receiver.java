package com.xun.smart.net.impl;

import com.xun.smart.net.IReceiver;
import com.xun.smart.net.OnReceiveListener;

public class Receiver implements IReceiver,Runnable {

	private Thread mReceiveThread;
	
	private OnReceiveListener listsner;
	
	private boolean isRunning;
	
	@Override
	public void start() {
		if(!isRunning){
			isRunning = true;
			if(mReceiveThread == null){
				mReceiveThread = new Thread(this);
				mReceiveThread.start();
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
			isRunning = false;
		}
	}

	@Override
	public void run() {
		while(isRunning){
			//TODO 收数据，自己实现，校验都在这里处理
			
			//数据校验无误，回调onReceive，传回数据
			byte[] data = new byte[1024];
			if (data!=null) {
				if(listsner!=null){
					listsner.onReceive(data);
				}
			}
		}
	}

}
