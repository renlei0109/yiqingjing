package com.yiqingjing.model;

import java.io.Serializable;

public class Sence implements Serializable{
	private Integer id;        
	private String modelName;  //模式名称
	private int status;
	private int alarmvolumn;  //闹钟音量
	private int mediavolumn;  //媒体音量
	private int callvolumn;   //来电音量
	private int gprs;         //数据连接
	private int wifi;         //wifi
	private String wifiname;   //wifi名称
	private int wifihot;      //wifi热点
	private int ringmode;     //铃声模式
	private int lightmode;    //亮度调节模式
	private int lightness;    //亮度值
	private int bluetooth;    //蓝牙
	private String latitude;     
	private String longitude;
	private int hour;
	private int minute;
	private int auto_rotate;   //自动转屏
	private String ringUri;       //铃声地址
	private int mode;
	private int imgId;
	
	public Sence(String modelName,int status,int alarmvolumn,int mediavolumn, int callvolumn,
			  int gprs,int wifi,int wifihot,int ringmode,int lightmode,int lightness,
			  int bluetooth, String latitude,String longitude,int hour,int minute,int auto_rotate,String ringUri
			  ,int mode,int imgId){
		this.modelName=modelName;
		this.status=status;
		this.alarmvolumn=alarmvolumn;
		this.mediavolumn=mediavolumn;
		this.callvolumn=callvolumn;
		this.gprs=gprs;
		this.wifi=wifi;
		this.wifihot=wifihot;
		this.ringmode=ringmode;
		this.lightmode=lightmode;
		this.lightness=lightness;
		this.bluetooth=bluetooth;
		this.latitude=latitude;
		this.longitude=longitude;
		this.hour=hour;
		this.minute=minute;
		this.auto_rotate=auto_rotate;
		this.ringUri=ringUri;
		this.mode=mode;
		this.imgId=imgId;
	}
	
	public Sence(){
		
	}
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id=id;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getAlarmvolumn() {
		return alarmvolumn;
	}
	public void setAlarmvolumn(int alarmvolumn) {
		this.alarmvolumn = alarmvolumn;
	}
	public int getMediavolumn() {
		return mediavolumn;
	}
	public void setMediavolumn(int mediavolumn) {
		this.mediavolumn = mediavolumn;
	}
	public int getCallvolumn() {
		return callvolumn;
	}
	public void setCallvolumn(int callvolumn) {
		this.callvolumn = callvolumn;
	}
	public String getGprs() {
		return translate(gprs);
	}
	public void setGprs(int gprs) {
		this.gprs = gprs;
	}
	public String getWifi() {
		return translate(wifi);
	}
	public void setWifi(int wifi) {
		this.wifi = wifi;
	}
	public String getWifihot() {
		return translate(wifihot);
	}
	public void setWifihot(int wifihot) {
		this.wifihot = wifihot;
	}
	public String getRingmode() {
		if(ringmode == -1){
			return "保持原有";
		}else if(ringmode == 2){
			return "铃声";
		}else if(ringmode == 0){
			return "静音";
		}else{
			return "震动";
		}
	}
	public void setRingmode(int ringmode) {
		this.ringmode = ringmode;
	}
	public String getLightmode() {
		if(lightmode == -1){
			return "保持原有";
		}else if(lightmode == 1){
			return "自动";
		}else{
			return "手动";
		}
	}
	public void setLightmode(int lightmode) {
		this.lightmode = lightmode;
	}
	public int getLightness() {
		return lightness;
	}
	public void setLightness(int lightness) {
		this.lightness = lightness;
	}
	public String getBluetooth() {
		return translate(bluetooth);
	}
	public void setBluetooth(int bluetooth) {
		this.bluetooth = bluetooth;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public String getAuto_rotate() {
		if(auto_rotate == -1){
			return "保持原有";
		}else if(auto_rotate == 0){
			return "关";
		}else{
			return "开"; 
		}
	}
	public void setAuto_rotate(int auto_rotate) {
		this.auto_rotate = auto_rotate;
	}
	public String getRingUri() {
		return ringUri;
	}
	public void setRingUri(String ringUri) {
		this.ringUri = ringUri;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public int getImgId() {
		return imgId;
	}

	public void setImgId(int imgId) {
		this.imgId = imgId;
	}
	
	
	private String translate(int mode){
		switch (mode) {
		case -1:
			return "保持原有";
		case 0:
			return "开";

		default:
			return "关";
		}
	}

	public int getRawGprs() {
		return gprs;
	}

	public int getRawWifi() {
		return wifi;
	}

	public int getRawWifihot() {
		return wifihot;
	}

	public int getRawRingmode() {
		return ringmode;
	}

	public int getRawLightmode() {
		return lightmode;
	}

	public int getRawBluetooth() {
		return bluetooth;
	}

	public int getRawAuto_rotate() {
		return auto_rotate;
	}

	public String getWifiname() {
		return wifiname;
	}

	public void setWifiname(String wifiname) {
		this.wifiname = wifiname;
	}
	
	public String toString(){
		return modelName+"    "+ status+"    "+ alarmvolumn+"    "+ mediavolumn+"    "+callvolumn
			  +"    "+gprs+"    "+ wifi+"    "+ wifihot +"    "+ ringmode+"    "+ lightmode+"    "+ lightness
			  +"    "+bluetooth+"    "+ latitude+"    "+longitude+"    "+ hour+"    "+ minute+"    "+ auto_rotate+"    "+ringUri
			  +"    "+ mode+"    "+ imgId+"    "+wifiname;
	}
	
}
