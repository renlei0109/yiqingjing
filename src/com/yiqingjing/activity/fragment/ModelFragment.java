package com.yiqingjing.activity.fragment;

import com.yiqingjing.activity.DetailSettingActivity;
import com.yiqingjing.activity.R;
import com.yiqingjing.activity.fragment.ModelListFragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

public class ModelFragment extends Fragment{
	private boolean isCommitAgain = false;
	private EditText senceEditText;
	private ImageView senceImageView;
	private SharedPreferences sharedPreferences;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.myqingjing, null);
		senceEditText=(EditText)view.findViewById(R.id.sence_name);
		senceImageView=(ImageView)view.findViewById(R.id.sence_icon);
		senceEditText.setEnabled(false);
		senceEditText.setFocusable(false);
		sharedPreferences=getActivity().getSharedPreferences("current_mode", Context.MODE_PRIVATE);
		senceEditText.setText(sharedPreferences.getString("mode_name", "无模式"));
		
		senceImageView.setImageResource(DetailSettingActivity.resources[sharedPreferences.getInt("mode_img", 0)]);
		
		return view ;
	}
	
	public void updateHead(String senceName,int senceImg){
		senceEditText.setText(senceName);
		senceImageView.setBackgroundResource(senceImg);
	}

	public boolean getIsCommitAgain(){
    	return isCommitAgain;
    }

	@Override
	public void onResume() {
		FragmentManager fManager=getFragmentManager();
		FragmentTransaction fTransaction=fManager.beginTransaction();
		Fragment modellistFragment=new ModelListFragment();
		fTransaction.replace(R.id.fragment_ll, modellistFragment);
		fTransaction.commit();
		super.onResume();
	}

}
