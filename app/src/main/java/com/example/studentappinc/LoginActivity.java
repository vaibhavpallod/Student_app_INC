package com.example.studentappinc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.studentappinc.StudentActivity.TEXT;

public class LoginActivity extends AppCompatActivity {
    private static final String SHARED_PEFS ="sharedPreds" ;
    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mLoginBtn;
    private EditText mFieldID;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    Firebase mRef;
    FirebaseDatabase database ;
    int posofspinner=0 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        Firebase.setAndroidContext(this);

        mAuth = FirebaseAuth.getInstance();
        mEmailField = (EditText)findViewById(R.id.emailField);
        mPasswordField= (EditText)findViewById(R.id.passwordField);
      //  mLoginBtn = (Button)findViewById(R.id.loginBtn);
        mFieldID = (EditText)findViewById(R.id.studentid);
        mRef = new Firebase("https://master-app-inc.firebaseio.com/");
        database = FirebaseDatabase.getInstance();

        Spinner spinner = findViewById(R.id.loginBtn);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.StudentIDs,android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();
                if(position==1)
                {   posofspinner = position;
                    FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if(task.isSuccessful()){
                                String studentID = mFieldID.getText().toString();
                                Firebase mRefchild = mRef.child("StudentID").child(studentID);
                                String token = task.getResult().getToken();
                                mRefchild.child("ID").setValue(studentID);
                                mRefchild.child("Token").setValue(token);


                                sharedPreferences = getSharedPreferences("SaveData",MODE_PRIVATE);
                                editor = sharedPreferences.edit();
                                editor.putString("Value",studentID);
                                editor.apply();


                                Intent intent = new Intent(getApplicationContext(), StudentActivity.class);
                                startActivity(intent);
                                startSignIn();


                            }else
                            {
                                Toast.makeText(getBaseContext(),"Token not generated",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else if(position == 2)
                { posofspinner = position;
                    FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if(task.isSuccessful()){
                                String JudgeID = mFieldID.getText().toString();
                                Firebase mRefchild = mRef.child("JudgeID").child(JudgeID);
                                String token = task.getResult().getToken();
                                mRefchild.child("ID").setValue(JudgeID);
                                mRefchild.child("Token").setValue(token);


                                sharedPreferences = getSharedPreferences("SaveData",MODE_PRIVATE);
                                editor = sharedPreferences.edit();
                                editor.putString("Value",JudgeID);
                                editor.apply();


                                Intent intent = new Intent(getApplicationContext(),JudgeActivity.class);
                                startActivity(intent);
                                startSignIn();


                            }else
                            {
                                Toast.makeText(getBaseContext(),"Token not generated",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()!=null){
                    String studentID = mFieldID.getText().toString();
                    if(posofspinner==1) {
                        Intent i = new Intent(LoginActivity.this, StudentActivity.class);
                        i.putExtra("Value", studentID);
                      //  startActivity(i);
                        finish();
                        saveData(studentID);
                        startActivity(new Intent(LoginActivity.this, StudentActivity.class));

                    }
                    if(posofspinner==2)
                    {
                        Intent i = new Intent(LoginActivity.this, JudgeActivity.class);
                        i.putExtra("Value", studentID);
                       // startActivity(i);
                        finish();
                        saveData(studentID);
                        startActivity(new Intent(LoginActivity.this, JudgeActivity.class));

                    }
                }
            }
        };

/*

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if(task.isSuccessful()){
                            String studentID = mFieldID.getText().toString();
                            Firebase mRefchild = mRef.child("StudentID").child(studentID);
                            String token = task.getResult().getToken();
                            mRefchild.child("ID").setValue(studentID);
                            mRefchild.child("Token").setValue(token);


                            sharedPreferences = getSharedPreferences("SaveData",MODE_PRIVATE);
                            editor = sharedPreferences.edit();
                            editor.putString("Value",studentID);
                            editor.apply();


                            Intent intent = new Intent(getApplicationContext(),StudentActivity.class);
                            startActivity(intent);


                        }else
                        {
                            Toast.makeText(getBaseContext(),"Token not generated",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                startSignIn();
            }
        });
*/



    }

    public void saveData(String StudenID){

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PEFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT,StudenID);

    }

    @Override
    protected void onStart(){
      /*  String studentID = mFieldID.getText().toString();
        Toast.makeText(getApplicationContext(),studentID,Toast.LENGTH_LONG).show();
        Intent i =new Intent(LoginActivity.this,StudentActivity.class);
        i.putExtra("Value",studentID);
        startActivity(i);
        finish();
        startActivity(new Intent(LoginActivity.this,StudentActivity.class));
       */


        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }




    private void startSignIn() {

        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();
        String studentID = mFieldID.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this,"Fields are empty",Toast.LENGTH_LONG  ).show();
        }
        else{
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Sign in Problem", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }
}
