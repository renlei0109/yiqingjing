package com.yiqingjing.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Service;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;


public class ChangeWiFi {
	private Context context;
	private WifiManager wifiManager;
	public String wifiState;
	public ChangeWiFi(Context context){
		this.context = context;
		wifiManager = (WifiManager) context.getSystemService(Service.WIFI_SERVICE);
	}
	//设置WiFi是否打开
	public void setWiFiState(boolean state){
		wifiManager.setWifiEnabled(state);
	}
	public String getWiFiState(){
		int mode = wifiManager.getWifiState();
		if(mode == 1){
			return "关";
		}else{
			return "开";
		}
	}
	//打开WiFi热点
	public void startWifiAp(String mSSID, String mPasswd) {  
        Method method1 = null;  
        if (wifiManager.isWifiEnabled()) {  
            wifiManager.setWifiEnabled(false);  
        } 
        try {  
            method1 = wifiManager.getClass().getMethod("setWifiApEnabled",  
                    WifiConfiguration.class, boolean.class);  
            WifiConfiguration netConfig = new WifiConfiguration();  
  
            netConfig.SSID = mSSID;  
            netConfig.preSharedKey = mPasswd;  
  
            netConfig.allowedAuthAlgorithms  
                    .set(WifiConfiguration.AuthAlgorithm.OPEN);  
            netConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);  
            netConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);  
            netConfig.allowedKeyManagement  
                    .set(WifiConfiguration.KeyMgmt.WPA_PSK);  
            netConfig.allowedPairwiseCiphers  
                    .set(WifiConfiguration.PairwiseCipher.CCMP);  
            netConfig.allowedPairwiseCiphers  
                    .set(WifiConfiguration.PairwiseCipher.TKIP);  
            netConfig.allowedGroupCiphers  
                    .set(WifiConfiguration.GroupCipher.CCMP);  
            netConfig.allowedGroupCiphers  
                    .set(WifiConfiguration.GroupCipher.TKIP);  
  
            method1.invoke(wifiManager, netConfig, true);  
  
        } catch (IllegalArgumentException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IllegalAccessException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (InvocationTargetException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (SecurityException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (NoSuchMethodException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }
	//关闭WiFi热点
	public void closeWifiAp() {  
        if (isWifiApEnabled()) {  
            try {  
                Method method = wifiManager.getClass().getMethod("getWifiApConfiguration");  
                method.setAccessible(true);  
  
                WifiConfiguration config = (WifiConfiguration) method.invoke(wifiManager);  
  
                Method method2 = wifiManager.getClass().getMethod("setWifiApEnabled", 
                		WifiConfiguration.class, boolean.class);  
                method2.invoke(wifiManager, config, false);  
            } catch (NoSuchMethodException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } catch (IllegalArgumentException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } catch (IllegalAccessException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } catch (InvocationTargetException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
    } 
	//WiFi热点是否已经打开
	public boolean isWifiApEnabled() {  
		boolean flag = false;
        try {  
            Method method = wifiManager.getClass().getMethod("isWifiApEnabled");  
            method.setAccessible(true);  
            flag = (Boolean) method.invoke(wifiManager);  
  
        } catch (NoSuchMethodException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
        if(flag){
        	wifiState = "开";
        }else{
        	wifiState = "关";
        }
        return flag;
    }
}
