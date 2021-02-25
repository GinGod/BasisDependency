package com.gingold.basisdependency.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * FragmentStatePagerAdapter
 */

public class MyFragmentStateAdapter extends FragmentStatePagerAdapter {
    ArrayList<Fragment> mList;

    public MyFragmentStateAdapter(FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }
}
