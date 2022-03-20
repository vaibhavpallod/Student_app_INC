package v.s.p.navigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import v.s.p.Sponsoradapter;
import v.s.p.Sponsorreturn;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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

        Toolbar toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("SPONSORS");
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.arrow_left_back, null);
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, Color.WHITE);

        toolbar.setNavigationIcon(drawable);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setBackgroundColor(Color.parseColor("#06023b"));

        setSupportActionBar(toolbar);

//        getActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setTitle("SPONSORS");

//        getWindow().setTitleColor(getResources().getColor(R.color.white));

//        getSupportActionBar().set;//.getCustomView().setBackgroundColor(getResources().getColor(R.color.white));
//        getActionBar().getThemedContext().setTheme(R.style.TitleTextStyle);


//getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#06023b")));
        Sponsorreturn[] sponsorreturn = new Sponsorreturn[]{
                new Sponsorreturn("Title Sponsor",R.drawable.sponsor_imocha),
                new Sponsorreturn("Co-Sponsor",R.drawable.sponsor_bajaj),
                new Sponsorreturn("Co-Sponsor",R.drawable.sponsor_airavana),
                new Sponsorreturn("Concepts Sponsor",R.drawable.sponsor_eq_logo_transparent),
                new Sponsorreturn("Concepts Sponsor",R.drawable.sponsor_eqube),
                new Sponsorreturn("Pradnya Sponsor",R.drawable.sponsor_veritas),
                new Sponsorreturn("Other Sponsor",R.drawable.sponsor_algoanalytics),
                new Sponsorreturn("Other Sponsor",R.drawable.sponsor_ieeepunesectionlogo),
                new Sponsorreturn("Other Sponsor",R.drawable.sponsor_pasc),
                new Sponsorreturn("Other Sponsor",R.drawable.sponsor_pcsb),
                new Sponsorreturn("Online Media Partner",R.drawable.sponsor_collegedunia),


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