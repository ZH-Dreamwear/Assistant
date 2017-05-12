package com.example.lapace.assistant.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.lapace.assistant.PersonalInfo.AlbumFrag;
import com.example.lapace.assistant.PersonalInfo.CollectionFrag;
import com.example.lapace.assistant.PersonalInfo.FendBackFrag;
import com.example.lapace.assistant.PersonalInfo.SetUpFrag;
import com.example.lapace.assistant.PersonalInfo.UserInfoFrag;
import com.example.lapace.assistant.PersonalInfo.VersionInfoFrag;
import com.example.lapace.assistant.R;
import com.example.lapace.assistant.Service.UserService;

import junit.runner.Version;

/**
 * Created by lapace on 2017/5/4.
 */
public class PersonalInfoFrag extends Fragment implements View.OnClickListener{
    public PersonalInfoFrag() {
        super();
    }

    private static final String TAG = "PersonalInfoFrag";
    private View view;

    private LinearLayout mUserInfo;
    private LinearLayout mCollection;
    private LinearLayout mAlbum;
    private LinearLayout mFendBack;
    private LinearLayout mVersionInfo;
    private LinearLayout mSetUp;

    private UserInfoFrag mUserInfo_F;
    private CollectionFrag mCollection_F;
    private AlbumFrag mAlbum_F;
    private FendBackFrag mFendBack_F;
    private VersionInfoFrag mVersionInfo_F;
    private SetUpFrag mSetup_F;

    private UserService.UserBinder userBinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personalinfo,container, false);
        this.view = view;
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mUserInfo = (LinearLayout)view.findViewById(R.id.ll_userInfo);
        mCollection = (LinearLayout)view.findViewById(R.id.ll_collection);
        mAlbum = (LinearLayout)view.findViewById(R.id.ll_album);
        mFendBack = (LinearLayout)view.findViewById(R.id.ll_FendBack);
        mVersionInfo = (LinearLayout)view.findViewById(R.id.ll_versionInfo);
        mSetUp = (LinearLayout)view.findViewById(R.id.ll_setUp);
        mUserInfo.setOnClickListener(this);
        mCollection.setOnClickListener(this);
        mAlbum.setOnClickListener(this);
        mFendBack.setOnClickListener(this);
        mVersionInfo.setOnClickListener(this);
        mSetUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (v.getId()){
            case R.id.ll_userInfo:
                mUserInfo_F = new UserInfoFrag();
                mUserInfo_F.setUserBinder(userBinder);
                transaction.replace(R.id.fl_content,mUserInfo_F);
                break;
            case R.id.ll_collection:
                mCollection_F = new CollectionFrag();
                transaction.replace(R.id.fl_content,mCollection_F);
                break;
            case R.id.ll_album:
                mAlbum_F = new AlbumFrag();
                transaction.replace(R.id.fl_content,mAlbum_F);
                break;
            case R.id.ll_FendBack:
                mFendBack_F = new FendBackFrag();
                transaction.replace(R.id.fl_content,mFendBack_F);
                break;
            case R.id.ll_versionInfo:
                mVersionInfo_F = new VersionInfoFrag();
                transaction.replace(R.id.fl_content,mVersionInfo_F);
                break;
            case R.id.ll_setUp:
                mSetup_F = new SetUpFrag();
                transaction.replace(R.id.fl_content,mSetup_F);
                break;
            default:
                break;
        }
        transaction.commit();
    }

    public void setUserBinder(UserService.UserBinder userBinder){
        this.userBinder=userBinder;
    }
}
