package com.yiqingjing.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

public class LoadingActivity extends Activity {
	
	private ImageView loadingImg;
	private Handler showImgHandler;
	private Handler turnHandler;
	private SharedPreferences sharedPreferences;
	private boolean isFirst;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		sharedPreferences = getSharedPreferences("isfirst", MODE_PRIVATE);
		isFirst = sharedPreferences.getBoolean("isfirst", true);
		InitHandler();
		loadingImg = (ImageView) findViewById(R.id.loading_img);
		showImgHandler.sendEmptyMessageDelayed(0x123, 1000);
	}
	
	private void InitHandler(){
		showImgHandler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				if(msg.what == 0x123){
					loadingImg.setVisibility(View.VISIBLE);
					turnHandler.sendEmptyMessageDelayed(0x123, 600);
				}
			}
			
		};
		
		turnHandler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				if(msg.what == 0x123){
					if(!isFirst){
						Intent intent = new Intent(LoadingActivity.this, HomePageActivity.class);
						startActivity(intent);
						finish();
					}else{
						Intent intent = new Intent(LoadingActivity.this, leadingActivity.class);
						startActivity(intent);
						finish();
					}
				}
			}
			
		};
	}

}
