package com.example.alimjivanifinalproject;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

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
    private int currentPage = 1;
    private int itemsPerPage = 5;
    private boolean isLoading = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_display);

        recyclerView = findViewById(R.id.productList);
        db = FirebaseDatabase.getInstance().getReference("Products");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        products = new ArrayList<>();

        myAdapter = new myAdapter(this, products,recyclerView);
        recyclerView.setAdapter(myAdapter);
        pBar = findViewById(R.id.pBar);

        loadNextPage();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                // Check if the last item is visible and load the next page
                if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0)  {
                    isLoading = true;
                    Log.d(TAG, "totalItemCount" + totalItemCount);
                    loadNextPage();
                }
            }
        });

    }

    private void loadNextPage() {
        // Query Firebase to load the next page of products
        pBar.setVisibility(View.VISIBLE);
        Query query = db.orderByKey().startAt("p" + ((currentPage - 1) * itemsPerPage)).limitToFirst(itemsPerPage);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    productData product = dataSnapshot.getValue(productData.class);

                    if (product != null) {
                        products.add(product);
                    }
                }

                // Update the current page and notify the adapter of changes
                currentPage++;
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Failed to read value.", error.toException());
            }
        });
        isLoading = false;
        pBar.setVisibility(View.GONE);
    }


}