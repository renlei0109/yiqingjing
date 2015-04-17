package com.yiqingjing.utils;

import java.util.Map;

import android.media.AudioManager;

import com.yiqingjing.model.Sence;

public class SenceAndSetting {
	public static void initSetting(Map<String, Object> setting, int mode){
		setting.put("mode", mode);
		setting.put("alarm", 3);
		setting.put("media", 3);
		setting.put("gprs", -1);
		setting.put("wifihot", -1);
		setting.put("bluetooth", -1);
		setting.put("wifi", -1);
		setting.put("lightness", 127);
		setting.put("lightmode", -1);
		setting.put("auto_rotate", -1);
		setting.put("ringmode", AudioManager.RINGER_MODE_NORMAL);
		setting.put("call", 3);
		setting.put("latitude", null);
		setting.put("longitude", null);
		setting.put("hour", -1);
		setting.put("minute", -1);
	}
	
	public static void SenceToSetting(Map<String, Object> setting, Sence sence){
		setting.put("mode", sence.getMode());
		setting.put("alarm", sence.getAlarmvolumn());
		setting.put("media", sence.getMediavolumn());
		setting.put("gprs", sence.getRawGprs());
		setting.put("wifihot", sence.getRawWifihot());
		setting.put("bluetooth", sence.getRawBluetooth());
		setting.put("wifi", sence.getRawWifi());
		setting.put("lightness", sence.getLightness());
		setting.put("lightmode", sence.getRawLightmode());
		setting.put("auto_rotate", sence.getRawAuto_rotate());
		setting.put("ringmode", sence.getRawRingmode());
		setting.put("call", sence.getCallvolumn());
		setting.put("latitude", sence.getLatitude());
		setting.put("longitude", sence.getLongitude());
		setting.put("hour", sence.getHour());
		setting.put("minute", sence.getMinute());
	}
}
