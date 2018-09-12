package com.example.clouds.catalogmovie.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.clouds.catalogmovie.R;
import com.example.clouds.catalogmovie.views.fragment.OnPlayingClickFragment;
import com.example.clouds.catalogmovie.views.fragment.UpComeingClickFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public ViewPagerAdapter(Context context,FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new OnPlayingClickFragment();
            case 1:
                return new UpComeingClickFragment();
                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return context.getString(R.string.now_playing);
            case 1:
                return context.getString(R.string.upcomeing);
                default:
                    return null;

        }
    }
}
