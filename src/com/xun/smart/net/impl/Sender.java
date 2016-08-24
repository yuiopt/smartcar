package com.xun.smart.net.impl;

import java.nio.charset.Charset;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.xun.smart.net.ISender;

public class Sender implements ISender, Runnable {

	private Thread mSendThread;

	private boolean isRunning;

	// 队列，线程安全
	private BlockingQueue<byte[]> quene;

	public Sender() {
		isRunning = false;
		// 队列大小100，可存储100个数组
		quene = new LinkedBlockingQueue<byte[]>(100);
	}

	@Override
	public void start() {
		if (!isRunning) {
			isRunning = true;
			if (mSendThread == null) {
				mSendThread = new Thread(this);
				mSendThread.start();
			}
		}
	}

	@Override
	public void send(byte[] data) {
		if (isRunning) {
			if (quene != null && data != null) {
				// 入队，若队列满，不会加入数据，也不会报错，可自己判断队列是否满
				quene.offer(data);
			}
		}
	}

	@Override
	public void send(String data) {
		if (data != null && data.length() > 0) {
			byte[] raw = data.getBytes(Charset.forName("UTF-8"));
			send(raw);
		}
	}

	@Override
	public void stop() {
		if (isRunning) {
			isRunning = false;
			quene.clear();
			mSendThread = null;
			quene = null;
		}
	}

	@Override
	public void run() {
		while (isRunning) {
			if (!quene.isEmpty()) {
				// 队列不为空，出队
				byte[] data = quene.poll();
				// TODO 发送数据，自己实现
				
			}else{
				//队列为空，先睡500ms
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
