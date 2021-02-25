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
import com.example.testapp5.Model.PieceViewAllData;
import com.example.testapp5.R;

import java.util.ArrayList;

public class PieceViewAllDataAdapter extends RecyclerView.Adapter<PieceViewAllDataAdapter.ViewHolder>
{
    ArrayList<PieceViewAllData> pieceViewAllDataList = new ArrayList<>();
    Context context;

    public PieceViewAllDataAdapter(ArrayList<PieceViewAllData> pieceViewAllDataList, Context context) {
        this.pieceViewAllDataList = pieceViewAllDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_piece_viewall,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        PieceViewAllData pieceViewAllData = pieceViewAllDataList.get(position);

        holder.txtClothName.setText(pieceViewAllData.getClothe_name());
        holder.txtQty.setText(pieceViewAllData.getQty());
        holder.txtQtyPrice.setText("(₹" + pieceViewAllData.getRate() + ")");
        holder.txtWashType.setText(pieceViewAllData.getWash_type());

        Glide.with(context)
                .load(pieceViewAllData.getImg_url())
                .into(holder.imgUrl);
    }

    @Override
    public int getItemCount() {
        return pieceViewAllDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imgUrl;
        TextView txtClothName,txtQty,txtQtyPrice,txtWashType;

        public ViewHolder(View itemView)
        {
            super(itemView);

            imgUrl = itemView.findViewById(R.id.imgUrl);
            txtClothName = itemView.findViewById(R.id.txtClothName);
            txtQty = itemView.findViewById(R.id.txtQty);
            txtQtyPrice = itemView.findViewById(R.id.txtQtyPrice);
            txtWashType = itemView.findViewById(R.id.txtWashType);
        }
    }
}
