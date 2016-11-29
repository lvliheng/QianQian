package com.sweet.qianqian.moments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sweet.qianqian.R;
import com.sweet.qianqian.main.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lvliheng on 16/11/29.
 */
public class MomentsFragment extends BaseFragment {

    private static final String EXTRA_POSITION = "position";
    @BindView(R.id.moments_lv)
    ListView momentsLv;
    private View view;
    private int position;


    public static Fragment newInstance(int position) {
        MomentsFragment momentsFragment = new MomentsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_POSITION, position);
        momentsFragment.setArguments(bundle);
        return momentsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(EXTRA_POSITION);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = View.inflate(getContext(), R.layout.moments_view, null);
        }
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        MomentsAdapter adapter = new MomentsAdapter(getContext());
        momentsLv.setAdapter(adapter);
    }
}
