package com.example.testapp5.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.testapp5.R;
import com.example.testapp5.URL.Config;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ViewAllActivity extends AppCompatActivity
{
    RecyclerView showCountRecycler,selectedKgRecycler,selectedPieceRecycler,selectedExtraRecycler;
    ProgressDialog progressDialog;

    String TAG = "ViewAllActivity";

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    String mtoken = "", order_id = "", message = "", status = "", qty = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewall);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        //mtoken = Config.mtoken;

        order_id = sharedpreferences.getString("order_id","");
        Log.d("TAG","order_id = " + order_id);

        ViewAllData(Config.mtoken,order_id);

        showCountRecycler = findViewById(R.id.showCountRecycler);
        selectedKgRecycler = findViewById(R.id.selectedKgRecycler);
        selectedExtraRecycler = findViewById(R.id.selectedExtraRecycler);
        selectedPieceRecycler = findViewById(R.id.selectedPieceRecycler);
    }

    public void ViewAllData(String mtoken, String order_id)
    {
        Log.d("TAG","url = " + Config.BASE_URL + Config.URL_VIEW_ALL);
        //progressDialog = ProgressDialog.show(ViewAllActivity.this,"Please wait","Loading..");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.BASE_URL + Config.URL_VIEW_ALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Log.d(TAG, "response = " + response);
                //progressDialog.dismiss();

                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    status = jsonObject.getString("status");
                    message = jsonObject.getString("message");

                    /*JSONObject objectData = new JSONObject("data");
                    for (int i = 0; i < objectData.length() ; i++)
                    {
                        JSONArray jsonArray = objectData.getJSONArray("kg_data");
                        for (int i1 = 0; i1 < jsonArray.length(); i1++)
                        {
                            JSONObject kgObject = jsonArray.getJSONObject(i1);
                            qty = kgObject.getString("qty");
                            Log.d(TAG,"qty = " + qty);
                        }
                    }*/
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"error = " + error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("mtoken",mtoken);
                params.put("order_id",order_id);
                return params;
            }
        };
        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);
        stringRequest.setTag("TAG");
    }
}
