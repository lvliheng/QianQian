package com.sweet.qianqian.entries;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sweet.qianqian.R;

import java.util.List;

/**
 * Created by lvliheng on 16/12/6.
 */
public class EntriesAdapter extends RecyclerView.Adapter<EntriesAdapter.EntriesViewHolder> {


    private Context context;
    private Handler handler;
    private List<EntriesModel> models;

    public EntriesAdapter(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    public void setData(List<EntriesModel> models) {
        this.models = models;
        notifyDataSetChanged();
    }

    @Override
    public EntriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.entries_item_view, null);
        return new EntriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EntriesViewHolder holder, final int position) {

        EntriesModel model = models.get(position);

        if (position == 0) {
            holder.getEntries_item_month_tv().setVisibility(View.VISIBLE);
            holder.getEntries_item_month_tv().setPadding(0, 10, 0, 10);
        } else {
            if (models.get(position).getMonthNum().equals(models.get(position - 1).getMonthNum())) {
                holder.getEntries_item_month_tv().setVisibility(View.GONE);
            } else {
                holder.getEntries_item_month_tv().setVisibility(View.VISIBLE);
            }
            holder.getEntries_item_month_tv().setPadding(0, 0, 0, 10);
        }

        holder.getEntries_item_month_tv().setText(model.getMonthNum());
        holder.getEntries_item_day_tv().setText(model.getDay());
        holder.getEntries_item_week_tv().setText(model.getWeekShort());
        holder.getEntries_item_time_tv().setText(model.getTime());
        holder.getEntries_item_title_tv().setText(model.getTitle());
        holder.getEntries_item_content_tv().setText(model.getContent());
        holder.getEntries_item_position_tv().setText(model.getPosition());

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
        return models == null ? 0 : models.size();
    }

    static class EntriesViewHolder extends RecyclerView.ViewHolder {

        private TextView entries_item_month_tv;
        private TextView entries_item_day_tv;
        private TextView entries_item_week_tv;
        private TextView entries_item_time_tv;
        private TextView entries_item_position_tv;
        private TextView entries_item_title_tv;
        private TextView entries_item_content_tv;
        private ImageView entries_item_weather_iv;
        private ImageView entries_item_emotion_iv;
        private ImageView entries_item_pic_iv;

        public EntriesViewHolder(View itemView) {
            super(itemView);
            entries_item_month_tv = (TextView) itemView.findViewById(R.id.entries_item_month_tv);
            entries_item_day_tv = (TextView) itemView.findViewById(R.id.entries_item_day_tv);
            entries_item_week_tv = (TextView) itemView.findViewById(R.id.entries_item_week_tv);
            entries_item_time_tv = (TextView) itemView.findViewById(R.id.entries_item_time_tv);
            entries_item_position_tv = (TextView) itemView.findViewById(R.id.entries_item_position_tv);
            entries_item_title_tv = (TextView) itemView.findViewById(R.id.entries_item_title_tv);
            entries_item_content_tv = (TextView) itemView.findViewById(R.id.entries_item_content_tv);
            entries_item_weather_iv = (ImageView) itemView.findViewById(R.id.entries_item_weather_iv);
            entries_item_emotion_iv = (ImageView) itemView.findViewById(R.id.entries_item_emotion_iv);
            entries_item_pic_iv = (ImageView) itemView.findViewById(R.id.entries_item_pic_iv);
        }

        public TextView getEntries_item_month_tv() {
            return entries_item_month_tv;
        }

        public TextView getEntries_item_day_tv() {
            return entries_item_day_tv;
        }

        public TextView getEntries_item_week_tv() {
            return entries_item_week_tv;
        }

        public TextView getEntries_item_time_tv() {
            return entries_item_time_tv;
        }

        public TextView getEntries_item_position_tv() {
            return entries_item_position_tv;
        }

        public TextView getEntries_item_title_tv() {
            return entries_item_title_tv;
        }

        public TextView getEntries_item_content_tv() {
            return entries_item_content_tv;
        }

        public ImageView getEntries_item_weather_iv() {
            return entries_item_weather_iv;
        }

        public ImageView getEntries_item_emotion_iv() {
            return entries_item_emotion_iv;
        }

        public ImageView getEntries_item_pic_iv() {
            return entries_item_pic_iv;
        }
    }

    public void remove(int position) {
        models.remove(0);
        notifyItemRemoved(position);
    }

    public void add(EntriesModel model) {
        models.add(0, model);
        notifyItemInserted(0);
    }
}
