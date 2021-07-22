package v.s.p;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.example.studentappinc.R;

public class instruction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instruction_activity);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Instructions");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#06023b")));



    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}