package com.example.testapp5.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
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
import com.example.testapp5.Adapters.KgCategoryGarmentsAdapter;
import com.example.testapp5.Adapters.SelectedClotheAdapter;
import com.example.testapp5.Interface.OnImageClickListener;
import com.example.testapp5.Interface.OnItemClick;
import com.example.testapp5.Model.CategoryGarments;
import com.example.testapp5.Model.ChildGarments;
import com.example.testapp5.Model.SelectedClothe;
import com.example.testapp5.R;
import com.example.testapp5.URL.Config;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.testapp5.URL.Config.BASE_URL;
import static com.example.testapp5.URL.Config.URL_ALL_CLOTH_CATEGORY_KG;

public class SelectKgGarmentsActivity extends AppCompatActivity implements OnImageClickListener, OnItemClick
{
    private ExpandableListView categoryGarmentsListView;
    private ArrayList<CategoryGarments> categoryGarmentsArrayList = new ArrayList<CategoryGarments>();
    //SearchView searchView;
    ExpandableListAdapter categoryGarmentsAdapter;

    ArrayList<SelectedClothe> listSelectedClothe = new ArrayList<>();
    RecyclerView selectedRecycler;
    SelectedClotheAdapter selectedClotheAdapter;
    String TAG = "SelectKgGarmentsActivity";

    String status="", message="", clothcatiname = "", clothcatiid = "", clothe_name = "", clothe_id = "",
            clothe_img = "",optionName="", order_id = "";

    CategoryGarments categoryGarments;
    ProgressDialog progressDialog;
    List<ChildGarments> childGarmentsList;
    int calculatedTotalQty;
    Button btnContinue;
    TextView txtOptionName;

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_garments);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        order_id = sharedpreferences.getString("order_id","");
        Log.d(TAG,"order_id = " + order_id);

        Intent i = getIntent();
        optionName = i.getStringExtra("name");
        Log.d(TAG,"name = " + optionName);

        btnContinue = findViewById(R.id.btnContinue);
        categoryGarmentsListView = (ExpandableListView) findViewById(R.id.list_category);

        txtOptionName = findViewById(R.id.txtOptionName);
        txtOptionName.setText(optionName);

        selectedRecycler = findViewById(R.id.selectedRecycler);
        selectedRecycler.setHasFixedSize(true);
        selectedRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        listSelectedClothe=new ArrayList<>();
        selectedClotheAdapter=new SelectedClotheAdapter(listSelectedClothe,this, SelectKgGarmentsActivity.this::onClick);

        //set data to expandable listview from volley
        GetAllCatiData();

        categoryGarmentsListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if ((previousGroup != -1) && (groupPosition != previousGroup)) {
                    categoryGarmentsListView.collapseGroup(previousGroup);
                }
                previousGroup = groupPosition;
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(getApplicationContext(),GarmentSelectionTypeActivity.class);
                Log.d(TAG, "calculatedTotalQty = " + calculatedTotalQty);
                i.putExtra("calculatedTotalQty", calculatedTotalQty);

                editor.putString("order_id",order_id);
                editor.commit();

                startActivity(i);
                /*if(listSelectedClothe.size() != 0)
                {
                    Intent i = new Intent(getApplicationContext(),GarmentSelectionTypeActivity.class);
                    Log.d(TAG, "calculatedTotalQty = " + calculatedTotalQty);
                    i.putExtra("calculatedTotalQty", calculatedTotalQty);

                    editor.putString("order_id",order_id);
                    editor.commit();

                    startActivity(i);
                }*/
                //finish();
            }
        });
    }

    public void GetAllCatiData()
    {
        progressDialog = ProgressDialog.show(SelectKgGarmentsActivity.this,"Please wait","Loading..");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL + URL_ALL_CLOTH_CATEGORY_KG, new Response.Listener<String>() {
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

                    if(status.equals("1"))
                    {
                        JSONArray jsonArray = object.getJSONArray("data");
                        Log.d("TAG","length = " + jsonArray.length());
                        for (int i = 0 ; i < jsonArray.length() ; i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            clothcatiid = jsonObject.getString("clothcatiid");
                            clothcatiname = jsonObject.getString("clothcatiname");
                            Log.d("TAG","clothcatiname = " + clothcatiname);

                            childGarmentsList = new ArrayList<ChildGarments>();
                            JSONArray jsonArray1 = jsonObject.getJSONArray("clothes");
                            for (int j = 0 ; j < jsonArray1.length() ; j++)
                            {
                                JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                                clothe_id = jsonObject1.getString("clothe_id");
                                clothe_name = jsonObject1.getString("clothe_name");
                                clothe_img = jsonObject1.getString("clothe_img");

                                Log.d("TAG","clothe_name = " + clothe_name);

                                ChildGarments mobile = new ChildGarments();
                                mobile.setClothe_id(jsonObject1.getString("clothe_id"));
                                mobile.setClothe_name(jsonObject1.getString("clothe_name").trim());
                                mobile.setClothe_img(jsonObject1.getString("clothe_img"));

                                childGarmentsList.add(mobile);
                            }
                            categoryGarments = new CategoryGarments(clothcatiname, childGarmentsList);
                            categoryGarmentsArrayList.add(categoryGarments);
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"not getting data from server, status is 0", Toast.LENGTH_SHORT).show();
                    }
                    categoryGarmentsAdapter = new KgCategoryGarmentsAdapter(getApplicationContext(), categoryGarmentsArrayList, SelectKgGarmentsActivity.this::onImageClick);
                    categoryGarmentsListView.setAdapter(categoryGarmentsAdapter);
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
                params.put("mtoken", Config.mtoken);
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

    @Override
    public void onImageClick(String imageData)
    {
        // handle image data
        Log.d("TAG","data = " + imageData);

        if(!imageData.equals(""))
        {
            SelectedClothe selectedClothe = new SelectedClothe();
            selectedClothe.setClotheImg(imageData);
            listSelectedClothe.add(selectedClothe);
        }

        selectedRecycler.setAdapter(selectedClotheAdapter);
        selectedClotheAdapter.notifyDataSetChanged();

        //Toast.makeText(getApplicationContext(),"Clicked ..",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(String totalcount)
    {
        //increment and decrement value from text
        Log.d("TAG","totalcount = " + totalcount);

        calculatedTotalQty = Integer.parseInt(totalcount);
        Log.d("TAG","calculatedTotalCount = " + calculatedTotalQty);
    }
}
