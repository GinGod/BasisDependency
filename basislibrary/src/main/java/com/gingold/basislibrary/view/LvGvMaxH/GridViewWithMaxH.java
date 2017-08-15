package com.gingold.basislibrary.view.LvGvMaxH;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * 显示所有item的GridView
 */
public class GridViewWithMaxH extends GridView {
    public GridViewWithMaxH(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridViewWithMaxH(Context context) {
        super(context);
    }

    public GridViewWithMaxH(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
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
