package com.yiqingjing.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class NewSceneActivity extends Activity implements OnClickListener{
	
	private ImageButton back;
	//wlan模式;
	private View wlanView;
	//位置模式
	private View locationView;
	//计时模式
	//private View calcuView;
	//时间模式
	private View timeView;
	//普通模式
	private View normalView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newscene);
		
		back = (ImageButton) findViewById(R.id.new_scene_back);
		wlanView = findViewById(R.id.wlan_scene);
		locationView = findViewById(R.id.location_scene);
		timeView = findViewById(R.id.time_scene);
		normalView = findViewById(R.id.normal_sence);
		
		wlanView.setOnClickListener(this);
		locationView.setOnClickListener(this);
		//calcuView.setOnClickListener(this);
		timeView.setOnClickListener(this);
		back.setOnClickListener(this);
		normalView.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.new_scene_back:
			finish();
			overridePendingTransition(R.anim.home_activity_in, R.anim.new_activity_out);
			break;
		case R.id.wlan_scene:
			Intent wlanIntent = new Intent(NewSceneActivity.this, DetailSettingActivity.class);
			wlanIntent.putExtra("mode", 1);
			startActivity(wlanIntent);
			finish();
			break;
		case R.id.location_scene:
			Intent locationIntent = new Intent(NewSceneActivity.this, DetailSettingActivity.class);
			locationIntent.putExtra("mode", 2);
			startActivity(locationIntent);
			finish();
			break;
		case R.id.time_scene:
			Intent timeIntent = new Intent(NewSceneActivity.this, DetailSettingActivity.class);
			timeIntent.putExtra("mode", 3);
			startActivity(timeIntent);
			finish();
			break;
		case R.id.normal_sence:
			Intent normalIntent = new Intent(NewSceneActivity.this, DetailSettingActivity.class);
			normalIntent.putExtra("mode", 0);
			startActivity(normalIntent);
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
			finish();
			overridePendingTransition(R.anim.home_activity_in, R.anim.new_activity_out);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
