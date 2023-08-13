package com.example.alimjivanifinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;

public class checkout extends AppCompatActivity {

    EditText fullName, phoneNumber, address, city, postalCode, province, cardNumber, holderName, expiryMonth, expiryYear, cardCvv;
    Button CheckoutClick;
    TextView paymentTotal;
    FirebaseAuth myAuthentication;
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

        CheckoutClick.setOnClickListener(v -> {
            String fullNameStr = fullName.getText().toString();
            int phoneNumberInt = Integer.parseInt(phoneNumber.getText().toString());
            String addressStr = address.getText().toString();
            String cityStr = city.getText().toString();
            String postalCodeStr = postalCode.getText().toString();
            String provinceStr = province.getText().toString();
            String cardNumberStr = cardNumber.getText().toString();
            String holderNameStr = holderName.getText().toString();
            String expiryMonthStr = expiryMonth.getText().toString();
            String expiryYearStr = expiryYear.getText().toString();
            String cardCvvStr = cardCvv.getText().toString();

            CardDetails cardDetails = new CardDetails(
                    cardNumber.getText().toString(),
                    holderName.getText().toString(),
                    expiryMonth.getText().toString(),
                    expiryYear.getText().toString(),
                    cardCvv.getText().toString()
            );

            UserDetails userDetails = new UserDetails(
                    fullNameStr,
                    phoneNumberInt,
                    addressStr,
                    cityStr,
                    postalCodeStr,
                    provinceStr,
                    cardDetails
            );

            databaseReference.child("UserDetails").child(currentUser.getUid()).setValue(userDetails);

        });

        Intent intent = getIntent();
        double pTotal = intent.getDoubleExtra("paymentTotal", 0.0);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        paymentTotal.setText("Total: $" + decimalFormat.format(pTotal));
    }

}