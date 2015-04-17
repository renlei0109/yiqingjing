package com.yiqingjing.service;

import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.yiqingjing.activity.R;
import com.yiqingjing.model.Sence;
import com.yiqingjing.utils.ApplaySetting;
import com.yiqingjing.utils.IsServiceAlive;

public class WorkService extends Service {
	
	private Thread mThread;
	private List<Sence>sences;
	private ApplaySetting applaySetting;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		startDeamon();
		
		sences = (List<Sence>) intent.getSerializableExtra("sence");
		System.out.println("service 传过去的值"+sences.toString());
		
	    applaySetting.addMode(sences);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
		builder.setSmallIcon(R.drawable.ic_launcher).setContentTitle("易情景").
		setContentText("为了您的使用体验，请将程序放入清理白名单").setTicker("易情景");
		startForeground(0x123, builder.build());
		
		flags = START_STICKY;
		return super.onStartCommand(intent, flags, startId);
	}

	private void startDeamon() {
		if(mThread == null || !mThread.isAlive()){
			mThread = new Thread(new Runnable() {
				@Override
				public void run() {
					if(!IsServiceAlive.isAlive(getApplicationContext(),
							"com.yiqingjing.service.ProtectedService"))
						startService(new Intent(WorkService.this, ProtectedService.class));
				}
			});
		}
		mThread.start();
	}

	@Override
	public void onDestroy() {
		stopForeground(true);
		System.out.println("workservice is destroyed");
		super.onDestroy();
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		applaySetting = new ApplaySetting(this);
	}
	
}
