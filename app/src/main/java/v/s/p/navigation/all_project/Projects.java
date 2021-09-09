package v.s.p.navigation.all_project;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.studentappinc.R;
import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;
import v.s.p.Adapters.JudgecustomListAdapter;
import v.s.p.Adapters.ProjectAdapter;
import v.s.p.Classes.ProjectClass;

public class Projects extends AppCompatActivity {
    LinearLayout expandableView;
    Button arrowBtn;
    CardView cardView;


    RecyclerView recyclerView;
    public List<ProjectClass> projectList;
    JudgecustomListAdapter myadapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        Firebase.setAndroidContext(this);
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        ArrayList<String> subName = getIntent().getStringArrayListExtra("domain");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(subName.get(1));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffae14")));

        projectList = new ArrayList<>();
        System.out.println("DOMAIN " + subName.get(0));
        expandableView = findViewById(R.id.expandableView);
        arrowBtn = findViewById(R.id.arrowBtn);
        cardView = findViewById(R.id.cardView);

        recyclerView =findViewById(R.id.recyclerviewallStudent);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final ProjectAdapter myadapter = new ProjectAdapter(Projects.this, projectList);

        mDatabase.child("studentdata").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.getKey().startsWith(subName.get(0))) {
                        System.out.println("YESS DOMAIN " + data.getKey());
                        ProjectClass obj = new ProjectClass(data.getKey(),
                                data.child("Title").getValue().toString(),
                                data.child("abstract").getValue().toString(),
                                data.child("college").getValue().toString(),
                                data.child("domain").getValue().toString(),
                                data.child("email1").getValue().toString(),
                                data.child("name1").getValue().toString(),
                                data.child("phone1").getValue().toString(),
                                data.child("projecttype").getValue().toString()
                        );
                        projectList.add(obj);
                    }
                }
                myadapter.notifyDataSetChanged();
                recyclerView.setAdapter(myadapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
