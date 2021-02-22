package com.example.testapp5.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_garment_1);

        btnCancel = findViewById(R.id.btnCancel);
        btnConti = findViewById(R.id.btnConti);

        txtTotalSelected = findViewById(R.id.txtTotalSelected);
        edtClotheWeight = findViewById(R.id.edtClotheWeight);
        edtClotheWeight.setSelection(edtClotheWeight.getText().length());

        total_count = getIntent().getIntExtra("totalcount",0);
        Log.d("TAG","count = " + total_count);
        txtTotalSelected.setText("" + total_count);

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
