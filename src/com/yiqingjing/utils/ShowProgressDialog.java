package com.yiqingjing.utils;

import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.yiqingjing.activity.R;

public class ShowProgressDialog {
	
	private Context context;
	private SharedPreferences sharedPreferences;
	private HashMap<String, String> setting;
	
	private ShowProgressDialog(Context context, HashMap<String, String> setting){
		this.context = context;
		this.setting = setting;
	}
	
	private void showSeekBarDialog(int percent, final int max, String title, final int whitch
			, final TextView textView){
		View view = ((Activity)context).getLayoutInflater().inflate(R.layout.seekbar, null);
		final TextView showPercent = (TextView) view.findViewById(R.id.seektext);
		showPercent.setText(percent+"%");
		final SeekBar showSeekBar = (SeekBar) view.findViewById(R.id.seekbar);
		showSeekBar.setProgress(percent*255/100);
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
		alertBuilder.setTitle(title).setView(view);
		alertBuilder.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				switch (whitch) {
				case R.id.alarm_volume:
					//changeVolume.setAlarmVolume(showSeekBar.getProgress()*max/255);
					textView.setText(showSeekBar.getProgress()*100
							/255+"%");
					break;
				case R.id.media_volume:
					//changeVolume.setMusicVolume(showSeekBar.getProgress()*max /255);
					textView.setText(showSeekBar.getProgress()*100/255+"%");
					break;
				case R.id.ring_volume:
					//changeVolume.setRingVolume(showSeekBar.getProgress()*max/255);
					textView.setText(showSeekBar.getProgress()*100/255+"%");
					break;
				case R.id.screen_brightness:
					//changeScreenLight.setScreenLight(showSeekBar.getProgress());
					textView.setText(showSeekBar.getProgress()*100/255+"%");
				default:
					break;
				}
			}
		}).setNeutralButton("取消", new android.content.DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
			}
		});
		showSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				if(whitch == R.id.screen_brightness){
					//changeScreenLight.showScreenLight(arg1);
				}
				showPercent.setText(arg1*100/255+"%");			
			}
		});
		alertBuilder.create().show();
	}
}
