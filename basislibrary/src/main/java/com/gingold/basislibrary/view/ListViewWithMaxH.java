package com.gingold.basislibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * 显示所有item的ListView
 */

public class ListViewWithMaxH extends ListView {
    public ListViewWithMaxH(Context context) {
        super(context);
    }

    public ListViewWithMaxH(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewWithMaxH(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_MOVE){
            return true;//true:禁止滚动
        }
        return super.dispatchTouchEvent(ev);
    }
}
