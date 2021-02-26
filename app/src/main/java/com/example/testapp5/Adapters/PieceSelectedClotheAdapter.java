package com.example.testapp5.Adapters;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testapp5.Interface.OnItemClick;
import com.example.testapp5.Interface.OnPieceItemClick;
import com.example.testapp5.Model.SelectedClothe;
import com.example.testapp5.R;

import java.util.List;

public class PieceSelectedClotheAdapter extends RecyclerView.Adapter<PieceSelectedClotheAdapter.ViewHolder>
{
    List<SelectedClothe> selectedClotheList;
    private Context context;
    private OnPieceItemClick mCallback;
    int totalItemPrice = 0;

    public PieceSelectedClotheAdapter(List<SelectedClothe> selectedClotheList, Context context, OnPieceItemClick mCallback) {
        this.selectedClotheList = selectedClotheList;
        this.context = context;
        this.mCallback = mCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_cloth,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        SelectedClothe selectedClothe = selectedClotheList.get(position);

        holder.txtCharges.setText(selectedClotheList.get(position).getCharges());
        //Log.d("TAG","charges == " + selectedClotheList.get(position).getCharges());

        Glide.with(context)
                .load(selectedClothe.getClotheImg())
                .into(holder.clotheimg);

        selectedClotheList.get(position).setQuantity("1");

        /*totalItemPrice = Integer.parseInt(selectedClotheList.get(position).getQuantity()) * Integer.parseInt(selectedClotheList.get(position).getCharges());
        Log.d("TAG", "totalItemPrice 1 = " + totalItemPrice);*/

        holder.txtQuantity.setText(selectedClotheList.get(position).getQuantity());
        mCallback.onClick(String.valueOf(getTotalQuantity()));

        holder.imgadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                int count= Integer.parseInt(String.valueOf(holder.txtQuantity.getText()));
                count++;
                selectedClotheList.get(position).setQuantity("" + count);

                /*totalItemPrice = Integer.parseInt(selectedClotheList.get(position).getQuantity()) * Integer.parseInt(selectedClotheList.get(position).getCharges());
                Log.d("TAG", "totalItemPrice add click = " + totalItemPrice);*/

                totalItemPrice = Integer.parseInt(selectedClotheList.get(position).getCharges()) + count;

                holder.txtQuantity.setText(selectedClotheList.get(position).getQuantity());
                mCallback.onClick(String.valueOf(getTotalQuantity()));
            }
        });

        holder.imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                int count= Integer.parseInt(String.valueOf(holder.txtQuantity.getText()));
                Log.d("TAG","count = " + count);
                if (count == 1)
                {
                    selectedClotheList.get(position).setQuantity("" + count);
                    holder.txtQuantity.setText(selectedClotheList.get(position).getQuantity());

                    //remove item from list
                    removeAt(position);

                    /*totalItemPrice = Integer.parseInt(selectedClotheList.get(position).getQuantity()) * Integer.parseInt(selectedClotheList.get(position).getCharges());
                    Log.d("TAG", "totalItemPrice minus click = " + totalItemPrice);*/

                    mCallback.onClick(String.valueOf(getTotalQuantity()));
                }
                else
                {
                    count -= 1;
                    selectedClotheList.get(position).setQuantity("" + count);

                    /*totalItemPrice = Integer.parseInt(selectedClotheList.get(position).getQuantity()) * Integer.parseInt(selectedClotheList.get(position).getCharges());
                    Log.d("TAG", "totalItemPrice minus click = " + totalItemPrice);*/

                    holder.txtQuantity.setText(selectedClotheList.get(position).getQuantity());
                    mCallback.onClick(String.valueOf(getTotalQuantity()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return selectedClotheList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView clotheimg;
        ImageView imgadd, imgMinus;
        TextView txtQuantity,txtCharges;

        public ViewHolder(View itemView)
        {
            super(itemView);

            clotheimg = (ImageView) itemView.findViewById(R.id.clotheimg);
            imgadd = (ImageView) itemView.findViewById(R.id.imgAdd);
            imgMinus = (ImageView) itemView.findViewById(R.id.imgMinus);

            txtQuantity = (TextView) itemView.findViewById(R.id.txt_quantity);
            txtCharges = (TextView) itemView.findViewById(R.id.txtCharges);
            txtCharges.setVisibility(View.VISIBLE);
        }
    }
    public void removeAt(int position)
    {
        selectedClotheList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, selectedClotheList.size());
    }


    /*get total quantity on increment/decrement click*/
    public int getTotalQuantity()
    {
        int totalQuantity = 0;
        for (int i = 0; i < selectedClotheList.size(); i++)
        {
            if (selectedClotheList.get(i) != null && selectedClotheList.get(i).getQuantity() != null && !TextUtils.isEmpty(selectedClotheList.get(i).getQuantity()))
            {
                totalQuantity = totalQuantity + Integer.parseInt(selectedClotheList.get(i).getQuantity());
            }
        }
        return totalQuantity;
    }

    /*private int grandTotal()
    {
        int totalPrice = 0;
        for(int i = 0 ; i < selectedClotheList.size(); i++)
        {
            totalPrice = totalPrice + totalItemPrice;
        }

        return totalPrice;
    }*/
}
