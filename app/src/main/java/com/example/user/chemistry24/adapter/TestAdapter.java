package com.example.user.chemistry24.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.user.chemistry24.TestDisplayActivity;
import com.example.user.chemistry24.TestFragment;

public class TestAdapter extends FragmentStatePagerAdapter {

    public static final int NUM_PAGES = 40;

    public TestAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return TestFragment.create(position,TestDisplayActivity.checkAnswer);
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
