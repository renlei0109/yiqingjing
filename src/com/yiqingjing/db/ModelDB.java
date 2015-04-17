package com.yiqingjing.db;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.yiqingjing.model.Sence;

public class ModelDB {
	private DatabaseHelper databaseHelper;
	private SQLiteDatabase sqLiteDatabase;
	
	public ModelDB(Context context){
		databaseHelper = new DatabaseHelper(context);
		sqLiteDatabase = databaseHelper.getReadableDatabase();
		System.out.println("start");
	}
	
	//增加模式
	 public void add(Sence Sence) {  
	        sqLiteDatabase.beginTransaction();  //开始事务  
	        try {  
	        	
	        	   System.out.println("插入之前"+Sence.toString());   
	        	
	                sqLiteDatabase.execSQL("INSERT INTO yiqingjing VALUES(null, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", 
	                		new Object[]{Sence.getModelName(), Sence.getStatus(), Sence.getAlarmvolumn(),Sence.getMediavolumn(),
	                		              Sence.getCallvolumn(),Sence.getRawGprs(),Sence.getRawWifi(),Sence.getRawWifihot(),
	                		              Sence.getRawRingmode(),Sence.getRawLightmode(),Sence.getLightness(),
	                		              Sence.getRawBluetooth(),Sence.getLatitude(),Sence.getLongitude(),Sence.getHour(),
	                		              Sence.getMinute(),Sence.getRawAuto_rotate(),Sence.getRingUri(),Sence.getMode(),
	                		              Sence.getImgId(),Sence.getWifiname()});  
	            sqLiteDatabase.setTransactionSuccessful();  //设置事务成功完成  
	        } finally {  
	            sqLiteDatabase.endTransaction();    //结束事务  
	        }  
	    }  
	 
	 //修改模式
	 public void edit(Sence sence){
		 System.out.println("修改的sence"+sence.toString());
		 ContentValues contentValues = new ContentValues();
		 contentValues.put("modelname", sence.getModelName());
		 contentValues.put("status", sence.getStatus());
		 contentValues.put("alarmvolumn", sence.getAlarmvolumn());
		 contentValues.put("mediavolumn", sence.getMediavolumn());
		 contentValues.put("callvolumn", sence.getCallvolumn());
		 contentValues.put("gprs", sence.getRawGprs());
		 contentValues.put("wifi", sence.getRawWifi());
		 contentValues.put("wifihot", sence.getRawWifihot());
		 contentValues.put("ringmode", sence.getRawRingmode());
		 contentValues.put("lightmode", sence.getRawLightmode());
		 contentValues.put("lightness", sence.getLightness());
		 contentValues.put("bluetooth", sence.getRawBluetooth()); 
		 contentValues.put("latitude", sence.getLatitude());
		 contentValues.put("longitude", sence.getLongitude());
		 contentValues.put("hour", sence.getHour());
		 contentValues.put("minute", sence.getMinute());
		 contentValues.put("auto_rotate", sence.getRawAuto_rotate());
		 contentValues.put("ringurl", sence.getRingUri());
		 contentValues.put("mode", sence.getMode()); 
		 contentValues.put("imgId", sence.getImgId());
		 contentValues.put("wifiname", sence.getWifiname());
		 sqLiteDatabase.update("yiqingjing", contentValues, "_id= ?", new String[]{sence.getId()+""});
		 System.out.println("修改成功");
	 }
	    /** 
	     * update Sence's status 
	     * @param Sence 
	     */  
	    public void updateStatus(Sence Sence) {  
	        ContentValues cv = new ContentValues();  
	        System.out.println("更新之前"+Sence.toString());
	        cv.put("status", Sence.getStatus());  
	        sqLiteDatabase.update("yiqingjing", cv, "modelname = ?", new String[]{Sence.getModelName()});  
	    }  
	      
