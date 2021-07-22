package v.s.p;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.studentappinc.R;

public class ContactUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us_activity);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Contact Us");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#357ec7")));

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void contact2(View view) {

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:+919422668223"));
        startActivity(intent);

    }


}