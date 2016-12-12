package com.sweet.qianqian.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.sweet.qianqian.R;
import com.sweet.qianqian.calender.CalendarFragment;
import com.sweet.qianqian.diary.DiaryFragment;
import com.sweet.qianqian.entries.EntriesFragment;
import com.sweet.qianqian.utils.DiaryEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {


    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.main_sliding_tab)
    SegmentTabLayout mainSlidingTab;
    @BindView(R.id.main_shadow_view)
    View mainShadowView;
    private ArrayList<Fragment> fragments;

    private String[] titles = {"Entries", "Calendar", "Diary"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        fragments = new ArrayList<Fragment>();
        fragments.add(EntriesFragment.getInstance());
        fragments.add(CalendarFragment.getInstance());
        fragments.add(DiaryFragment.getInstance());

        mainSlidingTab.setTabData(titles);

        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        mainSlidingTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vp.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mainSlidingTab.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp.setCurrentItem(0);

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DiaryEvent event) {
        if (vp != null) {
            vp.setCurrentItem(0);
        }
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
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }

}
