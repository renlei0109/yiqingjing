package com.yiqingjing.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.R.integer;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Parcelable;

import com.yiqingjing.db.ModelDB;
import com.yiqingjing.model.Sence;
import com.yiqingjing.nextset.GetLocation;

public class ApplaySetting implements LocationListener{
	
	private Context context;
	private WifiReceiver receiver = new WifiReceiver();
	private List<Sence> modes = new ArrayList<Sence>();
	private WifiManager wifiManager;
	private LocationManager locationManager;
	
	public ApplaySetting(final Context context){
		this.context = context;
		IntentFilter filter = new IntentFilter();
		filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
		filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		context.registerReceiver(receiver, filter);
		
		locationManager = (LocationManager) context.getSystemService(Service.LOCATION_SERVICE);
		Criteria criteria = new Criteria();  
		// 获得最好的定位效果
		criteria.setAccuracy(Criteria.ACCURACY_FINE);    
		criteria.setAltitudeRequired(false);    
		criteria.setBearingRequired(false);    
		criteria.setCostAllowed(false);    
		// 使用省电模式    
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		String provider =locationManager.getBestProvider(criteria, true);
		locationManager.requestLocationUpdates(provider, 60000, 0, 
				this);
		
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					Calendar calendar = Calendar.getInstance();
					System.out.println("time is running");
					for(int i = 0; i < modes.size(); ++i){
						if(calendar.get(Calendar.HOUR_OF_DAY) == modes.get(i).getHour() && 
								calendar.get(Calendar.MINUTE) == modes.get(i).getMinute()){
							applay(modes.get(i));
							System.out.println("时间模式到了");
						}
					}
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						
						e.printStackTrace();
						}
				}
			}
		}).start();
	}
	
	@Override
	public void onLocationChanged(Location location) {
		for(int i = 0; i < modes.size(); ++i){
			     
			if(modes.get(i).getLatitude()!=null)
			{
			    if(Math.abs(location.getLatitude() - Double.valueOf(modes.get(i).getLatitude())) < 0.0001
					   && Math.abs(location.getLongitude() - Double.valueOf(modes.get(i).getLongitude()))
					      < 0.0001){	
				    System.out.println("location is starting");
				    applay(modes.get(i));
			    }
			}
		}
	}

	@Override
	public void onProviderDisabled(String arg0) {
	}

	@Override
	public void onProviderEnabled(String arg0) {
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
	}

	public void addMode(List<Sence>sences){
		modes = sences;
	}
	
	public void applay(Sence sence){
		
		SharedPreferences sharedPreferences = context.getSharedPreferences("current_mode", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString("mode_name", sence.getModelName());
		editor.putInt("mode_img", sence.getImgId());
	    editor.putString("bluetooth", sence.getBluetooth());
	    editor.putString("wifi", sence.getWifi());
	    editor.putString("ring", sence.getRingmode());
		editor.commit();
		
		ChangeMode.setMode(context, sence.getAlarmvolumn(), sence.getMediavolumn(), sence.getCallvolumn(),
				sence.getRawRingmode(), sence.getRawLightmode(), sence.getLightness(),
				sence.getRawAuto_rotate(), sence.getRawWifi(), sence.getRawGprs(), 
				sence.getRawWifihot(), sence.getRawBluetooth(), sence.getRingUri());
	}
	
	public class WifiReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			if(WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(arg1.getAction())){
				Parcelable parcelable = arg1.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
				if(null != parcelable){
					NetworkInfo info = (NetworkInfo) parcelable;
					State state = info.getState();
					boolean isConnected = state == State.CONNECTED;
					if(isConnected){
						wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
						    Sence sence = null;
					        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
					        String wifiName = wifiInfo.getSSID();
					        for(int i=0;i<modes.size();i++){
					        	if(modes.get(i).getMode()==1)
					        	{
					        		 sence =modes.get(i);
					        	}
					        }
					       
					        if(sence!=null && wifiName.equals(sence.getWifiname())){
					        	System.out.println("wlan is starting");
					        	applay(sence);
					        }
					}
				}
			}
		}	
	}
	
}
