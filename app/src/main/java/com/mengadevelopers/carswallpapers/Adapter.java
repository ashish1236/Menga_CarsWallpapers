package com.mengadevelopers.carswallpapers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.mengadevelopers.carswallpapers.databinding.RvdesigBinding;
import com.squareup.picasso.Picasso;

import java.util.List;


public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {
    List<model> list;
    Context context;

    public Adapter(List<model> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.rvdesig,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
     model models=list.get(position);
     Picasso.get().load(models.getMedium()).placeholder(R.drawable.download).into(holder.binding.imageView);
//        Glide.with(context).load(models.getMedium()).into(holder.binding.imageView);

        holder.binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,MainActivity2.class);
                intent.putExtra("key",models.getMedium());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        RvdesigBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=RvdesigBinding.bind(itemView);

        }
    }
}
