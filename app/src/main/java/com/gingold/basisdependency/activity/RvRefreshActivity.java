package com.gingold.basisdependency.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.gingold.basisdependency.Base.BaseActivity;
import com.gingold.basisdependency.R;
import com.gingold.basisdependency.adapter.MultiRvAdapter;
import com.gingold.basisdependency.data.LVRVData;
import com.gingold.basislibrary.adapter.rv.BasisRvEmptyWrapper;
import com.gingold.basislibrary.adapter.rv.BasisRvHeaderAndFooterWrapper;
import com.gingold.basislibrary.adapter.rv.BasisRvLoadMoreWrapper;
import com.gingold.basislibrary.adapter.rv.BasisRvViewHolder;
import com.gingold.basislibrary.view.RvRefresh.YRecycleview;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 */

public class RvRefreshActivity extends BaseActivity {
    private YRecycleview rv_refresh;
    public MultiRvAdapter mAdapter;
    public ArrayList<LVRVData.LVBean> mData;
    public BasisRvEmptyWrapper mEmptyWrapper;
    public BasisRvLoadMoreWrapper mLoadMoreWrapper;
    public BasisRvHeaderAndFooterWrapper mHeaderAndFooterWrapper;

    @Override
    public void setupViewLayout() {
        setContentView(R.layout.activity_rvrefresh);
        initTitle("rv测试", "");
    }

    @Override
    public void initView() {
        rv_refresh = (YRecycleview) findViewById(R.id.rv_refresh);
        ;
    }

    @Override
    public void listener() {

    }

    @Override
    public void logicDispose() {
        mData = LVRVData.lvrvList;

        /**
         * 通用多类型条目适配器
         */
        mAdapter = new MultiRvAdapter(mActivity, mData) {
            @Override
            public void onItemClickListener(View v, BasisRvViewHolder viewHolder, LVRVData.LVBean data, int position) {
                super.onItemClickListener(v, viewHolder, data, position);
                toast(data.des + " ... " + position);
                if (position == mData.size() - 1) {
//                    toast("清空数据");
//                    mData.clear();
//                    notifyDataSetChanged();
                }
            }

            @Override
            public boolean onItemLongClickListener(View v, BasisRvViewHolder viewHolder, LVRVData.LVBean data, int position) {
                toast(data.status + " ... " + position);
                return true;
            }

            @Override
            public boolean isSpecific(int position) {//设置那些位置条目占据一行
                if (position == 5 || position == 8 || position == mData.size() - 1 || position == mData.size() - 2) {
                    return true;
                }
                return super.isSpecific(position);
            }
        };

//        mAdapter.onItemClickListener(new BasisRvMultiAdapter.onItemClickListener() {
//
//            @Override
//            public void onItemClick(View view, BasisRvViewHolder holder, int position) {
//                LVRVData.LVBean data = mData.get(position);
//                toast(data.des + " ... 2");
//                mData.remove(position);
//                mData.remove(position);
//                mData.remove(position);
////                mAdapter.notifyItemRemoved(position);
//                mAdapter.notifyItemRangeRemoved(position, 3);
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
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL);

        // 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
//        rv_refresh.setHasFixedSize(true);
        rv_refresh.setLayoutManager(linearLayoutManager);
        rv_refresh.setLayoutManager(gridLayoutManager);
//        rv_refresh.setLayoutManager(staggeredGridLayoutManager);

        /**
         * 添加头布局和脚布局
         */
        mHeaderAndFooterWrapper = new BasisRvHeaderAndFooterWrapper(mAdapter);
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
         * 加载更多适配器(不建议使用)
         */
        mLoadMoreWrapper = new BasisRvLoadMoreWrapper(mHeaderAndFooterWrapper) {
            @Override
            public void onLoadMoreListener() {
                mData.add(new LVRVData.LVBean("加载更多", new Random().nextInt(3) + 1));
                notifyDataSetChanged();
            }
        };

        TextView loadMore = new TextView(mActivity);
        loadMore.setText("加载更多");
        mLoadMoreWrapper.setLoadMoreView(loadMore);

        /**
         * 空布局适配器(数据为空时,显示设置好的空布局)
         */
        mEmptyWrapper = new BasisRvEmptyWrapper(mAdapter);
        TextView emptyView = new TextView(mActivity);
        emptyView.setText("数据为空");
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("数据为空");
            }
        });
        mEmptyWrapper.setEmptyView(emptyView);

        rv_refresh.setAdapter(mAdapter);//设置适配器
//        rv_refresh.setAdapter(mHeaderAndFooterWrapper);//设置适配器
//        rv_refresh.setAdapter(mLoadMoreWrapper);//设置适配器
//        rv_refresh.setAdapter(mEmptyWrapper);//设置适配器

        rv_refresh.setRefreshAndLoadMoreListener(new YRecycleview.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                toast("下拉刷新");
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rv_refresh.setReFreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore() {
                toast("加载更多");
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rv_refresh.setloadMoreComplete();
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
