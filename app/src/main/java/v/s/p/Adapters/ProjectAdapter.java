package v.s.p.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.studentappinc.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;
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
        final ProjectClass item = projectList.get(position);

        holder.subtitle.setText(item.getTitle());
        holder.id.setText(item.getId());
        holder.arrowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.arrowBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.expandableView.getVisibility() == View.GONE) {
                            TransitionManager.beginDelayedTransition(holder.cardView, new AutoTransition());
                            holder.expandableView.setVisibility(View.VISIBLE);
                            holder.arrowBtn.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                        } else {
                            TransitionManager.beginDelayedTransition(holder.cardView, new AutoTransition());
                            holder.expandableView.setVisibility(View.GONE);
                            holder.arrowBtn.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                        }
                    }
                });
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
