package com.example.covid19tracker.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.covid19tracker.Adapters.PreventionAdapter;
import com.example.covid19tracker.Models.PreventionModel;
import com.example.covid19tracker.R;

import java.util.ArrayList;
import java.util.List;

public class PreventionActivity extends AppCompatActivity {

    RecyclerView preventionRCV;
    PreventionAdapter preventionAdapter;
    List<PreventionModel> preventionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prevention);

        // Changing color of System Navigation Bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.navbar));
        }

        preventionRCV = findViewById(R.id.preventionRCV);
        preventionList = new ArrayList<>();
        preventionAdapter = new PreventionAdapter(this , preventionList);
        preventionRCV.setLayoutManager(new LinearLayoutManager(this));

        preventionList.add(new PreventionModel(R.drawable.mask , "Wear a mask"));
        preventionList.add(new PreventionModel(R.drawable.sanitize , "Clean your hands"));
        preventionList.add(new PreventionModel(R.drawable.social_distancing , "Maintain safe distance"));
        preventionList.add(new PreventionModel(R.drawable.vaccination , "Vaccination"));
        preventionList.add(new PreventionModel(R.drawable.cough , "Avoid contact with Sick people"));
        preventionList.add(new PreventionModel(R.drawable.sneeze , "Cover your Cough or Sneeze"));
        preventionList.add(new PreventionModel(R.drawable.quit_smoking , "No Smoking"));
        preventionList.add(new PreventionModel(R.drawable.diet , "Healthy Diet"));
        preventionList.add(new PreventionModel(R.drawable.brain , "Mental Health"));
        preventionList.add(new PreventionModel(R.drawable.exercise , "Regular Exercise"));
        preventionList.add(new PreventionModel(R.drawable.doctor , "Consult to Doctor"));

        preventionRCV.setAdapter(preventionAdapter);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}