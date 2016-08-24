package com.xun.smart.view;

import com.xun.smart.R;
import com.xun.smart.R.id;
import com.xun.smart.R.layout;
import com.xun.smart.R.menu;
import com.xun.smart.net.OnReceiveListener;
import com.xun.smart.net.impl.Receiver;
import com.xun.smart.net.impl.Sender;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener,OnReceiveListener{
	
	private Sender mSender;
	
	private Receiver mReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initData();
	}

	private void initView() {
		// TODO 在这里findViewByID
	}

	private void initData() {
		mSender = new Sender();
		mReceiver = new Receiver();
		mReceiver.receive(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View arg0) {
		// TODO 处理点击事件
		// 启动 例如 mSender.start() mReceiver.start()
		//发数据 例如 mSender.send(data)，改方法有两个重载方法，参数类型不一样
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//Activity退出的时候一定要结束子线程，否则会内存泄漏甚至崩溃
		mSender.stop();
		mReceiver.stop();
	}

	@Override
	public void onReceive(byte[] data) {
		//TODO 在这里处理接受到的数据
		// 注意该方法在子线程中执行，显示数据使用runOnUiThread或handler发消息
	}
}
