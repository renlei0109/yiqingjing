package com.yiqingjing.activity.fragment;

import java.util.HashMap;

import com.yiqingjing.activity.DetailSettingActivity;
import com.yiqingjing.activity.R;
import com.yiqingjing.utils.ChangeRingStatus;
import com.yiqingjing.utils.ChangeVolume;
import com.yiqingjing.utils.IntToPercent;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class VoiceSettingFragment extends Fragment implements OnClickListener{
	
	private View mainView;
	//调节闹钟音量
	private View alarm;
	private TextView alarmTextView;
	//调节媒体音量
	private View media;
	private TextView mediaTextView;
	//调节来电音量
	private View callin;
	private TextView callinTextView;
	
	//功能实现类
	private ChangeVolume changeVolume;
	//判断当前能不能修改来电音量
	private ChangeRingStatus changeRingStatus;
	//保存设置
	private HashMap<String, Integer> setting;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		changeVolume = new ChangeVolume(getActivity());
		changeRingStatus = new ChangeRingStatus(getActivity());
		mainView = inflater.inflate(R.layout.voiceset, null);
		setting = (HashMap<String, Integer>) getArguments().getSerializable("setting");
		initWidget();
		registerListener();
		return mainView;
	}

	private void initWidget() {
		alarm = mainView.findViewById(R.id.alarm_volume);
		alarmTextView = (TextView) mainView.findViewById(R.id.alarm_volume_textview);
		media = mainView.findViewById(R.id.media_volume);
		mediaTextView = (TextView) mainView.findViewById(R.id.media_volume_textview);
		callin = mainView.findViewById(R.id.ring_volume);
		callinTextView = (TextView) mainView.findViewById(R.id.ring_volume_textview);
		if(DetailSettingActivity.modeName != null){
			alarmTextView.setText(IntToPercent.translate(DetailSettingActivity.sence.getAlarmvolumn(), 7));
			mediaTextView.setText(IntToPercent.translate(DetailSettingActivity.sence.getMediavolumn(), 15));
			callinTextView.setText(IntToPercent.translate(DetailSettingActivity.sence.getCallvolumn(), 7));
		}
	}

	//给控件注册监听器
	private void registerListener() {
		alarm.setOnClickListener(this);
		media.setOnClickListener(this);
		callin.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.alarm_volume:
			if(DetailSettingActivity.modeName==null)
				showSeekBarDialog(50, changeVolume.getMaxAlarmVolume(), 
						"闹钟音量", R.id.alarm_volume);
			else{
				showSeekBarDialog(DetailSettingActivity.sence.getAlarmvolumn()*100/7,
						changeVolume.getMaxAlarmVolume(), "闹钟音量", R.id.alarm_volume);
			}
			break;
		case R.id.media_volume:
			if(DetailSettingActivity.modeName == null)
				showSeekBarDialog(50, changeVolume.getMaxMusicVolume(), 
						"媒体音量", R.id.media_volume);
			else
				showSeekBarDialog(DetailSettingActivity.sence.getMediavolumn()*100/15, 
						changeVolume.getMaxMusicVolume(), "媒体音量", R.id.media_volume);
			break;
		case R.id.ring_volume:
			if(setting.get("ringmode") == AudioManager.RINGER_MODE_VIBRATE ||
			setting.get("ringmode") == AudioManager.RINGER_MODE_SILENT){
				Toast.makeText(getActivity(), "当前为静音或震动模式，不能调节来电音量", 
						Toast.LENGTH_SHORT).show();
				return;
			}else if (setting.get("ringmode") == -1){
				if(changeRingStatus.getRingStatus() == AudioManager.RINGER_MODE_VIBRATE
						|| changeRingStatus.getRingStatus() == AudioManager.RINGER_MODE_SILENT){
					Toast.makeText(getActivity(), "当前为静音或震动模式，不能调节来电音量", 
							Toast.LENGTH_SHORT).show();
					return;
				}
			}
			if(DetailSettingActivity.modeName == null)
			showSeekBarDialog(50, changeVolume.getMaxRingVolume(), "来电音量", R.id.ring_volume);
			else
				showSeekBarDialog(DetailSettingActivity.sence.getCallvolumn()*100/7, 
						changeVolume.getMaxRingVolume(), "来电音量", R.id.ring_volume);
			break;
		default:
			break;
		}
	}
	
	//显示带有进度条的dialog
		private void showSeekBarDialog(int percent, final int max, String title, final int whitch){
			View view = LayoutInflater.from(getActivity()).inflate(R.layout.seekbar, null);			final TextView showPercent = (TextView) view.findViewById(R.id.seektext);
			showPercent.setText(percent+"%");
			final SeekBar showSeekBar = (SeekBar) view.findViewById(R.id.seekbar);
			showSeekBar.setProgress(percent*255/100);
			AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
			alertBuilder.setTitle(title).setView(view);
			alertBuilder.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {			
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					switch (whitch) {
					case R.id.alarm_volume:
						setting.put("alarm", showSeekBar.getProgress()*max/255);
						alarmTextView.setText(showSeekBar.getProgress()*100
								/255+"%");
						break;
					case R.id.media_volume:
						setting.put("media", showSeekBar.getProgress()*max /255);
						mediaTextView.setText(showSeekBar.getProgress()*100/255+"%");
						break;
					case R.id.ring_volume:
						setting.put("call", showSeekBar.getProgress()*max/255);
						callinTextView.setText(showSeekBar.getProgress()*100/255+"%");
						break;
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
					showPercent.setText(arg1*100/255+"%");			
				}
			});
			alertBuilder.create().show();
		}
		
}
