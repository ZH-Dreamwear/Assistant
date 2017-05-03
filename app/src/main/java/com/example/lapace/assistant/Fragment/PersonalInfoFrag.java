package com.example.lapace.assistant.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lapace.assistant.R;

/**
 * Created by lapace on 2017/5/4.
 */
public class PersonalInfoFrag extends Fragment{
    public PersonalInfoFrag() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personalinfo,container, false);
        return view;
    }
}
