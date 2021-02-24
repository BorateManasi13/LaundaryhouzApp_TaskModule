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
    EditText edtClotheWeight,edtTotalPrice,edtTotalQuantity;
    TextView txtTotalSelected;
    CheckBox chkAlteration,chkSoftener,chkFreshener;
    int totalPrice,totalQty;

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    String order_id = "", isCheck = "", TAG = "GarmentSelectionTypeActivity";

    LinearLayout linearPieceLayout,linearKgLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_garment_1);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        order_id = sharedpreferences.getString("order_id","");
        Log.d(TAG,"order_id = " + order_id);

        totalQty = getIntent().getIntExtra("calculatedTotalQty",0);
        Log.d(TAG,"totalQty = " + totalQty);

        totalPrice = getIntent().getIntExtra("calculatedTotalPrice",0);
        Log.d(TAG,"totalPrice = " + totalPrice);

        isCheck = getIntent().getStringExtra("isCheck");
        Log.d(TAG,"isCheck = " + isCheck);

        btnCancel = findViewById(R.id.btnCancel);
        btnConti = findViewById(R.id.btnConti);

        txtTotalSelected = findViewById(R.id.txtTotalSelected);
        edtClotheWeight = findViewById(R.id.edtClotheWeight);
        edtClotheWeight.setSelection(edtClotheWeight.getText().length());

        edtTotalPrice = findViewById(R.id.edtTotalPrice);
        edtTotalQuantity = findViewById(R.id.edtTotalQuantity);

        linearKgLayout = findViewById(R.id.linearKgLayout);
        linearPieceLayout = findViewById(R.id.linearPieceLayout);

        txtTotalSelected.setText("" + totalQty);

        /*if(isCheck.equals("PieceSelectedActivity"))
        {
            linearKgLayout.setVisibility(View.GONE);
            linearPieceLayout.setVisibility(View.VISIBLE);

            edtTotalQuantity.setText(totalQty);
            edtTotalPrice.setText(totalPrice);
        }
        else
        {
            linearPieceLayout.setVisibility(View.GONE);
            linearKgLayout.setVisibility(View.VISIBLE);

            txtTotalSelected.setText("" + totalQty);
        }*/

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
