package com.example.studentappinc;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


//import static com.example.studentappinc.JudgeActivity.marklist;

public class Add_marks extends AppCompatActivity {
    String name, id;

    SharedPreferences result;


    static List<Calculate> array = new ArrayList<>();
    int last = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_marks);

        int totaladdition = 0;

        result = getSharedPreferences("SaveData", MODE_PRIVATE);
        final String value = result.getString("Value", "Data not Found");

        final EditText myEditText1 = (EditText) findViewById(R.id.edittext1);
        myEditText1.setFilters(new InputFilter[]{new MinMaxFilter("0", "20")});

        final EditText myEditText2 = (EditText) findViewById(R.id.edittext2);
        myEditText2.setFilters(new InputFilter[]{new MinMaxFilter("0", "20")});

        final EditText myEditText3 = (EditText) findViewById(R.id.edittext3);
        myEditText3.setFilters(new InputFilter[]{new MinMaxFilter("0", "20")});

        final EditText myEditText4 = (EditText) findViewById(R.id.edittext4);
        myEditText4.setFilters(new InputFilter[]{new MinMaxFilter("0", "20")});

        final EditText myEditText5 = (EditText) findViewById(R.id.edittext5);
        myEditText5.setFilters(new InputFilter[]{new MinMaxFilter("0", "20")});


        TextView projectname = findViewById(R.id.projectnameaddmark);
        TextView projectid = findViewById(R.id.projectidaddmark);
        if (array.size() == 0)
            array = new ArrayList<>();

        projectname.setText(getIntent().getStringExtra("name"));
        projectid.setText(getIntent().getStringExtra("id"));

        id = getIntent().getStringExtra("id");

        final int position = getIntent().getIntExtra("position", 990);

        final int x = position;
        Button savebtn = findViewById(R.id.savebtn);

        Firebase lastref = new Firebase("https://master-app-inc.firebaseio.com/judgeid/" + value);
        lastref.child("disable").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                last = Integer.valueOf(String.valueOf(dataSnapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final TextView totaltextview = findViewById(R.id.totaladdmarks);
                if (!TextUtils.isEmpty(myEditText5.getText()) && !TextUtils.isEmpty(myEditText4.getText())
                        && !TextUtils.isEmpty(myEditText3.getText()) && !TextUtils.isEmpty(myEditText2.getText()) && !TextUtils.isEmpty(myEditText1.getText()) && getIntent().getIntExtra("button", 100) == 1) {
                    new AlertDialog.Builder(Add_marks.this).setTitle("Once entered marks cannot be changed").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            array.add(new Calculate(id, Integer.valueOf(myEditText1.getText().toString()), Integer.valueOf(myEditText2.getText().toString()),
                                    Integer.valueOf(myEditText3.getText().toString()), Integer.valueOf(myEditText4.getText().toString()),
                                    Integer.valueOf(myEditText4.getText().toString()), Integer.valueOf(totaltextview.getText().toString())));

                    /*Calculate calculate;
                    for (int i=0;i<array.size();i++){
                        calculate = array.get(i);
                        toast(String.valueOf(calculate.getTotal()));
                    }*/
                            Firebase mRef1 = new Firebase("https://master-app-inc.firebaseio.com/result/");
                            Calculate calculate;

                            if (getIntent().getIntExtra("size", 100) == last + 1) {
                                toast("last");
                                float big = 0, percentile = 0;
                                for (int i = 0; i < array.size(); i++) {
                                    calculate = array.get(i);
                                    if (big < calculate.getTotal())
                                        big = calculate.getTotal();
                                }
                                for (int i = 0; i < array.size(); i++) {
                                    calculate = array.get(i);
                                    percentile = (calculate.getTotal() / big) * 100;

                                    mRef1.child(value).child(calculate.getId()).child("Innovative Idea’s Involved ").setValue(calculate.getM2());
                                    mRef1.child(value).child(calculate.getId()).child("Approach To Exploit Ideas ").setValue(calculate.getM2());
                                    mRef1.child(value).child(calculate.getId()).child("Approach Towards Implementing The System And Future Applications ").setValue(calculate.getM3());
                                    mRef1.child(value).child(calculate.getId()).child("Implementation Of Engineering Principles ").setValue(calculate.getM4());
                                    mRef1.child(value).child(calculate.getId()).child("Presentation And Q & A ").setValue(calculate.getM5());
                                    mRef1.child(value).child(calculate.getId()).child("Total").setValue(calculate.getTotal());
                                    mRef1.child(value).child(calculate.getId()).child("Percentile").setValue(percentile);
                                }
                                dialog.cancel();

                                Dialog dialog2 = new Dialog(Add_marks.this);
                                dialog2.setCancelable(false);
                                dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog2.setContentView(R.layout.rating_activity);
                                dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog2.show();

                                RatingBar ratingBar = (RatingBar) dialog2.findViewById(R.id.ratingbarid);
                                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                                    @Override
                                    public void onRatingChanged(RatingBar ratingBar, float rating,
                                                                boolean fromUser) {

                                        Toast.makeText(getApplicationContext(), Float.toString(rating), Toast.LENGTH_LONG).show();

                                    }

                                });

                                Button nextbtn = dialog2.findViewById(R.id.nextbtnratingbar);
                                nextbtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        final Dialog dialog1 = new Dialog(Add_marks.this);
                                        dialog1.setCancelable(false);
                                        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog1.setContentView(R.layout.feedback_activity);
                                        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dialog1.show();
                                        final EditText textView = dialog1.findViewById(R.id.feedbacktextviewid);
                                        textView.setMovementMethod(new ScrollingMovementMethod());
                                        Button submitbtn = dialog1.findViewById(R.id.nextbtnfeedback);
                                        Button backbtn = dialog1.findViewById(R.id.backbtnfeedback);
                                        submitbtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                if (textView.getText().length() != 0) {
                                                    Intent intent = new Intent(getApplicationContext(), JudgeActivity.class);
                                                    intent.putExtra("success", x);
                                                    startActivity(intent);
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Please fill form ", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                        backbtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog1.cancel();
                                            }
                                        });

                                    }
                                });


