package com.yiqingjing.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.yiqingjing.activity.fragment.NetSettingFragment;
import com.yiqingjing.activity.fragment.OtherSettingFragment;
import com.yiqingjing.activity.fragment.RingSettingFragment;
import com.yiqingjing.activity.fragment.ScreenSettingFragment;
import com.yiqingjing.activity.fragment.VoiceSettingFragment;
import com.yiqingjing.db.ModelDB;
import com.yiqingjing.model.Sence;
import com.yiqingjing.nextset.GetLocation;
import com.yiqingjing.utils.ApplaySetting;
import com.yiqingjing.utils.SenceAndSetting;
//import android.view.View.OnClickListener;

public class DetailSettingActivity extends Activity implements android.view.View.OnClickListener {

	private FragmentManager fManager;
	private FragmentTransaction transaction;

	// 音量控制的fragment
	private VoiceSettingFragment voiceSettingFragment;
	// 铃声控制的fragment
	private RingSettingFragment ringSettingFragment;
	// 屏幕控制的fragment
	private ScreenSettingFragment screenSettingFragment;
	// 网络连接控制的fragment
	private NetSettingFragment netSettingFragment;
	// 其他控制的fragment
	private OtherSettingFragment otherSettingFragment;
	// 保存用户设置
	private HashMap<String, Object> setting;
	// 返回按钮
	private ImageButton back;
	// 保存按键
	private TextView save;
	//当前模式的选项卡
	private TextView statusTextView; 
	// 保存当前模式
	private int mode = -1;
	//保存模式
	private int mode0;
	// 图片的id
	private int[] resource = new int[] { R.drawable.wifi, R.drawable.location, R.drawable.time };
	// 自定义图片
	private int[] myResource = new int[] { R.drawable.mysence1,
			R.drawable.mysence2, R.drawable.mysence3 };
	// 随机数产生器
	private Random random;
	// 模式图标
	private ImageView senceIcon;
	// 模式名称
	private TextView name;
	
	// header的layout
	private View header;
	//判断从哪个界面跳转过来
	public static String modeName;
	//保存从数据库获取的数据
	public static Sence sence;
	private ModelDB db;
	// 模式头像选择界面
	private GridView gridView;
	public static int[] resources = new int[] { R.drawable.a001, R.drawable.a002,
			R.drawable.a003, R.drawable.a004, R.drawable.a005, R.drawable.a006,
			R.drawable.a007, R.drawable.a008, R.drawable.a009, R.drawable.a010,
			R.drawable.a011, R.drawable.a012 };
	private int imgId=0;
	
	private ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
	// 头像选择框
	private Dialog dialog;
	
