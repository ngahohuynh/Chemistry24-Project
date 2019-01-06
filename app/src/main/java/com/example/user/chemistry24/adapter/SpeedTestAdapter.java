package com.example.user.chemistry24.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.user.chemistry24.SpeedTestDisplayActivity;
import com.example.user.chemistry24.SpeedTestFragment;

public class SpeedTestAdapter extends FragmentStatePagerAdapter {

    public static final int NUM_PAGES = 15;

    public SpeedTestAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return SpeedTestFragment.create(i,SpeedTestDisplayActivity.checkAnswer);
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
