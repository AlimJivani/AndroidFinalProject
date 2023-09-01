package com.example.alimjivanifinalproject;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

    EditText fullName, phoneNumber, address, city, postalCode, province, cardNumber, holderName, expiryMonth, expiryYear, cardCvv, checkoutCouponCode;
    Button CheckoutClick, checkoutCouponClick;
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

        CheckoutClick = findViewById(R.id.CheckoutClick);
        checkoutCouponClick = findViewById(R.id.checkoutCouponClick);
        paymentTotal = findViewById(R.id.paymentTotal);

        fullName = findViewById(R.id.checkoutFullName);
        phoneNumber = findViewById(R.id.checkoutPhoneNumber);
        address = findViewById(R.id.checkoutAddress);
        city = findViewById(R.id.checkoutCity);
        postalCode = findViewById(R.id.checkoutPostalCode);
        province = findViewById(R.id.checkoutProvince);
        cardNumber = findViewById(R.id.checkoutCardNum);
        holderName = findViewById(R.id.checkoutCardHolderName);
        expiryMonth = findViewById(R.id.checkoutExpiryMonth);
        expiryYear = findViewById(R.id.checkoutExpiryYear);
        cardCvv = findViewById(R.id.checkoutCvv);
        checkoutCouponCode = findViewById(R.id.checkoutCouponCode);


        includeNav = findViewById(R.id.includeNav);
        navHeading = includeNav.findViewById(R.id.navHeading);
        cartClick = includeNav.findViewById(R.id.cartClick);
        backClick = includeNav.findViewById(R.id.backClick);
        logoutClick = includeNav.findViewById(R.id.logoutClick);

        CheckoutClick.setOnClickListener(v -> {

            if (validateUser()) {

                CardDetails cardDetails = new CardDetails(
                        cardNumber.getText().toString(),
                        holderName.getText().toString(),
                        expiryMonth.getText().toString(),
                        expiryYear.getText().toString(),
                        cardCvv.getText().toString()
                );

                UserDetails userDetails = new UserDetails(
                        fullName.getText().toString(),
                        phoneNumber.getText().toString(),
                        address.getText().toString(),
                        city.getText().toString(),
                        postalCode.getText().toString(),
                        province.getText().toString(),
                        cardDetails
                );

                databaseReference.child("UserDetails").child(currentUser.getUid()).setValue(userDetails)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(checkout.this, productDisplay.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(this, "Order Placed", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "Error placing order. Please try again later.", Toast.LENGTH_SHORT).show();
                            }
                });
            }
        });


        cartClick.setVisibility(View.GONE);
        navHeading.setText("Checkout");
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

        checkoutCouponClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String couponCode;
                couponCode = checkoutCouponCode.getText().toString().trim();
                Log.d(TAG, "onClick: " + (couponCode == "offer50"));
                if ("offer50".equals(couponCode)){
                    double discountAmount = pTotal * 0.30;
                    double discountedPrice = pTotal - discountAmount;
                    paymentTotal.setText("Total: $" + decimalFormat.format(discountedPrice));
                    Toast.makeText(checkout.this, "Offer Applied", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateUser() {

        final boolean[] checkErrors = {true};


        String fullNameStr = fullName.getText().toString();
        String phoneNumberStr = phoneNumber.getText().toString();
        String addressStr = address.getText().toString();
        String cityStr = city.getText().toString();
        String postalCodeStr = postalCode.getText().toString();
        String provinceStr = province.getText().toString();

        String cardNumberStr = cardNumber.getText().toString();
        String holderNameStr = holderName.getText().toString();
        String expiryMonthStr = expiryMonth.getText().toString();
        String expiryYearStr = expiryYear.getText().toString();
        String cardCvvStr = cardCvv.getText().toString();


        if (TextUtils.isEmpty(fullNameStr)) {
            fullName.setError("Full name is required.");
            checkErrors[0] = false;
        } else if (fullNameStr.length() <= 4) {
            fullName.setError("Full name must be more than 4 characters");
            checkErrors[0] = false;
        } else if(!fullNameStr.matches("^(?!.*[ ]{2})[a-zA-Z ]+$")){
            fullName.setError("Full name should only contain letters and spaces.");
            checkErrors[0] = false;
        }

        if (TextUtils.isEmpty(phoneNumberStr)) {
            phoneNumber.setError("Phone number is required.");
        } else if (phoneNumberStr.length() != 10) {
            phoneNumber.setError("Phone number must be 10 digits.");
            checkErrors[0] = false;
        }

        if (TextUtils.isEmpty(addressStr)) {
            address.setError("Address is required.");
            checkErrors[0] = false;
        }else if (!addressStr.matches("^[a-zA-Z0-9\\s]+$")){
            address.setError("Enter Valid address.");
            checkErrors[0] = false;
        }

        if (TextUtils.isEmpty(cityStr)) {
            city.setError("City is required.");
            checkErrors[0] = false;
        }else if (!cityStr.matches("^(?!.*[ ]{2})[a-zA-Z ]+$")){
            city.setError("Enter Valid City.");
            checkErrors[0] = false;
        }

        if (TextUtils.isEmpty(postalCodeStr)) {
            postalCode.setError("Postal code is required.");
            checkErrors[0] = false;
        } else if (!postalCodeStr.matches("^[a-zA-Z0-9\\s]+$")){
            postalCode.setError("Enter Valid Postal Code.");
            checkErrors[0] = false;
        }

        if (postalCodeStr.length() != 6) {
            postalCode.setError("Postal code must be 6 characters.");
            checkErrors[0] = false;
        }

        if (TextUtils.isEmpty(provinceStr)) {
            province.setError("Province is required.");
            checkErrors[0] = false;
        } else if (!provinceStr.matches("^(?!.*[ ]{2})[a-zA-Z ]+$")){
            province.setError("Enter Valid Province.");
            checkErrors[0] = false;
        }

        if (TextUtils.isEmpty(cardNumberStr)) {
            cardNumber.setError("Card number is required.");
            checkErrors[0] = false;
        } else if (cardNumberStr.length() > 20) {
            cardNumber.setError("Card number can not be more then 20 digit.");
            checkErrors[0] = false;
        } else if (cardNumberStr.length() < 16) {
            cardNumber.setError("Card number can not be less then 16 digit.");
            checkErrors[0] = false;
        }

        if (TextUtils.isEmpty(holderNameStr)) {
            holderName.setError("Holder name is required.");
            checkErrors[0] = false;
        } else if (holderNameStr.length() <= 4) {
            holderName.setError("Holder name must be more than 4 characters");
            checkErrors[0] = false;
        } else if(!holderNameStr.matches("^(?!.*[ ]{2})[a-zA-Z ]+$")){
            holderName.setError("Holder name should only contain letters and spaces.");
            checkErrors[0] = false;
        }

        if (TextUtils.isEmpty(expiryMonthStr)) {
            expiryMonth.setError("Expiry month required.");
            checkErrors[0] = false;
        } else {
            int month = Integer.parseInt(expiryMonthStr);
            if (month < 1 || month > 12) {
                expiryMonth.setError("Expiry month can not be " + month);
                checkErrors[0] = false;
            }
        }

        if (TextUtils.isEmpty(expiryYearStr)) {
            expiryYear.setError("Expiry year required.");
            checkErrors[0] = false;
        }

        if (TextUtils.isEmpty(cardCvvStr)) {
            cardCvv.setError(" Cvv is required.");
            checkErrors[0] = false;
        }

        return checkErrors[0];
    }


}