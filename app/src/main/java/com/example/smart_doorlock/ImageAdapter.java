package com.example.smart_doorlock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {

    Context context;

    ArrayList<Model> list = new ArrayList<>();

    public ImageAdapter(Context context, ArrayList<Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_image, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImageUrl()).into(holder.mImageView);
        holder.mtime.setText(list.get(position).getTime());
        holder.mname.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        TextView mname;
        TextView mtime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mImageView = itemView.findViewById(R.id.m_image);
            this.mtime = itemView.findViewById(R.id.m_time);
            this.mname = itemView.findViewById(R.id.m_name);
        }
    }
}

