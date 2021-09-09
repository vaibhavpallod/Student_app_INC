package v.s.p;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentappinc.R;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
ConnectionDetector cd;
//    public FirebaseAuth.AuthStateListener mAuthListener;

    DatabaseReference mRef;

    public boolean error = true;
    FirebaseApp firebaseApp;
    public FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        Firebase.setAndroidContext(this);

        firebaseApp = FirebaseApp.getInstance();
        mAuth =FirebaseAuth.getInstance();
        cd = new ConnectionDetector(this);

        mEmailField = (EditText) findViewById(R.id.emailField);
        mPasswordField = (EditText) findViewById(R.id.passwordField);

        Button loginbutton;

        if(!cd.isConnected())
            toast("Please check Internet Connection");

        loginbutton = findViewById(R.id.loginBtn);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {

                    if (firebaseAuth.getCurrentUser() != null) {

                        //  startActivity(new Intent(LoginActivity.this,j.class));


                    }

                }
            }
        };

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cd.isConnected())
                startSignIn();
                else
                toast("Please Check Internet Connection");

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

 @Override
    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    public void startSignIn() {

        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();
        // String studentID = mFieldID.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Fields are empty", Toast.LENGTH_LONG).show();

        } else {
           final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull final Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        mRef=FirebaseDatabase.getInstance().getReference();
                        mDatabase.child("check").addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                if (dataSnapshot.getValue().toString().equals(mEmailField.getText().toString())) {
                                    judgeid = dataSnapshot.getKey();
                                    error = false;
                                    if (judgeid != null) {
                                        Toast.makeText(LoginActivity.this, String.valueOf(judgeid), Toast.LENGTH_SHORT).show();
                                        sharedPreferences = getSharedPreferences("SaveData", MODE_PRIVATE);
                                        editor = sharedPreferences.edit();
                                        editor.putInt("check", 1);
                                        editor.putString("Value", judgeid);
                                        editor.apply();
                                        DatabaseReference mRefchild = mRef.child("judgeid").child(judgeid);

                                   /* String token = task.getResult().getToken();

                                    mRefchild.child("Token").setValue(token);
*/
                                        mRefchild.child("ID").setValue(judgeid);
                                        Intent intent = new Intent(getApplicationContext(), j.class);
                                        startActivity(intent);
                                    }
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

                    }
                    else{
                        toast("Incorrect email or password");
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
