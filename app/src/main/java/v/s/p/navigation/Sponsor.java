package v.s.p.navigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import v.s.p.Sponsoradapter;
import v.s.p.Sponsorreturn;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.example.studentappinc.R;

import java.util.List;

public class Sponsor extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Sponsorreturn> sponsorList;
    Sponsoradapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sponsors_mainactivity);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("SPONSORS");


getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#06023b")));
        Sponsorreturn[] sponsorreturn = new Sponsorreturn[]{
                new Sponsorreturn("SPONSOR",R.drawable.sponsormicrosoft),
                new Sponsorreturn("SPONSOR",R.drawable.sponsoreqtech),
                new Sponsorreturn("SPONSOR",R.drawable.sponsorcakesoft),
                new Sponsorreturn("SPONSOR",R.drawable.sponsorspriq),
                new Sponsorreturn("SPONSOR",R.drawable.sponsorsag),
                new Sponsorreturn("OTHER SPONSOR",R.drawable.sponsorcsi),
                new Sponsorreturn("OTHER SPONSOR",R.drawable.sponsoraccm),
                new Sponsorreturn("OTHER SPONSOR",R.drawable.sponsorieee)


};

        myadapter = new Sponsoradapter(getApplicationContext(),sponsorreturn);

        recyclerView = findViewById(R.id.sponsorrecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(myadapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));







    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}