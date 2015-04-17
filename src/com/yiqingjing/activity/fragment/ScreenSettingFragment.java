package com.yiqingjing.activity.fragment;

import java.util.HashMap;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.AvoidXfermode.Mode;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.yiqingjing.activity.DetailSettingActivity;
import com.yiqingjing.activity.R;
import com.yiqingjing.utils.ChangeScreenLight;
import com.yiqingjing.utils.ChangeScreenRotate;
import com.yiqingjing.utils.IntToPercent;

public class ScreenSettingFragment extends Fragment implements OnClickListener{
	
	private View mainView;
	//亮度调节模式
	private View lightMode;
	private TextView lightModeTextView;
	//亮度值调节
	private View lightness;
	private TextView lightnessTextView;
	//是否自动转屏
	private View autoRotate;
	private TextView autoRotateTextView;
	
	//单选项的dialog
	private AlertDialog singleDialog;
	
	//功能实现类
	private ChangeScreenLight changeScreenLight;
	
	//保存设置
	private HashMap<String, Integer> setting;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		changeScreenLight = new ChangeScreenLight(getActivity());
		setting = (HashMap<String, Integer>) getArguments().getSerializable("setting");
		mainView = inflater.inflate(R.layout.screenset, null);
		initWidget();
		registerListener();
		return mainView;
	}
	private void initWidget() {
		lightMode = mainView.findViewById(R.id.screen_mode);
		lightModeTextView = (TextView) mainView.findViewById(R.id.screen_mode_textview);
		lightness = mainView.findViewById(R.id.screen_brightness);
		lightnessTextView = (TextView) mainView.findViewById(R.id.screen_grightness_textview);
		autoRotate = mainView.findViewById(R.id.auto_rotate);
		autoRotateTextView = (TextView) mainView.findViewById(R.id.auto_rotate_textview);
		
		if(DetailSettingActivity.modeName != null){
			lightModeTextView.setText(DetailSettingActivity.sence.getLightmode());
			lightnessTextView.setText(IntToPercent.translate(DetailSettingActivity.sence.getLightness(), 255));
			autoRotateTextView.setText(DetailSettingActivity.sence.getAuto_rotate());
		}
		
	}
	private void registerListener() {
		lightMode.setOnClickListener(this);
		lightness.setOnClickListener(this);
		autoRotate.setOnClickListener(this);
	}
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.screen_mode:
			showSingleDialog("亮度调节", new String[]{"保持原有", "手动", "自动"}, 
					setting.get("lightmode") + 1, R.id.screen_mode);
			break;
		case R.id.screen_brightness:
			if(setting.get("lightmode") == 1){
				Toast.makeText(getActivity(), "请将亮度调节模式设为手动", 
						Toast.LENGTH_SHORT).show();
				return;
			}else if(setting.get("lightmode") == -1){
				System.out.println(changeScreenLight.getScreenLightMode());
				if(changeScreenLight.getScreenLightMode() == 1){
					Toast.makeText(getActivity(), "请将亮度调节模式设为手动", 
							Toast.LENGTH_SHORT).show();
					return;
				};
			}
			showSeekBarDialog(50, 255, "屏幕亮度");
			break;
		case R.id.auto_rotate:
			showSingleDialog("自动转屏", new String[]{"保持原有", "关", "开"},
					setting.get("auto_rotate") + 1, R.id.auto_rotate);
			break;
		default:
			break;
		}
	}
	
	//显示带有进度条的dialog
	private void showSeekBarDialog(int percent, final int max, String title){
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.seekbar, null);
		final TextView showPercent = (TextView) view.findViewById(R.id.seektext);
		showPercent.setText(percent+"%");
		final SeekBar showSeekBar = (SeekBar) view.findViewById(R.id.seekbar);
		showSeekBar.setProgress(percent*255/100);
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
		alertBuilder.setTitle(title).setView(view);
		alertBuilder.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				setting.put("lightness", showSeekBar.getProgress());
				lightnessTextView.setText(showSeekBar.getProgress()*100/255+"%");
			}
		}).setNeutralButton("取消", new android.content.DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
			}
		});
		showSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				changeScreenLight.showScreenLight(arg1);
				showPercent.setText(arg1*100/255 + "%");
			}
		});
		alertBuilder.create().show();
	}

	private void showSingleDialog(String title, final String[] contents, final int select,
			final int whitch){
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
		singleDialog = alertBuilder.setTitle(title).setSingleChoiceItems(contents, select, 
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int position) {
						switch (whitch) {
						case R.id.screen_mode:
							setting.put("lightmode", position - 1);
							lightModeTextView.setText(contents[position]);
							break;
						case R.id.auto_rotate:
							setting.put("auto_rotate", position - 1);
							autoRotateTextView.setText(contents[position]);
							break;
						default:
							break;
						}
						singleDialog.dismiss();
					}
				}).create();
		singleDialog.show();
	}

}
