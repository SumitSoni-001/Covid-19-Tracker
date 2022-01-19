package com.example.covid19tracker.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19tracker.Activities.PreventionActivity;
import com.example.covid19tracker.Adapters.WhoAdapter;
import com.example.covid19tracker.Models.WhoModel;
import com.example.covid19tracker.R;
import com.example.covid19tracker.Activities.SymptomsActivity;

import java.util.ArrayList;
import java.util.List;

public class HealthFragment extends Fragment {

    RecyclerView adviceRCV;
    List<WhoModel> adviceList;
    WhoAdapter whoAdapter;
    CardView symptomCard, preventionCard;
    TextView symptomTitle, symptomDesc, preventionTitle, preventionDesc;
    TextView WhoWeb;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_health, container, false);

//        symptomCard = root.findViewById(R.id.symptoms_card);
//        preventionCard = root.findViewById(R.id.preventions_card);
//        symptomDesc = root.findViewById(R.id.symptoms_desc);
//        preventionDesc = root.findViewById(R.id.preventions_desc);
        symptomTitle = root.findViewById(R.id.symptomsTitle);
        preventionTitle = root.findViewById(R.id.preventionsTitle);
        WhoWeb = root.findViewById(R.id.whoWeb);
        adviceRCV = root.findViewById(R.id.adviceRCV);
        adviceList = new ArrayList<>();
        whoAdapter = new WhoAdapter(getContext(), adviceList);

        adviceList.add(new WhoModel("https://www.who.int/images/default-source/health-topics/coronavirus/social-media-squares/be-ready-social-2.tmb-1920v.jpg?sfvrsn=28a6f92d_6"));
        adviceList.add(new WhoModel("https://www.who.int/images/default-source/health-topics/coronavirus/social-media-squares/be-ready-social-1.tmb-1920v.jpg?sfvrsn=c81745a7_6"));
        adviceList.add(new WhoModel("https://www.who.int/images/default-source/health-topics/coronavirus/health-care-facilities_8_1-01.tmb-1920v.png?sfvrsn=823c9ad5_11"));
        adviceList.add(new WhoModel("https://www.who.int/images/default-source/health-topics/coronavirus/infographics/home_care_covid_eng.tmb-1920v.jpg?sfvrsn=cc9a6f73_2"));
        adviceList.add(new WhoModel("https://www.who.int/images/default-source/health-topics/coronavirus/infographics/are-you-pregnant_11_3.tmb-1920v.png?sfvrsn=71ea572b_2"));
        adviceList.add(new WhoModel("https://www.who.int/images/default-source/health-topics/coronavirus/infographics/are-you-60-or-older_8_3.tmb-1920v.png?sfvrsn=9a4b070e_2"));
        adviceList.add(new WhoModel("https://www.who.int/images/default-source/health-topics/coronavirus/risk-communications/general-public/protect-yourself/blue-2.tmb-1920v.png?sfvrsn=2bc43de1_6"));
        adviceList.add(new WhoModel("https://www.who.int/images/default-source/health-topics/coronavirus/risk-communications/general-public/protect-yourself/blue-3.tmb-1920v.png?sfvrsn=b1ef6d45_6"));
        adviceList.add(new WhoModel("https://www.who.int/images/default-source/health-topics/coronavirus/risk-communications/general-public/stress/stress.tmb-1920v.jpg?sfvrsn=b8974505_29"));
        adviceList.add(new WhoModel("https://www.who.int/images/default-source/health-topics/coronavirus/risk-communications/general-public/stress/children-stress.tmb-1920v.jpg?sfvrsn=343355fd_6"));

        adviceRCV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adviceRCV.setAdapter(whoAdapter);

        WhoWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.who.int/emergencies/diseases/novel-coronavirus-2019/advice-for-public?gclid=Cj0KCQjws4aKBhDPARIsAIWH0JVspAUzdgLRiK24CSlBucp8Z4DSCTB9k0Qq4-fIr0ESVeP7wuqTVZcaAp8tEALw_wcB";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });

        preventionTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PreventionActivity.class));
            }
        });

        symptomTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SymptomsActivity.class));
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}