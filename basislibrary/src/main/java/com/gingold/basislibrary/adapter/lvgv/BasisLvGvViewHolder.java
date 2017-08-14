package com.gingold.basislibrary.adapter.lvgv;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * ViewHolder
 */
public class BasisLvGvViewHolder {
    private SparseArray<View> mViews;
    protected int mPosition;
    private View mConvertView;
    private Context mContext;
    protected int mLayoutId;

    public BasisLvGvViewHolder(Context context, View itemView, ViewGroup parent, int position) {
        mContext = context;
        mConvertView = itemView;
        mPosition = position;
        mViews = new SparseArray<View>();
        mConvertView.setTag(this);
    }


    public static BasisLvGvViewHolder get(Context context, View convertView,
                                          ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                    false);
            BasisLvGvViewHolder holder = new BasisLvGvViewHolder(context, itemView, parent, position);
            holder.mLayoutId = layoutId;
            return holder;
        } else {
            BasisLvGvViewHolder holder = (BasisLvGvViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }
    }


    /**
     * 通过viewId获取控件
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    public int getLayoutId() {
        return mLayoutId;
    }

    public void updatePosition(int position) {
        mPosition = position;
    }

    public int getItemPosition() {
        return mPosition;
    }


    /****以下为辅助方法*****/

    public ImageView getImageView(int viewId) {
        ImageView view = getView(viewId);
        return view;
    }

    public BasisLvGvViewHolder setTvText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text == null ? "" : text);
        return this;
    }

    public BasisLvGvViewHolder setTvTextListener(int viewId, String text, View.OnClickListener listener) {
        TextView tv = getView(viewId);
        tv.setText(text == null ? "" : text);
        tv.setOnClickListener(listener);
        return this;
    }

    public BasisLvGvViewHolder setEtText(int viewId, String text) {
        EditText et = getView(viewId);
        et.setText(text == null ? "" : text);
        return this;
    }

    public BasisLvGvViewHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public BasisLvGvViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public BasisLvGvViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public BasisLvGvViewHolder setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public BasisLvGvViewHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public BasisLvGvViewHolder setTvColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public BasisLvGvViewHolder setEtColor(int viewId, int textColor) {
        EditText view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public BasisLvGvViewHolder setTvColorRes(int viewId, int textColorRes) {
        TextView view = getView(viewId);
        view.setTextColor(mContext.getResources().getColor(textColorRes));
        return this;
    }

    public BasisLvGvViewHolder setEtColorRes(int viewId, int textColorRes) {
        EditText view = getView(viewId);
        view.setTextColor(mContext.getResources().getColor(textColorRes));
        return this;
    }

    @SuppressLint("NewApi")
    public BasisLvGvViewHolder setAlpha(int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        } else {
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }

    public BasisLvGvViewHolder setVisible(int... viewId) {
        for (int i = 0; i < viewId.length; i++) {
            View view = getView(viewId[i]);
            view.setVisibility(View.VISIBLE);
        }
        return this;
    }

    public BasisLvGvViewHolder setGone(int... viewId) {
        for (int i = 0; i < viewId.length; i++) {
            View view = getView(viewId[i]);
            view.setVisibility(View.GONE);
        }
        return this;
    }

    public BasisLvGvViewHolder setInVisible(int... viewId) {
        for (int i = 0; i < viewId.length; i++) {
            View view = getView(viewId[i]);
            view.setVisibility(View.INVISIBLE);
        }
        return this;
    }

    public BasisLvGvViewHolder linkify(int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public BasisLvGvViewHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public BasisLvGvViewHolder setProgress(int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    public BasisLvGvViewHolder setProgress(int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public BasisLvGvViewHolder setMax(int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }

    public BasisLvGvViewHolder setRating(int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    public BasisLvGvViewHolder setRating(int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public BasisLvGvViewHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public BasisLvGvViewHolder setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public BasisLvGvViewHolder setChecked(int viewId, boolean checked) {
        Checkable view = (Checkable) getView(viewId);
        view.setChecked(checked);
        return this;
    }

    /**
     * 关于事件的
     */
    public BasisLvGvViewHolder setOnClickListener(int viewId,
                                                  View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public BasisLvGvViewHolder setOnTouchListener(int viewId,
                                                  View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public BasisLvGvViewHolder setOnLongClickListener(int viewId,
                                                      View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }


}
