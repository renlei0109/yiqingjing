package com.yiqingjing.activity.fragment;

import java.util.HashMap;

import com.yiqingjing.activity.DetailSettingActivity;
import com.yiqingjing.activity.R;
import com.yiqingjing.utils.ChangeBluetootn;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class OtherSettingFragment extends Fragment implements OnClickListener{
	
	private View mainView;
	private TextView gpsTextView;
	//蓝牙连接
	private View bluetooth;
	private TextView bluetoothTextView;
	
	//功能实现类
	private ChangeBluetootn changeBluetootn;
	
	//单选dialog
	private AlertDialog singleDialog;
	//设置保存
	private HashMap<String, Integer> setting;
	//dialog内容
	private String[] contents = new String[]{"保持原有", "开", "关"};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		changeBluetootn = new ChangeBluetootn(getActivity());
		mainView = inflater.inflate(R.layout.connectionset, null);
		setting = (HashMap<String, Integer>) getArguments().getSerializable("setting");
		initWidget();
		registerListener();
		return mainView;
	}

	private void initWidget() {
		bluetooth = mainView.findViewById(R.id.buetooth);
		bluetoothTextView = (TextView) mainView.findViewById(R.id.bluetooth_textview);
		
		if(DetailSettingActivity.modeName != null){
			bluetoothTextView.setText(DetailSettingActivity.sence.getBluetooth());
			
		}
	}

	private void registerListener() {
		bluetooth.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.buetooth:
			showSingleDialog("蓝牙", contents, setting.get("bluetooth") + 1, R.id.bluetooth_textview);
			break;
		default:
			break;
		}
	}
	//显示单选项的dialog
		private void showSingleDialog(String title, final String[] contents, 
				final int select, final int whitch){
			AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
			singleDialog = alertBuilder.setTitle(title).setSingleChoiceItems(contents, select, 
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int position) {
							switch (whitch) {
							case R.id.bluetooth_textview:
								setting.put("bluetooth", position - 1);
								bluetoothTextView.setText(contents[position]);
							default:
								break;
							}
							singleDialog.dismiss();
						}
					}).create();
			singleDialog.show();
		}
}
