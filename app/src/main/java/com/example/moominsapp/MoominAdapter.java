package com.example.moominsapp;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

public class MoominAdapter extends RecyclerView.Adapter<MoominAdapter.MyViewHolder> implements Filterable {

    private final List<MoominModel> moominModels;
    private final List<MoominModel> moominModelsFull;
    private Dialog currentDialog; // Store the current Dialog instance

    public MoominAdapter(List<MoominModel> moominModels) {
        this.moominModels = new ArrayList<>(moominModels);
        this.moominModelsFull = new ArrayList<>(moominModels);
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

        // Handle row clicks to display a pop message for the selected character
        holder.itemView.setOnClickListener(v -> displayPopMessage(holder.itemView, currentItem));
    }

    /**
     * Display a pop message for a specific character.
     *
     * @param view         The view context for the Dialog.
     * @param character    The MoominModel character to display.
     */
    public void displayPopMessage(View view, MoominModel character) {
        // Dismiss the current dialog if it exists
        dismissCurrentDialog();

        // Create and configure a Dialog
        currentDialog = new Dialog(view.getContext());
        currentDialog.setContentView(R.layout.custom_pop_layout); // Use a custom layout file
        currentDialog.setCancelable(true);

        // Set Dialog width and height
        if (currentDialog.getWindow() != null) {
            currentDialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            currentDialog.getWindow().setGravity(Gravity.CENTER);
        }

        // Configure the Dialog content
        ImageView imageView = currentDialog.findViewById(R.id.dialogImage);
        TextView textView = currentDialog.findViewById(R.id.dialogText);
        Button dismissButton = currentDialog.findViewById(R.id.dialogDismissButton);

        if (imageView != null && textView != null && dismissButton != null) {
            imageView.setImageResource(character.getImage());
            textView.setText(character.getMoominName());
            dismissButton.setText("Dismiss"); // Set a clear dismiss label
            dismissButton.setOnClickListener(v -> dismissCurrentDialog());
        }

        // Show the Dialog
        currentDialog.show();
    }

    /**
     * Dismiss the current Dialog if it's displayed.
     */
    public void dismissCurrentDialog() {
        if (currentDialog != null && currentDialog.isShowing()) {
            currentDialog.dismiss();
            currentDialog = null;
        }
    }

    @Override
    public int getItemCount() {
        return moominModels != null ? moominModels.size() : 0; // Handle potential null list
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
