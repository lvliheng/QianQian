/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sweet.xianxian.fragment.diary;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.sweet.xianxian.R;
import com.sweet.xianxian.view.MyRelativeLayout;
import com.sweet.xianxian.model.EntriesDBModel;
import com.sweet.xianxian.model.EntriesModel;
import com.sweet.xianxian.main.BaseFragment;
import com.sweet.xianxian.utils.DiaryEvent;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiaryFragment extends BaseFragment implements View.OnClickListener, MyRelativeLayout.MyInputMethodListener {


    private static final String TAG = "DiaryFragment";

    @BindView(R.id.diary_date_month_tv)
    TextView diaryDateMonthTv;
    @BindView(R.id.diary_date_day_tv)
    TextView diaryDateDayTv;
    @BindView(R.id.diary_date_week_tv)
    TextView diaryDateWeekTv;
    @BindView(R.id.diary_date_time_tv)
    TextView diaryDateTimeTv;
    @BindView(R.id.diary_position_tv)
    TextView diaryPositionTv;
    @BindView(R.id.diary_date_ll)
    LinearLayout diaryDateLl;
    @BindView(R.id.diary_title_et)
    EditText diaryTitleEt;
    @BindView(R.id.diary_weather_ib)
    ImageButton diaryWeatherIb;
    @BindView(R.id.diary_emotion_ib)
    ImageButton diaryEmotionIb;
    @BindView(R.id.diary_title_ll)
    LinearLayout diaryTitleLl;
    @BindView(R.id.diary_content_et)
    EditText diaryContentEt;
    @BindView(R.id.diary_toolbar_more_ib)
    ImageButton diaryToolbarMoreIb;
    @BindView(R.id.diary_toolbar_position_ib)
    ImageButton diaryToolbarPositionIb;
    @BindView(R.id.diary_toolbar_camera_ib)
    ImageButton diaryToolbarCameraIb;
    @BindView(R.id.diary_toolbar_delete_ib)
    ImageButton diaryToolbarDeleteIb;
    @BindView(R.id.diary_toolbar_save_ib)
    ImageButton diaryToolbarSaveIb;
    @BindView(R.id.diary_main_rl)
    MyRelativeLayout diaryMainRl;

    private String weekShort;
    private String weather;
    private String emotion;
    private String position;
    private String monthNum;

    public static final int HANDLER_INPUT_METHOD_SHOWN = 0;
    public static final int HANDLER_INPUT_METHOD_HIDDEN = 1;
    public static final int HANDLER_TIP = 2;
    public static final int HANDLER_POSITION = 3;

    private MyHandler handler;

    private InputMethodManager inputMethodManager;

    private AMapLocationClient client;

    private static DiaryFragment diaryFragment;

    public static DiaryFragment getInstance() {
        if (diaryFragment == null) {
            diaryFragment = new DiaryFragment();
        }
        return diaryFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diary_view, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {

        weather = "0";
        emotion = "0";
        position = getString(R.string.postion_init);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.CHINA);
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);


        client = new AMapLocationClient(getContext());
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(true);
        option.setOnceLocationLatest(true);
        client.setLocationOption(option);
        client.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation == null) {
                    Log.e(TAG, "onLocationChanged: " + null);
                } else {
                    Log.e(TAG, "onLocationChanged: " + aMapLocation.getAddress());
                    position = aMapLocation.getCity() + aMapLocation.getDistrict() + aMapLocation.getStreet()
                            + aMapLocation.getStreetNum();
                    handler.sendEmptyMessage(HANDLER_POSITION);
                }
            }
        });
        client.startLocation();

        diaryMainRl.setMyListener(this);
        diaryWeatherIb.setOnClickListener(this);
        diaryEmotionIb.setOnClickListener(this);
        diaryToolbarMoreIb.setOnClickListener(this);
        diaryToolbarPositionIb.setOnClickListener(this);
        diaryToolbarCameraIb.setOnClickListener(this);
        diaryToolbarDeleteIb.setOnClickListener(this);
        diaryToolbarSaveIb.setOnClickListener(this);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getDefault());
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        diaryDateMonthTv.setText(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH));
        diaryDateDayTv.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        diaryDateWeekTv.setText(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH));
        diaryDateTimeTv.setText(simpleDateFormat.format(calendar.getTime()));
        weekShort = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.ENGLISH) + ".";
        monthNum = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        diaryPositionTv.setText(position);

        handler = new MyHandler(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.diary_weather_ib:
                break;
            case R.id.diary_emotion_ib:
                break;
            case R.id.diary_toolbar_more_ib:
                break;
            case R.id.diary_toolbar_position_ib:
                break;
            case R.id.diary_toolbar_camera_ib:
                break;
            case R.id.diary_toolbar_delete_ib:
                break;
            case R.id.diary_toolbar_save_ib:
                deliverData();
                break;
            default:
                break;
        }
    }

    private void deliverData() {
        final AVObject avObject = new AVObject(EntriesDBModel.ENTRIES);
        avObject.put(EntriesDBModel.MONTH, diaryDateMonthTv.getText());
        avObject.put(EntriesDBModel.MONTH_NUM, monthNum);
        avObject.put(EntriesDBModel.DAY, diaryDateDayTv.getText());
        avObject.put(EntriesDBModel.WEEK, diaryDateWeekTv.getText());
        avObject.put(EntriesDBModel.WEEK_SHORT, weekShort);
        avObject.put(EntriesDBModel.TIME, diaryDateTimeTv.getText());
        avObject.put(EntriesDBModel.POSITION, position);
        avObject.put(EntriesDBModel.WEATHER, weather);
        avObject.put(EntriesDBModel.EMOTION, emotion);
        avObject.put(EntriesDBModel.TITLE, diaryTitleEt.getText().toString().trim().getBytes());
        avObject.put(EntriesDBModel.CONTENT, diaryContentEt.getText().toString().trim().getBytes());

        final EntriesModel model = new EntriesModel();
        model.setMonth(diaryDateMonthTv.getText().toString());
        model.setMonthNum(monthNum);
        model.setDay(diaryDateDayTv.getText().toString());
        model.setWeek(diaryDateWeekTv.getText().toString());
        model.setWeekShort(weekShort);
        model.setTime(diaryDateTimeTv.getText().toString());
        model.setPosition(position);
        model.setWeather(weather);
        model.setEmotion(emotion);
        model.setTitle(diaryTitleEt.getText().toString().trim());
        model.setContent(diaryContentEt.getText().toString().trim());
        
        avObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    model.setId(avObject.getObjectId());
                    model.setCreate(avObject.getCreatedAt());
                    inputMethodManager.hideSoftInputFromWindow(diaryTitleEt.getWindowToken(), 0);
                    inputMethodManager.hideSoftInputFromWindow(diaryContentEt.getWindowToken(), 0);
                    Message msg = new Message();
                    msg.what = HANDLER_TIP;
                    msg.obj = model;
                    handler.sendMessageDelayed(msg, 400);
                }
            }
        });
    }

    @Override
    public void mySizeChanged(boolean isInputMethodShow) {
        if (isInputMethodShow) {
            handler.sendEmptyMessage(HANDLER_INPUT_METHOD_SHOWN);
        } else {
            handler.sendEmptyMessage(HANDLER_INPUT_METHOD_HIDDEN);
        }
    }

    private static class MyHandler extends Handler{

        private WeakReference<DiaryFragment> weakReference;

        private MyHandler(DiaryFragment diaryFragment) {
            weakReference = new WeakReference<>(diaryFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            DiaryFragment diaryFragment = weakReference.get();
            if (diaryFragment != null) {
                switch (msg.what) {
                    case HANDLER_INPUT_METHOD_SHOWN:
                        diaryFragment.startHeadAnimation(0, -diaryFragment.diaryDateLl.getHeight());
                        break;
                    case HANDLER_INPUT_METHOD_HIDDEN:
                        diaryFragment.startHeadAnimation(-diaryFragment.diaryDateLl.getHeight(), 0);
                        break;
                    case HANDLER_TIP:
                        EventBus.getDefault().post(new DiaryEvent((EntriesModel) msg.obj));
                        break;
                    case HANDLER_POSITION:
                        diaryFragment.diaryPositionTv.setText(diaryFragment.position);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void startHeadAnimation(int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                diaryMainRl.setPadding(0, Integer.parseInt("" + animation.getAnimatedValue()), 0, 0);
            }
        });
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }


    @Override
    public void onStop() {
        super.onStop();
        if (client != null) {
            client.stopLocation();
        }
    }
}