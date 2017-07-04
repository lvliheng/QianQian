package com.sweet.qianqian.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

/**
 * Created by lvliheng on 16/11/29.
 */
public class BaseFragment extends Fragment {


    public Gson gson;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        gson = new Gson();
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
