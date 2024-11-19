package com.example.sw.pypago;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sw.pypago.R;

import java.util.ArrayList;
import java.util.List;

public class ProblemDataAdapter extends RecyclerView.Adapter<ProblemDataAdapter.MyViewHolder> implements Filterable {
    private List<ProvblemData> mDataset;
    private List<ProvblemData> exampleListFull;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;


        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.ProblemName);

        }
    }

    public ProblemDataAdapter(List<ProvblemData> myData, Context context){
        this.mDataset = myData;
        mContext = context;
        exampleListFull = new ArrayList<>(myData);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.problemview, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return this.mDataset.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final ProblemDataAdapter.MyViewHolder holder, final int position) {
        holder.name.setText(mDataset.get(position).getName());

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ProvblemData> filteredList = new ArrayList<>();
            if (constraint == null|| constraint.length() == 0){
                filteredList.addAll( exampleListFull);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(ProvblemData item : exampleListFull){
                    if(item.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values =filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mDataset.clear();
            mDataset.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}



