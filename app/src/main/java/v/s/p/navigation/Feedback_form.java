package v.s.p.navigation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import v.s.p.Activities.activity_Home;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.studentappinc.R;

public class Feedback_form extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_form_activity);
/*
        Window window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.statusbarorange));*/

        getSupportActionBar().setTitle("Feedback Form");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6b10d0")));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().getTitle().

        webView = (WebView)findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSfnpguXsDkrd9Z1C8KKk8382Gr9-BMsAPb_BD4_EZ9X27B24w/viewform");





    }
    @Override
    public boolean onSupportNavigateUp() {
        /*Intent intent = new Intent(getApplicationContext(), activity_Home.class);
        startActivity(intent);
        finish();*/
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(getApplicationContext(), activity_Home.class);
        startActivity(intent);
        finish();


    }


}