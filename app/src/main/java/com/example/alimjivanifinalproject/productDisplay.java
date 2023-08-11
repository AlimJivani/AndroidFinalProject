package com.example.alimjivanifinalproject;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class productDisplay extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference db;
    myAdapter myAdapter;
    ArrayList<productData> products;
    private ProgressBar pBar;
    View includeNav;
    ImageView backClick, cartClick;
    TextView navHeading;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_display);


        includeNav = findViewById(R.id.includeNav);
        backClick = includeNav.findViewById(R.id.backClick);
        cartClick = includeNav.findViewById(R.id.cartClick);
        navHeading = includeNav.findViewById(R.id.navHeading);
        backClick.setVisibility(View.GONE);
        navHeading.setText("Products");

        recyclerView = findViewById(R.id.productList);
        db = FirebaseDatabase.getInstance().getReference("Products");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        products = new ArrayList<>();

        myAdapter = new myAdapter(this, products,recyclerView);
        recyclerView.setAdapter(myAdapter);
        pBar = findViewById(R.id.pBar);

        loadData();

        cartClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(productDisplay.this, AddToCart.class);
                startActivity(intent);
            }
        });
}


    private void loadData() {
        recyclerView.setVisibility(View.GONE);
        pBar.setVisibility(View.VISIBLE);

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    productData product = dataSnapshot.getValue(productData.class);
                    if (product != null) {
                        products.add(product);
                    }
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Failed to read value.", error.toException());
            }
        });
        recyclerView.setVisibility(View.VISIBLE);
        pBar.setVisibility(View.GONE);
    }


}