package com.example.covid19tracker.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19tracker.Activities.WebActivity;
import com.example.covid19tracker.Models.ArticlesModel;
import com.example.covid19tracker.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.viewHolder> {

    Context context;
    List<ArticlesModel> newsList;

    SimpleDateFormat simpleDateFormat;
    Calendar calendar;

    public NewsAdapter(Context context, List<ArticlesModel> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_news , parent , false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ArticlesModel model = newsList.get(position);

        Picasso.get().load(model.getUrlToImage()).placeholder(R.drawable.placeholder).into(holder.newsImg);
        holder.headline.setText(model.getTitle());
        holder.description.setText(model.getDescription());
        holder.newsProvider.setText(model.getSource().getName());
        holder.time.setText(TimeFormatting(model.getPublishedAt()));

         holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("url", model.getUrl());
                intent.putExtra("source", model.getSource().getName());
                intent.putExtra("headline", model.getTitle());
                context.startActivity(intent);
            }
        });

        holder.headline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("url", model.getUrl());
                intent.putExtra("source", model.getSource().getName());
                intent.putExtra("headline", model.getTitle());
                context.startActivity(intent);
            }
        });

        holder.description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("url", model.getUrl());
                intent.putExtra("source", model.getSource().getName());
                intent.putExtra("headline", model.getTitle());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView newsImg;
        TextView headline , description , newsProvider , time;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            newsImg = itemView.findViewById(R.id.newsImg);
            headline = itemView.findViewById(R.id.headline);
            description = itemView.findViewById(R.id.description);
            newsProvider = itemView.findViewById(R.id.source);
            time = itemView.findViewById(R.id.dateTime);

        }
    }

    public String TimeFormatting(String Time){
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd LLL yyyy, hh:mm aaa");
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(Time);
        } catch (ParseException e) {
            Log.d("TimeError" , e.getLocalizedMessage());
        }
        String DateTime = simpleDateFormat.format(date);

        return DateTime;
    }

}
