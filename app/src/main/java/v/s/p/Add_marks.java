package v.s.p;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentappinc.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import v.s.p.Activities.activity_Home;
import v.s.p.navigation.Feedback_form;


//import static com.example.studentappinc.activity_Home.marklist;

public class Add_marks extends AppCompatActivity {
    String name, id;

    SharedPreferences result;

    String domain;
    static List<Calculate> array = new ArrayList<>();
    public int last = 0;
    DatabaseReference lastref;
    ConnectionDetector cd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_marks);

        result = getSharedPreferences("SaveData", MODE_PRIVATE);
        final String value = result.getString("Value", "Data not Found");
        lastref = FirebaseDatabase.getInstance().getReference("/judgeid/" + value + "/disable/");
        cd = new ConnectionDetector(this);
        final EditText myEditText1 = (EditText) findViewById(R.id.edittext1);
        myEditText1.setFilters(new InputFilter[]{new MinMaxFilter("0", "20")});

        final EditText myEditText2 = (EditText) findViewById(R.id.edittext2);
        myEditText2.setFilters(new InputFilter[]{new MinMaxFilter("0", "10")});

        final EditText myEditText3 = (EditText) findViewById(R.id.edittext3);
        myEditText3.setFilters(new InputFilter[]{new MinMaxFilter("0", "10")});

        final EditText myEditText4 = (EditText) findViewById(R.id.edittext4);
        myEditText4.setFilters(new InputFilter[]{new MinMaxFilter("0", "10")});

        final EditText myEditText5 = (EditText) findViewById(R.id.edittext5);
        myEditText5.setFilters(new InputFilter[]{new MinMaxFilter("0", "15")});

        final EditText myEditText6 = (EditText) findViewById(R.id.edittext6);
        myEditText6.setFilters(new InputFilter[]{new MinMaxFilter("0", "20")});

        final EditText myEditText7 = (EditText) findViewById(R.id.edittext7);
        myEditText7.setFilters(new InputFilter[]{new MinMaxFilter("0", "15")});


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

        lastref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                last = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cd.isConnected()) {
                    final TextView totaltextview = findViewById(R.id.totaladdmarks);
                    if (!TextUtils.isEmpty(myEditText5.getText()) && !TextUtils.isEmpty(myEditText4.getText())
                            && !TextUtils.isEmpty(myEditText3.getText()) && !TextUtils.isEmpty(myEditText2.getText()) && !TextUtils.isEmpty(myEditText1.getText())
                            && !TextUtils.isEmpty(myEditText6.getText()) && !TextUtils.isEmpty(myEditText7.getText()) && getIntent().getIntExtra("button", 100) == 1) {
                        new AlertDialog.Builder(Add_marks.this).setTitle("Once entered marks cannot be changed").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                array.add(new Calculate(id, Integer.valueOf(myEditText1.getText().toString()), Integer.valueOf(myEditText2.getText().toString()),
                                        Integer.valueOf(myEditText3.getText().toString()), Integer.valueOf(myEditText4.getText().toString()),
                                        Integer.valueOf(myEditText5.getText().toString()), Integer.valueOf(myEditText6.getText().toString()), Integer.valueOf(myEditText7.getText().toString()),
                                        Integer.valueOf(totaltextview.getText().toString())));

                                DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("judgeid").child(value);
                                mRef.child("disable").child(String.valueOf(x)).setValue(x);
                                mRef.child("disable").child(String.valueOf(position)).setValue(position);
                                mRef.child("domain").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        domain = dataSnapshot.getValue().toString();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                DatabaseReference mRef1 = FirebaseDatabase.getInstance().getReference().child("result");

                                Calculate calculate;
//                                toast(getIntent().getIntExtra("size", 100) + " / "+ last);
                                if (getIntent().getIntExtra("size", 100) == last +1 ) {
                                    float big = 0, percentile = 0;
                                    for (int i = 0; i < array.size(); i++) {
                                        calculate = array.get(i);
                                        if (big < calculate.getTotal())
                                            big = calculate.getTotal();
                                    }
                                    for (int i = 0; i < array.size(); i++) {
                                        calculate = array.get(i);
                                        percentile = (Float.valueOf(calculate.getTotal()) / big) * 100;
                                        mRef1.child(value).child(calculate.getId()).child("domain").setValue(domain);
                                        mRef1.child(value).child(calculate.getId()).child("Originality, Creativity, Clarity, and Innovation in Project").setValue(calculate.getM1());
                                        mRef1.child(value).child(calculate.getId()).child("Patent or Product readiness from Project").setValue(calculate.getM2());
                                        mRef1.child(value).child(calculate.getId()).child("Relevance to Society").setValue(calculate.getM3());
                                        mRef1.child(value).child(calculate.getId()).child("Ability to Execute projects as a Startups or Startup Enrollment").setValue(calculate.getM4());
                                        mRef1.child(value).child(calculate.getId()).child("Testing or Demonstration").setValue(calculate.getM5());
                                        mRef1.child(value).child(calculate.getId()).child("Impact and Applications").setValue(calculate.getM6());
                                        mRef1.child(value).child(calculate.getId()).child("Presentation, and Q&A").setValue(calculate.getM7());
                                        mRef1.child(value).child(calculate.getId()).child("Total").setValue(calculate.getTotal());
                                        mRef1.child(value).child(calculate.getId()).child("Percentile").setValue(percentile);
                                    }
//                                    dialog.cancel();

                                    Dialog dialog2 = new Dialog(Add_marks.this);
                                    dialog2.setCancelable(false);
                                    dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog2.setContentView(R.layout.feedback_indialog);
                                    dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    dialog2.show();
                                    Button okbtn = dialog2.findViewById(R.id.okbtnfeeddialog);
                                    okbtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent4 = new Intent(getApplicationContext(), Feedback_form.class);
                                            startActivity(intent4);
                                            finish();
                                        }
                                    });

