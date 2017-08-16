package com.gingold.basisdependency.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gingold.basisdependency.Base.BaseActivity;
import com.gingold.basisdependency.R;
import com.gingold.basisdependency.adapter.MultiRvAdapter;
import com.gingold.basisdependency.data.LVRVData;
import com.gingold.basislibrary.adapter.rv.BasisRvEmptyWrapper;
import com.gingold.basislibrary.adapter.rv.BasisRvHeaderAndFooterWrapper;
import com.gingold.basislibrary.adapter.rv.BasisRvViewHolder;

import java.util.ArrayList;

/**
 *
 */

public class MultiRvActivity extends BaseActivity {
    private RecyclerView rv_multirv;
    public MultiRvAdapter mMultiRvAdapter;
    public ArrayList<LVRVData.LVBean> mData;
    public BasisRvEmptyWrapper mEmptyWrapper;
    public BasisRvHeaderAndFooterWrapper mHeaderAndFooterWrapper, mHeaderAndFooterWrapperRepeat;
    public RecyclerView.Adapter mAdapter;

    @Override
    public void setupViewLayout() {
        setContentView(R.layout.activity_multirv);
        initTitle("rv测试", "");
    }

    @Override
    public void initView() {
        rv_multirv = findRecyclerView(R.id.rv_multirv);
    }

    @Override
    public void listener() {

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = (TextView) itemView;
        }
    }

    @Override
    public void logicDispose() {
        mData = LVRVData.getData();

        /**
         * 一般adapter
         */
        mAdapter = new RecyclerView.Adapter<ViewHolder>() {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                TextView view = new TextView(mActivity);
                ViewHolder holder = new ViewHolder(view);
                return holder;
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                holder.view.setText(mData.get(position).des);
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        };

        /**
         * 通用多类型条目适配器
         */
        mMultiRvAdapter = new MultiRvAdapter(mActivity, mData) {
            @Override
            public void onItemClickListener(View v, BasisRvViewHolder viewHolder, LVRVData.LVBean data, int position) {
                super.onItemClickListener(v, viewHolder, data, position);
                toast(data.des + " ... " + position);
                if (position == mData.size() - 1) {
                    toast("清空数据");
                    mData.clear();
                    mHeaderAndFooterWrapperRepeat.notifyDataSetChanged();
                }
            }

            @Override
            public boolean onItemLongClickListener(View v, BasisRvViewHolder viewHolder, LVRVData.LVBean data, int position) {
                toast(data.status + " ... " + position);
                return true;
            }

            @Override
            public boolean isSpecific(int position) {//设置那些位置条目占据一行
//                BasisLogUtils.e("position: " + position);
                if (position == 5/* || position == 9  || position == mData.size() - 1 || position == mData.size() - 2*/) {
                    return true;
                }
                return super.isSpecific(position);
            }
        };

//        mMultiRvAdapter.onItemClickListener(new BasisRvMultiAdapter.onItemClickListener() {
//
//            @Override
//            public void onItemClick(View view, BasisRvViewHolder holder, int position) {
//                LVRVData.LVBean data = mData.get(position);
//                toast(data.des + " ... 2");
//                mData.remove(position);
//                mData.remove(position);
//                mData.remove(position);
////                mMultiRvAdapter.notifyItemRemoved(position);
//                mMultiRvAdapter.notifyItemRangeRemoved(position, 3);
//            }
//
//            @Override
//            public boolean onItemLongClick(View view, BasisRvViewHolder holder, int position) {
//                LVRVData.LVBean data = mData.get(position);
//                toast(data.status + " ... 2");
//                return false;
//            }
//        });

        /**
         * 布局管理器
         */
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity) /*{
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                // 这里要复写一下，因为默认宽高都是wrap_content
                // 这个不复写，你点击的背景色就只充满你的内容
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        }*/;
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 3, GridLayoutManager.VERTICAL, false);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);

        // 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
//        rv_multirv.setHasFixedSize(true);
        rv_multirv.setLayoutManager(linearLayoutManager);
        rv_multirv.setLayoutManager(gridLayoutManager);
        rv_multirv.setLayoutManager(staggeredGridLayoutManager);

        /**
         * 添加头布局和脚布局
         */
        mHeaderAndFooterWrapper = new BasisRvHeaderAndFooterWrapper(mMultiRvAdapter);
        TextView headView1 = new TextView(mActivity);
        headView1.setText("头布局1");
        mHeaderAndFooterWrapper.addHeaderView(headView1);

        TextView headView2 = new TextView(mActivity);
        headView2.setText("头布局2");
        mHeaderAndFooterWrapper.addHeaderView(headView2);

        TextView footView1 = new TextView(mActivity);
        footView1.setText("脚布局1");
        mHeaderAndFooterWrapper.addFootView(footView1);

        TextView footView2 = new TextView(mActivity);
        footView2.setText("脚布局2");
        mHeaderAndFooterWrapper.addFootView(footView2);

        /**
         * 空布局适配器(数据为空时,显示设置好的空布局)
         */
        mEmptyWrapper = new BasisRvEmptyWrapper(mHeaderAndFooterWrapper);
        TextView emptyView = new TextView(mActivity);
        emptyView.setText("数据为空");
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("数据为空");
            }
        });
        mEmptyWrapper.setEmptyView(emptyView);

        mHeaderAndFooterWrapperRepeat = new BasisRvHeaderAndFooterWrapper(mEmptyWrapper);
        TextView headView3 = new TextView(mActivity);
        headView3.setText("头布局3");
        mHeaderAndFooterWrapperRepeat.addHeaderView(headView3);

        TextView headView4 = new TextView(mActivity);
        headView4.setText("头布局4");
        mHeaderAndFooterWrapperRepeat.addHeaderView(headView4);

        TextView footView3 = new TextView(mActivity);
        footView3.setText("脚布局3");
        mHeaderAndFooterWrapperRepeat.addFootView(footView3);

        TextView footView4 = new TextView(mActivity);
        footView4.setText("脚布局4");
        mHeaderAndFooterWrapperRepeat.addFootView(footView4);

//        rv_multirv.setAdapter(mMultiRvAdapter);//设置适配器
//        rv_multirv.setAdapter(mHeaderAndFooterWrapper);//设置适配器
//        rv_multirv.setAdapter(mHeaderAndFooterWrapperRepeat);//设置适配器
        rv_multirv.setAdapter(mHeaderAndFooterWrapperRepeat);//设置适配器
    }

    @Override
    public void onClick(View v) {

    }
}
