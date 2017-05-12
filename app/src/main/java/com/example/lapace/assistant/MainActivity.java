package com.example.lapace.assistant;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.lapace.assistant.Fragment.NewsFrag;
import com.example.lapace.assistant.Fragment.PersonalInfoFrag;
import com.example.lapace.assistant.Fragment.ShiShenDataFrag;
import com.example.lapace.assistant.Service.UserService;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{

    private static final String TAG = "MainActivity";

    //底部导航栏
    private BottomNavigationBar mBottomNavigationBar;

    //对应底部导航栏的三个板块：新闻、式神资料、个人信息
    private NewsFrag mNews;
    private ShiShenDataFrag mShiShenData;
    private PersonalInfoFrag mPersonalInfo;
    public UserService.UserBinder userBinder;
    private ServiceConnection userServiceConnection = new ServiceConnection() {

        private static final String TAG = "UserInfoFrag";
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: ");
            userBinder = (UserService.UserBinder) service;
            Log.d(TAG, "onServiceConnected: "+userBinder.toString());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* 设置导航栏控件 */
        mBottomNavigationBar = (BottomNavigationBar)findViewById(R.id.bottom_Navigation_Bar);//绑定ID
        mBottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.toolbaricon,"资讯"))
                            .addItem(new BottomNavigationItem(R.drawable.toolbaricon,"式神资料"))
                            .addItem(new BottomNavigationItem(R.drawable.toolbaricon,"我"))
                            .setFirstSelectedPosition(0)
                            .initialise();//设置控件项目和基本信息。
        mBottomNavigationBar.setTabSelectedListener(this);//设置控件项目的监听器
        /* 设置默认显示的模块为新闻模块 */
        setDefaultFragment();
        Intent intent = new Intent(this,UserService.class);
        bindService(intent, userServiceConnection, Context.BIND_AUTO_CREATE);
    }

    /* 定义默认显示的模块为新闻模块 */
    private void setDefaultFragment(){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mNews = new NewsFrag();
        transaction.replace(R.id.fl_content,mNews);
        transaction.commit();
    }

    //底部导航栏选中监听器
    @Override
    public void onTabSelected(int i) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch(i){
            case 0://第0项切换到新闻模块。
                if(mNews == null){
                    mNews = new NewsFrag();
                }
                transaction.replace(R.id.fl_content,mNews);
                break;
            case 1://第1项切换到式神资料模块。
                if(mShiShenData == null){
                    mShiShenData = new ShiShenDataFrag();
                }
                transaction.replace(R.id.fl_content,mShiShenData);
                break;
            case 2://第2项切换到个人信息模块。
                if(mPersonalInfo == null){
                    mPersonalInfo = new PersonalInfoFrag();
                    mPersonalInfo.setUserBinder(userBinder);
                }
                transaction.replace(R.id.fl_content,mPersonalInfo);
                break;
            default:
                break;
        }
        transaction.commit();
    }

    //底部导航栏未选中监听器
    @Override
    public void onTabUnselected(int i) {

    }

    //不懂，接口方法，必须重写
    @Override
    public void onTabReselected(int i) {

    }

    public UserService.UserBinder getUserBinder(){
        return userBinder;
    }
}
