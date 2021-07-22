package v.s.p;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.example.studentappinc.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    ConnectionDetector cd;

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

        Animation slidedown = AnimationUtils.loadAnimation(context, R.anim.fadeloadadapterleft);

        holder.itemView.startAnimation(slidedown);
        holder.mytext1.setText(judgeIDreturn.getStudentID());
        holder.project.setText(judgeIDreturn.getProjecttitle());
//        holder.relativeLayout.setVisibility(View.GONE);
        SharedPreferences result;
        result = context.getSharedPreferences("SaveData", MODE_PRIVATE);
        String value = result.getString("Value", "Data not Found");

        DatabaseReference disablingref = FirebaseDatabase.getInstance().getReference().child("judgeid").child(value).child("disable");

        disablingref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    if(Integer.valueOf(dataSnapshot1.getValue().toString()).equals(position)){
                        Animation slidedown = AnimationUtils.loadAnimation(context, R.anim.fui_slide_in_right);
                        holder.relativeLayout.setVisibility(View.GONE);
                        holder.addmark.setEnabled(false);
                        holder.absentbtn.setEnabled(false);
                        holder.addmark.setClickable(false);
                        holder.absentbtn.setClickable(false);
                        holder.marksubmitlayout.setVisibility(View.VISIBLE);
                        //holder.marksubmitlayout.startAnimation(slidedown);

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        holder.project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.abstractdialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(true);

                final TextView textView =dialog.findViewById(R.id.abstracttextid);
                DatabaseReference abstractref = FirebaseDatabase.getInstance().getReference();

                abstractref.child("studentdata").child(judgeIDreturn.getStudentID()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        textView.setText(dataSnapshot.child("abstract").getValue().toString());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                dialog.show();
            }
        });

        holder.mytext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.abstractdialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(true);

                final TextView textView =dialog.findViewById(R.id.abstracttextid);
                DatabaseReference abstractref = FirebaseDatabase.getInstance().getReference();

                abstractref.child("studentdata").child(judgeIDreturn.getStudentID()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        textView.setText(dataSnapshot.child("abstract").getValue().toString());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                dialog.show();

            }
        });
        /*Animation slide = AnimationUtils.loadAnimation(context, R.anim.slide_down);
        holder.animlayout.startAnimation(slide);*/
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
           //     mListner.onaddmarkClick(position);

                cd = new ConnectionDetector(context);
                if(cd.isConnected()) {
                    Intent intent = new Intent(context.getApplicationContext(), Add_marks.class);
                    //    intent.putExtra("list",(ArrayList<Integer>)j.marklist);
                    intent.putExtra("button", 1);

                    intent.putExtra("position", position);
                    intent.putExtra("size", studentlist.size());
                    intent.putExtra("id", judgeIDreturn.getStudentID());
                    intent.putExtra("name", judgeIDreturn.getProjecttitle());
                    context.startActivity(intent);
                }
                else{
                    toast("Please check Internet Connection");
                }

            }
        });



        holder.absentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences result;
                result = context.getSharedPreferences("SaveData", MODE_PRIVATE);
                final String value = result.getString("Value", "Data not Found");

                cd = new ConnectionDetector(context);
                if (cd.isConnected()) {
                    new AlertDialog.Builder(context).setTitle("zero makrs will be assigned ").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Add_marks add_marks = new Add_marks();
                            add_marks.setzero(value, judgeIDreturn.getStudentID(), studentlist.size(), position, context);
                       /* Intent intent = new Intent(context.getApplicationContext(), Add_marks.class);
                        //    intent.putExtra("list",(ArrayList<Integer>)j.marklist);
                        intent.putExtra("button",0);
                        intent.putExtra("position",position);
                        intent.putExtra("size",studentlist.size());
                        intent.putExtra("id",judgeIDreturn.getStudentID());
                        intent.putExtra("name",judgeIDreturn.getProjecttitle());
                        context.startActivity(intent);
                    */
                        }
                    }).setNegativeButton("No", null).show();
                }
                else{
                    toast("Please check Internet Connection");
                }
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
        LinearLayout animlayout,marksubmitlayout;
        public MyViewHolder(@NonNull View itemView, final OnItemClickListner listner) {
            super(itemView);
            marksubmitlayout = itemView.findViewById(R.id.marksubmittedlayout);
            animlayout = itemView.findViewById(R.id.linearforanim);
            mytext1 = itemView.findViewById(R.id.textviewrowID);
            project = itemView.findViewById(R.id.projecttitlejudgelayout);
            addmark = itemView.findViewById(R.id.addmarkbtn);
            absentbtn = itemView.findViewById(R.id.absentbtn);
            relativeLayout = itemView.findViewById(R.id.relativelayoutcardview);
            linearLayout = itemView.findViewById(R.id.linearlayoutcardview);



        }


    }

    public void toast(String x)
    {
        Toast.makeText(context,x,Toast.LENGTH_SHORT).show();
    }

}
