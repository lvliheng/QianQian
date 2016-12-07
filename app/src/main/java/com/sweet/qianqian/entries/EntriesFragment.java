package com.sweet.qianqian.entries;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.sweet.qianqian.R;
import com.sweet.qianqian.main.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by lvliheng on 16/12/5.
 */
public class EntriesFragment extends BaseFragment {

    @BindView(R.id.entries_rv)
    RecyclerView entriesRv;
    private View view;

    private EntriesAdapter adapter;


    public static EntriesFragment newInstance(int index) {
        return new EntriesFragment();
    }

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
        entriesRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        entriesRv.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
        adapter = new EntriesAdapter(getContext(), handler);
        entriesRv.setAdapter(new AlphaInAnimationAdapter(adapter));
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter.add(msg.arg1);
                    entriesRv.scrollToPosition(0);
                    break;
                case 1:
                    adapter.remove(msg.arg1);
                    break;
                default:
                    break;
            }
        }
    };
}
