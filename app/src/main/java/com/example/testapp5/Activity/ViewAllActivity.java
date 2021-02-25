package com.example.testapp5.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.testapp5.Adapters.ExtraViewAllDataAdapter;
import com.example.testapp5.Adapters.KgViewAllDataAdapter;
import com.example.testapp5.Adapters.PieceViewAllDataAdapter;
import com.example.testapp5.Model.ExtraViewAllData;
import com.example.testapp5.Model.KgViewAllData;
import com.example.testapp5.Model.PieceViewAllData;
import com.example.testapp5.R;
import com.example.testapp5.URL.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewAllActivity extends AppCompatActivity
{
    RecyclerView showCountRecycler,selectedKgRecycler,selectedPieceRecycler,selectedExtraRecycler;
    ProgressDialog progressDialog;

    KgViewAllDataAdapter kgViewAllDataAdapter;
    ArrayList<KgViewAllData> kgViewAllDataList;

    PieceViewAllDataAdapter pieceViewAllDataAdapter;
    ArrayList<PieceViewAllData> pieceViewAllDataList;

    ExtraViewAllDataAdapter extraViewAllDataAdapter;
    ArrayList<ExtraViewAllData> extraViewAllDataList;

    String TAG = "ViewAllActivity";

    LinearLayout linear1,linear2,linear3,linear4;
    Button btnShowError;

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    String mtoken = "", order_id = "", message = "", status = "", qty = "", washtypeid = "",
            clothe_name = "", img_url = "", wash_type = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewall);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        order_id = sharedpreferences.getString("order_id","");
        Log.d("TAG","order_id = " + order_id);

        linear1 = findViewById(R.id.linear1);
        linear2 = findViewById(R.id.linear2);
        linear3 = findViewById(R.id.linear3);
        linear4 = findViewById(R.id.linear4);

        btnShowError = findViewById(R.id.btnShowError);

        //showCountRecycler = findViewById(R.id.showCountRecycler);
        selectedKgRecycler = findViewById(R.id.selectedKgRecycler);
        selectedExtraRecycler = findViewById(R.id.selectedExtraRecycler);
        selectedPieceRecycler = findViewById(R.id.selectedPieceRecycler);

        //Kg type view all data recycler adapter
        selectedKgRecycler.setHasFixedSize(true);
        selectedKgRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        kgViewAllDataList=new ArrayList<>();

        //Piece type view all data recycler adapter
        selectedPieceRecycler.setHasFixedSize(true);
        selectedPieceRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        pieceViewAllDataList = new ArrayList<>();

        //Extra Product view all data recycler adapter
        selectedExtraRecycler.setHasFixedSize(true);
        selectedExtraRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        extraViewAllDataList = new ArrayList<>();

        ViewAllData(Config.mtoken,order_id);
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

                    if (status.equals("1"))
                    {
                        JSONObject objectData = jsonObject.getJSONObject("data");
                        for (int i = 0; i < objectData.length() ; i++)
                        {
                            JSONArray jsonArray = objectData.getJSONArray("kg_data");
                            for (int i1 = 0; i1 < jsonArray.length(); i1++)
                            {
                                JSONObject kgObject = jsonArray.getJSONObject(i1);
                                qty = kgObject.getString("qty");
                                clothe_name = kgObject.getString("clothe_name");
                                img_url = kgObject.getString("img_url");
                                wash_type = kgObject.getString("wash_type");
                                washtypeid = kgObject.getString("washtypeid");

                                KgViewAllData kgViewAllData = new KgViewAllData();
                                kgViewAllData.setClothe_name(clothe_name);
                                kgViewAllData.setQty(qty);
                                kgViewAllData.setWashtypeid(washtypeid);
                                kgViewAllData.setImg_url(img_url);
                                kgViewAllData.setWash_type(wash_type);

                                kgViewAllDataList.add(kgViewAllData);
                            }

                            JSONArray jsonArray1 = objectData.getJSONArray("piece_data");
                            for (int i2 = 0; i2 < jsonArray1.length() ; i2++)
                            {
                                JSONObject pieceObject = jsonArray1.getJSONObject(i2);
                                PieceViewAllData pieceViewAllData = new PieceViewAllData();
                                pieceViewAllData.setQty(pieceObject.getString("qty"));
                                pieceViewAllData.setRate(pieceObject.getString("rate"));
                                pieceViewAllData.setClothe_name(pieceObject.getString("clothe_name"));
                                pieceViewAllData.setImg_url(pieceObject.getString("img_url"));
                                pieceViewAllData.setWash_type(pieceObject.getString("wash_type"));
                                pieceViewAllData.setWashtypeid(pieceObject.getString("washtypeid"));

                                pieceViewAllDataList.add(pieceViewAllData);
                            }

                            JSONArray jsonArray2 = objectData.getJSONArray("extra_prd_data");
                            for(int i3 = 0 ; i3 < jsonArray2.length() ; i3++)
                            {
                                JSONObject extraObject = jsonArray2.getJSONObject(i3);
                                ExtraViewAllData extraViewAllData = new ExtraViewAllData();
                                extraViewAllData.setProduct_name(extraObject.getString("product_name"));
                                extraViewAllData.setPrice(extraObject.getString("price"));

                                extraViewAllDataList.add(extraViewAllData);
                            }
                        }
                        kgViewAllDataAdapter = new KgViewAllDataAdapter(kgViewAllDataList,ViewAllActivity.this);
                        selectedKgRecycler.setAdapter(kgViewAllDataAdapter);

                        pieceViewAllDataAdapter = new PieceViewAllDataAdapter(pieceViewAllDataList,ViewAllActivity.this);
                        selectedPieceRecycler.setAdapter(pieceViewAllDataAdapter);

                        extraViewAllDataAdapter = new ExtraViewAllDataAdapter(extraViewAllDataList,ViewAllActivity.this);
                        selectedExtraRecycler.setAdapter(extraViewAllDataAdapter);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

                        linear1.setVisibility(View.VISIBLE);
                        btnShowError.setText(message);

                        linear2.setVisibility(View.GONE);
                        linear3.setVisibility(View.GONE);
                        linear4.setVisibility(View.GONE);
                    }
                }
                catch (JSONException ex)
                {
                    ex.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"error = " + error);
                //progressDialog.dismiss();
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
