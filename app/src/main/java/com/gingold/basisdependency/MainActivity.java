package com.gingold.basisdependency;

import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import com.gingold.basisdependency.Base.BaseActivity;
import com.gingold.basisdependency.activity.BugRecycler2To1Activity;
import com.gingold.basisdependency.activity.DBActivity;
import com.gingold.basisdependency.activity.DialogActivity;
import com.gingold.basisdependency.activity.GlideActivity;
import com.gingold.basisdependency.activity.LvRefreshActivity;
import com.gingold.basisdependency.activity.MultiLvActivity;
import com.gingold.basisdependency.activity.MultiRvActivity;
import com.gingold.basisdependency.activity.OkHttpActivity;
import com.gingold.basisdependency.activity.OkHttpPicActivity;
import com.gingold.basisdependency.activity.PhotoActivity;
import com.gingold.basisdependency.activity.RvRefreshActivity;
import com.gingold.basisdependency.activity.SPActivity;
import com.gingold.basisdependency.activity.TestActivity;
import com.gingold.basisdependency.data.MainData;
import com.gingold.basisdependency.data.TestData;
import com.gingold.basislibrary.adapter.lvgv.BasisLvGvAdapter;
import com.gingold.basislibrary.adapter.lvgv.BasisLvGvViewHolder;
import com.gingold.basislibrary.utils.BasisLogUtils;

public class MainActivity extends BaseActivity {

    @Override
    public void setupViewLayout() {
        setContentView(R.layout.activity_main);
        initTitle("Basis测试", "");
        startService(new Intent(mActivity, TestService.class));
    }

    @Override
    public void initView() {
    }

    @Override
    public void listener() {

    }

    @Override
    public void logicDispose() {
        findListView(R.id.lv_main).setAdapter(new BasisLvGvAdapter<MainData.MainBean>(mActivity, R.layout.item_textview, TestData.mainList) {
            @Override
            protected void initView(final BasisLvGvViewHolder basisViewHolder, final MainData.MainBean data, int position) {
                basisViewHolder.setTvTextListener(R.id.tv_item_main, data.des, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int flag = Paint.DITHER_FLAG;
                        toast(data.des);
                        switch (data.des) {
                            case MainData.TEST:
                                startActivity(TestActivity.class);
                                break;
                            case MainData.SP:
                                startActivity(SPActivity.class);
                                break;
                            case MainData.LOG:
                                BasisLogUtils.e(TAG, getStringById(R.string.long_string));
                                BasisLogUtils.e(getStringById(R.string.long_string));
                                break;
                            case MainData.LVADAPTER:
                                startActivity(MultiLvActivity.class);
                                break;
                            case MainData.RVADAPTER:
                                startActivity(MultiRvActivity.class);
                                break;
                            case MainData.RVREFRESH:
                                startActivity(RvRefreshActivity.class);
                                break;
                            case MainData.LVREFRESH:
                                startActivity(LvRefreshActivity.class);
                                break;
                            case MainData.OKHTTP:
                                startActivity(OkHttpActivity.class);
                                break;
                            case MainData.OKHTTPPIC:
                                startActivity(OkHttpPicActivity.class);
                                break;
                            case MainData.GLIDE:
                                startActivity(GlideActivity.class);
                                break;
                            case MainData.PHOTO:
                                startActivity(PhotoActivity.class);
                                break;
                            case MainData.DB:
                                startActivity(DBActivity.class);
                                break;
                            case MainData.DIALOG:
                                startActivity(DialogActivity.class);
                                break;
                            case MainData.BUGRECYCLER2TO1:
                                startActivity(BugRecycler2To1Activity.class);
                                break;
                        }

                        ((TextView) basisViewHolder.getView(R.id.tv_item_main)).getPaint().setFlags(flag);
                    }
                });

                int flag = Paint.UNDERLINE_TEXT_FLAG;
                switch (data.des) {
                    case MainData.TEST:
                        flag = Paint.ANTI_ALIAS_FLAG;
                        break;
                    case MainData.SP:
                        flag = Paint.FILTER_BITMAP_FLAG;
                        break;
                    case MainData.LOG:
                        flag = Paint.LINEAR_TEXT_FLAG;
                        break;
                    case MainData.LVADAPTER:
                        flag = Paint.SUBPIXEL_TEXT_FLAG;
                        break;
                    case MainData.RVADAPTER:
                        flag = Paint.UNDERLINE_TEXT_FLAG;
                        break;
                    case MainData.RVREFRESH:
                        flag = Paint.DEV_KERN_TEXT_FLAG;
                        break;
                    case MainData.LVREFRESH:
                        flag = Paint.DITHER_FLAG;
                        break;
                    case MainData.OKHTTP:
                        flag = Paint.EMBEDDED_BITMAP_TEXT_FLAG;
                        break;
                    case MainData.OKHTTPPIC:
                        flag = Paint.FAKE_BOLD_TEXT_FLAG;
                        break;
                    case MainData.GLIDE:
                        flag = Paint.STRIKE_THRU_TEXT_FLAG;
                        break;
                    case MainData.PHOTO:
                        flag = Paint.HINTING_OFF;
                        break;
                    case MainData.DB:
                        flag = Paint.HINTING_ON;
                        break;
                }

                ((TextView) basisViewHolder.getView(R.id.tv_item_main)).getPaint().setFlags(flag);
            }
        });

    }

    @Override
    public void onClick(View v) {

    }
}
