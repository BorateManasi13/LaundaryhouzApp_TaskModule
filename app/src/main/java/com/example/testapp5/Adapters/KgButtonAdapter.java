package com.example.testapp5.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp5.Activity.SelectKgGarmentsActivity;
import com.example.testapp5.Model.KgButton;
import com.example.testapp5.R;

import java.util.List;

public class KgButtonAdapter extends RecyclerView.Adapter<KgButtonAdapter.ViewHolder>
{
    List<KgButton> kgButtonList;
    private Context context;

    public KgButtonAdapter(List<KgButton> kgButtonList, Context context) {
        this.kgButtonList = kgButtonList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subtype,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        KgButton kgButton = kgButtonList.get(position);
        holder.btnKgType.setText(kgButton.getName());

        holder.btnKgType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(context, SelectKgGarmentsActivity.class);
                intent.putExtra("name", kgButton.getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return kgButtonList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        Button btnKgType;

        public ViewHolder(View itemView)
        {
            super(itemView);

            btnKgType = (Button) itemView.findViewById(R.id.btnType);
        }
    }
}
