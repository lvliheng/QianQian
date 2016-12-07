package com.sweet.qianqian.entries;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sweet.qianqian.R;
import com.sweet.qianqian.main.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lvliheng on 16/12/5.
 */
public class EntriesFragment extends BaseFragment {

    @BindView(R.id.entries_lv)
    ListView entriesLv;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = View.inflate(getContext(), R.layout.entries_view, null);
        }
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        entriesLv.setAdapter(new EntriesAdapter(getContext()));
    }

    public static EntriesFragment newInstance(int index) {
        return new EntriesFragment();
    }
}
