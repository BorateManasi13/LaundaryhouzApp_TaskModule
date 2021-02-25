package com.example.testapp5.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp5.Model.ExtraViewAllData;
import com.example.testapp5.R;

import java.util.ArrayList;

public class ExtraViewAllDataAdapter extends RecyclerView.Adapter<ExtraViewAllDataAdapter.ViewHolder>
{
    ArrayList<ExtraViewAllData> extraViewDataList = new ArrayList<>();
    Context context;

    public ExtraViewAllDataAdapter(ArrayList<ExtraViewAllData> extraViewDataList, Context context) {
        this.extraViewDataList = extraViewDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_extra_viewall,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        ExtraViewAllData extraViewAllData = extraViewDataList.get(position);

        holder.txtProdctName.setText(extraViewAllData.getProduct_name());
        holder.txtPriceForExtra.setText(extraViewAllData.getPrice());
    }

    @Override
    public int getItemCount() {
        return extraViewDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtPriceForExtra,txtProdctName;

        public ViewHolder(View itemView)
        {
            super(itemView);

            txtProdctName = itemView.findViewById(R.id.txtProdctName);
            txtPriceForExtra = itemView.findViewById(R.id.txtPriceForExtra);
        }
    }
}