	private ModelDB modelDB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_setting);
		random = new Random();
		for (int i = 0; i < 12; ++i) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("reasource", resources[i]);
			list.add(map);
		}
		initIntent();
		setting = new HashMap<String, Object>();
		modelDB=new ModelDB(this);
		// 填充默认setting
		initSetting();
		// 初始化fragment
		initFragment();
		// 更换fragment
		translateFragment();
	}

	private void initIntent() {
		Intent intent = getIntent();
		mode = intent.getIntExtra("mode", -1);
		mode0 = mode;
		modeName = intent.getStringExtra("name");
		if(modeName != null){
			db = new ModelDB(this);
			sence = db.queryModel(modeName);
			System.out.println(sence.toString());
		}
	}

	private void initSetting() {
		back = (ImageButton) findViewById(R.id.detail_back);
		save = (TextView) findViewById(R.id.detail_save);
		statusTextView = (TextView)findViewById(R.id.status);
		
		senceIcon = (ImageView) findViewById(R.id.sence_icon_no);
		name = (TextView) findViewById(R.id.sence_name_no);
		header = findViewById(R.id.image);
		if(modeName != null){
			name.setText(sence.getModelName());
			senceIcon.setImageResource(resources[sence.getImgId()]);
			if(sence.getStatus()==0)
			   statusTextView.setText("未开启");
			else {
				statusTextView.setText("使用中");
			}
			imgId = sence.getImgId();
			switch (mode0) {
			case 1:
				save.setText(sence.getWifiname());
				break;
			case 2:
				save.setText(sence.getLatitude()+"-----"+sence.getLongitude());
				break;
			case 3:
				save.setText(sence.getHour()+":"+sence.getMinute());
				break;
			

			default:
				break;
			}
		}
		else {
			switch (mode0) {
			case 0:
				save.setText("启用");
				break;
			case 1:
				save.setText("WIFI名");
				break;
			case 2:
				save.setText("定位");
				break;
			case 3:
				save.setText("设置时间");
				break;
			default:
				break;
		    }
		
		}
		
		initImageView();

		back.setOnClickListener(this);
		save.setOnClickListener(this);
		senceIcon.setOnClickListener(this);
		name.setOnClickListener(this);
		

		if(modeName == null){
			SenceAndSetting.initSetting(setting, mode);
		}else{
			SenceAndSetting.SenceToSetting(setting, sence);
		}
		
		
	}

	private void initImageView() {
		if (mode != 0) {
			header.setBackgroundResource(resource[mode-1]);
		} else {
			header.setBackgroundResource(myResource[random.nextInt(2)]);
		}
		int width = getWindowManager().getDefaultDisplay().getWidth();
		System.out.println(width);
		int height = 4 * width / 10;
		android.view.ViewGroup.LayoutParams params = header.getLayoutParams();
		params.height = height;
		params.width = width;
		header.setLayoutParams(params);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.detail_back:
			finish();
			break;
		case R.id.detail_save:
			if(modeName == null){
		
			switch (mode) {
			case 0:
				mode = -1;
				Toast.makeText(DetailSettingActivity.this, "再点一次保存", Toast.LENGTH_SHORT).show();
				
				break;
			case 1:
				boolean ifOpen = createWifiChoose();
				if(ifOpen){
					save.setText("保存");
					mode = -1;
				}
				break;
			case 2:
				ArrayList<Double> result;
				try {
					result = GetLocation.getCurrentLocation(DetailSettingActivity.this);
					setting.put("latitude", String.valueOf(result.get(0)));
					setting.put("longitude", String.valueOf(result.get(1)));
					
					save.setText("保存");
					mode = -1;
				} catch (Exception e) {
					Toast.makeText(this, "定位失败，请检查手机设置", Toast.LENGTH_SHORT).show();
				}
				
				break;
			case 3:
				save.setText("保存");
				int hour = 0;
				int minute = 0;
				Calendar c = Calendar.getInstance();    
			    hour = c.get(Calendar.HOUR_OF_DAY);             
			    minute = c.get(Calendar.MINUTE);
				TimePickerDialog dialog = new TimePickerDialog(DetailSettingActivity.this,
						new OnTimeSetListener() {

							@Override
							public void onTimeSet(TimePicker arg0, int hour,
									int minute) {
								System.out.println(hour + ":" + minute);
								setting.put("hour", hour);
								setting.put("minute", minute);
							}
						}, hour, minute, true);
				dialog.setCancelable(false);
				dialog.show();
				mode = -1;
				break;
			default:
				if(saveSettins())
				{
				    Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
				    finish();
				}
				else {
					Toast.makeText(DetailSettingActivity.this, "改名字已存在，请更换名称", Toast.LENGTH_SHORT).show();
				}
				break;
			 }
			
			}
			
			else if(modeName!=null){
				switch (mode) {
				case 1:
					save.setText("修改");
					createWifiChoose();
					mode = -1;
					break;
				case 2:
					save.setText("修改");
					ArrayList<Double> result;
					try {
						result = GetLocation.getCurrentLocation(DetailSettingActivity.this);
						setting.put("latitude", String.valueOf(result.get(0)));
						setting.put("longitude", String.valueOf(result.get(1)));
						
					} catch (Exception e) {
						Toast.makeText(this, "定位失败，请检查网络设置", Toast.LENGTH_SHORT).show();
					}
					mode = -1;
					break;
				case 3:
					save.setText("修改");
					new TimePickerDialog(DetailSettingActivity.this,
							new OnTimeSetListener() {

								@Override
								public void onTimeSet(TimePicker arg0, int hour,
										int minute) {
									System.out.println(hour + ":" + minute);
									setting.put("hour", hour);
									setting.put("minute", minute);
								}
							}, sence.getHour(), sence.getMinute(), true).show();
					mode = -1;
					break;
				default:
					if(editSaves())
					{
						Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
						finish();
					}
					else {
						Toast.makeText(DetailSettingActivity.this, "改名字已存在，请更换名称", Toast.LENGTH_SHORT).show();
					}
					break;
				 }
			}
			
			break;
			
		case R.id.sence_icon_no:
			System.out.println("dialog");
			showGridDialog();
			break;
		default:
			break;
		}
	}

	private boolean  saveSettins() {
		
		
		if(modelDB.queryModel(name.getText().toString()) == null)
		{
		Sence sence=new Sence();
		sence.setModelName(name.getText().toString());
		sence.setStatus(0);
		sence.setAlarmvolumn((Integer) setting.get("alarm"));
		sence.setMediavolumn((Integer) setting.get("media"));
		sence.setCallvolumn((Integer)setting.get("call"));
		sence.setRingmode((Integer) setting.get("ringmode"));
		sence.setRingUri((String) setting.get("ringuri"));
		sence.setLightmode((Integer) setting.get("lightmode"));
		sence.setLightness((Integer) setting.get("lightness"));
		sence.setAuto_rotate((Integer) setting.get("auto_rotate"));
		sence.setWifi((Integer) setting.get("wifi"));
		sence.setWifiname((String)setting.get("wifiname"));
		sence.setWifihot((Integer) setting.get("wifihot"));
		sence.setGprs((Integer) setting.get("gprs"));
		sence.setBluetooth( (Integer) setting.get("bluetooth"));
		sence.setHour((Integer) setting.get("hour"));
		sence.setMinute((Integer) setting.get("minute"));
		sence.setLatitude((String) setting.get("latitude"));
		sence.setLongitude((String) setting.get("longitude"));
		sence.setMode(mode0);
		sence.setImgId(imgId);
		modelDB.add(sence);
		return true;
		}
		return false;
	}
	
	private boolean editSaves(){
		
		sence.setModelName(name.getText().toString());
		//sence.setStatus(0);
		sence.setAlarmvolumn((Integer) setting.get("alarm"));
		sence.setMediavolumn((Integer) setting.get("media"));
		sence.setCallvolumn((Integer)setting.get("call"));
		sence.setRingmode((Integer) setting.get("ringmode"));
		sence.setRingUri((String) setting.get("ringuri"));
		sence.setLightmode((Integer) setting.get("lightmode"));
		sence.setLightness((Integer) setting.get("lightness"));
		sence.setAuto_rotate((Integer) setting.get("auto_rotate"));
		sence.setWifi((Integer) setting.get("wifi"));
		sence.setWifiname((String)setting.get("wifiname"));
		sence.setWifihot((Integer) setting.get("wifihot"));
		sence.setGprs((Integer) setting.get("gprs"));
		sence.setBluetooth( (Integer) setting.get("bluetooth"));
		sence.setHour((Integer) setting.get("hour"));
		sence.setMinute((Integer) setting.get("minute"));
		sence.setLatitude((String) setting.get("latitude"));
		sence.setLongitude((String) setting.get("longitude"));
		//sence.setMode(mode0);
		sence.setImgId(imgId);
		modelDB.edit(sence);
		return true;
	}
	

	private void showGridDialog() {
		View view = getLayoutInflater().inflate(R.layout.icon_choose, null);
		gridView = (GridView) view.findViewById(R.id.icon_choose);
		gridView.setAdapter(new SimpleAdapter(this, list, R.layout.grid_item,
				new String[] { "reasource" }, new int[] { R.id.grid_item }));
		gridView.setFocusable(true);
		gridView.setFocusableInTouchMode(true);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				imgId=arg2;
				senceIcon.setImageResource(resources[arg2]);
				dialog.dismiss();
			}

		});
		dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(view);
		dialog.show();
	}

	private void translateFragment() {
		fManager = getFragmentManager();
		transaction = fManager.beginTransaction();

		transaction.replace(R.id.voiceset, voiceSettingFragment);
		transaction.replace(R.id.ringset, ringSettingFragment);
		transaction.replace(R.id.screenset, screenSettingFragment);
		transaction.replace(R.id.netset, netSettingFragment);
		transaction.replace(R.id.otherset, otherSettingFragment);

		transaction.commit();

	}

	private void initFragment() {
		Bundle bundle = new Bundle();
		bundle.putSerializable("setting", setting);
		
		voiceSettingFragment = new VoiceSettingFragment();
		screenSettingFragment = new ScreenSettingFragment();
		ringSettingFragment = new RingSettingFragment();
		netSettingFragment = new NetSettingFragment();
		otherSettingFragment = new OtherSettingFragment();


		// 将bundle传递给fragment
		voiceSettingFragment.setArguments(bundle);
		screenSettingFragment.setArguments(bundle);
		ringSettingFragment.setArguments(bundle);
		netSettingFragment.setArguments(bundle);
		otherSettingFragment.setArguments(bundle);
	}
	
	//wifi选择输入框
	
			public boolean createWifiChoose(){  
		        AlertDialog dialog = null;  
		        AlertDialog.Builder builder = null;  
		        final View view = LayoutInflater.from(this).inflate(R.layout.wifidialog, null);  
		        ListView listView = (ListView)view.findViewById(R.id.wifilist);
				WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
				List<ScanResult>wifiList = new ArrayList<ScanResult>();
			    wifiList = wifiManager.getScanResults();
			    
			    if(wifiList==null){
			    	Toast.makeText(DetailSettingActivity.this, "wifi未开启，请在设置界面开启", Toast.LENGTH_SHORT).show();
			    	return false;
			    }
			    else {
				
				final String[]wifiArray = new String[wifiList.size()];
				
				for(int i=0;i<wifiList.size();i++){
					wifiArray[i] = wifiList.get(i).SSID;
				}
				
				
				listView.setAdapter(new ArrayAdapter<String>(DetailSettingActivity.this, android.R.layout.simple_list_item_single_choice, wifiArray));
				listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
				
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0,
							View arg1, int arg2, long arg3) {
						// TODO Auto-generated method stub
						setting.put("wifiname", wifiArray[arg2]);
					}
				});
				
		        builder = new AlertDialog.Builder(this);  
		        builder.setTitle("启动该模式的WIFI");  
		        builder.setView(view); 
		        builder.setPositiveButton("确定", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
 
					}
				});
		        dialog = builder.create();
		        dialog.setCancelable(false);
		        dialog.show();  
		        return true;
		       }
			}

			@Override
			protected void onDestroy() {
				// TODO Auto-generated method stub
				super.onDestroy();
				if(db!=null){
					db.closeDB();
					modelDB.closeDB();
				}
			}
			
			

}
