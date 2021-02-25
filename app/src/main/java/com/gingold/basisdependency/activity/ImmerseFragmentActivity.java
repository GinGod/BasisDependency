package com.gingold.basisdependency.activity;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.gingold.basisdependency.Base.BaseActivity;
import com.gingold.basisdependency.R;
import com.gingold.basisdependency.fragment.Test1Fragment;
import com.gingold.basisdependency.fragment.Test2Fragment;
import com.gingold.basisdependency.fragment.Test3Fragment;
import com.gingold.basisdependency.fragment.Test4Fragment;
import com.gingold.basisdependency.fragment.Test5Fragment;
import com.gingold.basislibrary.utils.BasisImmerseUtils;

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
        BasisImmerseUtils.setImmerseLayout(mActivity);//设置沉浸式状态

//        BasisImmerseUtils.hideStatusBar(mActivity);//隐藏顶部状态栏
//        BasisImmerseUtils.hideNavigationBar(mActivity);//隐藏底部导航栏
//        BasisImmerseUtils.setTransparentWindowBar(mActivity);//设置顶部状态栏和底部导航栏透明, 并使主题布局占据顶部和底部位置
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

    /**
     * 初始化Fragment
     */
    private void initFragmentData() {
        mFragments.add(new Test1Fragment());
        mFragments.add(new Test2Fragment());
        mFragments.add(new Test3Fragment());
        mFragments.add(new Test4Fragment());
        mFragments.add(new Test5Fragment());
    }

    /**
     * 设置适配器
     */
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
         * 创建生命周期: onattach  oncreate  oncreateview  onactivitycreated  onstart  onresume
         * 销毁生命周期: onpause  onstop  ondestoryview  ondestory  ondetach
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
