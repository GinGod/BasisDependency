package com.gingold.basisdependency.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.gingold.basisdependency.Base.BaseActivity;
import com.gingold.basisdependency.R;
import com.gingold.basisdependency.fragment.Test1Fragment;
import com.gingold.basisdependency.fragment.Test2Fragment;
import com.gingold.basisdependency.fragment.Test3Fragment;
import com.gingold.basisdependency.fragment.Test4Fragment;
import com.gingold.basisdependency.fragment.Test5Fragment;

import java.util.ArrayList;

/**
 * 实现沉浸式状态栏Fragment
 */

public class ImmerseFragmentActivity extends BaseActivity {
    private ViewPager vp_immersefragment;
    private FragmentManager mFragmentManager = getSupportFragmentManager();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    public FragmentPagerAdapter mFragmentPagerAdapter;
    public FragmentStatePagerAdapter mFragmentStatePagerAdapter;

    @Override
    public void setupViewLayout() {
        setContentView(R.layout.activity_immersefragment);
//        BasisImmerseUtils.hideStatusBar(mActivity);
//        BasisImmerseUtils.hideNavigationBar(mActivity);
//        BasisImmerseUtils.setTransparentWindowBar(mActivity);
    }

    @Override
    public void initView() {
        vp_immersefragment = getViewNoClickable(R.id.vp_immersefragment);
    }

    @Override
    public void listener() {
    }

    @Override
    public void logicDispose() {
        initFragmentData();
        setVPAdapter();
    }

    private void initFragmentData() {
        mFragments.add(new Test1Fragment());
        mFragments.add(new Test2Fragment());
        mFragments.add(new Test3Fragment());
        mFragments.add(new Test4Fragment());
        mFragments.add(new Test5Fragment());
    }

    private void setVPAdapter() {
        mFragmentPagerAdapter = new FragmentPagerAdapter(mFragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments == null ? 0 : mFragments.size();
            }
        };

        /**
         * onattach  oncreate  oncreateview  onactivitycreated  onstart  onresume
         * onpause  onstop  ondestoryview  ondestory  ondetach
         * 创建时会多执行 onattach oncreate
         * 销毁时会多执行 ondestory ondetach
         */
        mFragmentStatePagerAdapter = new FragmentStatePagerAdapter(mFragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments == null ? 0 : mFragments.size();
            }
        };

        vp_immersefragment.setAdapter(mFragmentStatePagerAdapter);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        BasisImmerseUtils.setOnWindowFocusChanged(mActivity, hasFocus);
    }


}
