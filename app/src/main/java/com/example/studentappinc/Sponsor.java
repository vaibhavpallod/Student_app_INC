package com.example.studentappinc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

public class Sponsor extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Sponsorreturn> sponsorList;
    Sponsoradapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sponsors_mainactivity);


        Sponsorreturn[] sponsorreturn = new Sponsorreturn[]{
  new Sponsorreturn("SPONSOR",R.drawable.sponsorsag),
                new Sponsorreturn("SPONSOR",R.drawable.sponsoraccm),
                new Sponsorreturn("SPONSOR",R.drawable.sponsorieee),

};

        myadapter = new Sponsoradapter(getApplicationContext(),sponsorreturn);

        recyclerView = findViewById(R.id.sponsorrecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(myadapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));







    }
}