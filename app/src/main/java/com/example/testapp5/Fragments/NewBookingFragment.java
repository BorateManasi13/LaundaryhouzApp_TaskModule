package com.example.testapp5.Fragments;

import android.app.ProgressDialog;
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

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.testapp5.Adapters.BookingPagerAdapter;
import com.example.testapp5.R;
import com.example.testapp5.URL.Config;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NewBookingFragment extends Fragment
{
    ViewPager viewPager;
    TabLayout tabLayout;
    String mtoken = "";

    String TAG = "NewBookingFragment";

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    ProgressDialog progressDialog;
    int flag = 0;
    String order_id = "", status = "", newOrder = "";

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

        //GetStatusOfOrder(Config.mtoken);

        tabLayout = view.findViewById(R.id.sliding_tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Kg"));
        tabLayout.addTab(tabLayout.newTab().setText("Piece"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = view.findViewById(R.id.viewpager);

        //String tag = makeFragmentName(viewPager.getId(), getItemId(position));
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

    public void GetStatusOfOrder(String mtoken)
    {
        Log.d("TAG","url = " + Config.BASE_URL + Config.URL_GET_STATUS_OF_ORDER);
        progressDialog = ProgressDialog.show(getActivity(),"Please wait","Loading..");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.BASE_URL + Config.URL_GET_STATUS_OF_ORDER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Log.d(TAG, "response = " + response);
                progressDialog.dismiss();

                try
                {
                    JSONObject object = new JSONObject(response);
                    status = object.getString("status");
                    order_id = object.getString("order_id");

                    Log.d(TAG,"status = " + status);
                    Log.d(TAG,"order_id = " + order_id);

                    editor.putString("order_id",order_id);
                    editor.commit();
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG","error = " + error);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("mtoken",mtoken);
                params.put("order_id",newOrder);
                return params;
            }
        };
        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
        stringRequest.setTag("TAG");
    }
}
