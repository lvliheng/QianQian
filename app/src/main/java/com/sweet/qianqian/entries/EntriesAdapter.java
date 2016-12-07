package com.sweet.qianqian.entries;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.sweet.qianqian.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvliheng on 16/12/6.
 */
public class EntriesAdapter extends RecyclerView.Adapter {


    private Context context;
    private Handler handler;
    private List<EntriesModel> models;



    public EntriesAdapter(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
        models = new ArrayList<>();
        EntriesModel model = new EntriesModel();
        for (int i = 0; i < 20; i++) {
            models.add(model);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.entries_item_view, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 0;
                msg.arg1 = position;
                handler.sendMessage(msg);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Message msg = new Message();
                msg.what = 1;
                msg.arg1 = position;
                handler.sendMessage(msg);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);

        }
    }

    public void remove(int position) {
        models.remove(0);
        notifyItemRemoved(position);
    }

    public void add(int position) {
        models.add(new EntriesModel());
        notifyItemInserted(0);
    }
}
