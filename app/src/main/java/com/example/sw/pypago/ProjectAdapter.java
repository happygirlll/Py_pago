package com.example.sw.pypago;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sw.pypago.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class ProjectAdapter extends FirestoreRecyclerAdapter<ProjectInfo, ProjectAdapter.ProjectHolder> {
 private OnItemClickListener listener;

    public ProjectAdapter(@NonNull FirestoreRecyclerOptions<ProjectInfo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProjectHolder holder, int position, @NonNull ProjectInfo model) {
        holder.textViewTitle.setText(model.getTitle());
    }

    @NonNull
    @Override
    public ProjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.title_item,parent,false);
        return new ProjectHolder(v);
    }

    class ProjectHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle;
        TextView dateText;

        public ProjectHolder(View itemView){
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            dateText = itemView.findViewById(R.id.text_view_date);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);

                    }
                }
            });

        }
    }
    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
