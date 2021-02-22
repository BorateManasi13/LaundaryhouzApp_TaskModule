package com.example.testapp5.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.testapp5.Adapters.BookingPagerAdapter;
import com.example.testapp5.R;
import com.google.android.material.tabs.TabLayout;

public class NewBookingFragment extends Fragment
{
    ViewPager viewPager;
    TabLayout tabLayout;
    String mtoken = "";

    String TAG = "NewBookingFragment";

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newbooking, container, false);

        getActivity().setTitle("New Booking");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        mtoken = sharedpreferences.getString("mtoken","");
        Log.d(TAG,"mtoken = " + mtoken);

        tabLayout = view.findViewById(R.id.sliding_tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Kg"));
        tabLayout.addTab(tabLayout.newTab().setText("Piece"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = view.findViewById(R.id.viewpager);

        //Creating our pager adapter
        BookingPagerAdapter adapter = new BookingPagerAdapter(getActivity().getSupportFragmentManager(),0,tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //Adding onTabSelectedListener to swipe views
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });

        return view;
    }
}
