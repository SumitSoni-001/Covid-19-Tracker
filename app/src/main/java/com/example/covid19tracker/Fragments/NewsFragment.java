package com.example.covid19tracker.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.covid19tracker.API.NewsController;
import com.example.covid19tracker.API.NewsInterface;
import com.example.covid19tracker.Adapters.NewsAdapter;
import com.example.covid19tracker.Models.ArticlesModel;
import com.example.covid19tracker.Models.NewsModel;
import com.example.covid19tracker.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout refreshLayout;
    RecyclerView newsRCV;
    NewsAdapter newsAdapter;
    List<ArticlesModel> newsList;
    NewsInterface newsInterface;
    TextView title;
    String apiKey = "ad6c8df5b4dd47b3a498e4232f4a53b0";

    LinearLayout ErrorLayout;
    ImageView ErrorImg;
    TextView ErrorTitle, ErrorMsg;
    AppCompatButton retryBtn;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_news, container, false);

        // Error Layout
        ErrorLayout = root.findViewById(R.id.error_layout);
        ErrorImg = root.findViewById(R.id.ErrorImage);
        ErrorTitle = root.findViewById(R.id.errorTitle);
        ErrorMsg = root.findViewById(R.id.errorMsg);
        retryBtn = root.findViewById(R.id.retry);

        // Refresh Layout
        refreshLayout = root.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(R.color.Corona_active);

        // API & RCV
        title = root.findViewById(R.id.Title);
        newsRCV = root.findViewById(R.id.newsRCV);
        newsList = new ArrayList<>();
        newsAdapter = new NewsAdapter(getContext(), newsList);
        newsRCV.setLayoutManager(new LinearLayoutManager(getContext()));
        newsRCV.setAdapter(newsAdapter);
        newsInterface = NewsController.getClient().create(NewsInterface.class);

        OnLoadingSwipeRefresh();

        return root;
    }

    private void fetchNews() {
        ErrorLayout.setVisibility(View.GONE);
        refreshLayout.setRefreshing(true);

        newsInterface.getNews("covid 19", "relevancy", "en", 100, apiKey).enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                if (response.isSuccessful() && response.body().getStatus().equals("ok")) {

                    if (!newsList.isEmpty()) {
                        newsList.clear();
                    }

                    newsList.addAll(response.body().getArticles());
                    newsAdapter.notifyDataSetChanged();
                    refreshLayout.setRefreshing(false);

                } else {
                    refreshLayout.setRefreshing(false);
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
                    showError(R.drawable.connection_error, "No Result", "Please try again \n" + errorCode);
                }
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                refreshLayout.setRefreshing(false);
                showError(R.drawable.connection_error, "Oops:", "Network Failure, Please try again \n" + t.toString());

                Log.d("TAG", t.getLocalizedMessage());
            }
        });
    }

    private void showError(int image, String error, String message) {

        if (ErrorLayout.getVisibility() == View.GONE) {
            ErrorLayout.setVisibility(View.VISIBLE);
        }

        ErrorImg.setImageResource(image);
        ErrorTitle.setText(error);
        ErrorMsg.setText(message);

        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnLoadingSwipeRefresh();
            }
        });
    }

    /**
     * This method is invoked when the user performs a swipe gesture.
     */
    @Override
    public void onRefresh() {
        fetchNews();
    }

    /**
     * In this method we put the 'apiCall' method and call it in 'onCreateView'. It will execute the task on
     * another thread and is executed immediately after the Refresh Completed and handover the cooked data to
     * onRefresh to display.
     */
    private void OnLoadingSwipeRefresh() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                fetchNews();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}