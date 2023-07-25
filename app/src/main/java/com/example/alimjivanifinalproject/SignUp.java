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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class SignUp extends AppCompatActivity {

    Button signUpButtonClick;
    EditText signUpEmailTextInput, signUpPasswordTextInput, signUpCPasswordTextInput;
    FirebaseAuth mAuth;
    TextView forgotPassword, loginClick;
    ProgressBar pBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpButtonClick = findViewById(R.id.signUpButtonClick);
        mAuth = FirebaseAuth.getInstance();
        signUpEmailTextInput = findViewById(R.id.signUpEmailTextInput);
        signUpPasswordTextInput = findViewById(R.id.signUpPasswordTextInput);
        signUpCPasswordTextInput = findViewById(R.id.signUpCPasswordTextInput);
        loginClick = findViewById(R.id.loginClick);
        pBar = findViewById(R.id.pBar);

        signUpButtonClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pBar.setVisibility(View.VISIBLE);
                String email, password, cPassword;
                email =  String.valueOf(signUpEmailTextInput.getText());
                password =  String.valueOf(signUpPasswordTextInput.getText());
                cPassword =  String.valueOf(signUpCPasswordTextInput.getText());

                if (validateUser(email, password, cPassword)){
                    registerUser(email, password);
                    Intent intent = new Intent(SignUp.this, login.class);
                    startActivity(intent);
                    finish();
                } else{
                    pBar.setVisibility(View.GONE);
                }
            }
        });

        loginClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, login.class);
                startActivity(intent);
            }
        });
    }

    private void registerUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                // Send verification email
                                user.sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> verificationTask) {
                                                if (verificationTask.isSuccessful()) {
                                                    // Verification email sent successfully
                                                    pBar.setVisibility(View.GONE);
                                                    Toast.makeText(SignUp.this, "Successfully SignUp. Verification email sent.",
                                                            Toast.LENGTH_SHORT).show();
                                                } else {
                                                    // Failed to send verification email
                                                    Toast.makeText(SignUp.this, "Failed to send verification email.",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                // User object is null
                                Toast.makeText(SignUp.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Failed to create user account
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        pBar.setVisibility(View.GONE);
                    }
                });
    }


    private boolean validateUser(String email, String password, String cPassword){
        final boolean[] checkErrors = {true};

        if (TextUtils.isEmpty(email)) {
            signUpEmailTextInput.setError("Email is required.");
            checkErrors[0] = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signUpEmailTextInput.setError("Enter Valid Email");
            checkErrors[0] = false;
        } else if(checkErrors[0]){
            // Using asynchronous email validation because to check email it takes time and at end it will return true so I have to use this asynchronous
            CompletableFuture<Boolean> emailValidationFuture = CompletableFuture.supplyAsync(() -> {
                try {
                    // Fetch sign-in methods for the email
                    Task<SignInMethodQueryResult> task = mAuth.fetchSignInMethodsForEmail(email);
                    Tasks.await(task); // Wait for the task to complete

                    if (task.isSuccessful()) {
                        SignInMethodQueryResult result = task.getResult();
                        return result != null && result.getSignInMethods() != null && result.getSignInMethods().size() > 0;
                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                return false;
            });

            try {
                boolean emailExists = emailValidationFuture.get();
                if (emailExists) {
                    signUpEmailTextInput.setError("This email is already registered.");
                    checkErrors[0] = false;
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        if (TextUtils.isEmpty(password)) {
            signUpPasswordTextInput.setError("Password is required.");
            checkErrors[0] = false;
        } else if (password.length() < 6) {
            signUpPasswordTextInput.setError("Password must be at least 6 characters.");
            checkErrors[0] = false;
        }

        if (TextUtils.isEmpty(cPassword)) {
            signUpCPasswordTextInput.setError("Confirm Password is required.");
            checkErrors[0] = false;
        } else if (!password.equals(cPassword)) {
            signUpCPasswordTextInput.setError("Password and Confirm Password must be the same.");
            checkErrors[0] = false;
        }
        return checkErrors[0];
    }
}