package com.yiqingjing.view;

import android.R.integer;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class AnimationViewPager extends ViewPager {
	
	private OnPagerScrollListener listener;
	private int direction = 0;
	private int current = 0;

	public AnimationViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}

	public AnimationViewPager(Context context) {
		super(context);
		
	}

	public void setOnPagerScrollListener(OnPagerScrollListener listener) {
		this.listener = listener;
	}

	@Override
	protected void onPageScrolled(int arg0, float arg1, int arg2) {
		if(getCurrentItem() > current){
			direction = 1;
		}else if(getCurrentItem() < current){
			direction = -1;
		}
		if(listener != null){
			listener.OnScroll(arg1, direction);
			current = getCurrentItem();
			direction = 0;
		}
		super.onPageScrolled(arg0, arg1, arg2);
	}
	public interface OnPagerScrollListener{
		//length在0-1之间变换
		public void OnScroll(float length, int direction);
	}
}
