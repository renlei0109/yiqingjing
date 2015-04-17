package com.yiqingjing.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yiqingjing.view.AnimationViewPager;
import com.yiqingjing.view.AnimationViewPager.OnPagerScrollListener;

public class leadingActivity extends Activity implements OnPagerScrollListener{
	
	private AnimationViewPager pager;
	private ArrayList<ImageView> list = new ArrayList<ImageView>();
	private int[] images = new int[]{R.drawable.b001,
			R.drawable.b002, R.drawable.b003};
	private ImageView imageView;
	private String[] names = new String[]{"厌烦了每天手动去调振动，换铃声，开WiFi，手动很麻烦，头疼ING...", "易情景来了！WIFI，位置，定时通通智能检测，智能开启，随时随地定制您个人的情景模式", "这么好的应用，求你不要杀死它，为了更好体验，放入白名单吧"};
	private TextView textView;
	private int currentItem = 0;
	private boolean lock = false;
	//开始图标
	private Button start;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leading);
		pager = (AnimationViewPager) findViewById(R.id.pager);
		start = (Button) findViewById(R.id.start);
		textView = (TextView) findViewById(R.id.title);
		textView.setText(names[0]);
	
		for(int i = 0; i < 3; ++i){
			imageView = new ImageView(this);
			LayoutParams pams= new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			imageView.setLayoutParams(pams);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			imageView.setImageResource(images[i]);
			list.add(imageView);
		}
		
		start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				SharedPreferences preferences = getSharedPreferences("isfirst", MODE_PRIVATE);
				Editor editor = preferences.edit();
				editor.putBoolean("isfirst", false);
				editor.commit();
				Intent intent = new Intent(leadingActivity.this, HomePageActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		pager.setOnPagerScrollListener(this);
		pager.setAdapter(new MyPagerAdapter());
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				if(arg0 == images.length - 1){
					start.setVisibility(View.VISIBLE);
				}
				else {
					start.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public class MyPagerAdapter extends PagerAdapter{

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(list.get(position));
		}

		@Override
		public int getCount() {
			
			return images.length;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(list.get(position));
			return list.get(position);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			
			return arg0 == arg1;
		}
		
	}
	
	private boolean isShort(float length){
		if(Math.abs(length - 0.5) < 0.1){
			return true;
		}else{
			lock = false;
			return false;
		}
	}

	@Override
	public void OnScroll(float length, int direction) {
		if( direction != 0){
			if(direction > 0){
				++ currentItem;
			}else{
				-- currentItem;
			}
			lock = true;
			int temp = currentItem % names.length;
			textView.setText(names[temp]);
		}
		textView.setAlpha((float) Math.abs(0.5 - length));
	}

}
