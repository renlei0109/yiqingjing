package com.yiqingjing.utils;

import java.lang.reflect.Method;

import android.content.Context;
import android.net.ConnectivityManager;

public class ChangeGPRSConnection {
	 private Context context;
	 private ConnectivityManager conMgr;
	 
	 public ChangeGPRSConnection(Context context){
		 this.context = context;
		 try{
			conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		    
		 }catch(Exception e){
			 e.printStackTrace();
		 }
	 }
	 
	 public String gprsIsOpenMethod()  
	 {  
	     Class cmClass       = conMgr.getClass();  
	     Class[] argClasses  = null;  
	     Object[] argObject  = null;  
	     Boolean isOpen = false;  
	     try  
	     {  
	         Method method = cmClass.getMethod("getMobileDataEnabled", argClasses);  
	         isOpen = (Boolean) method.invoke(conMgr, argObject);  
	     } catch (Exception e)  
	     {  
	        e.printStackTrace();  
	     }  
	      if(isOpen){
	    	  return "开";
	      }else{
	    	  return "关";
	      }
	 } 
	 
	 public void setGprsEnabled(boolean isEnable)  
	    {  
	        Class cmClass       = conMgr.getClass();  
	        Class[] argClasses  = new Class[1];  
	        argClasses[0]       = boolean.class; 
	        try  
	        {  
	            Method method = cmClass.getMethod("setMobileDataEnabled", argClasses);  
	            method.invoke(conMgr, isEnable);  
	        } catch (Exception e)  
	        {  
	            e.printStackTrace();  
	        }  
	    } 
}
