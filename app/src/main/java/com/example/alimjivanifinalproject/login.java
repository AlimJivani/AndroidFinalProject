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

public class login extends AppCompatActivity {

    Button loginButtonClick;
    EditText loginEmailTextInput, loginPasswordTextInput;
    FirebaseAuth mAuth;
    TextView forgotPassword, signUpClick;
    ProgressBar pBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButtonClick = findViewById(R.id.loginButtonClick);
        mAuth = FirebaseAuth.getInstance();
        loginEmailTextInput = findViewById(R.id.loginEmailTextInput);
        loginPasswordTextInput = findViewById(R.id.loginPasswordTextInput);
        forgotPassword = findViewById(R.id.forgotPassword);
        signUpClick = findViewById(R.id.signUpClick);
        pBar = findViewById(R.id.pBar);

        loginButtonClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pBar.setVisibility(View.VISIBLE);
                String email, password;
                email =  String.valueOf(loginEmailTextInput.getText());
                password =  String.valueOf(loginPasswordTextInput.getText());

                if (validateUser(email, password)){
                    loginUser(email, password);
                }else{
                    pBar.setVisibility(View.GONE);
                }
            }
        });

        signUpClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, SignUp.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null && user.isEmailVerified()) {
                                Toast.makeText(login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(login.this, "Please verify your email address.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            loginPasswordTextInput.setError("Authentication failed. Please check your credentials.");
                        }
                    }
                });
        pBar.setVisibility(View.GONE);
    }

    private boolean validateUser(String email, String password){
        final boolean[] checkErrors = {true};

        if (TextUtils.isEmpty(email)) {
            loginEmailTextInput.setError("Email is required.");
            checkErrors[0] = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginEmailTextInput.setError("Enter Valid Email");
            checkErrors[0] = false;
        }

        if (TextUtils.isEmpty(password)) {
            loginPasswordTextInput.setError("Password is required.");
            checkErrors[0] = false;
        } else if (password.length() < 6) {
            loginPasswordTextInput.setError("Password must be at least 6 characters.");
            checkErrors[0] = false;
        }
        return checkErrors[0];
    }

}