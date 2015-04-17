package com.yiqingjing.service;

import com.yiqingjing.utils.IsServiceAlive;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ProtectedService extends Service {
	
	private Thread mThread;
	//判断当前属于什么模式

	@Override
	public IBinder onBind(Intent arg0) {

		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		startDeamon();
		
		
		
		flags = START_STICKY;
		return super.onStartCommand(intent, flags, startId);
	}

	private void startDeamon() {
		if(mThread == null || !mThread.isAlive()){
			mThread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					if(IsServiceAlive.isAlive(getApplicationContext(), 
							"com.yiqingjing.service.WorkService")){
						startService(new Intent(ProtectedService.this, WorkService.class));
					}
				}
			});
		}
	}

}
