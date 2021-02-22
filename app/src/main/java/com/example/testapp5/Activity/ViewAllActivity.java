package com.example.testapp5.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp5.R;

public class ViewAllActivity extends AppCompatActivity
{
    RecyclerView showCountRecycler,selectedKgRecycler,selectedPieceRecycler,selectedExtraRecycler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewall);

        showCountRecycler = findViewById(R.id.showCountRecycler);
        selectedKgRecycler = findViewById(R.id.selectedKgRecycler);
        selectedExtraRecycler = findViewById(R.id.selectedExtraRecycler);
        selectedPieceRecycler = findViewById(R.id.selectedPieceRecycler);
    }
}
