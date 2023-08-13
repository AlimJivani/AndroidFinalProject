package com.example.alimjivanifinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;

public class checkout extends AppCompatActivity {

    EditText fullName, phoneNumber, address, city, postalCode, province, cardNumber, holderName, expiryMonth, expiryYear, cardCvv;
    Button CheckoutClick;
    ImageView backClick, cartClick, logoutClick;
    TextView paymentTotal;
    FirebaseAuth myAuthentication;
    View includeNav;
    TextView navHeading;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        myAuthentication = FirebaseAuth.getInstance();
        FirebaseUser currentUser = myAuthentication.getCurrentUser();

        fullName = findViewById(R.id.checkoutFullName);
        phoneNumber = findViewById(R.id.checkoutPhoneNumber);
        address = findViewById(R.id.checkoutAddress);
        city = findViewById(R.id.checkoutCity);
        postalCode = findViewById(R.id.checkoutPostalCode);
        province = findViewById(R.id.checkoutProvince);
        cardNumber = findViewById(R.id.checkoutCardNum);
        holderName = findViewById(R.id.checkoutCardHolderName);
        expiryMonth = findViewById(R.id.checkoutExpiryDate);
        expiryYear = findViewById(R.id.checkoutExpiryMonth);
        cardCvv = findViewById(R.id.checkoutCvv);
        CheckoutClick = findViewById(R.id.CheckoutClick);
        paymentTotal = findViewById(R.id.paymentTotal);

        includeNav = findViewById(R.id.includeNav);
        navHeading = includeNav.findViewById(R.id.navHeading);
        cartClick = includeNav.findViewById(R.id.cartClick);
        backClick = includeNav.findViewById(R.id.backClick);
        logoutClick = includeNav.findViewById(R.id.logoutClick);

        CheckoutClick.setOnClickListener(v -> {


            CardDetails cardDetails = new CardDetails(
                    cardNumber.getText().toString(),
                    holderName.getText().toString(),
                    expiryMonth.getText().toString(),
                    expiryYear.getText().toString(),
                    cardCvv.getText().toString()
            );

            UserDetails userDetails = new UserDetails(
                    fullName.getText().toString(),
                    Integer.parseInt(phoneNumber.getText().toString()),
                    address.getText().toString(),
                    city.getText().toString(),
                    postalCode.getText().toString(),
                    province.getText().toString(),
                    cardDetails
            );

            databaseReference.child("UserDetails").child(currentUser.getUid()).setValue(userDetails);
            Intent intent = new Intent(checkout.this, productDisplay.class);
            startActivity(intent);
            finish();
            Toast.makeText(this, "Order Placed", Toast.LENGTH_SHORT).show();

        });

        cartClick.setVisibility(View.GONE);
        backClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(checkout.this, AddToCart.class);
                startActivity(intent);
            }
        });
        logoutClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAuthentication = FirebaseAuth.getInstance();
                myAuthentication.signOut();
                Intent intent = new Intent(checkout.this, login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        Intent intent = getIntent();
        double pTotal = intent.getDoubleExtra("paymentTotal", 0.0);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        paymentTotal.setText("Total: $" + decimalFormat.format(pTotal));
    }

}