	    /** 
	     * delete old Sence 
	     * @param Sence 
	     */  
	    public void deleteOldSence(String modelName) {  
	       // sqLiteDatabase.delete("yiqingjing", "age >= ?", new String[]{String.valueOf(Sence.age)}); 
	    	sqLiteDatabase.delete("yiqingjing", "modelname= ?", new String[]{modelName});
	    }  
	      
	    /** 
	     * query all Sences, return list 
	     * @return List<Sence> 
	     */  
	    public List<Sence> query() {  
	        ArrayList<Sence> Sences = new ArrayList<Sence>();  
	        Cursor c = queryTheCursor();  
	        while (c.moveToNext()) {  
	            Sence Sence = new Sence();  
	            
	            Sence.setId(c.getInt(c.getColumnIndex("_id")));
	            Sence.setModelName(c.getString(c.getColumnIndex("modelname")));  
	            Sence.setImgId(c.getInt(c.getColumnIndex("imgId")));
	            Sence.setAlarmvolumn(c.getInt(c.getColumnIndex("alarmvolumn")));
	            Sence.setMediavolumn(c.getInt(c.getColumnIndex("mediavolumn")));
	            Sence.setCallvolumn(c.getInt(c.getColumnIndex("callvolumn")));
	            Sence.setRingmode(c.getInt(c.getColumnIndex("ringmode")));
	            Sence.setRingUri(c.getString(c.getColumnIndex("ringurl")));
	            Sence.setGprs(c.getInt(c.getColumnIndex("gprs")));
	            Sence.setWifi(c.getInt(c.getColumnIndex("wifi")));
	            Sence.setWifihot(c.getInt(c.getColumnIndex("wifihot")));
	            Sence.setWifiname(c.getString(c.getColumnIndex("wifiname")));
	            Sence.setLightmode(c.getInt(c.getColumnIndex("lightmode")));
	            Sence.setLightness(c.getInt(c.getColumnIndex("lightness")));
	            Sence.setBluetooth(c.getInt(c.getColumnIndex("bluetooth")));
	            Sence.setHour(c.getInt(c.getColumnIndex("hour")));
	            Sence.setMinute(c.getInt(c.getColumnIndex("minute")));
	            Sence.setLatitude(c.getString(c.getColumnIndex("latitude")));
	            Sence.setLongitude(c.getString(c.getColumnIndex("longitude")));
	            Sence.setAuto_rotate(c.getInt(c.getColumnIndex("auto_rotate")));
	            Sence.setMode(c.getInt(c.getColumnIndex("mode")));
	            Sence.setStatus(c.getInt(c.getColumnIndex("status")));
	            Sences.add(Sence);  
	        }  
	        c.close();  
	        return Sences;  
	    }  
	      
	    public List<Sence> queryOpenMode(String status) {   
	    	ArrayList<Sence> Sences = new ArrayList<Sence>();
	        Cursor c = queryTheOpenCursor(status);  
	        Sence Sence=null;
	        while (c.moveToNext()) {  
	            Sence = new Sence();  
	            
	            Sence.setModelName(c.getString(c.getColumnIndex("modelname")));  
	            Sence.setImgId(c.getInt(c.getColumnIndex("imgId")));
	            Sence.setAlarmvolumn(c.getInt(c.getColumnIndex("alarmvolumn")));
	            Sence.setMediavolumn(c.getInt(c.getColumnIndex("mediavolumn")));
	            Sence.setCallvolumn(c.getInt(c.getColumnIndex("callvolumn")));
	            Sence.setRingmode(c.getInt(c.getColumnIndex("ringmode")));
	            Sence.setRingUri(c.getString(c.getColumnIndex("ringurl")));
	            Sence.setGprs(c.getInt(c.getColumnIndex("gprs")));
	            Sence.setWifi(c.getInt(c.getColumnIndex("wifi")));
	            Sence.setWifiname(c.getString(c.getColumnIndex("wifiname")));
	            Sence.setWifihot(c.getInt(c.getColumnIndex("wifihot")));
	            Sence.setLightmode(c.getInt(c.getColumnIndex("lightmode")));
	            Sence.setLightness(c.getInt(c.getColumnIndex("lightness")));
	            Sence.setBluetooth(c.getInt(c.getColumnIndex("bluetooth")));
	            Sence.setHour(c.getInt(c.getColumnIndex("hour")));
	            Sence.setMinute(c.getInt(c.getColumnIndex("minute")));
	            Sence.setLatitude(c.getString(c.getColumnIndex("latitude")));
	            Sence.setLongitude(c.getString(c.getColumnIndex("longitude")));
	            Sence.setAuto_rotate(c.getInt(c.getColumnIndex("auto_rotate")));
	            Sence.setMode(c.getInt(c.getColumnIndex("mode")));
	            Sence.setStatus(c.getInt(c.getColumnIndex("status")));
	            
	            Sences.add(Sence);
	        }  
	        c.close();  
	        return Sences;  
	    }  
	    
