package com.example.andrey.naumentest.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrey.naumentest.R;
import com.example.andrey.naumentest.adapters.SimilarAdapter;
import com.example.andrey.naumentest.entities.Item;
import com.example.andrey.naumentest.storage.OnListItemClickListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardActivity extends AppCompatActivity{
    private int itemId;
    private TextView companyName;
    private TextView description;
    private ImageView cardImg;
    private RecyclerView similarList;
    private List<Item>similarItems = new ArrayList<>();
    SimilarAdapter adapter;
    List<View>elementsOnPage = new ArrayList<>();

    private OnListItemClickListener clickListener = (v, position) -> {
        Intent intent = new Intent(this, CardActivity.class);
        intent.putExtra("itemId", similarItems.get(position).getId());
        startActivity(intent);
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_item);


        itemId = getIntent().getIntExtra("itemId", 0);
        companyName = (TextView) findViewById(R.id.company_name);
        description = (TextView) findViewById(R.id.description);
        cardImg = (ImageView) findViewById(R.id.comp_img);
        similarList = (RecyclerView) findViewById(R.id.similar_list);

        addToTouchArray();

        loadPage();


    }

    private void addToTouchArray() {
        elementsOnPage.add(similarList);
        elementsOnPage.add(cardImg);
        elementsOnPage.add(description);
        elementsOnPage.add(companyName);

        for(View v:elementsOnPage){
            v.setOnClickListener(v1 -> {
                description.setMaxLines(Integer.MAX_VALUE);
                description.setEllipsize(null);
            });
        }
    }

    private void loadPage(){
        App.getData().getComputer(itemId).enqueue(new Callback<Item>() {
            @Override
            public void onResponse(@NonNull Call<Item> call, @NonNull Response<Item> response) {
                Context context = CardActivity.this;

                Item item = (response.body());
                getSupportActionBar().setTitle(item.getName());
                if(item.getCompany()!=null) {
                    companyName.setText(item.getCompany().getName());
                }
                description.setText(item.getDescription());

                getSimilar(item.getId(), context);

                Picasso.with(context).load(item.getImageUrl()).into(cardImg);

            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                Toast.makeText(CardActivity.this, "error data download", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getSimilar(int id, Context context) {
        App.getData().getSimilarList(id).enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                similarItems.addAll(response.body());
                similarList.setLayoutManager(new LinearLayoutManager(context));
                adapter = new SimilarAdapter(similarItems, clickListener);
                similarList.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Toast.makeText(context, "error data download", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
