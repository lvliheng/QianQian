package com.sweet.qianqian.entries;

import android.content.Intent;
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

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.sweet.qianqian.R;
import com.sweet.qianqian.main.BaseFragment;
import com.sweet.qianqian.utils.DiaryEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

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

    private List<EntriesModel> models;

    private static EntriesFragment entriesFragment;

    public static String EXTRA_ID = "id";
    public static String EXTRA_TITLE = "title";
    public static String EXTRA_CONTENT = "content";

    public static EntriesFragment getInstance() {
        if (entriesFragment == null) {
            entriesFragment = new EntriesFragment();
        }
        return entriesFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = View.inflate(getContext(), R.layout.entries_view, null);
            ButterKnife.bind(this, view);
            init();
        }
        return view;
    }

    private void init() {
        entriesRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        entriesRv.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
        adapter = new EntriesAdapter(getContext(), handler);
        entriesRv.setAdapter(new AlphaInAnimationAdapter(adapter));

        models = new ArrayList<>();

        AVQuery<AVObject> avQuery = new AVQuery(EntriesDBModel.ENTRIES);
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                EntriesModel model;
                for (AVObject temp : list) {
                    try {
                        model = new EntriesModel();
                        model.setId(temp.getObjectId());
                        model.setContent(temp.getString(EntriesDBModel.CONTENT));
                        model.setDay(temp.getString(EntriesDBModel.DAY));
                        model.setEmotion(temp.getString(EntriesDBModel.EMOTION));
                        model.setMonth(temp.getString(EntriesDBModel.MONTH));
                        model.setMonthNum(temp.getString(EntriesDBModel.MONTH_NUM));
                        model.setPosition(temp.getString(EntriesDBModel.POSITION));
                        model.setTime(temp.getString(EntriesDBModel.TIME));
                        model.setTitle(temp.getString(EntriesDBModel.TITLE));
                        model.setWeather(temp.getString(EntriesDBModel.WEATHER));
                        model.setWeek(temp.getString(EntriesDBModel.WEEK));
                        model.setWeekShort(temp.getString(EntriesDBModel.WEEK_SHORT));

                        if (models.size() == 0) {
                            models.add(model);
                        } else {
                            if (String.valueOf(temp.getCreatedAt()).compareTo(models.get(0).getCreate()) > 0) {
                                models.add(0, model);
                            } else {
                                models.add(model);
                            }
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                adapter.setData(models);
            }
        });

        EventBus.getDefault().register(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DiaryEvent event) {
        if (adapter != null && models != null) {
            Message msg = new Message();
            msg.what = 2;
            msg.obj = event.data;
            handler.sendMessageDelayed(msg, 400);
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Intent intent = new Intent();
                    intent.setClass(getContext(), EntriesDetailActivity.class);
                    intent.putExtra(EntriesDetailActivity.EXTRA_MODEL, models.get(msg.arg1));
                    startActivityForResult(intent, 0);
                    break;
                case 1:
                    break;
                case 2:
                    models.add(0, (EntriesModel) msg.obj);
                    adapter.notifyDataSetChanged();
                    entriesRv.scrollToPosition(0);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (data != null && data.getExtras() != null) {
                    Bundle bundle = data.getExtras();
                    if (models != null) {
                        for (EntriesModel temp : models) {
                            if (temp.getId().equals(bundle.getString(EXTRA_ID, "0"))) {
                                temp.setTitle(bundle.getString(EXTRA_TITLE, ""));
                                temp.setContent(bundle.getString(EXTRA_CONTENT, ""));
                            }
                        }
                    }
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                }
                break;
            default:
                break;
        }
    }
}
