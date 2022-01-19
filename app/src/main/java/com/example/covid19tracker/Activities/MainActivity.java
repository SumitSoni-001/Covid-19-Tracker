package com.example.covid19tracker.Activities;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.covid19tracker.R;
import com.example.covid19tracker.Fragments.CovidFragment;
import com.example.covid19tracker.Fragments.HealthFragment;
import com.example.covid19tracker.Fragments.NewsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.covid19tracker.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.nav_view);

        // Changing color of System Navigation Bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.navbar));
        }

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_covid, R.id.navigation_news, R.id.navigation_health).build();

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main,
                new CovidFragment()).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.navigation_covid) {

                    CovidFragment covidFragment = new CovidFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment_activity_main, covidFragment);
                    transaction.commit();
                } else if (item.getItemId() == R.id.navigation_news) {

                    NewsFragment newsFragment = new NewsFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment_activity_main, newsFragment);
                    transaction.commit();
                } else if (item.getItemId() == R.id.navigation_health) {

                    HealthFragment healthFragment = new HealthFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment_activity_main, healthFragment);
                    transaction.commit();
                }

                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        /** If the selected bottom nav item is covid fragment, then onBackPress we close the app ;
         * else we will make the homeFragment as main fragment */
        if (bottomNavigationView.getSelectedItemId() == R.id.navigation_covid) {
            super.onBackPressed();
        } else {
            bottomNavigationView.setSelectedItemId(R.id.navigation_covid);
        }

    }
}