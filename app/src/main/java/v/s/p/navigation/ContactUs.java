package v.s.p.navigation;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.example.studentappinc.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

public class ContactUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us_activity);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Contact Us");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#357ec7")));

        AppCompatTextView mTitleTextView = new AppCompatTextView(getApplicationContext());
        mTitleTextView.setSingleLine();
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        getSupportActionBar().setCustomView(mTitleTextView, layoutParams);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);
        mTitleTextView.setText("Contact Us");

        mTitleTextView.setTextColor(Color.BLACK);
        mTitleTextView.setTextSize(2, 22);
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

    public void call(View view) {
        switch (view.getId()) {
            case R.id.vaibhavContactusMail:
                Intent intent7 = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "vaibhavpallod@gmail.com"));
                startActivity(intent7);
                break;
            case R.id.tusharContactusMail:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "tarane@pict.edu"));

                startActivity(intent);
                break;
        }

    }


}