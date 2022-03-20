package v.s.p.navigation.all_project;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.studentappinc.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class Add_dynamic_project extends AppCompatActivity {
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_dynamic_project_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("All Projects");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffae14")));
        gridView = (GridView) findViewById(R.id.gridView);

        Map<String, String> assignmap = new HashMap<>();
        assignmap.put("ap", "AP");
        assignmap.put("co", "CN");
        assignmap.put("em", "ES");
        assignmap.put("ot", "OT");
        assignmap.put("vi", "VA");
        assignmap.put("vl", "VD");
        assignmap.put("di", "DSP");
        assignmap.put("ma", "ML");
        assignmap.put("bi", "BD");
        assignmap.put("bl", "BL");

        String[] eventName = {"Machine Learning", "Blockchain", "Communication Networks", "Digital / Image/ Speech / Video Processing",
                "Embedded Systems", "Others", "Virtualization and Autonomic Computing", "VLSI Design & Test"};
        int[] eventImages = {R.drawable.ml, R.drawable.blockchain, R.drawable.cn, R.drawable.dsp, R.drawable.embsys, R.drawable.idea, R.drawable.data_virtualization,
                R.drawable.vlsi};

        Map<String, String> map = new HashMap<String, String>();

        for (int x = 0; x < eventName.length; x++) {
            map.put(eventName[x], assignmap.get(eventName[x].toLowerCase().substring(0, 2)));
        }

        // Big Data Analytics, Applications is remaining


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            map.forEach((key, value) -> {
                System.out.println("Value of DOMAIN " + key + " is " + value);
            });
        }


        GridAdapter gridAdapter = new GridAdapter(this, eventName, eventImages);

        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // Send intent to SingleViewActivity
                Intent i = new Intent(getApplicationContext(), Projects.class);

                // Pass image index
                i.putStringArrayListExtra("domain", new ArrayList<String>() {{
                    add(map.get(eventName[position]));
                    add(eventName[position]);
                }});
                startActivity(i);
            }
        });
        /*
        binding.gridView.setAdapter(gridAdapter);


        binding.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(MainActivity.this,"You Clicked on "+ flowerName[position],Toast.LENGTH_SHORT).show();

            }
        });

*/

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
