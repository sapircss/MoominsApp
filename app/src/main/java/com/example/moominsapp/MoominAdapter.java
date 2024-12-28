package com.example.moominsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Filter;
import android.widget.Filterable;
import java.util.ArrayList;
import java.util.List;

public class MoominAdapter extends RecyclerView.Adapter<MoominAdapter.MyViewHolder> implements Filterable {

    private final List<MoominModel> moominModels;
    private final List<MoominModel> moominModelsFull; // Backup list for filtering

    public MoominAdapter(List<MoominModel> moominModels) {
        this.moominModels = new ArrayList<>(moominModels);
        this.moominModelsFull = new ArrayList<>(moominModels); // Deep copy for filtering
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvName, tvDescription;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            tvName = itemView.findViewById(R.id.moomin_name);
            tvDescription = itemView.findViewById(R.id.moomin_description);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MoominModel currentItem = moominModels.get(position);
        holder.tvName.setText(currentItem.getMoominName());
        holder.tvDescription.setText(currentItem.getMoominDesc());
        holder.imageView.setImageResource(currentItem.getImage());

        // Handle row clicks
        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Clicked: " + currentItem.getMoominName(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return moominModels.size();
    }

    @Override
    public Filter getFilter() {
        return moominFilter;
    }

    private final Filter moominFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<MoominModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(moominModelsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (MoominModel item : moominModelsFull) {
                    if (item.getMoominName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        @SuppressWarnings("unchecked")
        protected void publishResults(CharSequence constraint, FilterResults results) {
            moominModels.clear();
            if (results.values instanceof List) {
                moominModels.addAll((List<MoominModel>) results.values);
            }
            notifyDataSetChanged();
        }
    };
}
