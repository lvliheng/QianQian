package com.sweet.qianqian.entries;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.sweet.qianqian.R;
import com.sweet.qianqian.main.MyBaseAdapter;

/**
 * Created by lvliheng on 16/12/6.
 */
public class EntriesAdapter extends MyBaseAdapter {


    private Context context;



    public EntriesAdapter(Context context) {
        this.context = context;
    }

    public void setData() {
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.entries_item_view, null);
        }

        return convertView;
    }
}
