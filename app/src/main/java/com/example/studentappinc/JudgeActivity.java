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
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;


public class JudgeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static int i;
    private long backPressedTime;
    private FirebaseDatabase database;
    private Firebase mRef1, mRef2, mRef3, lablocationreference, evaluatedreference;
    private DatabaseReference databaseReference;

    public String Notification;
    private static final String CHANNEL_ID = "INC";
    private static final String CHANNEL_NAME = "VAIBHAV";
    private static final String CHANNEL_DESC = "JUDGES ARE COMING";

    private NotificationManagerCompat notificationManager;

    public static final String SHARED_PEFS = "sharedPreds";
    public static final String TEXT = "text";
    public static final String SWITCH1 = "switch1";

    public String id, labloc;

    SharedPreferences result;
    SharedPreferences.Editor editor;
    Button addmark;

    public RecyclerView recyclerView;
    //  JudgecustomListAdapter judgecustomListAdapter;
    List<JudgeIDreturn> studentlist;
    JudgecustomListAdapter myadapter;

    public Toolbar mtoolbar;
    public DrawerLayout drawer;
    public ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.judge_activity);
        Firebase.setAndroidContext(this);
        addmark = (Button) findViewById(R.id.addmarkbtn);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mtoolbar = findViewById(R.id.toolb);
        setSupportActionBar(mtoolbar);


        drawer = findViewById(R.id.judge_drawer_layout);
        toggle = new ActionBarDrawerToggle(JudgeActivity.this, drawer, mtoolbar, R.string.navigation_darwer_open, R.string.navigation_darwer_close);
        toggle.setDrawerIndicatorEnabled(true);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    )
        }

        studentlist = new ArrayList<>();
        final JudgecustomListAdapter myadapter = new JudgecustomListAdapter(JudgeActivity.this, studentlist);

        result = getSharedPreferences("SaveData", MODE_PRIVATE);
        final String value = result.getString("Value", "Data not Found");

        mRef1 = new Firebase("https://master-app-inc.firebaseio.com/");
        database = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();


        mRef3 = new Firebase("https://master-app-inc.firebaseio.com/JudgeID/" + value + "/StudentIDalloted");
        mRef3.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(final DataSnapshot dataSnapshot1, String s) {
                //Toast.makeText(StudentActivity.this,dataSnapshot.getValue().toString() + " child added",Toast.LENGTH_SHORT).show();
               /*     i++;
                    studentlist.add(new JudgeIDreturn("ID "+ i +" :"+ dataSnapshot.getValue().toString()));
                    adapter.notifyDataSetChanged();
    */
                id = dataSnapshot1.getValue().toString();
                lablocationreference = new Firebase("https://master-app-inc.firebaseio.com/StudentID/" + id + "/Lablocation");
                lablocationreference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Toast.makeText(getApplicationContext(), dataSnapshot.getValue().toString(), Toast.LENGTH_LONG).show();
                        id = dataSnapshot1.getValue().toString();
                        labloc = dataSnapshot.getValue().toString();

                        studentlist.add(new JudgeIDreturn(id, labloc));
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
                String id;
                /*   final JudgeIDreturn studentIDposition = studentlist.get(position);
                   id= studentIDposition.getStudentID();*/
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

            public void onabsentbtnClick(int position) {
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.runtimeaddproject:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Add_dynamic_project()).commit();
                break;


        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
