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
import com.example.testapp5.Adapters.KgButtonAdapter;
import com.example.testapp5.Model.ExtraProduct;
import com.example.testapp5.Model.KgButton;
import com.example.testapp5.R;
import com.example.testapp5.URL.Config;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KgFragment extends Fragment implements View.OnClickListener
{
    ArrayList<KgButton> kgButtonArrayList = new ArrayList<>();
    ArrayList<ExtraProduct> extraProductArrayList = new ArrayList<>();
    RecyclerView kgRecycler,extraProductRecycler;
    KgButtonAdapter kgButtonAdapter;

    ProgressDialog progressDialog;
    String name = "", charges = "", chargeid = "", short_name = "",status="",message="";
    Button btnSelectGarments;
    String ProductPrice = "", ProductName = "",order_id = "";

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_kg, container, false);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        order_id = sharedpreferences.getString("order_id","");
        Log.d("TAG","order_id = " + order_id);

        order_id="ORD21488";

        /*ProductName = getArguments().getString("ProductName");
        Log.d("TAG","ProductName = " + ProductName);
        ProductPrice = getArguments().getString("ProductPrice");
        Log.d("TAG","ProductPrice = " + ProductPrice);

        ExtraProduct extraProduct = new ExtraProduct();
        extraProduct.setProductName(ProductName);
        extraProduct.setProductPrice(ProductPrice);
        extraProductArrayList.add(extraProduct);*/

        btnSelectGarments = view.findViewById(R.id.btnSelectGarments);
        btnSelectGarments.setOnClickListener(this);

        kgRecycler = view.findViewById(R.id.kgRecycler);
        kgRecycler.setHasFixedSize(true);
        kgRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        kgButtonArrayList=new ArrayList<>();

        getKgData(Config.mtoken);

        extraProductRecycler = view.findViewById(R.id.extraProductRecycler);

        return view;
    }

    public void getKgData(final String mtoken)
    {
        Log.d("TAG","url = " + Config.BASE_URL + Config.URL_ALL_KG_CHARGES);
        progressDialog = ProgressDialog.show(getActivity(),"Please wait","Loading..");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.BASE_URL + Config.URL_ALL_KG_CHARGES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Log.d("TAG", "response = " + response);
                progressDialog.dismiss();

                try
                {
                    JSONObject object = new JSONObject(response);
                    status = object.getString("status");
                    message = object.getString("message");

                    JSONArray jsonArray = object.getJSONArray("data");
                    Log.d("TAG","length = " + jsonArray.length());
                    for (int i = 0 ; i < jsonArray.length() ; i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        chargeid = jsonObject.getString("chargeid");
                        Log.d("TAG","chargeid = " + chargeid);

                        name = jsonObject.getString("name");
                        Log.d("TAG","name = " + name);

                        short_name = jsonObject.getString("short_name");
                        Log.d("TAG","short_name = " + short_name);

                        charges = jsonObject.getString("charges");
                        Log.d("TAG","charges = " + charges);

                        KgButton button = new KgButton();
                        button.setName(name);

                        kgButtonArrayList.add(button);
                    }

                    kgButtonAdapter=new KgButtonAdapter(kgButtonArrayList,getActivity());
                    kgRecycler.setAdapter(kgButtonAdapter);
                    kgButtonAdapter.notifyDataSetChanged();
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
                        Log.d("TAG","error = " + error);
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
           Intent i = new Intent(getActivity(), SelectKgGarmentsActivity.class);
           editor.putString("order_id",order_id);
           editor.commit();
           startActivity(i);
       }
    }
}
