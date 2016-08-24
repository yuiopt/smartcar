package com.xun.smart.view;

import com.xun.smart.R;
import com.xun.smart.R.id;
import com.xun.smart.R.layout;
import com.xun.smart.R.menu;
import com.xun.smart.net.OnConnectListener;
import com.xun.smart.net.OnReceiveListener;
import com.xun.smart.net.impl.Connector;
import com.xun.smart.net.impl.Receiver;
import com.xun.smart.net.impl.Sender;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener,OnReceiveListener{
	
	private static final String TAG = "MainActivity";

	public static final String IP = "192.168.1.100";
	
	public static final int PORT = 8000;
	
	private Connector connector;
	
	private Sender mSender;
	
	private Receiver mReceiver;
	
	private TextView mReceive;
	
	private EditText mSendEdit;
	
	private Button mSendBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initData();
	}

	private void initView() {
		mReceive = (TextView) findViewById(R.id.receive);
		mSendEdit = (EditText) findViewById(R.id.edit);
		mSendBtn = (Button) findViewById(R.id.send);
		mSendBtn.setOnClickListener(this);
	}

	private void initData() {
		connector = new Connector();
		mSender = new Sender();
		mReceiver = new Receiver();
		mReceiver.receive(this);
		connector.setOnConnectListener(new OnConnectListener() {
			@Override
			public void onSuccess() {
				Log.d(TAG, "connect success");
				mSender.setOutputStream(connector.getOutStream());
				mReceiver.setInputInputStream(connector.getInStream());
			}
			
			@Override
			public void onError(int code, String msg) {
				Log.e(TAG, "code:"+code+",msg:"+msg);	
			}
			
		});
		connector.connect(IP, PORT);
		mReceiver.start();
		mSender.start();
	}

	@Override
	public void onClick(View v) {
		// TODO 处理点击事件
		int id = v.getId();
		switch (id) {
		case R.id.send:
			send();
			break;
		default:
			break;
		}
	}

	private void send() {
		String text = mSendEdit.getText().toString().trim();
		if(!TextUtils.isEmpty(text)){
			mReceive.append(">>"+text+"\n");
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//Activity退出的时候一定要结束子线程，否则会内存泄漏甚至崩溃
		mSender.stop();
		mReceiver.stop();
		//断开连接
		connector.disconnect();
	}

	@Override
	public void onReceive(byte[] data) {
		//TODO 在这里处理接受到的数据
		// 注意该方法在子线程中执行，显示数据使用runOnUiThread或handler发消息
		final String str = new String(data);
		Log.d(TAG, "OnReceive:"+str);
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mReceive.append("<<"+str+"\n");
			}
		});
	}

}