/*
                                    Intent intent4 = new Intent(getApplicationContext(),Feedback_form.class);
                                    startActivity(intent4);*/



                                    /*Dialog dialog2 = new Dialog(Add_marks.this);
                                    dialog2.setCancelable(false);
                                    dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog2.setContentView(R.layout.feedback_indialog);
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
                                                        Intent intent = new Intent(getApplicationContext(), activity_Home.class);
                                                        intent.putExtra("success", x);
                                                        startActivity(intent);
                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "Please fill the form ", Toast.LENGTH_SHORT).show();
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
*/

//                                toast("saved");
                                } else {

                       /*         array.add(new Calculate(id,Integer.valueOf(myEditText1.getText().toString()),Integer.valueOf(myEditText2.getText().toString()),
                                        Integer.valueOf(myEditText3.getText().toString()),Integer.valueOf(myEditText4.getText().toString()),
                                        Integer.valueOf(myEditText4.getText().toString()),Integer.valueOf(totaltextview.getText().toString())));
*/
                                    mRef1.child(value).child(id).child("domain").setValue(domain);
                                    mRef1.child(value).child(id).child("Originality, Creativity, Clarity, and Innovation in Project").setValue(myEditText1.getText().toString());
                                    mRef1.child(value).child(id).child("Patent or Product readiness from Project").setValue(myEditText2.getText().toString());
                                    mRef1.child(value).child(id).child("Relevance to Society").setValue(myEditText3.getText().toString());
                                    mRef1.child(value).child(id).child("Ability to Execute projects as a Startups or Startup Enrollment").setValue(myEditText4.getText().toString());
                                    mRef1.child(value).child(id).child("Testing or Demonstration").setValue(myEditText5.getText().toString());
                                    mRef1.child(value).child(id).child("Impact and Applications").setValue(myEditText6.getText().toString());
                                    mRef1.child(value).child(id).child("Presentation, and Q&A").setValue(myEditText7.getText().toString());
                                    mRef1.child(value).child(id).child("Total").setValue(totaltextview.getText().toString());

                                    Intent intent = new Intent(getApplicationContext(), activity_Home.class);

                                    startActivity(intent);
                                }


                            }
                        }).setNegativeButton("No", null).create().show();
                        // recyclerView.findViewHolderForAdapterPosition(x).itemView.findViewById(R.id.addmarkbtn).setClickable(false);

                    } else if (getIntent().getIntExtra("button", 100) == 1)
                        toast("Kindly assign marks for each point");
                } else {
                    toast("Please check Internet Connection");
                }
            }
        });

        myEditText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int first = 0, second = 0, third = 0, fourth = 0, fifth = 0, sixth = 0, seventh = 0;
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
                if (!TextUtils.isEmpty(myEditText6.getText()))
                    sixth = Integer.valueOf(myEditText6.getText().toString());
                if (!TextUtils.isEmpty(myEditText7.getText()))
                    seventh = Integer.valueOf(myEditText7.getText().toString());
                TextView totaltextview = findViewById(R.id.totaladdmarks);
                totaltextview.setText(String.valueOf(first + second + third + fourth + fifth + sixth + seventh));


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
                int first = 0, second = 0, third = 0, fourth = 0, fifth = 0, sixth = 0, seventh = 0;
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
                if (!TextUtils.isEmpty(myEditText6.getText()))
                    sixth = Integer.valueOf(myEditText6.getText().toString());
                if (!TextUtils.isEmpty(myEditText7.getText()))
                    seventh = Integer.valueOf(myEditText7.getText().toString());
                TextView totaltextview = findViewById(R.id.totaladdmarks);
                totaltextview.setText(String.valueOf(first + second + third + fourth + fifth + sixth + seventh));


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
                int first = 0, second = 0, third = 0, fourth = 0, fifth = 0, sixth = 0, seventh = 0;
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
                if (!TextUtils.isEmpty(myEditText6.getText()))
                    sixth = Integer.valueOf(myEditText6.getText().toString());
                if (!TextUtils.isEmpty(myEditText7.getText()))
                    seventh = Integer.valueOf(myEditText7.getText().toString());
                TextView totaltextview = findViewById(R.id.totaladdmarks);
                totaltextview.setText(String.valueOf(first + second + third + fourth + fifth + sixth + seventh));


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
                int first = 0, second = 0, third = 0, fourth = 0, fifth = 0, sixth = 0, seventh = 0;
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
                if (!TextUtils.isEmpty(myEditText6.getText()))
                    sixth = Integer.valueOf(myEditText6.getText().toString());
                if (!TextUtils.isEmpty(myEditText7.getText()))
                    seventh = Integer.valueOf(myEditText7.getText().toString());
                TextView totaltextview = findViewById(R.id.totaladdmarks);
                totaltextview.setText(String.valueOf(first + second + third + fourth + fifth + sixth + seventh));


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
                int first = 0, second = 0, third = 0, fourth = 0, fifth = 0, sixth = 0, seventh = 0;
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
                if (!TextUtils.isEmpty(myEditText6.getText()))
                    sixth = Integer.valueOf(myEditText6.getText().toString());
                if (!TextUtils.isEmpty(myEditText7.getText()))
                    seventh = Integer.valueOf(myEditText7.getText().toString());
                TextView totaltextview = findViewById(R.id.totaladdmarks);
                totaltextview.setText(String.valueOf(first + second + third + fourth + fifth + sixth + seventh));


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        myEditText6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int first = 0, second = 0, third = 0, fourth = 0, fifth = 0, sixth = 0, seventh = 0;
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
                if (!TextUtils.isEmpty(myEditText6.getText()))
                    sixth = Integer.valueOf(myEditText6.getText().toString());
                if (!TextUtils.isEmpty(myEditText7.getText()))
                    seventh = Integer.valueOf(myEditText7.getText().toString());
                TextView totaltextview = findViewById(R.id.totaladdmarks);
                totaltextview.setText(String.valueOf(first + second + third + fourth + fifth + sixth + seventh));


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        myEditText7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int first = 0, second = 0, third = 0, fourth = 0, fifth = 0, sixth = 0, seventh = 0;
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
                if (!TextUtils.isEmpty(myEditText6.getText()))
                    sixth = Integer.valueOf(myEditText6.getText().toString());
                if (!TextUtils.isEmpty(myEditText7.getText()))
                    seventh = Integer.valueOf(myEditText7.getText().toString());
                TextView totaltextview = findViewById(R.id.totaladdmarks);
                totaltextview.setText(String.valueOf(first + second + third + fourth + fifth + sixth + seventh));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    public void setzero(final String value, final String id, final int size, int position, final Context context) {
        final int[] last = {0};
        DatabaseReference lastref = FirebaseDatabase.getInstance().getReference("/judgeid/" + value + "/disable/");
        lastref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                last[0] = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        if (array.size() == 0)
            array = new ArrayList<>();
        array.add(new Calculate(id, 0, 0, 0, 0, 0, 0, 0, 0));

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("judgeid").child(value);
        mRef.child("disable").child(String.valueOf(position)).setValue(position);
        mRef.child("domain").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                domain = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final DatabaseReference mRef1 = FirebaseDatabase.getInstance().getReference().child("result");
        Toast.makeText(context,"Marked absent", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Calculate calculate;

                if (size == last[0]) {
                    Toast.makeText(context,"entered",Toast.LENGTH_SHORT).show();
                    float big = 1, percentile = 0;
                    for (int i = 0; i < array.size(); i++) {
                        calculate = array.get(i);
                        if (big < calculate.getTotal())
                            big = calculate.getTotal();
                    }

                    mRef1.child("domain").setValue(domain);
                    for (int i = 0; i < array.size(); i++) {
                        calculate = array.get(i);
                        percentile = (calculate.getTotal() / big) * 100;
                        mRef1.child(value).child(calculate.getId()).child("domain").setValue(domain);
                        mRef1.child(value).child(calculate.getId()).child("Originality, Creativity, Clarity, and Innovation in Project").setValue(calculate.getM1());
                        mRef1.child(value).child(calculate.getId()).child("Patent or Product readiness from Project").setValue(calculate.getM2());
                        mRef1.child(value).child(calculate.getId()).child("Relevance to Society").setValue(calculate.getM3());
                        mRef1.child(value).child(calculate.getId()).child("Ability to Execute projects as a Startups or Startup Enrollment").setValue(calculate.getM4());
                        mRef1.child(value).child(calculate.getId()).child("Testing or Demonstration").setValue(calculate.getM5());
                        mRef1.child(value).child(calculate.getId()).child("Impact and Applications").setValue(calculate.getM6());
                        mRef1.child(value).child(calculate.getId()).child("Presentation, and Q&A").setValue(calculate.getM7());
                        mRef1.child(value).child(calculate.getId()).child("Total").setValue(calculate.getTotal());
                        mRef1.child(value).child(calculate.getId()).child("Percentile").setValue(percentile);
                    }
                    Dialog dialog2 = new Dialog(context);
                    dialog2.setCancelable(false);
                    dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog2.setContentView(R.layout.feedback_indialog);
                    dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog2.show();
                    Button okbtn = dialog2.findViewById(R.id.okbtnfeeddialog);
                    okbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent4 = new Intent(context, Feedback_form.class);
                            context.startActivity(intent4);
                            finish();
                        }
                    });

           /* Intent intent4 = new Intent(getApplicationContext(),Feedback_form.class);
            startActivity(intent4);*/

                } else {
                    mRef1.child(value).child(id).child("domain").setValue(domain);
                    mRef1.child(value).child(id).child("Originality, Creativity, Clarity, and Innovation in Project").setValue(0);
                    mRef1.child(value).child(id).child("Patent or Product readiness from Project").setValue(0);
                    mRef1.child(value).child(id).child("Relevance to Society").setValue(0);
                    mRef1.child(value).child(id).child("Ability to Execute projects as a Startups or Startup Enrollment").setValue(0);
                    mRef1.child(value).child(id).child("Testing or Demonstration").setValue(0);
                    mRef1.child(value).child(id).child("Impact and Applications").setValue(0);
                    mRef1.child(value).child(id).child("Presentation, and Q&A").setValue(0);
                    mRef1.child(value).child(id).child("Total").setValue(0);
                    Intent intent = new Intent(context, activity_Home.class);
                    context.startActivity(intent);
                }
            }
        },1000);

     /*  Firebase disablereference = new Firebase("https://master-app-inc.firebaseio.com/judgeid/"+value);

        disablereference.child("disable").child(String.valueOf(position)).setValue(position);*/


    }

    public void toast(String x) {
        Toast.makeText(getApplicationContext(), x, Toast.LENGTH_SHORT).show();
    }
}
