package com.sweet.xianxian.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.sweet.xianxian.R;
import com.sweet.xianxian.fragment.entries.EntriesFragment;
import com.sweet.xianxian.model.EntriesDBModel;
import com.sweet.xianxian.model.EntriesModel;
import com.sweet.xianxian.view.ResizeRelativeLayout;
import com.sweet.xianxian.main.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lvliheng on 16/12/12.
 */
public class EntriesDetailActivity extends BaseActivity implements View.OnClickListener, ResizeRelativeLayout.LayoutSizeChangeListener {


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
    ResizeRelativeLayout diaryMainRl;
    @BindView(R.id.entries_detail_main_rl)
    RelativeLayout entriesDetailMainRl;


    public static String EXTRA_MODEL = "model";

    private EntriesModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entries_detail_view);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            model = (EntriesModel) bundle.getSerializable(EXTRA_MODEL);
        }
        diaryMainRl.setLayoutSizeChangeListenner(this);
        diaryWeatherIb.setOnClickListener(this);
        diaryEmotionIb.setOnClickListener(this);
        diaryToolbarMoreIb.setOnClickListener(this);
        diaryToolbarPositionIb.setOnClickListener(this);
        diaryToolbarCameraIb.setOnClickListener(this);
        diaryToolbarDeleteIb.setOnClickListener(this);
        diaryToolbarSaveIb.setOnClickListener(this);

        if (model != null) {
            diaryDateMonthTv.setText(model.getMonth());
            diaryDateDayTv.setText(model.getDay());
            diaryDateWeekTv.setText(model.getWeek());
            diaryDateTimeTv.setText(model.getTime());
            diaryPositionTv.setText(model.getPosition());
            diaryTitleEt.setText(model.getTitle());
            diaryContentEt.setText(model.getContent());
        }
    }

    @Override
    public void onIsSoftInputShow(boolean isShow) {
        if (isShow) {
            handler.sendEmptyMessage(0);
        } else {
            handler.sendEmptyMessage(1);
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    startHeadAnimation(0, -diaryDateLl.getHeight());
                    entriesDetailMainRl.setPadding(10, 0, 10, 0);
                    break;
                case 1:
                    startHeadAnimation(-diaryDateLl.getHeight(), 0);
                    entriesDetailMainRl.setPadding(10, 50, 10, 50);
                    break;
            }
        }
    };

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
        if (model != null) {
            AVObject avObject = AVObject.createWithoutData(EntriesDBModel.ENTRIES, model.getId());
//        avObject.put(EntriesDBModel.MONTH, diaryDateMonthTv.getText());
//        avObject.put(EntriesDBModel.MONTH_NUM, model.getMonthNum());
//        avObject.put(EntriesDBModel.DAY, diaryDateDayTv.getText());
//        avObject.put(EntriesDBModel.WEEK, diaryDateWeekTv.getText());
//        avObject.put(EntriesDBModel.WEEK_SHORT, model.getWeekShort());
//        avObject.put(EntriesDBModel.TIME, diaryDateTimeTv.getText());
//        avObject.put(EntriesDBModel.POSITION, model.getPosition());
//        avObject.put(EntriesDBModel.WEATHER, model.getWeather());
//        avObject.put(EntriesDBModel.EMOTION, model.getEmotion());
            avObject.put(EntriesDBModel.TITLE, diaryTitleEt.getText().toString().trim());
            avObject.put(EntriesDBModel.CONTENT, diaryContentEt.getText().toString().trim());
            avObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        Intent intent = new Intent();
                        intent.putExtra(EntriesFragment.EXTRA_ID, model.getId());
                        intent.putExtra(EntriesFragment.EXTRA_TITLE, diaryTitleEt.getText().toString().trim());
                        intent.putExtra(EntriesFragment.EXTRA_CONTENT, diaryContentEt.getText().toString().trim());
                        setResult(0, intent);
                        finish();
                    }
                }
            });
        }
    }
}
