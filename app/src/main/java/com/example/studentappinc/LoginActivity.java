package com.example.studentappinc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private static final String SHARED_PEFS = "sharedPreds";
    private String judgeid;
    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mLoginBtn;

    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public FirebaseAuth mAuth;
    Firebase firebase;

    FirebaseAuth.AuthStateListener mAuthListener;


    public boolean error = true;
FirebaseApp firebaseApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        Firebase.setAndroidContext(this);
// Initialize the default app
     //   FirebaseApp defaultApp = FirebaseApp.initializeApp(getApplicationContext());

        mAuth =FirebaseAuth.getInstance();



        mEmailField = (EditText) findViewById(R.id.emailField);
        mPasswordField = (EditText) findViewById(R.id.passwordField);

        Button loginbutton;
        loginbutton = findViewById(R.id.loginBtn);
        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null) {

                    if (firebaseAuth.getCurrentUser() != null) {
/*
                        mRef = new Firebase("https://master-app-inc.firebaseio.com/");
                        mRef.child("check").addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                if (dataSnapshot.getValue().toString().equals(mEmailField.getText().toString())) {
                                    judgeid = dataSnapshot.getKey();
                                    error = false;

                                }
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });

                        sharedPreferences = getSharedPreferences("SaveData", MODE_PRIVATE);
                        editor = sharedPreferences.edit();
                        editor.putInt("check", 1);
                        editor.putString("Value", judgeid);
                        editor.apply();
                        Firebase mRefchild = mRef.child("judgeid").child(judgeid);
*/
/*                        String token = task.getResult().getToken();
                        mRefchild.child("Token").setValue(token);*//*

                        mRefchild.child("ID").setValue(judgeid);

*/

                        //  startActivity(new Intent(LoginActivity.this,JudgeActivity.class));


                    }

                }
            }
        };

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startSignIn();



                /*
                startSignIn();
*/

               /* FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if(task.isSuccessful()){
                        }else
                        {
                            Toast.makeText(getBaseContext(),"Token not generated",Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
            }
        });

    }
/*

 @Override
    protected void onStart(){
      */
/*  String studentID = mFieldID.getText().toString();
        Toast.makeText(getApplicationContext(),studentID,Toast.LENGTH_LONG).show();
        Intent i =new Intent(LoginActivity.this,StudentActivity.class);
        i.putExtra("Value",studentID);
        startActivity(i);
        finish();
        startActivity(new Intent(LoginActivity.this,StudentActivity.class));
       *//*


        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
*/


    public void startSignIn() {

        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();
        // String studentID = mFieldID.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Fields are empty", Toast.LENGTH_LONG).show();

        } else {

            //new Firebase("https://master-app-inc.firebaseio.com/");

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull final Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        mRef.getReference().child("check").addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                if (dataSnapshot.getValue().toString().equals(mEmailField.getText().toString())) {
                                    judgeid = dataSnapshot.getKey();
                                    error = false;
                                }
                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        /*.addChildEventListener(new com.firebase.client.ChildEventListener() {
                            @Override
                            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });
*/
                        if (judgeid != null) {

                            sharedPreferences = getSharedPreferences("SaveData", MODE_PRIVATE);
                            editor = sharedPreferences.edit();
                            editor.putInt("check", 1);
                            editor.putString("Value", judgeid);
                            editor.apply();
                            DatabaseReference mRefchild = firebaseDatabase.getReference().child("judgeid").child(judgeid);

                                   /* String token = task.getResult().getToken();

                                    mRefchild.child("Token").setValue(token);
*/
                            mRefchild.child("ID").setValue(judgeid);


                            Intent intent = new Intent(getApplicationContext(), JudgeActivity.class);
                            startActivity(intent);


                        }
                    }

                }


            });


        }
    }


    public void toast(String x) {
        Toast.makeText(getApplicationContext(), x, Toast.LENGTH_SHORT).show();
    }

    public void contact(View view) {

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:9422668223"));
        startActivity(intent);

    }
}
