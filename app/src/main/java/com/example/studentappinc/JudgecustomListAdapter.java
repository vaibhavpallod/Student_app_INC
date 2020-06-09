package com.example.studentappinc;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;


public class JudgecustomListAdapter extends RecyclerView.Adapter<JudgecustomListAdapter.MyViewHolder> {

    String textviewid, lablocation;  // MAKE IT TO TEXTVIEW INSTEAD OF STRING
    Context context;
    List<JudgeIDreturn> studentlist;
    private OnItemClickListner mListner;
    private static int currentPosition = 0;

    public JudgecustomListAdapter(Context context, List<JudgeIDreturn> studentlist) {
        this.studentlist = studentlist;
        this.context = context;

    }

    public void setOnItemClickListner(OnItemClickListner listner) {
        mListner = listner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_layout_judge, parent, false);

        return new MyViewHolder(view, mListner);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final JudgeIDreturn judgeIDreturn = studentlist.get(position);
        holder.mytext1.setText(judgeIDreturn.getStudentID());
        holder.project.setText(judgeIDreturn.getProjecttitle());
//        holder.relativeLayout.setVisibility(View.GONE);

        holder.mytext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.abstractdialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(true);

                final TextView textView =dialog.findViewById(R.id.abstracttextid);

                Firebase abstractref = new Firebase("https://master-app-inc.firebaseio.com/studentdata");

                abstractref.child(judgeIDreturn.getStudentID()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        textView.setText(dataSnapshot.child("abstract").getValue().toString());
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                dialog.show();

            }
        });
/*

        if(currentPosition == position){
            Animation slidedown = AnimationUtils.loadAnimation(context, R.anim.slide_down);

            //toggling visibility
            holder.relativeLayout.setVisibility(View.VISIBLE);

            //adding sliding effect

            holder.relativeLayout.startAnimation(slidedown);


        }
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPosition = position;

                notifyDataSetChanged();
            }
        });
*/

        holder.addmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListner.onaddmarkClick(position);

                if(!(holder.addmark.isClickable()))
                    toast("Double evaluation not allowed2");

                if(!(holder.addmark.isEnabled()))
                    toast("Double evaluation not allowed");


                Intent intent = new Intent(context.getApplicationContext(), Add_marks.class);
            //    intent.putExtra("list",(ArrayList<Integer>)JudgeActivity.marklist);
                intent.putExtra("button",1);

                intent.putExtra("position",position);
                intent.putExtra("size",studentlist.size());
                intent.putExtra("id",judgeIDreturn.getStudentID());
                intent.putExtra("name",judgeIDreturn.getProjecttitle());
                context.startActivity(intent);


            }
        });



        holder.absentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences result;
                result = context.getSharedPreferences("SaveData", MODE_PRIVATE);
                final String value = result.getString("Value", "Data not Found");


                new AlertDialog.Builder(context).setTitle("zero makrs will be assigned ").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                      Add_marks add_marks = new Add_marks();
                      add_marks.setzero(value,judgeIDreturn.getStudentID(),studentlist.size(),position,context);
                       /* Intent intent = new Intent(context.getApplicationContext(), Add_marks.class);
                        //    intent.putExtra("list",(ArrayList<Integer>)JudgeActivity.marklist);
                        intent.putExtra("button",0);
                        intent.putExtra("position",position);
                        intent.putExtra("size",studentlist.size());
                        intent.putExtra("id",judgeIDreturn.getStudentID());
                        intent.putExtra("name",judgeIDreturn.getProjecttitle());
                        context.startActivity(intent);
                    */}
                }).setNegativeButton("No", null).show();
            }
        });

    }



    @Override
    public int getItemCount() {
        return studentlist.size();
    }

    public interface OnItemClickListner {
        void onaddmarkClick(int position);

        void onabsentbtnClick(int position);
    }


    //receiving view
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public Button addmark, absentbtn;
        RelativeLayout relativeLayout;
        TextView mytext1, project;
        LinearLayout linearLayout;
        LinearLayout animlayout;
        public MyViewHolder(@NonNull View itemView, final OnItemClickListner listner) {
            super(itemView);
            animlayout = itemView.findViewById(R.id.linearforanim);
            mytext1 = itemView.findViewById(R.id.textviewrowID);
            project = itemView.findViewById(R.id.projecttitlejudgelayout);
            addmark = itemView.findViewById(R.id.addmarkbtn);
            absentbtn = itemView.findViewById(R.id.absentbtn);
            relativeLayout = itemView.findViewById(R.id.relativelayoutcardview);
            linearLayout = itemView.findViewById(R.id.linearlayoutcardview);




         /*   addmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listner != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listner.onaddmarkClick(position);


                        }

                    }
                }
            });
*/

        }


    }

    public void toast(String x)
    {
        Toast.makeText(context,x,Toast.LENGTH_SHORT).show();
    }

}
















/*
public class JudgecustomListAdapter extends ArrayAdapter<JudgeIDreturn> {

Context mCtx;
int resource;
List<JudgeIDreturn> studentIDlist;

    public JudgecustomListAdapter(Context mCtx, int resource, List<JudgeIDreturn> studentIDlist){

        super(mCtx,resource,studentIDlist);
        this.mCtx = mCtx;
        this.resource = resource;
        this.studentIDlist = studentIDlist;

    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(R.layout.row_layout_judge,null);
        final TextView textViewIDadapter = view.findViewById(R.id.textviewrowID);
        final TextView textViewLablocation = view.findViewById(R.id.lablocation);
        Button btnAdapter = view.findViewById(R.id.addmarkbtn);
        //   return super.getView(position, convertView, parent);

        final JudgeIDreturn studentIDposition = studentIDlist.get(position);                          //get position of listview

        textViewIDadapter.setText(studentIDposition.getStudentid());                    //getting Judge ID
        textViewLablocation.setText(studentIDposition.getLablocation());
        btnAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(StudentIDreturn.class,textViewIDadapter.toString(),Toast.LENGTH_SHORT);
                Toast.makeText(getContext(), studentIDposition.getStudentid(),Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}
*/