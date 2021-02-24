package com.example.testapp5.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testapp5.Model.KgViewAllData;
import com.example.testapp5.R;

import java.util.ArrayList;

public class KgViewAllDataAdapter extends RecyclerView.Adapter<KgViewAllDataAdapter.ViewHolder>
{
    ArrayList<KgViewAllData> kgViewAllDataList = new ArrayList<>();
    Context context;

    public KgViewAllDataAdapter(ArrayList<KgViewAllData> kgViewAllDataList, Context context) {
        this.kgViewAllDataList = kgViewAllDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kg_viewall,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        KgViewAllData kgViewAllData = kgViewAllDataList.get(position);

        holder.txtClothName.setText(kgViewAllData.getClothe_name());
        holder.txtQty.setText("(Qty:" + kgViewAllData.getQty() + ")");
        holder.txtWashType.setText(kgViewAllData.getWash_type());

        Glide.with(context)
                .load(kgViewAllData.getImg_url())
                .into(holder.imgUrl);
    }

    @Override
    public int getItemCount() {
        return kgViewAllDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imgUrl;
        TextView txtQty,txtWashType,txtClothName;

        public ViewHolder(View itemView)
        {
            super(itemView);

            imgUrl = (ImageView) itemView.findViewById(R.id.imgUrl);
            txtQty = (TextView) itemView.findViewById(R.id.txtQty);
            txtClothName = (TextView) itemView.findViewById(R.id.txtClothName);
            txtWashType = (TextView) itemView.findViewById(R.id.txtWashType);
        }
    }
}
