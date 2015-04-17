package com.yiqingjing.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class OurGroupActivity extends Activity{
	
	//返回按钮
	private ImageButton returnButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ourgroup);
		
		returnButton = (ImageButton) findViewById(R.id.our_group_return);
		returnButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

}
