package v.s.p.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.studentappinc.R;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import v.s.p.Classes.ProjectClass;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.MyViewHolder> {

    Context context;
    List<ProjectClass> projectList;

    public ProjectAdapter(Context context, List<ProjectClass> projectList) {
        this.context = context;
        this.projectList = projectList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_all_project, parent, false);

        return new ProjectAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ProjectClass citem = projectList.get(position);

        holder.subtitle.setText(citem.getTitle());
        holder.id.setText(citem.getId());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DialogPlus dialog = DialogPlus.newDialog(context)
//                        .setOnItemClickListener(new OnItemClickListener() {
//                            @Override
//                            public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
//                            }
//                        })
//                        .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
//                        .create();
//                dialog.show();

                DialogPlus dialog = DialogPlus.newDialog(context)
                        .setExpanded(true)
                        .setPadding(20, 20, 20, 20)
                        .setContentHolder(new ViewHolder(R.layout.bottom_sheet))// This will enable the expand feature, (similar to android L share dialog)
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                            }
                        })
                        .create();

//                ((TextView) dialog.findViewById(R.id.bid)).setText(citem.getId());
//                ((TextView) dialog.findViewById(R.id.btitle)).setText(citem.getTitle());
//                ((TextView) dialog.findViewById(R.id.babs)).setText(citem.getAbstr());
//                ((TextView) dialog.findViewById(R.id.bname)).setText(citem.getName());
//                ((TextView) dialog.findViewById(R.id.bmobno)).setText(citem.getMobno());


                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout expandableView;
        TextView id, subtitle, abstr, college, domain, email, name, mobno, projecttype;
        Button arrowBtn;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            expandableView = itemView.findViewById(R.id.expandableView);
            arrowBtn = itemView.findViewById(R.id.arrowBtn);
            cardView = itemView.findViewById(R.id.cardView);
            id = itemView.findViewById(R.id.projectId);
            subtitle = itemView.findViewById(R.id.projectSubtitle);


        }
    }
}
