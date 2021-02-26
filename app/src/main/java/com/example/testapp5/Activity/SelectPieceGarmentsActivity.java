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
import com.example.testapp5.Adapters.PieceCategoryGarmentsAdapter;
import com.example.testapp5.Adapters.PieceSelectedClotheAdapter;
import com.example.testapp5.Adapters.SelectedClotheAdapter;
import com.example.testapp5.Interface.OnPieceImageClickListener;
import com.example.testapp5.Interface.OnPieceItemClick;
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
import static com.example.testapp5.URL.Config.URL_ALL_CLOTH_CATEGORY_PIECE;

public class SelectPieceGarmentsActivity extends AppCompatActivity implements OnPieceImageClickListener, OnPieceItemClick
{
    private ExpandableListView categoryGarmentsListView;
    private ArrayList<CategoryGarments> categoryGarmentsArrayList = new ArrayList<CategoryGarments>();
    ExpandableListAdapter categoryGarmentsAdapter;

    ArrayList<SelectedClothe> listSelectedClothe = new ArrayList<>();
    RecyclerView selectedRecycler;
    PieceSelectedClotheAdapter selectedClotheAdapter;

    String status="", message="", charges = "", clothe_name = "",
            clothe_id = "",clothe_img = "",optionName="", tbl_washtype_id = "", chargeid = "",
            cate_group = "", cate_group_name = "", order_id = "", isCheck = "PieceSelectedActivity";

    CategoryGarments categoryGarments;
    ProgressDialog progressDialog;
    List<ChildGarments> childGarmentsList;
    Button btnContinue;
    TextView txtOptionName;
    int calculatedTotalQty, calculatedTotalPrice;

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    String TAG = "SelectPieceGarmentsActivity";

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

        tbl_washtype_id = i.getStringExtra("tbl_washtype_id");
        Log.d(TAG,"tbl_washtype_id = " + tbl_washtype_id);

        btnContinue = findViewById(R.id.btnContinue);
        categoryGarmentsListView = (ExpandableListView) findViewById(R.id.list_category);

        txtOptionName = findViewById(R.id.txtOptionName);
        txtOptionName.setText(optionName);

        selectedRecycler = (RecyclerView) findViewById(R.id.selectedRecycler);
        selectedRecycler.setHasFixedSize(true);
        selectedRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        listSelectedClothe=new ArrayList<>();
        selectedClotheAdapter=new PieceSelectedClotheAdapter(listSelectedClothe,this, SelectPieceGarmentsActivity.this::onClick);

        //set data to expandable listview from volley
        GetAllPieceCatiData();

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
                if(listSelectedClothe.size() != 0)
                {
                    Intent i = new Intent(getApplicationContext(),GarmentSelectionTypeActivity.class);
                    Log.d(TAG, "calculatedTotalQty = " + calculatedTotalQty);
                    i.putExtra("calculatedTotalQty", calculatedTotalQty);
                    i.putExtra("isCheck", isCheck);
                    //i.putExtra("calculatedTotalPrice",calculatedTotalPrice);

                    editor.putString("order_id",order_id);
                    editor.commit();

                    startActivity(i);
                }
            }
        });
    }

    public void GetAllPieceCatiData()
    {
        progressDialog = ProgressDialog.show(SelectPieceGarmentsActivity.this,"Please wait","Loading..");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL + URL_ALL_CLOTH_CATEGORY_PIECE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Log.d(TAG, "response = " + response);
                progressDialog.dismiss();

                try
                {
                    JSONObject object = new JSONObject(response);
                    status = object.getString("status");
                    message = object.getString("message");

                    if(status.equals("1"))
                    {
                        JSONArray jsonArray = object.getJSONArray("data");
                        Log.d(TAG,"length = " + jsonArray.length());
                        for (int i = 0 ; i < jsonArray.length() ; i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            cate_group = jsonObject.getString("cate_group");
                            cate_group_name = jsonObject.getString("cate_group_name");
                            Log.d(TAG,"cate_group_name = " + cate_group_name);

                            childGarmentsList = new ArrayList<ChildGarments>();
                            JSONArray jsonArray1 = jsonObject.getJSONArray("clothes");
                            for (int j = 0 ; j < jsonArray1.length() ; j++)
                            {
                                JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                                chargeid = jsonObject1.getString("chargeid");
                                clothe_id = jsonObject1.getString("clothe_id");
                                clothe_name = jsonObject1.getString("clothe_name").trim();
                                clothe_img = jsonObject1.getString("clothe_img");
                                charges = jsonObject1.getString("charges");

                                //Log.d(TAG,"charges = " + charges);

                                ChildGarments childGarments = new ChildGarments();
                                childGarments.setChargeid(chargeid);
                                childGarments.setClothe_id(clothe_id);
                                childGarments.setClothe_name(clothe_name);
                                childGarments.setClothe_img(clothe_img);
                                childGarments.setCharges(charges);

                                childGarmentsList.add(childGarments);
                            }
                            categoryGarments = new CategoryGarments(cate_group_name, childGarmentsList);
                            categoryGarmentsArrayList.add(categoryGarments);
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"not getting data from server, status is 0", Toast.LENGTH_SHORT).show();
                    }
                    categoryGarmentsAdapter = new PieceCategoryGarmentsAdapter(getApplicationContext(), categoryGarmentsArrayList, SelectPieceGarmentsActivity.this::onPieceImageClick);
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
                        Log.d(TAG,"error = " + error);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("mtoken", Config.mtoken);
                params.put("tbl_washtype_id",tbl_washtype_id);
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
        stringRequest.setTag(TAG);
    }

    @Override
    public void onPieceImageClick(String imageData, String charges)
    {
        // handle image data
        Log.d(TAG,"data = " + imageData);
        Log.d(TAG,"on click charges = " + charges);

        if(!imageData.equals(""))
        {
            SelectedClothe selectedClothe = new SelectedClothe();
            selectedClothe.setClotheImg(imageData);
            selectedClothe.setCharges(charges);
            listSelectedClothe.add(selectedClothe);
        }

        selectedRecycler.setAdapter(selectedClotheAdapter);
        //selectedClotheAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(String totalqty)
    {
        Log.d(TAG,"totalqty = " + totalqty);

        calculatedTotalQty = Integer.parseInt(totalqty);
        Log.d(TAG,"calculatedTotalQty = " + calculatedTotalQty);
    }
}
