package com.example.studentappinc;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class StudentActivity extends AppCompatActivity {
    private static final String TAG = "StudentActivity";
    public  int i;
    private long backPressedTime;
    private FirebaseDatabase database;
    private Firebase mRef1,notify,mRef3;
    private DatabaseReference databaseReference;

    public String Notification;
    private static final String CHANNEL_ID = "INC";
    private static final String CHANNEL_NAME = "VAIBHAV";
    private static final String CHANNEL_DESC = "JUDGES ARE COMING";

    private NotificationManagerCompat notificationManager;

    public static final String SHARED_PEFS = "sharedPreds";
    public static final String TEXT = "text";
    public  static final String SWITCH1 = "switch1";
    public String text;
    private TextView textView;

    SharedPreferences result;
    SharedPreferences.Editor editor;

    StudentcustomListAdapter studentcustomListAdapter;

    public RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    public String totaljudgeprojects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final List<StudentIDreturn> judgelist;

        final StudentcustomListAdapter.MyviewHolder holder ;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewstudent);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        judgelist = new ArrayList<>();

        final StudentcustomListAdapter adapter = new StudentcustomListAdapter(StudentActivity.this,judgelist);


        result = getSharedPreferences("SaveData",MODE_PRIVATE);
        String value = result.getString("Value","Data not Found");
        database = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        EditText editTextstudentID=(EditText)findViewById(R.id.studentid);
        textView = (TextView)findViewById(R.id.studentid);

        swipeRefreshLayout =findViewById(R.id.swipe);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               // recyclerView.setAdapter(null);
                recyclerView.setAdapter(adapter);
               /* adapter.updateData(judgelist);
                adapter.notifyDataSetChanged();
                */new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },1000);

            }
        });

       /* mRef1 = new Firebase("https://master-app-inc.firebaseio.com/JudgeID/15/");

        mRef1.child("allocated").addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        //  mRef1.child("evaluated");
        mRef1.child("evaluated");
        mRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String judgedprojects = dataSnapshot.getValue().toString();
                //  String put = judgedprojects + " / " + totaljudgeprojects;
                //   Toast.makeText(getApplicationContext(),put,Toast.LENGTH_SHORT).show();
                //   holder.judgedprojects.setText(put);

                adapter.updateData(judgelist);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
*/


        mRef3 = new Firebase("https://master-app-inc.firebaseio.com/StudentID/"+value+"/Judgealloted");

       mRef3.addChildEventListener(new ChildEventListener() {
           @Override

           public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               //Toast.makeText(StudentActivity.this,dataSnapshot.getValue().toString() + " child added",Toast.LENGTH_SHORT).show();
               i++;
//"Judge ID "+ i +" : "+
               judgelist.add(new StudentIDreturn(dataSnapshot.getValue().toString(),"0 / "+totaljudgeprojects));
               adapter.notifyDataSetChanged();

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


        notify = new Firebase("https://master-app-inc.firebaseio.com/StudentID/"+value+"/Notification");

        notify.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Notification= dataSnapshot.getValue().toString();
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
                    notificationChannel.setDescription(CHANNEL_DESC);
                    NotificationManager manager = getSystemService((NotificationManager.class));
                    manager.createNotificationChannel(notificationChannel);
                }

                notificationManager = NotificationManagerCompat.from(StudentActivity.this);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(StudentActivity.this,CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_inclogo)
                        .setContentTitle(Notification).setContentText("He will arrive in some time ").setPriority(NotificationCompat.PRIORITY_HIGH) ;

                notificationManager.notify(0, builder.build());
            }




            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });


        recyclerView.setAdapter(adapter);
    }


    protected void displayNotification()
    {
    notificationManager = NotificationManagerCompat.from(this);
        android.app.Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_inclogo)
            .setContentTitle(Notification).setContentText("He will arrive in some time ").setPriority(NotificationCompat.PRIORITY_HIGH).build() ;

            notificationManager.notify(1,notification);
    }


    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
            }
            return;
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();


    }



}
