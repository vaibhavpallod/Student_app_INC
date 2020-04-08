package com.example.studentappinc;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.BreakIterator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;



public class StudentcustomListAdapter extends RecyclerView.Adapter<StudentcustomListAdapter.MyviewHolder> {

    static int i;
Context mCtx;
int resource;
List<StudentIDreturn> judgelist;
String totaljudgeprojects;

  String alloted ;
  String evaluated;
    String TAG = "StudentcustomListAdapter";

    private OnItemClickListnerstudent mListner;

    public StudentcustomListAdapter(Context context, List<StudentIDreturn> judgelist) {
        this.mCtx=context;
        this.judgelist=judgelist;
    }

    public class MyviewHolder extends RecyclerView.ViewHolder   {

        TextView alloted,judgeid,evaluated;
    Button btn;
        public MyviewHolder(@NonNull View itemView, OnItemClickListnerstudent mListner) {
            super(itemView);
            judgeid = itemView.findViewById(R.id.textviewrowID);
            alloted = itemView.findViewById(R.id.allotedtextview);
            evaluated = itemView.findViewById(R.id.evaluatedtextview);
            btn = itemView.findViewById(R.id.donebtn);


        }
    }

    public void updateData(List<StudentIDreturn> viewModels) {
       // judgelist.clear();
       // notifyDataSetChanged();

        judgelist.addAll(viewModels);
        notifyDataSetChanged();
    }
    public void addItem(int position, StudentIDreturn viewModel) {
        judgelist.add(position, viewModel);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        judgelist.remove(position);
        notifyItemRemoved(position);
    }

    public void setOnItemClickListner(OnItemClickListnerstudent listner)
    {
        mListner=listner;
    }

    public interface OnItemClickListnerstudent{
        void ondonebtnclick(int position);

    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.row_layout_student,parent,false);
        return new MyviewHolder(view,mListner);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyviewHolder holder, int position) {
        final StudentIDreturn studentIDreturn = judgelist.get(position);
        holder.judgeid.setText(studentIDreturn.getJudgeid());

        Firebase mRef1 = new Firebase("https://master-app-inc.firebaseio.com/JudgeID/"+studentIDreturn.getJudgeid()+"/");

        mRef1.child("allocated").addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                alloted = dataSnapshot.getValue().toString();
                holder.alloted.setText(alloted);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



        mRef1.child("evaluated").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                evaluated = dataSnapshot.getValue().toString();
                holder.evaluated.setText(evaluated);
                //  String put = judgedprojects + " / " + totaljudgeprojects;
             //   Toast.makeText(mCtx, evaluated, Toast.LENGTH_SHORT).show();
                //   holder.judgedprojects.setText(put);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

       /* String put = evaluated + " / " +  alloted;
        Log.v(TAG,studentIDreturn.getJudgeid() + " : " +put);

        holder.judgedprojects.setText(put);
*/
    }



    @Override
    public int getItemCount() {
        return judgelist.size();
    }




}
