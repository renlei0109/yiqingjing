package com.yiqingjing.nextset;

import java.util.ArrayList;

import android.app.Service;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;

public class GetLocation{
	private static LocationManager locationManager;
	private static Location location;
	private static ArrayList<Double> result = new ArrayList<Double>();
	
	public static ArrayList<Double> getCurrentLocation(Context context) throws Exception{
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
		location =locationManager.getLastKnownLocation(provider); 
		result.add(location.getLatitude());
		result.add(location.getLongitude());
		return result;
	}
	
}