//                                toast("saved");
                            } else {

                       /*         array.add(new Calculate(id,Integer.valueOf(myEditText1.getText().toString()),Integer.valueOf(myEditText2.getText().toString()),
                                        Integer.valueOf(myEditText3.getText().toString()),Integer.valueOf(myEditText4.getText().toString()),
                                        Integer.valueOf(myEditText4.getText().toString()),Integer.valueOf(totaltextview.getText().toString())));

*/
//                                calculate = array.get(position);
                                mRef1.child(value).child(id).child("Innovative Idea’s Involved ").setValue(myEditText1.getText().toString());
                                mRef1.child(value).child(id).child("Approach To Exploit Ideas ").setValue(myEditText2.getText().toString());
                                mRef1.child(value).child(id).child("Approach Towards Implementing The System And Future Applications ").setValue(myEditText3.getText().toString());
                                mRef1.child(value).child(id).child("Implementation Of Engineering Principles ").setValue(myEditText4.getText().toString());
                                mRef1.child(value).child(id).child("Presentation And Q & A ").setValue(myEditText5.getText().toString());
                                mRef1.child(value).child(id).child("Total").setValue(totaltextview.getText().toString());

                                Intent intent = new Intent(getApplicationContext(), JudgeActivity.class);
                                intent.putExtra("success", x);

                                startActivity(intent);
                            }


                        }
                    }).setNegativeButton("No", null).create().show();
                    // recyclerView.findViewHolderForAdapterPosition(x).itemView.findViewById(R.id.addmarkbtn).setClickable(false);

                } else if (getIntent().getIntExtra("button", 100) == 1)
                    toast("Fields are Empty");
            }
        });

        myEditText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int first = 0, second = 0, third = 0, fourth = 0, fifth = 0;
                int total;
                if (!TextUtils.isEmpty(myEditText1.getText()))
                    first = Integer.valueOf(myEditText1.getText().toString());
                if (!TextUtils.isEmpty(myEditText2.getText()))
                    second = Integer.valueOf(myEditText2.getText().toString());
                if (!TextUtils.isEmpty(myEditText3.getText()))
                    third = Integer.valueOf(myEditText3.getText().toString());
                if (!TextUtils.isEmpty(myEditText4.getText()))
                    fourth = Integer.valueOf(myEditText4.getText().toString());
                if (!TextUtils.isEmpty(myEditText5.getText()))
                    fifth = Integer.valueOf(myEditText5.getText().toString());
                TextView totaltextview = findViewById(R.id.totaladdmarks);
                totaltextview.setText(String.valueOf(first + second + third + fourth + fifth));


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        myEditText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int first = 0, second = 0, third = 0, fourth = 0, fifth = 0;
                int total;
                if (!TextUtils.isEmpty(myEditText1.getText()))
                    first = Integer.valueOf(myEditText1.getText().toString());
                if (!TextUtils.isEmpty(myEditText2.getText()))
                    second = Integer.valueOf(myEditText2.getText().toString());
                if (!TextUtils.isEmpty(myEditText3.getText()))
                    third = Integer.valueOf(myEditText3.getText().toString());
                if (!TextUtils.isEmpty(myEditText4.getText()))
                    fourth = Integer.valueOf(myEditText4.getText().toString());
                if (!TextUtils.isEmpty(myEditText5.getText()))
                    fifth = Integer.valueOf(myEditText5.getText().toString());
                TextView totaltextview = findViewById(R.id.totaladdmarks);
                totaltextview.setText(String.valueOf(first + second + third + fourth + fifth));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        myEditText3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int first = 0, second = 0, third = 0, fourth = 0, fifth = 0;
                int total;
                if (!TextUtils.isEmpty(myEditText1.getText()))
                    first = Integer.valueOf(myEditText1.getText().toString());
                if (!TextUtils.isEmpty(myEditText2.getText()))
                    second = Integer.valueOf(myEditText2.getText().toString());
                if (!TextUtils.isEmpty(myEditText3.getText()))
                    third = Integer.valueOf(myEditText3.getText().toString());
                if (!TextUtils.isEmpty(myEditText4.getText()))
                    fourth = Integer.valueOf(myEditText4.getText().toString());
                if (!TextUtils.isEmpty(myEditText5.getText()))
                    fifth = Integer.valueOf(myEditText5.getText().toString());
                TextView totaltextview = findViewById(R.id.totaladdmarks);
                totaltextview.setText(String.valueOf(first + second + third + fourth + fifth));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        myEditText4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int first = 0, second = 0, third = 0, fourth = 0, fifth = 0;
                int total;
                if (!TextUtils.isEmpty(myEditText1.getText()))
                    first = Integer.valueOf(myEditText1.getText().toString());
                if (!TextUtils.isEmpty(myEditText2.getText()))
                    second = Integer.valueOf(myEditText2.getText().toString());
                if (!TextUtils.isEmpty(myEditText3.getText()))
                    third = Integer.valueOf(myEditText3.getText().toString());
                if (!TextUtils.isEmpty(myEditText4.getText()))
                    fourth = Integer.valueOf(myEditText4.getText().toString());
                if (!TextUtils.isEmpty(myEditText5.getText()))
                    fifth = Integer.valueOf(myEditText5.getText().toString());
                TextView totaltextview = findViewById(R.id.totaladdmarks);
                totaltextview.setText(String.valueOf(first + second + third + fourth + fifth));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        myEditText5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int first = 0, second = 0, third = 0, fourth = 0, fifth = 0;
                int total;
                if (!TextUtils.isEmpty(myEditText1.getText()))
                    first = Integer.valueOf(myEditText1.getText().toString());
                if (!TextUtils.isEmpty(myEditText2.getText()))
                    second = Integer.valueOf(myEditText2.getText().toString());
                if (!TextUtils.isEmpty(myEditText3.getText()))
                    third = Integer.valueOf(myEditText3.getText().toString());
                if (!TextUtils.isEmpty(myEditText4.getText()))
                    fourth = Integer.valueOf(myEditText4.getText().toString());
                if (!TextUtils.isEmpty(myEditText5.getText()))
                    fifth = Integer.valueOf(myEditText5.getText().toString());
                TextView totaltextview = findViewById(R.id.totaladdmarks);
                totaltextview.setText(String.valueOf(first + second + third + fourth + fifth));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    public void setzero(String value, String id, int size, int position, Context context) {
        if (array.size() == 0)
            array = new ArrayList<>();
        array.add(new Calculate(id, 0, 0, 0, 0, 0, 0));

        Calculate calculate;
        for (int i = 0; i < array.size(); i++) {
            calculate = array.get(i);
            Toast.makeText(context, String.valueOf(calculate.getTotal()), Toast.LENGTH_SHORT).show();
            // toast(String.valueOf(calculate.getTotal()));
        }
        Firebase mRef1 = new Firebase("https://master-app-inc.firebaseio.com/result/");

        if (size == last) {

            float big = 0, percentile = 0;
            for (int i = 0; i < array.size(); i++) {
                calculate = array.get(i);
                if (big < calculate.getTotal())
                    big = calculate.getTotal();
            }
            //toast("biggest value"+String.valueOf(big));


            for (int i = 0; i < array.size(); i++) {
                calculate = array.get(i);
                percentile = (calculate.getTotal() / big) * 100;

                mRef1.child(value).child(calculate.getId()).child("Innovative Idea’s Involved ").setValue(calculate.getM2());
                mRef1.child(value).child(calculate.getId()).child("Approach To Exploit Ideas ").setValue(calculate.getM2());
                mRef1.child(value).child(calculate.getId()).child("Approach Towards Implementing The System And Future Applications ").setValue(calculate.getM3());
                mRef1.child(value).child(calculate.getId()).child("Implementation Of Engineering Principles ").setValue(calculate.getM4());
                mRef1.child(value).child(calculate.getId()).child("Presentation And Q & A ").setValue(calculate.getM5());
                mRef1.child(value).child(calculate.getId()).child("Total").setValue(calculate.getTotal());
                mRef1.child(value).child(calculate.getId()).child("Percentile").setValue(percentile);
            }
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        } else {
            mRef1.child(value).child(id).child("Innovative Idea’s Involved ").setValue(0);
            mRef1.child(value).child(id).child("Approach To Exploit Ideas ").setValue(0);
            mRef1.child(value).child(id).child("Approach Towards Implementing The System And Future Applications ").setValue(0);
            mRef1.child(value).child(id).child("Implementation Of Engineering Principles ").setValue(0);
            mRef1.child(value).child(id).child("Presentation And Q & A ").setValue(0);
            mRef1.child(value).child(id).child("Total").setValue(0);


        }
     /*  Firebase disablereference = new Firebase("https://master-app-inc.firebaseio.com/judgeid/"+value);

        disablereference.child("disable").child(String.valueOf(position)).setValue(position);*/

        Intent intent = new Intent(context, JudgeActivity.class);
        intent.putExtra("success", position);

        context.startActivity(intent);


    }

    public void toast(String x) {
        Toast.makeText(getApplicationContext(), x, Toast.LENGTH_SHORT).show();
    }
}
