package com.sweet.qianqian.moments;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sweet.qianqian.R;
import com.sweet.qianqian.main.MyBaseAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lvliheng on 16/11/29.
 */
public class MomentsAdapter extends MyBaseAdapter {


    private Context context;


    public MomentsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.moments_item_view, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }



        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.moments_item_date_tv)
        TextView momentsItemDateTv;
        @BindView(R.id.moments_item_content_tv)
        TextView momentsItemContentTv;
        @BindView(R.id.moments_item_iv)
        ImageView momentsItemIv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}