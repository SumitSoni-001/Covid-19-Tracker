package com.example.covid19tracker.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid19tracker.R;

public class WebActivity extends AppCompatActivity {

    ImageView back , share;
    TextView title;
    Toolbar toolbar;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        toolbar = findViewById(R.id.Toolbar);
        title = findViewById(R.id.webViewTitle);
        webView = findViewById(R.id.webView);
        back = findViewById(R.id.back);
        share = findViewById(R.id.share);

        setSupportActionBar(toolbar);

        // News Activity Intent Data
        String URL = getIntent().getStringExtra("url");
        String source = getIntent().getStringExtra("source");
        String Headline = getIntent().getStringExtra("headline") + "\n" + URL + "\n" + "Shared from Covid Tracker App\n" ;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, source);  // Subject/Title of the shared news.
                    intent.putExtra(Intent.EXTRA_TEXT, Headline); // Content about shared news(Here, Title and URL).
                    startActivity(intent.createChooser(intent, "Share With : "));   // It shows the bottom sheet containing apps to which we share the news.
                }
                catch (Exception e) {
                    Toast.makeText(WebActivity.this, "Sorry, It Cannot be shared", Toast.LENGTH_SHORT).show();
                }
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(URL);

    }
}