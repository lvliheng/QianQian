package com.sweet.xianxian.view;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by lvliheng on 16/11/1.
 */
public class MyRelativeLayout extends RelativeLayout {

    private MyInputMethodListener listener;
    private int originHeight;

    public MyRelativeLayout(Context context) {
        super(context);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMyListener(MyInputMethodListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        boolean isInputMethodShow = false;
        if (oldh > 0 && originHeight == 0) {
            originHeight = h;
        }
        if (h < originHeight) {
            isInputMethodShow = true;
        }
        if (listener != null) {
            listener.mySizeChanged(isInputMethodShow);
        }
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    public interface MyInputMethodListener {
        void mySizeChanged(boolean isInputMethodShow);
    }
}
