package com.sweet.qianqian.calender;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sweet.qianqian.R;
import com.sweet.qianqian.main.BaseFragment;

/**
 * Created by lvliheng on 16/12/12.
 */
public class CalendarFragment extends BaseFragment {

    private static CalendarFragment calendarFragment;
    private View view;


    public static CalendarFragment getInstance() {
        if (calendarFragment == null) {
            calendarFragment = new CalendarFragment();
        }
        return calendarFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = View.inflate(getContext(), R.layout.calendar_view, null);
        }
        init(view);
        return view;
    }

    private void init(View view) {

    }
}
