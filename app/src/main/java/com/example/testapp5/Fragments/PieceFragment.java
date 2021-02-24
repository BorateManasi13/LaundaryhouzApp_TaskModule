package com.example.testapp5.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.testapp5.Activity.SelectKgGarmentsActivity;
import com.example.testapp5.Adapters.PieceButtonAdapter;
import com.example.testapp5.Model.PieceButtons;
import com.example.testapp5.R;
import com.example.testapp5.URL.Config;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PieceFragment extends Fragment implements View.OnClickListener
{
    ArrayList<PieceButtons> pieceButtonsArrayList = new ArrayList<>();
    PieceButtonAdapter pieceButtonAdapter;
    RecyclerView pieceRecycler;

    String TAG = "PieceFragment";

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    ProgressDialog progressDialog;
    String name = "", tbl_washtype_id = "", short_name = "",status="",message="", order_id = "";
    Button btnSelectGarments;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_piece, container, false);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        order_id = sharedpreferences.getString("order_id","");
        Log.d("TAG","order_id = " + order_id);

        order_id="ORD21488";

        btnSelectGarments = view.findViewById(R.id.btnSelectGarments);
        btnSelectGarments.setOnClickListener(this);

        pieceRecycler = view.findViewById(R.id.pieceRecycler);
        pieceRecycler.setHasFixedSize(true);
        pieceRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        pieceButtonsArrayList=new ArrayList<>();

        getPieceData(Config.mtoken);

        return view;
    }

    public void getPieceData(final String mtoken)
    {
        Log.d(TAG,"url = " + Config.BASE_URL + Config.URL_ALL_PIECE_CHARGES);
        progressDialog = ProgressDialog.show(getActivity(),"Please wait","Loading..");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.BASE_URL + Config.URL_ALL_PIECE_CHARGES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Log.d(TAG, "response = "  + response);
                progressDialog.dismiss();

                try
                {
                    JSONObject object = new JSONObject(response);
                    status = object.getString("status");
                    message = object.getString("message");

                    JSONArray jsonArray = object.getJSONArray("data");
                    Log.d(TAG,"length = " + jsonArray.length());
                    for (int i = 0 ; i < jsonArray.length() ; i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        tbl_washtype_id = jsonObject.getString("tbl_washtype_id");
                        Log.d(TAG,"tbl_washtype_id = " + tbl_washtype_id);

                        name = jsonObject.getString("name");
                        Log.d(TAG,"name = " + name);

                        short_name = jsonObject.getString("short_name");
                        Log.d(TAG,"short_name = " + short_name);

                        PieceButtons pieceButtons = new PieceButtons();
                        pieceButtons.setName(name);
                        pieceButtons.setTbl_washtype_id(tbl_washtype_id);
                        pieceButtons.setShort_name(short_name);

                        pieceButtonsArrayList.add(pieceButtons);
                    }

                    pieceButtonAdapter=new PieceButtonAdapter(pieceButtonsArrayList,getActivity());
                    pieceRecycler.setAdapter(pieceButtonAdapter);
                    pieceButtonAdapter.notifyDataSetChanged();
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.d(TAG,"error = " + error);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("mtoken",mtoken);
                return params;

            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);
        stringRequest.setTag("TAG");
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.btnSelectGarments)
        {
           /* Intent i = new Intent(getActivity(), SelectKgGarmentsActivity.class);
            startActivity(i);*/
        }
    }
}
