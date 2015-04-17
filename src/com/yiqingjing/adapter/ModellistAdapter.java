package com.yiqingjing.adapter;

import java.util.List;
import com.yiqingjing.activity.DetailSettingActivity;
import com.yiqingjing.activity.R;
import com.yiqingjing.model.Sence;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ModellistAdapter extends BaseAdapter{
	
	private List<Sence> dataList;
	private LayoutInflater layoutInflater;
	private Context context;
	
	public ModellistAdapter(Context context,List<Sence>list){
		this.context=context;
		this.layoutInflater=LayoutInflater.from(context);
		dataList=list;
	}
	
	public final class Item{
		public ImageView titleImg;
		public TextView upText;
		public TextView downText;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return dataList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		Item item=null;
		if(arg1==null){
			item=new Item();
			arg1=layoutInflater.inflate(R.layout.modellist_item, null);
			item.titleImg=(ImageView)arg1.findViewById(R.id.modellist_item_img);
			item.upText=(TextView)arg1.findViewById(R.id.item_txt_up);
			item.downText=(TextView)arg1.findViewById(R.id.item_txt_bottom);
			arg1.setTag(item);
		}
		else {
			item=(Item)arg1.getTag();
		}
		
		int num=dataList.get(arg0).getImgId();
		item.titleImg.setBackgroundResource(DetailSettingActivity.resources[num]);
		item.upText.setText((String)dataList.get(arg0).getModelName());
		switch (dataList.get(arg0).getMode()) {
		case 0:
			item.downText.setText("");
			break;
		case 1:
			item.downText.setText((String)dataList.get(arg0).getWifiname());
			break;
		case 2:
			
			item.downText.setText(dataList.get(arg0).getLatitude()+":"+dataList.get(arg0).getLongitude());
			break;
		case 3:
			item.downText.setText(dataList.get(arg0).getHour()+":"+dataList.get(arg0).getMinute());
			break;
        
		default:
			break;
		}
		
		return arg1;
	}

}
