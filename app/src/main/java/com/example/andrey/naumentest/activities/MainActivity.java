package com.example.andrey.naumentest.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrey.naumentest.R;
import com.example.andrey.naumentest.adapters.CompItemsAdapter;
import com.example.andrey.naumentest.entities.Item;
import com.example.andrey.naumentest.entities.ResultSet;
import com.example.andrey.naumentest.storage.OnListItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private int currentPage = 1;
    private Button previous;
    private Button next;
    private TextView pageNumber;
    CompItemsAdapter adapter;
    List<Item> items = new ArrayList<>();
    int pageTotal;

    private OnListItemClickListener clickListener = (v, position) -> {
        Intent intent = new Intent(this, CardActivity.class);
        intent.putExtra("itemId", items.get(position).getId());
        startActivity(intent);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentPage = getPref();

        pageNumber = (TextView) findViewById(R.id.page_number);
        recyclerView = (RecyclerView) findViewById(R.id.comp_items);
        next = (Button) findViewById(R.id.btn_next);
        previous = (Button) findViewById(R.id.btn_previous);

        next.setOnClickListener(v -> openNextPage());
        previous.setOnClickListener(v -> openPreviousPage());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CompItemsAdapter(items, clickListener);
        recyclerView.setAdapter(adapter);
        loadPage(currentPage);
    }

    private void loadPage(int pageNum){
        App.getData().getPage(pageNum).enqueue(new Callback<ResultSet>() {
            @Override
            public void onResponse(@NonNull Call<ResultSet> call, @NonNull Response<ResultSet> response) {
                items.clear();

                pageTotal = response.body().getTotal()/10;
                items.addAll(response.body().getItems());

                pageNumber.setText("Page " + response.body().getPage() + " of " + (pageTotal));
                adapter.notifyDataSetChanged();
                addToPref(pageNum);
            }

            @Override
            public void onFailure(Call<ResultSet> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error data download", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addToPref(int pageId){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("pageId", pageId);
        editor.apply();
    }

    private int getPref(){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getInt("pageId", 1);
    }

    private void openPreviousPage() {
        if(currentPage<=1){
            currentPage = pageTotal;
            loadPage(currentPage); // check
            return;
        }else {
            loadPage(--currentPage);
        }
    }

    private void openNextPage() {
        if(currentPage>=pageTotal){
            currentPage = 1;
            loadPage(currentPage);
        }else {
            loadPage(++currentPage);
        }
    }
}
