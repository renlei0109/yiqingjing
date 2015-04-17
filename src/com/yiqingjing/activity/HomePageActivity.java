package com.yiqingjing.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.yiqingjing.activity.fragment.ModelFragment;
import com.yiqingjing.activity.fragment.MoreInstallFragment;
import com.yiqingjing.db.ModelDB;

public class HomePageActivity extends Activity implements OnClickListener {

    private boolean isFinish = false;
    private Button change_install, main_push, more_install;
    private View preView;
    private int preBitmap;
    private FragmentManager fManager;
    private Fragment moreInstallFragment;
    Fragment modelFragment;

    ModelDB modelDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        change_install = (Button) findViewById(R.id.change_install);
        main_push = (Button) findViewById(R.id.main_push);
        more_install = (Button) findViewById(R.id.more_install);
        modelDB = new ModelDB(this);
        change_install.setOnClickListener(this);
        main_push.setOnClickListener(this);
        more_install.setOnClickListener(this);

        moreInstallFragment = new MoreInstallFragment();
        modelFragment = new ModelFragment();
        preView = change_install;
        preBitmap = R.drawable.my_scene_no_selected;

        fManager = getFragmentManager();
        FragmentTransaction fTransaction = fManager.beginTransaction();
        Fragment modelFragment = new ModelFragment();
        fTransaction.replace(R.id.homepage_main, modelFragment);
        fTransaction.commit();
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.change_install:
                preView.setBackgroundResource(preBitmap);
                preView = arg0;
                preView.setBackgroundResource(R.drawable.change_install_selected);
                preBitmap = R.drawable.change_install_no_selected;
                if (!((ModelFragment) modelFragment).getIsCommitAgain()) {
                    FragmentTransaction fTransaction = fManager.beginTransaction();
                    ;
                    fTransaction.replace(R.id.homepage_main, modelFragment);
                    fTransaction.commit();
                }
                break;
            case R.id.more_install:
                preView.setBackgroundResource(preBitmap);
                preView = arg0;
                preView.setBackgroundResource(R.drawable.more_install_selected);
                preBitmap = R.drawable.more_install_no_selected;
                if (!((MoreInstallFragment) moreInstallFragment).getIsCommitAgain()) {
                    FragmentTransaction fTransaction1 = fManager.beginTransaction();
                    ;
                    fTransaction1.replace(R.id.homepage_main, moreInstallFragment);
                    fTransaction1.commit();
                }
                break;
            case R.id.main_push:
                Intent main_push_intent = new Intent(HomePageActivity.this, NewSceneActivity.class);
                startActivity(main_push_intent);
                overridePendingTransition(R.anim.new_activity_in, R.anim.home_activity_out);

                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isFinish) {
                isFinish = true;
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                new Timer().schedule(new TimerTask() {

                    @Override
                    public void run() {
                        isFinish = false;
                    }
                }, 2000);
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        modelDB.closeDB();
    }
}
