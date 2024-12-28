package com.example.moominsapp;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MoominAdapter adapter;
    private ArrayList<MoominModel> moomins_arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupRecyclerView();
        setupSearchView();
        setupRootTouchListener();
    }

    /**
     * Initialize the views.
     */
    private void initializeViews() {
        recyclerView = findViewById(R.id.moominsRecyclerView);
    }

    /**
     * Setup the RecyclerView with the adapter.
     */
    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        moomins_arr = generateMoominsArr();

        // Check if data is empty
        if (moomins_arr.isEmpty()) {
            Toast.makeText(this, "No data available", Toast.LENGTH_SHORT).show();
        }

        adapter = new MoominAdapter(moomins_arr);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Setup the SearchView for filtering the RecyclerView.
     */
    private void setupSearchView() {
        SearchView searchView = findViewById(R.id.searchBar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dismissPopMessageIfVisible();
                adapter.getFilter().filter(query.toLowerCase().trim()); // Ensure case-insensitive search
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                dismissPopMessageIfVisible();
                adapter.getFilter().filter(newText.toLowerCase().trim()); // Ensure case-insensitive search
                return false;
            }
        });

        // Dismiss pop message when the search bar is focused
        searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                dismissPopMessageIfVisible();
            }
        });
    }

    /**
     * Set up a touch listener for the root layout to dismiss the pop message.
     */
    private void setupRootTouchListener() {
        View rootView = findViewById(android.R.id.content);
        rootView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                dismissPopMessageIfVisible();
            }
            return false;
        });
    }

    /**
     * Dismiss the pop-up message if it is currently visible.
     */
    private void dismissPopMessageIfVisible() {
        if (adapter != null) {
            adapter.dismissCurrentDialog(); // Call the new method in MoominAdapter to dismiss the Dialog
        }
    }

    /**
     * Generate an ArrayList of MoominModel objects.
     *
     * @return ArrayList of MoominModel
     */
    private ArrayList<MoominModel> generateMoominsArr() {
        ArrayList<MoominModel> moomins = new ArrayList<>();
        if (Data.moomin_name != null && Data.moomin_des != null && Data.images != null && Data.id != null) {
            for (int i = 0; i < Data.moomin_name.length; i++) {
                moomins.add(new MoominModel(
                        Data.moomin_name[i],
                        Data.moomin_des[i],
                        Data.images[i],
                        Data.id[i]
                ));
            }
        }
        return moomins;
    }
}
