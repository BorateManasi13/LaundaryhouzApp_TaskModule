package com.example.testapp5.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.testapp5.Fragments.KgFragment;
import com.example.testapp5.Fragments.PieceFragment;

public class BookingPagerAdapter extends FragmentStatePagerAdapter
{
    int tabCount;

    String mtoken = "";
    Context context;

    //constructor to the class
    public BookingPagerAdapter(FragmentManager fm, int behavior, int tabCount)
    {
        super(fm, behavior);
        this.tabCount = tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        Fragment fragment = null;
        switch (position)
        {
            case 0:
                fragment = new KgFragment();
                break;
            case 1:
                fragment = new PieceFragment();
                break;
            default:
                return null;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

