package com.example.covid19tracker.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19tracker.Models.PreventionModel;
import com.example.covid19tracker.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PreventionAdapter extends RecyclerView.Adapter<PreventionAdapter.viewHolder>{

    Context context;
    List<PreventionModel> preventionList;

    public PreventionAdapter(Context context, List<PreventionModel> preventionList) {
        this.context = context;
        this.preventionList = preventionList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_preventions , parent , false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        PreventionModel preventionModel = preventionList.get(position);
        Picasso.get().load(preventionModel.getPreventionImage()).into(holder.preventionImg);
        holder.preventionName.setText(preventionModel.getPreventionName());
    }

    @Override
    public int getItemCount() {
        return preventionList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView preventionImg;
        TextView preventionName;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            preventionImg = itemView.findViewById(R.id.preventionImage);
            preventionName = itemView.findViewById(R.id.preventionName);
        }
    }

}