	    public Sence queryModel(String modeName) {    
	        Cursor c = queryTheCursor(modeName);  
	        Sence Sence=null;
	        while (c.moveToNext()) {  
	            Sence = new Sence();  
	            Sence.setId(c.getInt(c.getColumnIndex("_id")));
	            Sence.setModelName(c.getString(c.getColumnIndex("modelname")));  
	            Sence.setImgId(c.getInt(c.getColumnIndex("imgId")));
	            Sence.setAlarmvolumn(c.getInt(c.getColumnIndex("alarmvolumn")));
	            Sence.setMediavolumn(c.getInt(c.getColumnIndex("mediavolumn")));
	            Sence.setCallvolumn(c.getInt(c.getColumnIndex("callvolumn")));
	            Sence.setRingmode(c.getInt(c.getColumnIndex("ringmode")));
	            Sence.setRingUri(c.getString(c.getColumnIndex("ringurl")));
	            Sence.setGprs(c.getInt(c.getColumnIndex("gprs")));
	            Sence.setWifi(c.getInt(c.getColumnIndex("wifi")));
	            Sence.setWifihot(c.getInt(c.getColumnIndex("wifihot")));
	            Sence.setWifiname(c.getString(c.getColumnIndex("wifiname")));
	            Sence.setLightmode(c.getInt(c.getColumnIndex("lightmode")));
	            Sence.setLightness(c.getInt(c.getColumnIndex("lightness")));
	            Sence.setBluetooth(c.getInt(c.getColumnIndex("bluetooth")));
	            Sence.setHour(c.getInt(c.getColumnIndex("hour")));
	            Sence.setMinute(c.getInt(c.getColumnIndex("minute")));
	            Sence.setLatitude(c.getString(c.getColumnIndex("latitude")));
	            Sence.setLongitude(c.getString(c.getColumnIndex("longitude")));
	            Sence.setAuto_rotate(c.getInt(c.getColumnIndex("auto_rotate")));
	            Sence.setMode(c.getInt(c.getColumnIndex("mode")));
	            Sence.setStatus(c.getInt(c.getColumnIndex("status")));
	            
	        }  
	        c.close();  
	        return Sence;  
	    }  
	    /** 
	     * query all Sences, return cursor 
	     * @return  Cursor 
	     */  
	    public Cursor queryTheCursor() {  
	        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM yiqingjing", null);  
	        return c;  
	    }  
	    
	    public Cursor queryTheCursor(String modeName) {  
	    	Cursor c = sqLiteDatabase.query("yiqingjing", null, "modelname = ?", 
	    			new String[]{modeName}, null, null, null);
	    	return c;
	    }  
	    
	    public Cursor queryTheOpenCursor(String status){
	    	Cursor c = sqLiteDatabase.query("yiqingjing", null, "status = ?", 
	    			new String[]{status}, null, null, null);
	    	return c;
	    }
	    
	
	public void closeDB() {  
        sqLiteDatabase.close();  
    }  
	
}
