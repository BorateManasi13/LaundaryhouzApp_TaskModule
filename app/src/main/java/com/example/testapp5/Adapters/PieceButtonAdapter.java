package com.example.testapp5.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp5.Activity.SelectGarmentsActivity;
import com.example.testapp5.Model.KgButton;
import com.example.testapp5.Model.PieceButtons;
import com.example.testapp5.R;

import java.util.List;

public class PieceButtonAdapter extends RecyclerView.Adapter<PieceButtonAdapter.ViewHolder>
{
    List<PieceButtons> pieceButtonsList;
    private Context context;

    public PieceButtonAdapter(List<PieceButtons> pieceButtonsList, Context context) {
        this.pieceButtonsList = pieceButtonsList;
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
        PieceButtons pieceButtons = pieceButtonsList.get(position);
        holder.btnPieceType.setText(pieceButtons.getName());

        holder.btnPieceType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(context, SelectGarmentsActivity.class);
                intent.putExtra("name", pieceButtons.getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pieceButtonsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        Button btnPieceType;

        public ViewHolder(View itemView)
        {
            super(itemView);
            btnPieceType = (Button) itemView.findViewById(R.id.btnType);
        }
    }
}
