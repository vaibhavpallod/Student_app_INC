package v.s.p.Activities;

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

public class activity_Developers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developers_activity);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Developers");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#357ec7")));

        AppCompatTextView mTitleTextView = new AppCompatTextView(getApplicationContext());
        mTitleTextView.setSingleLine();
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        getSupportActionBar().setCustomView(mTitleTextView, layoutParams);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);
        mTitleTextView.setText("Developers");

        mTitleTextView.setTextColor(Color.BLACK);
        mTitleTextView.setTextSize(2,22);
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
            case R.id.vaibhavcall:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+919422668223"));
                startActivity(intent);
                break;
            case R.id.tejascall:
                Intent intent2 = new Intent(Intent.ACTION_DIAL);
                intent2.setData(Uri.parse("tel:+917875664474"));
                startActivity(intent2);
                break;

            case R.id.vaibhavlinkedin:
                Uri uri = Uri.parse("https://www.linkedin.com/in/vaibhav-pallod-556ba51a3/"); // missing 'http://' will cause crashed
                Intent intent3 = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent3);
                break;

            case R.id.tejaslinkedin:
                Uri uri2 = Uri.parse("https://in.linkedin.com/in/tejas-dahad-b2a492b3"); // missing 'http://' will cause crashed
                Intent intent4 = new Intent(Intent.ACTION_VIEW, uri2);
                startActivity(intent4);
                break;

            case R.id.vaibhavgit:
                Uri uri3 = Uri.parse("https://github.com/vaibhavpallod"); // missing 'http://' will cause crashed
                Intent intent5 = new Intent(Intent.ACTION_VIEW, uri3);
                startActivity(intent5);
                break;

            case R.id.tejasgithub:
                Uri uri4 = Uri.parse("https://github.com/tejasdahad"); // missing 'http://' will cause crashed
                Intent intent6 = new Intent(Intent.ACTION_VIEW,uri4);
                startActivity(intent6);
                break;


            case R.id.vaibhavgmail:
                Intent intent7 = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "vaibhavpallod@gmail.com"));
//                Uri uri5 = Uri.parse("https://github.com/vaibhavpallod"); // missing 'http://' will cause crashed
//                Intent intent7 = new Intent(Intent.ACTION_VIEW,uri5);
                startActivity(intent7);
                break;

            case R.id.tejasgmail:
                Intent intent8 = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "tejasdahad@gmail.com"));
                startActivity(intent8);
                break;


        }
    }

}