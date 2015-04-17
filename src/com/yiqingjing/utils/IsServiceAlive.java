package com.yiqingjing.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;

public class IsServiceAlive {
	private static ActivityManager manager;
	
	public static boolean isAlive(Context context, String serviceName){
		manager = (ActivityManager) context.getSystemService(Service.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> list = manager.getRunningServices(30);
		for(int i = 0; i < list.size(); ++i){
			if(serviceName.equals(list.get(i).service.getClassName())){
				return true;
			}
		}
		return false;
	}
}
