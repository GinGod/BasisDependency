package com.gingold.basisdependency.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.gingold.basisdependency.R;
import com.gingold.basisdependency.bean.DBTable;
import com.gingold.basisdependency.bean.DBVersion;
import com.gingold.basislibrary.Base.BasisBaseActivity;
import com.gingold.basislibrary.adapter.lvgv.BasisLvGvAdapter;
import com.gingold.basislibrary.adapter.lvgv.BasisLvGvViewHolder;
import com.gingold.basislibrary.db.BasisDBUtils;

import java.util.ArrayList;

/**
 *
 */

public class DBActivity extends BasisBaseActivity {
    private ArrayList<DBTable> mDBTables = new ArrayList<>();
    public BasisLvGvAdapter<DBTable> mAdapter;
    private ListView lv_db;

    @Override
    public void setupViewLayout() {
        setContentView(R.layout.activity_db);
    }

    @Override
    public void initView() {
        lv_db = getViewNoClickable(R.id.lv_db);
    }

    @Override
    public void listener() {
        setOnClickListener(R.id.tv_db_save, R.id.tv_db_query, R.id.tv_db_delete, R.id.tv_db_deleteAll, R.id.tv_db_update, R.id.tv_db_queryName, R.id.tv_db_queryAll);
    }

    @Override
    public void logicDispose() {
        queryAll(null);
    }

    private void initAdapter() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter = new BasisLvGvAdapter<DBTable>(mActivity, R.layout.item_db, mDBTables) {
                @Override
                protected void initView(BasisLvGvViewHolder basisViewHolder, DBTable data, int position) {
                    basisViewHolder.setTvText(R.id.tv_item_db_name, data.name)
                            .setTvText(R.id.tv_item_db_age, data.age)
                            .setTvText(R.id.tv_item_db_sex, data.sex);
                }
            };
            lv_db.setAdapter(mAdapter);
        }

    }

    @Override
    public void onClick(View v) {
        DBTable dbTable = new DBTable();
        String name = getTvText(R.id.tv_db_name);
        String age = getTvText(R.id.tv_db_age);
        String sex = getTvText(R.id.tv_db_sex);
        if (!TextUtils.isEmpty(name)) {
            dbTable.name = name;
        }
        if (!TextUtils.isEmpty(name)) {
            dbTable.age = age;
        }
        if (!TextUtils.isEmpty(name)) {
            dbTable.sex = sex;
        }
        switch (v.getId()) {
            case R.id.tv_db_save:
                BasisDBUtils.init(mActivity, new DBVersion(), dbTable).save();
                queryAll(null);
                break;
            case R.id.tv_db_query:
                queryAll(null);
                break;
            case R.id.tv_db_delete:
                if (TextUtils.isEmpty(name)) {
                    toast("姓名为空");
                    return;
                }
                BasisDBUtils.init(mActivity, new DBVersion(), dbTable).delete("name = ?", new String[]{name});
                queryAll(null);
                break;
            case R.id.tv_db_deleteAll:
                BasisDBUtils.init(mActivity, new DBVersion(), dbTable).deleteAll();
                queryAll(null);
                break;
            case R.id.tv_db_update:
                if (TextUtils.isEmpty(name)) {
                    toast("姓名为空");
                    return;
                }
                BasisDBUtils.init(mActivity, new DBVersion(), dbTable).update("name = ?", new String[]{name});
                queryAll(null);
                break;
            case R.id.tv_db_queryAll:
                queryAll("_id desc");
                break;
            case R.id.tv_db_queryName:
                try {
                    ArrayList<DBTable> query = BasisDBUtils.init(mActivity, new DBVersion(), dbTable).querySelect("name = ?", new String[]{name});
                    mDBTables.clear();
                    mDBTables.addAll(query);
                    initAdapter();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void queryAll(String orderBy) {
        try {
            ArrayList<DBTable> query = BasisDBUtils.init(mActivity, new DBVersion(), new DBTable()).queryOrder(null, null, orderBy, null);
            mDBTables.clear();
            mDBTables.addAll(query);
            initAdapter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
