package com.example.lapace.assistant.PersonalInfo;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lapace.assistant.R;
import com.example.lapace.assistant.Service.UserService;

/**
 * Created by lapace on 2017/5/8.
 */
public class UserInfoFrag extends Fragment {

    private static final String TAG = "UserInfoFrag";
    private View view;
    private ImageView mDisplayPhoto;
    private TextView mUsername;
    private TextView mNickname;
    private TextView mSex;
    private TextView mArea;
    private TextView mIndividualSiguature;

    private UserService.UserBinder userBinder;
    public UserInfoFrag() {
        super();
    }

    public void setUserBinder(UserService.UserBinder userBinder){
        this.userBinder=userBinder;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userinfo,container, false);
        this.view = view;
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mDisplayPhoto = (ImageView)view.findViewById(R.id.iv_displayPhoto);
        mUsername = (TextView)view.findViewById(R.id.tv_username);
        mNickname = (TextView)view.findViewById(R.id.tv_nickname);
        mSex = (TextView)view.findViewById(R.id.tv_sex);
        mArea = (TextView)view.findViewById(R.id.tv_area);
        mIndividualSiguature = (TextView)view.findViewById(R.id.tv_individualSiguature);
        Log.d(TAG, "onResume: "+"123456543");
        Log.d(TAG, "onResume: ");
        mDisplayPhoto.setImageBitmap(userBinder.getDisplayPhoto());
        mUsername.setText(userBinder.getUsername());
        mNickname.setText(userBinder.getNickname());
        mSex.setText(userBinder.getSex());
        mArea.setText(userBinder.getArea());
        mIndividualSiguature.setText(userBinder.getInvidualSiguature());

    }
}
