package com.example.covid19tracker.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19tracker.Models.CovidModel;
import com.example.covid19tracker.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CovidAdapter extends RecyclerView.Adapter<CovidAdapter.viewHolder> {

    int pos = 1; // Used for Spinner item
    Context context;
    List<CovidModel> covidList;

    public CovidAdapter(Context context, List<CovidModel> covidList) {
        this.context = context;
        this.covidList = covidList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_country_cases, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        CovidModel covidModel = covidList.get(position);

        holder.Country.setText(covidModel.getCountry());

        if (pos == 1) {
            holder.Cases.setText(covidModel.getCases());
        } else {
            if (pos == 2) {
                holder.Cases.setText(covidModel.getActive());
            } else if (pos == 3) {
                holder.Cases.setText(covidModel.getRecovered());
            } else if (pos == 4) {
                holder.Cases.setText(covidModel.getDeaths());
            }
        }

    }

    @Override
    public int getItemCount() {
        return covidList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView Country, Cases;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            Country = itemView.findViewById(R.id.Country);
            Cases = itemView.findViewById(R.id.Cases);
        }
    }

    // This method checks whether the item selected in spinner, matches with the string provided.
    public void filter(String spinnerItem) {
        if (spinnerItem.equals("Cases")) {
            pos = 1;
        } else if (spinnerItem.equals("Active")) {
            pos = 2;
        } else if (spinnerItem.equals("Recovered")) {
            pos = 3;
        } else if (spinnerItem.equals("Deaths")) {
            pos = 4;
        }

        notifyDataSetChanged();
    }

}
