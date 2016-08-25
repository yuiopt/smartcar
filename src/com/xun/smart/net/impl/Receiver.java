package com.xun.smart.net.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import com.xun.smart.net.IReceiver;
import com.xun.smart.net.OnReceiveListener;

import android.util.Log;

public class Receiver implements IReceiver, Runnable {

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
		if (!isRunning) {
			Log.d(TAG, "Receiver Starting...");
			isRunning = true;
			if (mReceiveThread == null) {
				mReceiveThread = new Thread(this);
				mReceiveThread.setName("Receiver");
				mReceiveThread.start();
				Log.d(TAG, "Receiver Started...");
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
		if (isRunning) {
			Log.d(TAG, "Receiver Stopping...");
			isRunning = false;
			Log.d(TAG, "Receiver Stopped...");
		}
	}

	@Override
	public void run() {
		Log.d(TAG, "Receiver Thread Started");
		BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
		while (isRunning) {
			try {
				if (reader.ready()) {
					String line = reader.readLine();
					// 回调onReceive，传回数据
					if (listsner != null) {
						line+="\n";
						listsner.onReceive(line.getBytes(Charset.forName("UTF-8")));
					}
				}
			} catch (IOException e) {
			}
		}
		if (inStream != null) {
			try {
				reader.close();
				inStream.close();
			} catch (IOException e) {
				inStream = null;
			}
		}
		Log.d(TAG, "Receiver Thread Exited");
	}

}
