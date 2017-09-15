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
import com.gingold.basislibrary.utils.BasisLogUtils;
import com.gingold.basislibrary.view.RvRefresh.BasisRecyclerView;

import java.util.ArrayList;

/**
 *
 */

public class RvRefreshActivity extends BaseActivity {
    private BasisRecyclerView rv_refresh;
    public MultiRvAdapter mMultiRvAdapter;
    public ArrayList<LVRVData.LVBean> mData;
    public BasisRvEmptyWrapper mEmptyWrapper;
    public BasisRvHeaderAndFooterWrapper mHeaderAndFooterWrapper;
    public RecyclerView.Adapter<ViewHolder> mAdapter;
    public BasisRvHeaderAndFooterWrapper mHeaderAndFooterWrapperRepeat;
    private ArrayList<TextView> mViews = new ArrayList<>();

    @Override
    public void setupViewLayout() {
        setContentView(R.layout.activity_rvrefresh);
        initTitle("rv测试", "");
    }

    @Override
    public void initView() {
        rv_refresh = (BasisRecyclerView) findViewById(R.id.rv_refresh);
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

        for (int i = 0; i < 12; i++) {
            TextView view = new TextView(mActivity);
            view.setText("布局" + i);
            mViews.add(view);
        }

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
//                toast("清空数据");
//                mData.clear();
                rv_refresh.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClickListener(View v, BasisRvViewHolder viewHolder, LVRVData.LVBean data, int position) {
                toast(data.status + " ... " + position);
                return true;
            }

            @Override
            public boolean isSpecific(int position) {//设置那些位置条目占据一行
                if (position == 8 || position == 5 || position == mData.size() - 3 || position == mData.size() - 6) {
                    BasisLogUtils.e("position: " + position);
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
//        rv_refresh.setHasFixedSize(true);
        rv_refresh.setLayoutManager(linearLayoutManager);
//        rv_refresh.setLayoutManager(gridLayoutManager);
//        rv_refresh.setLayoutManager(staggeredGridLayoutManager);

        /**
         * 添加头布局和脚布局
         */
        mHeaderAndFooterWrapper = new BasisRvHeaderAndFooterWrapper(mMultiRvAdapter);

        mHeaderAndFooterWrapper.addHeaderView(mViews.get(0));
        mHeaderAndFooterWrapper.addHeaderView(mViews.get(1));

        mHeaderAndFooterWrapper.addFootView(mViews.get(2));
        mHeaderAndFooterWrapper.addFootView(mViews.get(3));

        mHeaderAndFooterWrapperRepeat = new BasisRvHeaderAndFooterWrapper(mHeaderAndFooterWrapper);

        mHeaderAndFooterWrapperRepeat.addHeaderView(mViews.get(4));
        mHeaderAndFooterWrapperRepeat.addHeaderView(mViews.get(5));

        mHeaderAndFooterWrapperRepeat.addFootView(mViews.get(6));
        mHeaderAndFooterWrapperRepeat.addFootView(mViews.get(7));

        /**
         * 空布局适配器(数据为空时,显示设置好的空布局)
         */
        mEmptyWrapper = new BasisRvEmptyWrapper(mMultiRvAdapter);
        TextView emptyView = new TextView(mActivity);
        emptyView.setText("数据为空");
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("数据为空");
            }
        });
        mEmptyWrapper.setEmptyView(emptyView);

        mViews.get(8).setText("刷新头布局");
        rv_refresh.addHeadView(mViews.get(8));

        mViews.get(9).setText("刷新脚布局");
        mViews.get(9).setTextSize(52);
        rv_refresh.addFootView(mViews.get(9));

        mViews.get(10).setText("数据为空");
        rv_refresh.setEmptyView(mViews.get(10));

        rv_refresh.setLoadMoreEnabled(true);

//        rv_refresh.setAdapter(mMultiRvAdapter);//设置适配器
//        rv_refresh.setAdapter(mHeaderAndFooterWrapper);//设置适配器
        rv_refresh.setAdapter(mHeaderAndFooterWrapperRepeat);//设置适配器
//        rv_refresh.setAdapter(mEmptyWrapper);//设置适配器

        rv_refresh.setRefreshAndLoadMoreListener(new BasisRecyclerView.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                toast("下拉刷新");
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mData.add(0, new LVRVData.LVBean("刷新数据", 1));
                        rv_refresh.resetStatus();
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore() {
                toast("加载更多");
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mData.add(new LVRVData.LVBean("加载数据", 1));
//                        for (int i = 0; i < 10; i++) {
//                            mData.add(new LVRVData.LVBean("加载数据", 2));
//                            mData.add(new LVRVData.LVBean("加载数据", 3));
//                        }
                        rv_refresh.resetStatus();
//                        rv_refresh.setNoMoreData(true);
                    }
                }, 2000);
            }
        });

    }

    @Override
    public void onClick(View v) {

    }
}
