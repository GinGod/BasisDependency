package com.gingold.basisdependency.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.gingold.basisdependency.Base.BaseActivity;
import com.gingold.basisdependency.R;
import com.gingold.basislibrary.adapter.rv.BasisRvAdapter;
import com.gingold.basislibrary.adapter.rv.BasisRvMultiAdapter;
import com.gingold.basislibrary.adapter.rv.BasisRvViewHolder;
import com.gingold.basislibrary.view.RvRefresh.BasisRecyclerView;

import java.util.ArrayList;

/**
 * 1. 现象: recycler数据由两个变为一个, 或由一个变为零个, 适配器刷新时会造成ANR(异常)(已解决)
 * <p>
 * 分析: 当展示的item没有完全填满屏幕时, 删减一条数据后刷新会报如下异常
 * 异常: java.lang.IllegalArgumentException: Called removeDetachedView with a view which is not flagged as tmp detached.ViewHolder
 * <p>
 * 原因: 原理未知
 * 解决办法:
 * 1. 设置适配器的 notifyDataSetChanged 不可用, 使用BasisRecyclerView内部封装的 notifyDataSetChanged 方法
 * 2. BasisRecyclerView 延迟调用 onRefresh 和 OnLoadMore, 即等待刷新和加载更多布局的动画完成再调用
 * 3. 刷新或加载完成时, 先调用 notifyDataSetChanged 将数据刷新, 再延迟调用刷新和加载布局的完成动画
 */

public class BugRecycler2To1Activity extends BaseActivity {
    private TextView tv_bug_recycler_add;
    private TextView tv_bug_recycler_delete;
    private BasisRecyclerView rv_bug_recycler;
    private ArrayList<String> mDatas = new ArrayList<>();
    public BasisRvAdapter<String> mAdapter;

    @Override
    public void setupViewLayout() {
        setContentView(R.layout.activity_bug_recycler);
        initTitle("Bug_Recycler", "");
    }

    @Override
    public void initView() {
        tv_bug_recycler_add = getView(R.id.tv_bug_recycler_add);
        tv_bug_recycler_delete = getView(R.id.tv_bug_recycler_delete);
        rv_bug_recycler = getViewNoClickable(R.id.rv_bug_recycler);

        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_bug_recycler.setLayoutManager(manager);
    }

    @Override
    public void listener() {

    }

    @Override
    public void logicDispose() {
        initStrData();
        setAdapter();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_bug_recycler_delete:
                if (mDatas.size() > 0) {
                    mDatas.remove(0);
                } else {
                    initStrData();
                }
                rv_bug_recycler.notifyDataSetChanged();
//                toast("delete");
                break;
            case R.id.tv_bug_recycler_add:
                mDatas.add(0, "add");
                rv_bug_recycler.notifyDataSetChanged();
//                toast("add");
                break;
        }
    }

    private void initStrData() {
        mDatas.add("0");
        mDatas.add("1");
        mDatas.add("2");
        mDatas.add("3");
        mDatas.add("4");
        mDatas.add("5");
//        mDatas.add("6");
//        mDatas.add("7");
//        mDatas.add("8");
//        mDatas.add("9");
//        mDatas.add("10");
//        mDatas.add("11");
//        mDatas.add("12");
//        mDatas.add("13");
//        mDatas.add("14");
//        mDatas.add("15");
//        mDatas.add("16");
    }

    private void setAdapter() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter = new BasisRvAdapter<String>(mActivity, R.layout.item_textview, mDatas) {
                @Override
                public void initView(BasisRvViewHolder holder, String data, int position) {
                    holder.setTvText(R.id.tv_item_main, data);
                }

                @Override
                public void onItemClickListener(View v, BasisRvViewHolder viewHolder, String data, int position) {
                    super.onItemClickListener(v, viewHolder, data, position);
                    toast(data + "---" + position);
                }
            };

            mAdapter.setOnItemClickListener(new BasisRvMultiAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, BasisRvViewHolder holder, int position) {
                    toast(mDatas.get(position) + "..." + position);
                }

                @Override
                public boolean onItemLongClick(View view, BasisRvViewHolder holder, int position) {
                    return false;
                }
            });

            rv_bug_recycler.setAdapter(mAdapter);
            rv_bug_recycler.setLoadMoreEnabled(true);
            rv_bug_recycler.setReFreshEnabled(true);

            rv_bug_recycler.setRefreshAndLoadMoreListener(new BasisRecyclerView.OnRefreshAndLoadMoreListener() {
                @Override
                public void onRefresh() {

                    if (mDatas.size() > 0) {
                        mDatas.remove(0);
                    } else {
                        initStrData();
                    }
                    rv_bug_recycler.setReFreshComplete();
                    rv_bug_recycler.notifyDataSetChanged();
                }

                @Override
                public void onLoadMore() {
                    if (mDatas.size() < 20) {
                        mDatas.add(0, "loadmore");
                        rv_bug_recycler.setloadMoreComplete();
                    } else {
                        rv_bug_recycler.setNoMoreData(true);
                    }
                    rv_bug_recycler.notifyDataSetChanged();
                }
            });
        }
    }
}
