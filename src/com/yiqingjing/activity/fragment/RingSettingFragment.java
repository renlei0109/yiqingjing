package com.yiqingjing.activity.fragment;

import java.util.HashMap;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Audio;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yiqingjing.activity.DetailSettingActivity;
import com.yiqingjing.activity.R;
import com.yiqingjing.model.Sence;
import com.yiqingjing.utils.ChangeRingStatus;

public class RingSettingFragment extends Fragment implements OnClickListener{
	
	private View mainView;
	//设置铃声模式
	private View ringMode;
	private TextView ringModeTextView;
	//设置铃声
	private View ring;
	
	//功能实现类
	private ChangeRingStatus changeRingStatus;
	//用于保存设置的map
	private HashMap<String, Object> setting;
	//显示选择的dialog
	private AlertDialog dialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		changeRingStatus = new ChangeRingStatus(getActivity());
		mainView = inflater.inflate(R.layout.ringset, null);
		setting = (HashMap<String, Object>) getArguments().getSerializable("setting");

		initWidget();
		registerListener();
		return mainView;
	}

	private void initWidget() {
		ringMode = mainView.findViewById(R.id.ring_mode);
		ringModeTextView = (TextView) mainView.findViewById(R.id.ring_mode_textview);
		ring = mainView.findViewById(R.id.ring_change);
		
		if(DetailSettingActivity.modeName != null){
			ringModeTextView.setText(DetailSettingActivity.sence.getRingmode());
		}
	}

	private void registerListener() {
		ringMode.setOnClickListener(this);
		ring.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.ring_mode:
			System.out.println(AudioManager.RINGER_MODE_NORMAL + " " + AudioManager.RINGER_MODE_SILENT + " "
					+ AudioManager.RINGER_MODE_VIBRATE);
			showSingleDialog((Integer) setting.get("ringmode") + 1);
			break;
		case R.id.ring_change:
			Intent intent = changeRingStatus.setRingMusic();
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
		setting.put("ringuri", uri);
	}

	private void showSingleDialog(final int select) {
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
		dialog = alertBuilder.setTitle("铃声模式").setSingleChoiceItems(new String[]{"保持原有", "静音", "震动", "铃声"}, 
				select, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int position) {
						switch (position) {
						case 0:
							setting.put("ringmode", -1);
							ringModeTextView.setText("保持原有");
							break;
						case 1:
							setting.put("ringmode", AudioManager.RINGER_MODE_SILENT);
							ringModeTextView.setText("静音");
							break;
						case 2:
							setting.put("ringmode", AudioManager.RINGER_MODE_VIBRATE);
							ringModeTextView.setText("震动");
							break;
						case 3:
							setting.put("ringmode", AudioManager.RINGER_MODE_NORMAL);
							ringModeTextView.setText("铃声");
							break;
						default:
							break;
						}
						dialog.dismiss();
					}
				}).create();
		dialog.show();
	}
}
