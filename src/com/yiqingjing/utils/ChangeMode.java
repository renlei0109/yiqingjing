package com.yiqingjing.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;

public class ChangeMode {
	
	//声音模式
	private static ChangeVolume changeVolume;
	private static ChangeBluetootn changeBluetootn;
	private static ChangeScreenLight changeScreenLight;
	private static ChangeScreenRotate changeScreenRotate;
	private static ChangeGPRSConnection changeGPRSConnection;
	private static ChangeWiFi changeWiFi;
	private static ChangeRingStatus changeRingStatus;
	
	public static void setMode(Context context, int alarm, int media, int ring, int ringmode, 
			int lightmode, int lightness, int auto_rotate, int wifi, int gprs, 
			int wifihot,int bluetooth, String ringUri){
		
		
		
		changeBluetootn = new ChangeBluetootn(context);
		changeGPRSConnection = new ChangeGPRSConnection(context);
		changeRingStatus = new ChangeRingStatus(context);
		changeScreenLight = new ChangeScreenLight(context);
		changeScreenRotate = new ChangeScreenRotate(context);
		changeVolume = new ChangeVolume(context);
		changeWiFi = new ChangeWiFi(context);
		
		if(ringmode != -1){
			changeRingStatus.setRingStatus(ringmode);
		}
		
		if(ringmode == AudioManager.RINGER_MODE_NORMAL){
			changeVolume.setAlarmVolume(alarm);
			changeVolume.setMusicVolume(media);
			changeVolume.setRingVolume(ring);
		}
		changeScreenLight.setScreenLight(lightness);
		if(lightmode != -1){
			changeScreenLight.setScreenLightMode(lightmode);
		}
		if(auto_rotate != -1){
			System.out.println(auto_rotate + "auto_rotate");
			changeScreenRotate.setScreenRotate(auto_rotate);
		}
		System.out.println(wifi + "wifi");
		if(wifi == 0){
			changeWiFi.setWiFiState(true);
		}else if(wifi == 1){
			changeWiFi.setWiFiState(false);
		}
		
		if(wifihot == 0){
			changeWiFi.startWifiAp("yiqingjing", "12345678");
		}else if(wifihot == 1){
			changeWiFi.closeWifiAp();
		}
		if(gprs == 0){
			changeGPRSConnection.setGprsEnabled(true);
		}else if(gprs == 1){
			changeGPRSConnection.setGprsEnabled(false);
		}
		
		if(bluetooth == 0){
			changeBluetootn.setBluetoothState(true);
		}else if(bluetooth == 1){
			changeBluetootn.setBluetoothState(false);
		}
		
		if(ringUri != null){
			RingtoneManager.setActualDefaultRingtoneUri(context,
					RingtoneManager.TYPE_RINGTONE, Uri.parse(ringUri));
		}
		
	}
}
