package com.example.testapp5.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testapp5.R;

public class GarmentSelectionTypeActivity extends AppCompatActivity
{
    Button btnCancel,btnConti;
    EditText edtClotheWeight;
    TextView txtTotalSelected;
    CheckBox chkAlteration,chkSoftener,chkFreshener;
    int total_count;

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    String order_id = "", isCheck = "";

    LinearLayout linearPieceLayout,linearKgLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_garment_1);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        order_id = sharedpreferences.getString("order_id","");
        Log.d("TAG","order_id = " + order_id);

        btnCancel = findViewById(R.id.btnCancel);
        btnConti = findViewById(R.id.btnConti);

        txtTotalSelected = findViewById(R.id.txtTotalSelected);
        edtClotheWeight = findViewById(R.id.edtClotheWeight);
        edtClotheWeight.setSelection(edtClotheWeight.getText().length());

        total_count = getIntent().getIntExtra("totalcount",0);
        Log.d("TAG","count = " + total_count);
        txtTotalSelected.setText("" + total_count);

        isCheck = getIntent().getStringExtra("isCheck");
        Log.d("TAG","isCheck = " + isCheck);

        linearKgLayout = findViewById(R.id.linearKgLayout);
        linearPieceLayout = findViewById(R.id.linearPieceLayout);

        if(isCheck.equals("PieceSelectedActivity"))
        {
            linearKgLayout.setVisibility(View.GONE);
            linearPieceLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            linearPieceLayout.setVisibility(View.GONE);
            linearKgLayout.setVisibility(View.VISIBLE);
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Clicked..",Toast.LENGTH_SHORT).show();
            }
        });

        btnConti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //Toast.makeText(getApplicationContext(),"Clicked..",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
