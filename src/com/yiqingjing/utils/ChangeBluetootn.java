package com.yiqingjing.utils;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;

@SuppressLint("NewApi")
public class ChangeBluetootn {
	private Context context;
	private BluetoothAdapter bluetoothAdapter;
	public ChangeBluetootn(Context context){
		this.context = context;
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	}
	
	
	public String getBluetoothState(){
		boolean flag = bluetoothAdapter.isEnabled();
		if(flag){
			return "开";
		}else{
			return "关";
		}
	}
	public void setBluetoothState(boolean state){
		if(state == true){
			bluetoothAdapter.enable();
		}else{
			bluetoothAdapter.disable();
		}
	}
}
