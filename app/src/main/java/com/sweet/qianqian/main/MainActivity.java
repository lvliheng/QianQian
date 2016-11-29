package com.sweet.qianqian.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.sweet.qianqian.R;
import com.sweet.qianqian.common.tab.SlidingTabLayout;
import com.sweet.qianqian.moments.MomentsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {


    @BindView(R.id.main_sliding_tab)
    SlidingTabLayout mainSlidingTab;
    @BindView(R.id.vp)
    ViewPager vp;
    private ArrayList<Fragment> fragments;
    private List<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        titles = new ArrayList<>();
        titles.add("moments");
        titles.add("temp");

        fragments = new ArrayList<Fragment>();
        fragments.add(MomentsFragment.newInstance(0));
        fragments.add(TempFragment.newInstance(1));

        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mainSlidingTab.setViewPager(vp);
        vp.setCurrentItem(0);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }

}
