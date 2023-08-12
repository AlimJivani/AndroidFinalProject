package com.example.alimjivanifinalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class AddToCart extends AppCompatActivity {

    private CartManager cartManager;
    TextView paymentTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        paymentTotal = findViewById(R.id.paymentTotal);

        RecyclerView recyclerView = findViewById(R.id.cartProductList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CartManager cartManager = CartManager.getInstance();
        CartAdapter cartAdapter = new CartAdapter(cartManager.getCartItems());
        recyclerView.setAdapter(cartAdapter);
        updatePaymentTotal();
        cartAdapter.notifyDataSetChanged();

    }

    private void updatePaymentTotal() {
        cartManager = new CartManager();
        String totalPayment = cartManager.getTotalPayment();
        paymentTotal.setText("Total : $" + totalPayment);
    }
}