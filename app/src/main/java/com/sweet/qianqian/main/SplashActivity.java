package com.sweet.qianqian.main;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sweet.qianqian.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lvliheng on 16/11/24.
 */
public class SplashActivity extends BaseActivity {


    private static String START_DATE = "19/08/2016";

    @BindView(R.id.splash_days_tv)
    TextView splashDaysTv;
    @BindView(R.id.splash_main_rl)
    RelativeLayout splashMainRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_view);
        ButterKnife.bind(this);
        initView();
        startCountAnimation();
    }

    private void startCountAnimation() {
        ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues(0, getDays(START_DATE));
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                splashDaysTv.setText("" + (int) animation.getAnimatedValue());
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                handler.sendEmptyMessageDelayed(0, 1000);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    private void initView() {

    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Intent intent = new Intent();
                    intent.setClass(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.no_change);
                    break;
            }
        }
    };

    public static int getDays(String startDateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        Date startDate = null, todayWithZeroTime = null;
        try {
            startDate = dateFormat.parse(startDateStr);

            Date today = new Date();

            todayWithZeroTime = dateFormat.parse(dateFormat.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int c_year, c_month, c_day;

        Calendar c_cal = Calendar.getInstance();
        c_cal.setTime(todayWithZeroTime);
        c_year = c_cal.get(Calendar.YEAR);
        c_month = c_cal.get(Calendar.MONTH);
        c_day = c_cal.get(Calendar.DAY_OF_MONTH);

        Calendar e_cal = Calendar.getInstance();
        e_cal.setTime(startDate);

        int e_year = e_cal.get(Calendar.YEAR);
        int e_month = e_cal.get(Calendar.MONTH);
        int e_day = e_cal.get(Calendar.DAY_OF_MONTH);

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.clear();
        date1.set(c_year, c_month, c_day);
        date2.clear();
        date2.set(e_year, e_month, e_day);

        long diff = date1.getTimeInMillis() - date2.getTimeInMillis();

        float dayCount = (float) diff / (24 * 60 * 60 * 1000);

        return (int) dayCount;
    }
}
