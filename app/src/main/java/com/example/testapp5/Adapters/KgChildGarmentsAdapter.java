package com.example.testapp5.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testapp5.Interface.OnImageClickListener;
import com.example.testapp5.Model.ChildGarments;
import com.example.testapp5.R;

import java.util.ArrayList;
import java.util.List;

public class KgChildGarmentsAdapter extends RecyclerView.Adapter<KgChildGarmentsAdapter.ViewHolder>
{
    private Context context;
    private List<ChildGarments> childGarmentsList = new ArrayList<ChildGarments>();
    private OnImageClickListener onImageClickListener;

    public KgChildGarmentsAdapter(Context context, List<ChildGarments> childGarmentsList, OnImageClickListener onImageClickListener) {
        this.context = context;
        this.childGarmentsList = childGarmentsList;
        this.onImageClickListener = onImageClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View cardView = inflater.inflate(R.layout.item_child, null, false);
        ViewHolder viewHolder = new ViewHolder(cardView);
        viewHolder.clotheimg = (ImageView) cardView.findViewById(R.id.clothe_img);
        viewHolder.clothe_name = (TextView) cardView.findViewById(R.id.txt_clothe_name);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        String imgUrl = childGarmentsList.get(position).getClothe_img();
        holder.clothe_name.setText(childGarmentsList.get(position).getClothe_name());

        //ImageView mobileImageView = (ImageView) holder.mobileImage;
        //mobileImageView.setImageResource(mobiles.get(position).getImageResource());

        Glide.with(context)
                .load(imgUrl)
                .into(holder.clotheimg);

        holder.clotheimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //Toast.makeText(context,"Clicked on adapter .." + position,Toast.LENGTH_SHORT).show();
                onImageClickListener.onImageClick(imgUrl);
            }
        });
    }

    @Override
    public int getItemCount() {
        return childGarmentsList.size();
    }

    public void setfilter(List<ChildGarments> mobilesList){
        mobilesList=new ArrayList<>();
        mobilesList.addAll(mobilesList);
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView clotheimg;
        TextView clothe_name;
        //TextView price;

        public ViewHolder(View itemView) {
            super(itemView);

            clotheimg = (ImageView) itemView.findViewById(R.id.clothe_img);
            clothe_name = (TextView) itemView.findViewById(R.id.txt_clothe_name);
        }
    }
}
