package com.yiqingjing.utils;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.view.WindowManager;


public class ChangeScreenLight {
	private Context context;
	/** 可调节的最小亮度值 */  
	public static final int MIN_BRIGHTNESS = 30;  
	/** 可调节的最大亮度值 */  
	public static final int MAX_BRIGHTNESS = 255; 
	public ChangeScreenLight(Context context){
		this.context = context;
	}
	//获取屏幕亮度调节方式
	public int getScreenLightMode(){
		int mode = -1;
		try {
			mode = Settings.System.getInt(context.getContentResolver(),
					Settings.System.SCREEN_BRIGHTNESS_MODE);
		} catch (SettingNotFoundException e) {
			e.printStackTrace();
		}
		return mode;
	}
	//获取屏幕亮度
	public int getScreenLight(){
		int lightness = -1;
		try {
			lightness = Settings.System.getInt(context.getContentResolver(), 
					Settings.System.SCREEN_BRIGHTNESS);
		} catch (SettingNotFoundException e) {
			
			return -1;
		}
		return lightness;
	}
	//设置屏幕亮度调节方式
	public void setScreenLightMode(int mode){
		Settings.System.putInt(context.getContentResolver(), 
				Settings.System.SCREEN_BRIGHTNESS_MODE, mode);
	}
	//设置屏幕亮度
	public void setScreenLight(int lightness){
		lightness = adjustLightness(lightness);
		Settings.System.putInt(context.getContentResolver(), 
				Settings.System.SCREEN_BRIGHTNESS, lightness);
	}
	//改变当前窗口亮度，让用户知道当前屏幕亮度状况
	public void showScreenLight(int lightness){
		lightness = adjustLightness(lightness);
		final WindowManager.LayoutParams lp = ((Activity)context).getWindow().getAttributes();
		lp.screenBrightness = lightness / (float) MAX_BRIGHTNESS;  
		((Activity)context).getWindow().setAttributes(lp);
	}
	//调整屏幕亮度值，不大于255，不小于30
	//屏幕亮度的调整范围是30-255
	private int adjustLightness(int lightness){
		return lightness < MIN_BRIGHTNESS ? MIN_BRIGHTNESS : lightness;
	}
}
