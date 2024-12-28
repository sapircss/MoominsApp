package com.example.moominsapp;

import android.os.Bundle;
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

        recyclerView = findViewById(R.id.moominsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        moomins_arr = generateMoominsArr();
        adapter = new MoominAdapter(moomins_arr);
        recyclerView.setAdapter(adapter);

        SearchView searchView = findViewById(R.id.searchBar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private ArrayList<MoominModel> generateMoominsArr() {
        ArrayList<MoominModel> moomins = new ArrayList<>();
        for (int i = 0; i < Data.moomin_name.length; i++) {
            moomins.add(new MoominModel(
                    Data.moomin_name[i],
                    Data.moomin_des[i],
                    Data.images[i],
                    Data.id[i]
            ));
        }
        return moomins;
    }
}
