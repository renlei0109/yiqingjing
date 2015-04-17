package com.yiqingjing.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;

public class ChangeRingStatus {
	private AudioManager audioManager;
	private Context context;
	public ChangeRingStatus(Context context){
		audioManager = (AudioManager) context.getSystemService(Service.AUDIO_SERVICE);
		this.context = context;
	}
	public void setRingStatus(int status){
		audioManager.setRingerMode(status);
	}
	public int getRingStatus(){
		return audioManager.getRingerMode();
	}
	public Intent setRingMusic(){
		Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);  
        // Allow user to pick 'Default'  
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);  
        // Show only ringtones  
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);  
        //set the default Notification value  
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));  
        // Don't show 'Silent'  
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, true);  
  
        Uri notificationUri;  
        // Otherwise pick default ringtone Uri so that something is selected.  
        notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);  
        // Put checkmark next to the current ringtone for this contact  
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, notificationUri); 
        return intent;
	}
}
