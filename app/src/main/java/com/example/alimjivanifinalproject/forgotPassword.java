package com.example.alimjivanifinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPassword extends AppCompatActivity {

    Button fPasswordButtonClick, fPasswordBack;
    EditText fPasswordEmailTextInput;
    FirebaseAuth mAuth;
    ProgressBar pBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        fPasswordButtonClick = findViewById(R.id.fPasswordButtonClick);
        fPasswordBack = findViewById(R.id.fPasswordBack);
        fPasswordEmailTextInput = findViewById(R.id.fPasswordEmailTextInput);
        pBar = findViewById(R.id.pBar);
        mAuth = FirebaseAuth.getInstance();

        fPasswordButtonClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email;
                email =  fPasswordEmailTextInput.getText().toString().trim();
                if (validateUser(email)){
                    resetPassword(email);
                }
            }
        });
    }

    private void resetPassword(String email) {
        pBar.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(forgotPassword.this, "Reset password link send to your register email id", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(forgotPassword.this, login.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                fPasswordEmailTextInput.setError("Email not found. Please enter a valid email address.");
            }
        });

        pBar.setVisibility(View.GONE);

    }
    private boolean validateUser(String email) {
        final boolean[] checkErrors = {true};

        if (TextUtils.isEmpty(email)) {
            fPasswordEmailTextInput.setError("Email is required.");
            checkErrors[0] = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            fPasswordEmailTextInput.setError("Enter Valid Email");
            checkErrors[0] = false;
        }

        return checkErrors[0];
    }


}