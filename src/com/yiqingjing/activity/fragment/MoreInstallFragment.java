package com.yiqingjing.activity.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.yiqingjing.activity.AboutUsActivity;
import com.yiqingjing.activity.AgreementActivity;
import com.yiqingjing.activity.FeedBackActivity;
import com.yiqingjing.activity.OurGroupActivity;
import com.yiqingjing.activity.R;

public class MoreInstallFragment extends Fragment implements OnClickListener{
	
	private View mainView;
	private boolean isCommitAgain = false;
	private View our_group, agreement, about_us, feed_back, share; 
	private Intent intent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		intent = new Intent();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainView = inflater.inflate(R.layout.moreinstall, null);
		
		our_group = mainView.findViewById(R.id.our_group);
		our_group.setOnClickListener(this);
		agreement = mainView.findViewById(R.id.agreement);
		agreement.setOnClickListener(this);
		about_us = mainView.findViewById(R.id.aboutus);
		about_us.setOnClickListener(this);
		feed_back = mainView.findViewById(R.id.feedback);
		feed_back.setOnClickListener(this);
		share = mainView.findViewById(R.id.share);
		share.setOnClickListener(this);
		
		return mainView;
	}
	
    @Override
	public void onClick(View arg0) {
    	switch (arg0.getId()) {
		case R.id.our_group:
			intent.setClass(getActivity(), OurGroupActivity.class);
			startActivity(intent);
			break;
		case R.id.agreement:
			intent.setClass(getActivity(), AgreementActivity.class);
			startActivity(intent);
			break;
		case R.id.aboutus:
			intent.setClass(getActivity(), AboutUsActivity.class);
			startActivity(intent);
			break;
		case R.id.feedback:
			intent.setClass(getActivity(), FeedBackActivity.class);
			startActivity(intent);
			break;
		case R.id.share:
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");
			intent.putExtra(Intent.EXTRA_TEXT, "易情景这个软件不错哦");
	        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
	        startActivity(Intent.createChooser(intent, "请选择"));
			break;
		default:
			break;
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		isCommitAgain = true;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		isCommitAgain = false;
	}

	public boolean getIsCommitAgain(){
    	return isCommitAgain;
    }
}
