package com.example.testapp5.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp5.Model.ExtraProduct;
import com.example.testapp5.R;

import java.util.ArrayList;

public class ExtraProductAdapter extends RecyclerView.Adapter<ExtraProductAdapter.ViewHolder>
{
    ArrayList<ExtraProduct> extraProductList = new ArrayList<>();
    Context context;

    public ExtraProductAdapter(ArrayList<ExtraProduct> extraProductList, Context context) {
        this.extraProductList = extraProductList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        ExtraProduct extraProduct = extraProductList.get(position);

        holder.txtProductName.setText(extraProduct.getProductName());
        holder.txtProductPrice.setText(extraProduct.getProductPrice());

        holder.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

            }
        });

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

            }
        });
    }

    @Override
    public int getItemCount() {
        return extraProductList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imgClose,imgEdit;
        TextView txtProductName,txtProductPrice;

        public ViewHolder(View itemView)
        {
            super(itemView);

            txtProductName = (TextView) itemView.findViewById(R.id.txtProductName);
            txtProductPrice = (TextView) itemView.findViewById(R.id.txtProductPrice);

            imgClose = (ImageView) itemView.findViewById(R.id.imgClose);
            imgEdit = (ImageView) itemView.findViewById(R.id.imgEdit);

        }
    }
}
