package v.s.p;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;

import com.example.studentappinc.R;

public class Aboutus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus_activity);

        Window window = this.getWindow();
//        window.setStatusBarColor(getResources().getColor(R.color.statusbarorange));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("About Us");
       getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffae14")));
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}