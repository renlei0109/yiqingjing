package com.yiqingjing.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FeedBackActivity extends Activity{
	
	private EditText modifyText, addText;
	private Button send;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback);
		modifyText = (EditText) findViewById(R.id.modify);
		addText = (EditText) findViewById(R.id.add);
		send = (Button) findViewById(R.id.send);
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(modifyText.getText().toString().equals("")&&addText.getText().toString().equals("")){
					Toast.makeText(FeedBackActivity.this, "请填写您的宝贵意见", Toast.LENGTH_SHORT).show();
					return;
				}
				finish();
			}
		});
		findViewById(R.id.feedback_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

}
