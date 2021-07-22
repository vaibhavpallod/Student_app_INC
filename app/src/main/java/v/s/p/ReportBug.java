package v.s.p;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentappinc.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReportBug extends AppCompatActivity {
    ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_bug_activity);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Report a Bug");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#357ec7")));

        SharedPreferences result;
        result = getSharedPreferences("SaveData", MODE_PRIVATE);
        final String value = result.getString("Value", "Data not Found");

        Button btn = (Button)findViewById(R.id.submitreport);
        final EditText desrip = findViewById(R.id.decripedittext);
        final EditText androidversion = findViewById(R.id.androidvedittext);
        final EditText name = findViewById(R.id.yournamereportedittext);
        cd = new ConnectionDetector(this);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!cd.isConnected())
                    Toast.makeText(getApplicationContext(),"Please check Internet Connection",Toast.LENGTH_LONG).show();
                else if(desrip.getText().toString().matches("") || androidversion.getText().toString().matches("") || name.getText().toString().matches("")){
                    if (desrip.getText().toString().matches(""))
                        desrip.setError("Field is empty");
                    else if(androidversion.getText().toString().matches(""))
                        androidversion.setError("Field is empty");
                    else if(name.getText().toString().matches(""))
                        name.setError("Field is empty");

                }
                else {

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference.child("Bug Report").child(value).child("description").setValue(desrip.getText().toString());
                    databaseReference.child("Bug Report").child(value).child("android version").setValue(androidversion.getText().toString());
                    databaseReference.child("Bug Report").child(value).child("name").setValue(name.getText().toString());




                    Toast.makeText(getApplicationContext(),"Thank You!",Toast.LENGTH_LONG).show();
                    Intent intent4 = new Intent(getApplicationContext(),j.class);
                    startActivity(intent4);
                    finish();
                }

            }
        });

    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}