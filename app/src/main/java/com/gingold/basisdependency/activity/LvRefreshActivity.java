package com.gingold.basisdependency.activity;

import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;

import com.gingold.basisdependency.Base.BaseActivity;
import com.gingold.basisdependency.R;
import com.gingold.basislibrary.utils.BasisLogUtils;
import com.gingold.basislibrary.view.LvRefresh.BasisListView;

import java.util.ArrayList;


public class LvRefreshActivity extends BaseActivity implements BasisListView.BasisLvRefreshListener {
    private BasisListView mListView;
    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> items = new ArrayList<String>();
    private Handler mHandler;
    private int start = 0;
    private static int refreshCnt = 0;

    @Override
    public void setupViewLayout() {
        setContentView(R.layout.activity_lvrefresh);
        initTitle("LvRefresh", "");
        mHandler = new Handler();
    }

    @Override
    public void initView() {
        mListView = (BasisListView) findViewById(R.id.xListView);
    }

    @Override
    public void listener() {
        mListView.setBasisLvRefreshListener(this);
    }

    @Override
    public void logicDispose() {
        geneItems();
        mListView.setPullLoadEnable(true, true);
        mAdapter = new ArrayAdapter<String>(this, R.layout.list_item, items);
        mListView.setAdapter(mAdapter);
//		mListView.setPullLoadEnable(false);
//		mListView.setPullRefreshEnable(false);
    }

    private void geneItems() {
        for (int i = 0; i != 20; ++i) {
            items.add("refresh cnt " + (++start));
        }
    }

    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime("刚刚");
        BasisLogUtils.e("refresh");
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                start = ++refreshCnt;
                items.clear();
                geneItems();
                // mAdapter.notifyDataSetChanged();
                mAdapter = new ArrayAdapter<String>(LvRefreshActivity.this, R.layout.list_item, items);
                mListView.setAdapter(mAdapter);
                onLoad();
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                geneItems();
                mAdapter.notifyDataSetChanged();
                onLoad();
            }
        }, 2000);
    }

    @Override
    public void onClick(View view) {

    }
}