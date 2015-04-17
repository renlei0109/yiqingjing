package com.yiqingjing.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class DatabaseHelper extends SQLiteOpenHelper{
	private static String  sql="test1.db";
 
	public DatabaseHelper(Context context) {
		super(context, sql, null, 1);
		
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		arg0.execSQL("CREATE TABLE IF NOT EXISTS yiqingjing" +  
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, modelname VARCHAR(20), status integer,alarmvolumn integer," +
                    "mediavolumn integer,callvolumn integer,gprs integer,wifi integer,wifihot integer," +
                    "ringmode integer,lightmode integer,lightness integer,bluetooth integer,latitude varchar," +
                    "longitude varchar,hour integer,minute integer,auto_rotate integer,ringurl varchar(40)," +
                    "mode integer,imgId integer,wifiname varchar(30))");  
		System.out.println("sql 创建成功");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}
