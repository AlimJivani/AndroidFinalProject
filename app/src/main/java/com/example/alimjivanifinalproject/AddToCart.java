package com.example.alimjivanifinalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class AddToCart extends AppCompatActivity {

    CartManager cartManager;
    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    DatabaseReference db;
    View includeNav;
    TextView navHeading, paymentTotal;
    ImageView backClick, cartClick, logoutClick;
    FirebaseAuth mAuth;
    Button CheckoutClick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        includeNav = findViewById(R.id.includeNav);
        navHeading = includeNav.findViewById(R.id.navHeading);
        cartClick = includeNav.findViewById(R.id.cartClick);
        backClick = includeNav.findViewById(R.id.backClick);
        paymentTotal = findViewById(R.id.paymentTotal);
        CheckoutClick = findViewById(R.id.CheckoutClick);
        logoutClick = includeNav.findViewById(R.id.logoutClick);

        recyclerView = findViewById(R.id.cartProductList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cartManager = CartManager.getInstance();
        cartAdapter = new CartAdapter(cartManager.getCartItems(), this);
        cartAdapter.saveCartItemsToFirestore();

        recyclerView.setAdapter(cartAdapter);
        updatePaymentTotal();
        cartAdapter.notifyDataSetChanged();

        cartClick.setVisibility(View.GONE);
        backClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddToCart.this, productDisplay.class);
                startActivity(intent);
            }
        });

        logoutClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                Intent intent = new Intent(AddToCart.this, login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        CheckoutClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddToCart.this, checkout.class);
                intent.putExtra("paymentTotal", updatePaymentTotal());
                startActivity(intent);
            }
        });

    }

    public double updatePaymentTotal() {
        double total = cartManager.calculateTotal();
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        paymentTotal.setText("Total: $" + decimalFormat.format(total));
        return total;
    }

    public void loadData() {

        db = FirebaseDatabase.getInstance().getReference("cartItems");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot cartItemSnapshot : dataSnapshot.getChildren()) {
                    String name = cartItemSnapshot.child("name").getValue(String.class);
                    String id = cartItemSnapshot.child("id").getValue(String.class);
                    double price = cartItemSnapshot.child("price").getValue(Double.class);
                    int quantity = cartItemSnapshot.child("quantity").getValue(Integer.class);
                    String image = cartItemSnapshot.child("image").getValue(String.class);

                    CartItem cartItem = new CartItem(name, price, image, id, quantity);
                    cartManager.addToCart(cartItem);
                }
                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


}