package com.yiqingjing.activity.fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnSwipeListener;
import com.yiqingjing.activity.DetailSettingActivity;
import com.yiqingjing.activity.HomePageActivity;
import com.yiqingjing.activity.R;
import com.yiqingjing.adapter.ModellistAdapter;
import com.yiqingjing.db.ModelDB;
import com.yiqingjing.model.Sence;
import com.yiqingjing.service.WorkService;
import com.yiqingjing.utils.ApplaySetting;

public class ModelListFragment extends Fragment {
    private SwipeMenuListView listView;
    private List<Sence> list;
    private ModellistAdapter modellistAdapter;
    private LinearLayout relativeLayout;
    private Sence sence;
    private ModelDB modelDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.modellist, null);
        listView = (SwipeMenuListView) view.findViewById(R.id.model_list);
        relativeLayout = (LinearLayout) view.findViewById(R.id.relative_layout);
        modelDB = new ModelDB(getActivity());
        list = getData();
        if (list.size() != 0) {
            relativeLayout.setBackgroundColor(Color.TRANSPARENT);
            System.out.println(list.toString());
        }
        modellistAdapter = new ModellistAdapter(getActivity(), list);
        listView.setAdapter(modellistAdapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getActivity());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.del_icon_normal);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        listView.setMenuCreator(creator);

        // step 2. listener item click event
        listView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                sence = list.get(position);
                String senceName = sence.getModelName();
                sence.setStatus(1);
                // modelDB = new ModelDB(getActivity());
                switch (index) {
                    case 0:
                        if (sence.getMode() == 0) {
                            System.out.println("普通模式" + sence.toString());
                            modelDB.updateStatus(sence);
                            new ApplaySetting(getActivity()).applay(sence);
                        } else {
                            modelDB.updateStatus(sence);
                            System.out.println("更新状态成功");
                            initService();
                            modellistAdapter.notifyDataSetChanged();
                        }
                        Toast.makeText(getActivity(), senceName + "已开启", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        list.remove(position);
                        new ModelDB(getActivity()).deleteOldSence(sence.getModelName());
                        initService();
                        modellistAdapter.notifyDataSetChanged();
                        break;
                }
            }
        });

        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                sence = list.get(arg2);
                int mode = sence.getMode();

                String name = sence.getModelName();

                Intent intent = new Intent(getActivity(), DetailSettingActivity.class);
                intent.putExtra("mode", mode);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });

        // set SwipeListener
        listView.setOnSwipeListener(new OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });


        return view;
    }

    public List<Sence> getData() {
        List<Sence> list = new ArrayList<Sence>();
        list = new ModelDB(getActivity()).query();
        return list;
    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        if (modelDB != null)
            modelDB.closeDB();
    }


    public void initService() {
        //modelDB = new ModelDB(getActivity());
        List<Sence> sences = (List<Sence>) modelDB.queryOpenMode("1");
        if (sence != null) {
            Intent intent = new Intent(getActivity(), WorkService.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("sence", (Serializable) sences);
            intent.putExtras(bundle);
            getActivity().startService(intent);
        }
    }


}
