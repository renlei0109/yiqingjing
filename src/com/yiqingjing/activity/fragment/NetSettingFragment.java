package com.yiqingjing.activity.fragment;

import java.util.HashMap;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yiqingjing.activity.DetailSettingActivity;
import com.yiqingjing.activity.R;
import com.yiqingjing.utils.ChangeWiFi;

public class NetSettingFragment extends Fragment implements android.view.View.OnClickListener{
	
	private View mainView;
	//wifi改变
	private View wifi;
	private TextView wifiTextView;
	//数据连接改变
	private View gprs;
	private TextView gprsTextView;
	//wifi热点改变
	private View wifihot;
	private TextView wifihotTextView;
	
	//功能实现类
	private ChangeWiFi changeWiFi;
	//private ChangeGPRSConnection changeGPRSConnection;
	//单选项dialog
	private AlertDialog singleDialog;
	
	//保存设置
	private HashMap<String, Integer> setting;
	//dialog内容
	private String[] contents = new String[]{"保持原有", "开", "关"};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		changeWiFi = new ChangeWiFi(getActivity());
//		changeGPRSConnection = new ChangeGPRSConnection(getActivity());
		setting = (HashMap<String, Integer>) getArguments().getSerializable("setting");
		mainView = inflater.inflate(R.layout.netset, null);
		initWidget();
		registerListener();
		return mainView;
	}

	private void registerListener() {
	   	wifi.setOnClickListener((android.view.View.OnClickListener) this);
		wifihot.setOnClickListener((android.view.View.OnClickListener) this);
		gprs.setOnClickListener((android.view.View.OnClickListener) this);
	}

	private void initWidget() {
		wifi = mainView.findViewById(R.id.wifi);
		wifiTextView = (TextView) mainView.findViewById(R.id.wifi_textview);
		gprs = mainView.findViewById(R.id.datacon);
		gprsTextView = (TextView) mainView.findViewById(R.id.datacon_textview);
		wifihot = mainView.findViewById(R.id.wifihot);
		wifihotTextView = (TextView) mainView.findViewById(R.id.wifihot_textview);
		if(DetailSettingActivity.modeName != null){
			wifihotTextView.setText(DetailSettingActivity.sence.getWifihot());
			wifiTextView.setText(DetailSettingActivity.sence.getWifi());
			gprsTextView.setText(DetailSettingActivity.sence.getGprs());
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.wifi:
			if(setting.get("wifihot") == 0){
				Toast.makeText(getActivity(), "请关闭WiFi热点", Toast.LENGTH_SHORT).show();
				return;
			}else if(setting.get("wifi") == -1 && changeWiFi.isWifiApEnabled()){
				Toast.makeText(getActivity(), "请关闭WiFi热点", Toast.LENGTH_SHORT).show();
				return;
			}
			showSingleDialog("WiFi", contents, setting.get("wifi") + 1, R.id.wifi);
			break;
		case R.id.datacon:
			showSingleDialog("数据连接", contents, setting.get("gprs") + 1, R.id.datacon);
			break;
		case R.id.wifihot:
			if(setting.get("wifi") == 0){
				Toast.makeText(getActivity(), "请关闭WiFi", Toast.LENGTH_SHORT).show();
				return;
			}else if(setting.get("wifi") == -1 && changeWiFi.getWiFiState().equals("开")){
				Toast.makeText(getActivity(), "请关闭WiFi", Toast.LENGTH_SHORT).show();
				return;
			}
			showSingleDialog("WiFi热点", contents, setting.get("wifihot") + 1, R.id.wifihot);
			break;
		default:
			break;
		}
	}
	//显示单选项的dialog
		private void showSingleDialog(String title, final String[] contents, final int select, final int whitch){
			AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
			singleDialog = alertBuilder.setTitle(title).setSingleChoiceItems(contents, select, 
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int position) {
							switch (whitch) {
							case R.id.wifi:
								setting.put("wifi", position - 1);
								wifiTextView.setText(contents[position]);
								break;
							case R.id.datacon:
								setting.put("gprs", position - 1);
								gprsTextView.setText(contents[position]);
								break;
							case R.id.wifihot:
								setting.put("wifihot", position - 1);
								wifihotTextView.setText(contents[position]);
								break;
							default:
								break;
							}
							singleDialog.dismiss();
						}
					}).create();
			singleDialog.show();
		}
		
}
