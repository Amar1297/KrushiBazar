package com.example.myapplication.ui.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position){
            case 0:
                fragment=new Fragment_Main();
                break;
            case 1:
                fragment=new Fragment1();
        }
        return fragment;
    }

//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        switch (position) {
//            case 0:
//                return "Customer Page......";
//            case 1:
//                return "Farmar Login....";
//            case 2:
//                return "SECTION 3";
//        }
//        return null;
//    }



    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}