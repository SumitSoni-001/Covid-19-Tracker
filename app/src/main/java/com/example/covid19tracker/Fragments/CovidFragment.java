package com.example.covid19tracker.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19tracker.API.CovidController;
import com.example.covid19tracker.API.CovidInterface;
import com.example.covid19tracker.Adapters.CovidAdapter;
import com.example.covid19tracker.Models.CovidModel;
import com.example.covid19tracker.R;
import com.hbb20.CountryCodePicker;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CovidFragment extends Fragment implements AdapterView.OnItemSelectedListener {     // The Interface is used for spinner

    CovidAdapter covidAdapter;
    List<CovidModel> AdapterList;   // It stores data used in CovidAdapter class.
    List<CovidModel> ActivityList;  // It stores data used in CovidFragment.
    CovidInterface covidInterface;
    private RecyclerView countryRCV;

    String Country;     // It stores the selected country name.
    String[] status = {"Cases", "Active", "Recovered", "Deaths"};    // Spinner Items

    private TextView totalCases, increasedCases, active, recovered, increasedRecovered, death, increasedDeath;
    private TextView filter;
    private PieChart pieChart;
    private Spinner spinner;
    private CountryCodePicker ccp;

    LinearLayout ErrorLayout;
    ImageView ErrorImg;
    TextView ErrorTitle, ErrorMsg;
    AppCompatButton retryBtn;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_covid, container, false);

        /** Initialisation */

        // Simple Views
        totalCases = root.findViewById(R.id.totalCases);
        increasedCases = root.findViewById(R.id.increasedCases);
        active = root.findViewById(R.id.active);
        recovered = root.findViewById(R.id.recovered);
        increasedRecovered = root.findViewById(R.id.increasedRecovered);
        death = root.findViewById(R.id.death);
        increasedDeath = root.findViewById(R.id.increasedDeath);

        // Chart & spinner
        pieChart = root.findViewById(R.id.pieChart);
        spinner = root.findViewById(R.id.spinner);
        filter = root.findViewById(R.id.filter);
        ccp = root.findViewById(R.id.ccp);
        countryRCV = root.findViewById(R.id.countryRCV);

        // API & RCV
        covidInterface = CovidController.getClient().create(CovidInterface.class);
        AdapterList = new ArrayList<>();
        ActivityList = new ArrayList<>();
        covidAdapter = new CovidAdapter(getContext(), AdapterList);
        countryRCV.setLayoutManager(new LinearLayoutManager(getContext()));
        countryRCV.setAdapter(covidAdapter);

        // Error Layout
        ErrorLayout = root.findViewById(R.id.error_layout);
        ErrorImg = root.findViewById(R.id.ErrorImage);
        ErrorTitle = root.findViewById(R.id.errorTitle);
        ErrorMsg = root.findViewById(R.id.errorMsg);
        retryBtn = root.findViewById(R.id.retry);

        // Spinner
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, status);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        // Country Code Picker
        ccp.setAutoDetectedCountry(true);
        Country = ccp.getSelectedCountryName();
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                Country = ccp.getSelectedCountryName();
                fetchDataActivity();
            }
        });

        fetchDataAdapter();
        fetchDataActivity();

        return root;

    }

    private void fetchDataAdapter(){

        covidInterface.getCountryStatus().enqueue(new Callback<List<CovidModel>>() {    // Fetching Data for Recycler View
            @Override
            public void onResponse(Call<List<CovidModel>> call, Response<List<CovidModel>> response) {

                if (response.isSuccessful()) {
                    AdapterList.addAll(response.body());
                    covidAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<CovidModel>> call, Throwable t) {
                Log.d("TAG", t.getLocalizedMessage());
            }
        });

    }

    private void fetchDataActivity() {      // Fetch Data For Fragment

        ErrorLayout.setVisibility(View.GONE);
        covidInterface.getCountryStatus().enqueue(new Callback<List<CovidModel>>() {
            @Override
            public void onResponse(Call<List<CovidModel>> call, Response<List<CovidModel>> response) {

                if (response.isSuccessful()) {
                    ActivityList.addAll(response.body());    // Here, we got all countries data

                    // Getting he data for selected country and setting it on Views and PieChart.
                    for (int i = 0; i < ActivityList.size(); i++) {

                        if (ActivityList.get(i).getCountry().equals(Country)) {   // Comparing with selected country.

                            totalCases.setText(ActivityList.get(i).getCases());
                            increasedCases.setText("+" + ActivityList.get(i).getTodayCases());
                            active.setText(ActivityList.get(i).getActive());
                            recovered.setText(ActivityList.get(i).getRecovered());
                            increasedRecovered.setText("+" + ActivityList.get(i).getTodayRecovered());
                            death.setText(ActivityList.get(i).getDeaths());
                            increasedDeath.setText("+" + ActivityList.get(i).getTodayDeaths());

                            // updating Pie chart
                            int total, active, recovered, death;      // Created because the api return these values in Integer format

                            total = Integer.parseInt(ActivityList.get(i).getCases());
                            active = Integer.parseInt(ActivityList.get(i).getActive());
                            recovered = Integer.parseInt(ActivityList.get(i).getRecovered());
                            death = Integer.parseInt(ActivityList.get(i).getDeaths());

                            updateChart(total, active, recovered, death);

                        }
                    }
                } else {
                    String errorCode;
                    switch (response.code()) {
                        case 404:
                            errorCode = "404 not found";
                            break;

                        case 500:
                            errorCode = "500 not found";
                            break;

                        default:
                            errorCode = "Unknown error";
                            break;
                    }
                    showErrorActivity(R.drawable.connection_error, "No Result", "Please try again \n" + errorCode);
                }
            }

            @Override
            public void onFailure(Call<List<CovidModel>> call, Throwable t) {

                showErrorActivity(R.drawable.connection_error, "Oops:", "Network Failure, Please try again \n" + t.toString());

                Log.d("TAG", t.getLocalizedMessage());
            }
        });
    }

    private void updateChart(int total, int active, int recovered, int death) {
        pieChart.clearChart();
        pieChart.addPieSlice(new PieModel("Total", total, Color.parseColor("#FFC107")));
        pieChart.addPieSlice(new PieModel("Active", active, Color.parseColor("#6CC307")));
        pieChart.addPieSlice(new PieModel("Recovered", recovered, Color.parseColor("#17A4E4")));
        pieChart.addPieSlice(new PieModel("Death", death, Color.parseColor("#E52112")));
        pieChart.startAnimation();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String SpinnerItem = status[position];
        filter.setText(SpinnerItem);
        covidAdapter.filter(SpinnerItem);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void showErrorActivity(int image, String error, String message) {

        if (ErrorLayout.getVisibility() == View.GONE) {
            ErrorLayout.setVisibility(View.VISIBLE);
        }

        ErrorImg.setImageResource(image);
        ErrorTitle.setText(error);
        ErrorMsg.setText(message);

        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchDataActivity();
                fetchDataAdapter();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}