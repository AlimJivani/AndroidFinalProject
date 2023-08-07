package com.example.alimjivanifinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class checkout extends AppCompatActivity {

    EditText fullName, phoneNumber, address, city, postalCode, province, cardNumber, holderName, expiryMonth, expiryYear, cardCvv;
    Button CheckoutClick;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);


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

        CheckoutClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullNameValue = fullName.getText().toString();
                int phoneNumberValue = Integer.parseInt(phoneNumber.getText().toString());
                String addressValue = address.getText().toString();
                String cityValue = city.getText().toString();
                String postalCodeValue = postalCode.getText().toString();
                String provinceValue = province.getText().toString();
                String cardNumberValue = cardNumber.getText().toString();
                String holderNameValue = holderName.getText().toString();
                String expiryMonthValue = expiryMonth.getText().toString();
                String expiryYearValue = expiryYear.getText().toString();
                String cardCvvValue = cardCvv.getText().toString();
            }

//            UserDetails userDetails = new UserDetails(
//                    fullNameValue, phoneNumberValue, addressValue, cityValue, postalCodeValue,
//                    provinceValue, cardNumberValue, holderNameValue, expiryMonthValue,
//                    expiryYearValue, cardCvvValue
//            );

            String userId = databaseReference.child("users").push().getKey();
         });

    }
}