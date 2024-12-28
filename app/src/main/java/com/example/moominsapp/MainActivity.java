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

  
    private void initializeViews() {
        recyclerView = findViewById(R.id.moominsRecyclerView);
    }

   
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

   
    private void setupSearchView() {
        SearchView searchView = findViewById(R.id.searchBar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dismissPopMessageIfVisible();
                adapter.getFilter().filter(query.toLowerCase().trim()); 
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                dismissPopMessageIfVisible();
                adapter.getFilter().filter(newText.toLowerCase().trim()); 
                return false;
            }
        });

      
        searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                dismissPopMessageIfVisible();
            }
        });
    }

  
    private void setupRootTouchListener() {
        View rootView = findViewById(android.R.id.content);
        rootView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                dismissPopMessageIfVisible();
            }
            return false;
        });
    }

  
    private void dismissPopMessageIfVisible() {
        if (adapter != null) {
            adapter.dismissCurrentDialog(); 
        }
    }

    
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
