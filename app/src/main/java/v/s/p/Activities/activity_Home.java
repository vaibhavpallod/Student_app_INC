package v.s.p.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentappinc.R;
import com.firebase.client.Firebase;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import v.s.p.Adapters.JudgecustomListAdapter;
import v.s.p.Calculate;
import v.s.p.Classes.JudgeIDreturn;
import v.s.p.LoginActivity;
import v.s.p.instruction;
import v.s.p.navigation.Aboutus;
import v.s.p.navigation.ContactUs;
import v.s.p.navigation.Feedback_form;
import v.s.p.navigation.ReportBug;
import v.s.p.navigation.Sponsor;
import v.s.p.navigation.all_project.Add_dynamic_project;


public class activity_Home extends AppCompatActivity implements OnNavigationItemSelectedListener {

    public static int i;
    private long backPressedTime;

    private RecyclerViewReadyCallback recyclerViewReadyCallback;

    public String namejudge, emailjudge;

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

    public String judgeid;
    private DatabaseReference disablereference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.judge_activity);
        Firebase.setAndroidContext(this);
        addmark = (Button) findViewById(R.id.addmarkbtn);

        mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        studentlist = new ArrayList<>();

        drawer = findViewById(R.id.judge_drawer_layout);
        toggle = new ActionBarDrawerToggle(activity_Home.this, drawer, mtoolbar, R.string.navigation_darwer_open, R.string.navigation_darwer_close);
        toggle.setDrawerIndicatorEnabled(true);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.inflateHeaderView(R.layout.navigation_bar);
        final TextView nameheader, emailheader;
        nameheader = view.findViewById(R.id.judgenameheader);
        emailheader = view.findViewById(R.id.judgeemailheader);

        final JudgecustomListAdapter myadapter = new JudgecustomListAdapter(activity_Home.this, studentlist);

        result = getSharedPreferences("SaveData", MODE_PRIVATE);
        final String value = result.getString("Value", "Data not Found");

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


//        Firebase navrefname = new Firebase("https://master-app-inc.firebaseio.com/judgeid/"+value);
//        Firebase navrefemail = new Firebase("https://master-app-inc.firebaseio.com/judgeid/"+value);

        mDatabase.child("judgeid").child(value).addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                // Toast.makeText(activity_Home.this,dataSnapshot.child("name").getValue().toString(), Toast.LENGTH_SHORT).show();
                nameheader.setText(dataSnapshot.child("name").getValue().toString());
                emailheader.setText(dataSnapshot.child("email").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabase.child("judgeid").child(value).child("StudentIDalloted").addChildEventListener(new com.google.firebase.database.ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull final com.google.firebase.database.DataSnapshot dataSnapshot1, @Nullable String s) {
                id = dataSnapshot1.getValue().toString();
                DatabaseReference titlereference = mDatabase.child("studentdata").child(id).child("Title");
                titlereference.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                        id = dataSnapshot1.getValue().toString();
                        String projecttitle = dataSnapshot.getValue().toString();

                        studentlist.add(new JudgeIDreturn(id, projecttitle));
                        myadapter.notifyDataSetChanged();
                        recyclerView.setAdapter(myadapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        disablereference = FirebaseDatabase.getInstance().getReference().child("judgeid").child(value);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        myadapter.setOnItemClickListner(new JudgecustomListAdapter.OnItemClickListner() {
            @Override
            public void onaddmarkClick(int position) {
/*
                if(!(recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.addmarkbtn).isEnabled()))
                    toast("Double evaluation not allowed");*/

            }

            public void onabsentbtnClick(int position) {
                Calculate calculate;


              /*  String id;
                new AlertDialog.Builder(activity_Home.this).setTitle("zero makrs will be assigned ").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toast("absent button clicked");
                    }
                }).setNegativeButton("No", null).show();

*/

            }


        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        if (item.getItemId() == R.id.signOutMenuId) {
//            FirebaseAuth.getInstance().signOut();
//
//            sharedPreferences = getSharedPreferences("SaveData", MODE_PRIVATE);
//            editor = sharedPreferences.edit();
//            editor.putInt("check", 0);
//
//            editor.apply();
//
//            Intent intent = new Intent(this, LoginActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//            finish();
//        }

        if (item.getItemId() == R.id.instruction) {

            Intent intent = new Intent(this, instruction.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

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

    public void toast(String x) {
        Toast.makeText(getApplicationContext(), x, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

//            case R.id.allprojects:
//                Intent intent0 = new Intent(getApplicationContext(), Add_dynamic_project.class);
//                startActivity(intent0);
//                break;


            case R.id.visitwebsiteid:
                Uri uri = Uri.parse("http://pictinc.org/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;

            case R.id.about:
                Intent intent1 = new Intent(getApplicationContext(), Aboutus.class);
                startActivity(intent1);
                break;

            case R.id.sponsor:
                Intent intent2 = new Intent(getApplicationContext(), Sponsor.class);
                startActivity(intent2);
                break;

            case R.id.contactud:
                Intent intent3 = new Intent(getApplicationContext(), ContactUs.class);
                startActivity(intent3);
                break;
            case R.id.developernav:
                Intent intentd = new Intent(getApplicationContext(), activity_Developers.class);
                startActivity(intentd);
                break;
//            case R.id.reportbugid:
//                Intent intent5 = new Intent(getApplicationContext(), ReportBug.class);
//                startActivity(intent5);
//                break;


            case R.id.feedbacknav:
                 // https://docs.google.com/forms/d/e/1FAIpQLSfnpguXsDkrd9Z1C8KKk8382Gr9-BMsAPb_BD4_EZ9X27B24w/viewform
                Uri uri0 = Uri.parse("https://rebrand.ly/incform"); // missing 'http://' will cause crashed
                Intent intent0 = new Intent(Intent.ACTION_VIEW, uri0);
                startActivity(intent0);
                break;

            case R.id.rateplay:
                Uri uri1 = Uri.parse("https://play.google.com/store/apps/details?id=v.s.p"); // missing 'http://' will cause crashed
                Intent intent6 = new Intent(Intent.ACTION_VIEW, uri1);
                startActivity(intent6);
                break;


            case R.id.logoutnav:
                FirebaseAuth.getInstance().signOut();

                sharedPreferences = getSharedPreferences("SaveData", MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putInt("check", 0);

                editor.apply();

                Intent intent9 = new Intent(this, LoginActivity.class);
                intent9.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent9);
                finish();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    public interface RecyclerViewReadyCallback {
        void onLayoutReady();
    }


}
