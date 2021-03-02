package com.gingold.basisdependency.activity;

import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gingold.basisdependency.Base.BaseActivity;
import com.gingold.basisdependency.R;
import com.gingold.basislibrary.utils.BasisCommonUtils;
import com.gingold.basislibrary.utils.BasisLogUtils;
import com.gingold.basislibrary.view.LvRefresh.BasisListView;
import com.gingold.basislibrary.view.LvRefresh.BasisOnItemClickListener;
import com.gingold.basislibrary.view.LvRefresh.BasisOnLongItemClickListener;

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
    public void initListener() {
        mListView.setBasisLvRefreshListener(this);
    }

    @Override
    public void initData() {
        geneItems();
        mAdapter = new ArrayAdapter<String>(this, R.layout.list_item, items);
        TextView view1 = new TextView(mActivity);
        view1.setText("头布局1");
        TextView view2 = new TextView(mActivity);
        view2.setText("头布局2");
        TextView view3 = new TextView(mActivity);
        view3.setText("脚布局1");
        TextView view4 = new TextView(mActivity);
        view4.setText("脚布局2");
        mListView.addHeaderView(view1);
        mListView.addHeaderView(view2);
        mListView.addFooterView(view3);
        mListView.addFooterView(view4);
        mListView.setAdapter(mAdapter);
//        mListView.setSelection(mAdapter.getCount());
//		mListView.setPullLoadEnable(false);
        mListView.setPullLoadEnable(true, true);
//		mListView.setPullRefreshEnable(false);
        mListView.setOnItemClickListener(new BasisOnItemClickListener(mListView) {
            @Override
            public void onBasisItemClick(AdapterView<?> parent, View view, int position, long id) {
                toast(position + " - " + items.get(position));
                switch (position) {
                    case 0:
                        BasisCommonUtils.callTel(mActivity, "114");
                        break;
                    case 1:
                        BasisCommonUtils.sendSMS(mActivity, "114", "111");
                        break;
                }
            }
        });

        mListView.setOnItemLongClickListener(new BasisOnLongItemClickListener(mListView) {
            @Override
            public void onBasisItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                toast(position + " --- " + items.get(position));
            }
        });
    }

    private void geneItems() {
        for (int i = 0; i != 252; ++i) {
            items.add("refresh cnt " + (++start));
        }
    }

    private void onLoad() {
        mListView.resetStatus();
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