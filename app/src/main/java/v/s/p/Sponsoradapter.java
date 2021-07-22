package v.s.p;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.studentappinc.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Sponsoradapter extends RecyclerView.Adapter<Sponsoradapter.MyViewHolder> {

    Context context;
    Sponsorreturn[] sponsorreturn;

    public Sponsoradapter(Context context, Sponsorreturn[] sponsorreturn) {
        this.context = context;
        this.sponsorreturn = sponsorreturn;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.row_layout_sponsor,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Sponsoradapter.MyViewHolder holder, int position) {

        Sponsorreturn sponsorreturn1 = sponsorreturn[position];
        holder.textView.setText(sponsorreturn1.getTextView());
        holder.imageView.setImageResource(sponsorreturn1.getImageid());

    }

    @Override
    public int getItemCount() {
        return sponsorreturn.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.sponsorimage);
            textView = itemView.findViewById(R.id.sponsortextview);

        }
    }
}
