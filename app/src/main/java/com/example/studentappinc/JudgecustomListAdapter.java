package com.example.studentappinc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class JudgecustomListAdapter extends RecyclerView.Adapter<JudgecustomListAdapter.MyViewHolder> {

    String textviewid,lablocation;  // MAKE IT TO TEXTVIEW INSTEAD OF STRING
    Context context;
    List<JudgeIDreturn> studentlist;
    private OnItemClickListner mListner;

    public JudgecustomListAdapter(Context context,List<JudgeIDreturn> studentlist) {
        this.studentlist = studentlist;
        this.context = context;

    }

    public void setOnItemClickListner(OnItemClickListner listner)
    {
        mListner=listner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.row_layout_judge,parent,false);
            return new MyViewHolder(view,mListner);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       JudgeIDreturn judgeIDreturn = studentlist.get(position);

        holder.mytext1.setText(judgeIDreturn.getStudentID());
        holder.mytext2.setText(judgeIDreturn.getLablocation());
    }

    @Override
    public int getItemCount() {
    return  studentlist.size();
    }

    public interface OnItemClickListner{
        void onaddmarkClick(int position);
        void onabsentbtnClick(int position);
        void onnotintrestedClick(int position);
    }

//receiving view
    public class MyViewHolder extends  RecyclerView.ViewHolder{
    public Button addmark,absentbtn,notintrestedbtn;

      TextView mytext1,mytext2;
        public MyViewHolder(@NonNull View itemView,final OnItemClickListner listner) {
            super(itemView);
            mytext1 = itemView.findViewById(R.id.textviewrowID);
            mytext2 = itemView.findViewById(R.id.lablocationtext);
            addmark = itemView.findViewById(R.id.addmarkbtn);
            absentbtn = itemView.findViewById(R.id.absentbtn);
            notintrestedbtn = itemView.findViewById(R.id.notintrested);

           addmark.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if(listner!=null)
                   {
                       int position = getAdapterPosition();
                       if(position != RecyclerView.NO_POSITION ){
                           listner.onaddmarkClick(position);


                       }

                   }
               }
           });

            absentbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listner!=null)
                    {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION ){
                            listner.onabsentbtnClick(position);


                        }

                    }
                }
            });

            notintrestedbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listner!=null)
                    {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION ){
                            listner.onnotintrestedClick(position);
                        }

                    }
                }
            });

        }
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