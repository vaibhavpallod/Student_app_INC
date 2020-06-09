package com.example.studentappinc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class JudgeActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {

    public static int i;
    private long backPressedTime;
    private Firebase disablereference, mRef3, titlereference, evaluatedreference;

    private RecyclerViewReadyCallback recyclerViewReadyCallback;

    public String namejudge,emailjudge;

    public String Notification;
    private static final String CHANNEL_ID = "INC";
    private static final String CHANNEL_NAME = "VAIBHAV";
    private static final String CHANNEL_DESC = "JUDGES ARE COMING";

    private NotificationManagerCompat notificationManager;

    public static final String SHARED_PEFS = "sharedPreds";
    public static final String TEXT = "text";
    public static final String SWITCH1 = "switch1";

    public String id;

    SharedPreferences result;
    SharedPreferences.Editor editor;
    Button addmark;

    RecyclerView recyclerView;
    ArrayList disable = new ArrayList();
    public List<JudgeIDreturn> studentlist;
    JudgecustomListAdapter myadapter;

    public Toolbar mtoolbar;
    public DrawerLayout drawer;
    public ActionBarDrawerToggle toggle;

    public SharedPreferences sharedPreferences;
    Firebase mRef;

    public   String judgeid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.judge_activity);
        Firebase.setAndroidContext(this);
        addmark = (Button) findViewById(R.id.addmarkbtn);

        mtoolbar = findViewById(R.id.toolb);
        setSupportActionBar(mtoolbar);
        studentlist = new ArrayList<>();

        drawer = findViewById(R.id.judge_drawer_layout);
        toggle = new ActionBarDrawerToggle(JudgeActivity.this, drawer, mtoolbar, R.string.navigation_darwer_open, R.string.navigation_darwer_close);
        toggle.setDrawerIndicatorEnabled(true);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.inflateHeaderView(R.layout.navigation_bar);
        final TextView nameheader,emailheader;
        nameheader = view.findViewById(R.id.judgenameheader);
        emailheader = view.findViewById(R.id.judgeemailheader);

        final JudgecustomListAdapter myadapter = new JudgecustomListAdapter(JudgeActivity.this, studentlist);

        result = getSharedPreferences("SaveData", MODE_PRIVATE);
        final String value = result.getString("Value", "Data not Found");

        Firebase navrefname = new Firebase("https://master-app-inc.firebaseio.com/judgeid/"+value);
        Firebase navrefemail = new Firebase("https://master-app-inc.firebaseio.com/judgeid/"+value);

        navrefname.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               nameheader.setText(dataSnapshot.child("name").getValue().toString());
               emailheader.setText(dataSnapshot.child("email").getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        disablereference = new Firebase("https://master-app-inc.firebaseio.com/judgeid/"+value);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setHasFixedSize(true);
           // recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setLayoutManager(new LinearLayoutManager(this) {
                @Override
                public void onLayoutCompleted(final RecyclerView.State state) {
                    super.onLayoutCompleted(state);
                    if (getIntent().getIntExtra("success", 500) != 500 ) {
                        int x = getIntent().getIntExtra("success", 500);

                        disablereference.child("disable").child(String.valueOf(x)).setValue(x);
                        disablereference.child("disable").addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                recyclerView.findViewHolderForAdapterPosition(Integer.valueOf(dataSnapshot.getValue().toString())).itemView.findViewById(R.id.addmarkbtn).setEnabled(false);
                                recyclerView.findViewHolderForAdapterPosition(Integer.valueOf(dataSnapshot.getValue().toString())).itemView.findViewById(R.id.addmarkbtn).setClickable(false);
                                recyclerView.findViewHolderForAdapterPosition(Integer.valueOf(dataSnapshot.getValue().toString())).itemView.findViewById(R.id.absentbtn).setEnabled(false);
                                recyclerView.findViewHolderForAdapterPosition(Integer.valueOf(dataSnapshot.getValue().toString())).itemView.findViewById(R.id.absentbtn).setClickable(false);



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


                    }
                    else {

                        disablereference.child("disable").addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                recyclerView.findViewHolderForAdapterPosition(Integer.valueOf(dataSnapshot.getValue().toString())).itemView.findViewById(R.id.addmarkbtn).setEnabled(false);
                                recyclerView.findViewHolderForAdapterPosition(Integer.valueOf(dataSnapshot.getValue().toString())).itemView.findViewById(R.id.addmarkbtn).setClickable(false);
                                recyclerView.findViewHolderForAdapterPosition(Integer.valueOf(dataSnapshot.getValue().toString())).itemView.findViewById(R.id.absentbtn).setEnabled(false);
                                recyclerView.findViewHolderForAdapterPosition(Integer.valueOf(dataSnapshot.getValue().toString())).itemView.findViewById(R.id.absentbtn).setClickable(false);

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

                    }

                }
            });

        mRef3 = new Firebase("https://master-app-inc.firebaseio.com/judgeid/" + value + "/StudentIDalloted");
        mRef3.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(final DataSnapshot dataSnapshot1, String s) {
                //Toast.makeText(StudentActivity.this,dataSnapshot.getValue().toString() + " child added",Toast.LENGTH_SHORT).show();
               /*     i++;
                    studentlist.add(new JudgeIDreturn("ID "+ i +" :"+ dataSnapshot.getValue().toString()));
                    adapter.notifyDataSetChanged();
    */


                id = dataSnapshot1.getValue().toString();
                titlereference = new Firebase("https://master-app-inc.firebaseio.com/studentdata/" + id + "/Title");
                titlereference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
              //        Toast.makeText(getApplicationContext(), dataSnapshot.getValue().toString(), Toast.LENGTH_LONG).show();
                        id = dataSnapshot1.getValue().toString();
                        String projecttitle = dataSnapshot.getValue().toString();

                        studentlist.add(new JudgeIDreturn(id, projecttitle));
                        myadapter.notifyDataSetChanged();
                        recyclerView.setAdapter(myadapter);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                // Toast.makeText(getApplicationContext(),labloc,Toast.LENGTH_LONG).show();

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



        myadapter.setOnItemClickListner(new JudgecustomListAdapter.OnItemClickListner() {
            @Override
            public void onaddmarkClick(int position) {
/*
                if(!(recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.addmarkbtn).isEnabled()))
                    toast("Double evaluation not allowed");*/

            }

            public void onabsentbtnClick(int position) {
                Firebase mRef1= new Firebase("https://master-app-inc.firebaseio.com/result/");
                Calculate calculate;


              /*  String id;
                new AlertDialog.Builder(JudgeActivity.this).setTitle("zero makrs will be assigned ").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toast("absent button clicked");
                    }
                }).setNegativeButton("No", null).show();

*/

            }

            public void onnotintrestedClick(int position) {
                String id;
                new AlertDialog.Builder(JudgeActivity.this).setTitle("Are you sure ?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        evaluatedreference = new Firebase("https://master-app-inc.firebaseio.com/JudgeID/" + value + "/evaluated");
                        evaluatedreference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                int i = Integer.parseInt(dataSnapshot.getValue().toString());
                                i++;
                                evaluatedreference.setValue(i);

                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });
                    }
                }).setNegativeButton("No", null).show();

            }

        });


    }







   /* @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
// Sync the toggle state after onRestoreInstanceState has occurred.
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }*/
/*

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home :
                drawer.openDrawer(GravityCompat.START);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

      ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_navigation_button_24dp);*/


    protected void displayNotification() {
        notificationManager = NotificationManagerCompat.from(this);
        android.app.Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_inclogo)
                .setContentTitle(Notification).setContentText("He will arrive in some time ").setPriority(NotificationCompat.PRIORITY_HIGH).build();

        notificationManager.notify(1, notification);
    }


    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (backPressedTime + 2000 > System.currentTimeMillis()) {
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
    public void toast(String x)
    {
        Toast.makeText(getApplicationContext(),x,Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.visitwebsiteid:
                Uri uri = Uri.parse("http://pictinc.org/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;

            case  R.id.about:
                Intent intent1 = new Intent(getApplicationContext(),Aboutus.class);
                startActivity(intent1);
                break;

            case R.id.sponsor:
                Intent intent2 = new Intent(getApplicationContext(),Sponsor.class);
                startActivity(intent2);
                break;





        }
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    public interface RecyclerViewReadyCallback {
        void onLayoutReady();
    }



}
