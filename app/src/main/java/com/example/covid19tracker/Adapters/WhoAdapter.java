package com.example.covid19tracker.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19tracker.Models.WhoModel;
import com.example.covid19tracker.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WhoAdapter extends RecyclerView.Adapter<WhoAdapter.viewHolder> {

    Context context;
    List<WhoModel> adviceList;

    public WhoAdapter(Context context, List<WhoModel> adviceList) {
        this.context = context;
        this.adviceList = adviceList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_who_advice, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        WhoModel whoModel = adviceList.get(position);
        Picasso.get().load(whoModel.getAdviceImage()).placeholder(R.drawable.placeholder).into(holder.adviceImg);

        holder.adviceImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_VIEW , Uri.parse(whoModel.getAdviceImage())));
            }
        });

    }

    @Override
    public int getItemCount() {
        return adviceList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView adviceImg;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            adviceImg = itemView.findViewById(R.id.adviceImg);
        }
    }
}
