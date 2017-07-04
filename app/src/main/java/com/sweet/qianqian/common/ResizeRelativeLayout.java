package com.sweet.qianqian.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by lvliheng on 16/11/1.
 */
public class ResizeRelativeLayout extends RelativeLayout {

    private LayoutSizeChangeListener listener;

    public ResizeRelativeLayout(Context context) {
        super(context);
    }

    public ResizeRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ResizeRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLayoutSizeChangeListenner(LayoutSizeChangeListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (null != listener) {
            if (h > oldh) {
                listener.onIsSoftInputShow(false);
            } else {
                listener.onIsSoftInputShow(true);
            }
        }
    }

    public interface LayoutSizeChangeListener {
        void onIsSoftInputShow(boolean isShow);
    }
}
