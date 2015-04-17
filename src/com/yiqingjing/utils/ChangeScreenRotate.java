package com.yiqingjing.utils;

import android.content.Context;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;

public class ChangeScreenRotate {
	private Context context;
	public ChangeScreenRotate(Context context){
		this.context = context;
	}
	public void setScreenRotate(int mode){
		Settings.System.putInt(context.getContentResolver(), 
				Settings.System.ACCELEROMETER_ROTATION, mode);
	}
	public String getScreenRotate(){
		int mode = -1;
		try {
			mode = Settings.System.getInt(context.getContentResolver(), 
					Settings.System.ACCELEROMETER_ROTATION);
		} catch (SettingNotFoundException e) {
			
			e.printStackTrace();
		}
		if(mode == 1){
			return "关";
		}else{
			return "开";
		}
	}
}
