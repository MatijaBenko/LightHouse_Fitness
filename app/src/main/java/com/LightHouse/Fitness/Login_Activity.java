package com.LightHouse.Fitness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.LightHouse_fitness.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login_Activity extends AppCompatActivity {

    private TextView newUser, forgotPassword;
    private EditText editUserEmail, editUserPassword;
    private Button button_signIn;

    private FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        newUser = (TextView) findViewById(R.id.Text_CreateAccount);
        editUserEmail = (EditText) findViewById(R.id.Text_Login_EmailAddress);
        editUserPassword = (EditText) findViewById(R.id.Text_Login_Password);
        button_signIn = (Button) findViewById(R.id.button_Login_SignIn);
        forgotPassword = (TextView) findViewById(R.id.Text_Forgot_Password);

        SpannableString ss = new SpannableString("Don't have an account?\nCreate an Account");
        ForegroundColorSpan fcsLightBlue = new ForegroundColorSpan(Color.parseColor("#FF04C2DC"));
        ss.setSpan(fcsLightBlue, 22, 40, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        newUser.setText(ss);
        main(newUser, editUserEmail, editUserPassword, button_signIn, forgotPassword);
    }

    private void main(TextView newUser, EditText editUserEmail, EditText editUserPassword, Button button_signIn, TextView forgotPassword) {
        fbAuth = FirebaseAuth.getInstance();
        FirebaseUser tmpUser = fbAuth.getCurrentUser();

        if (tmpUser != null) {
            startActivity(new Intent(Login_Activity.this, Main_Activity.class));
        }

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Activity.this, Registration_Activity.class));
            }
        });

        button_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = editUserEmail.getText().toString().trim();
                String userPassword = editUserPassword.getText().toString().trim();

                validation(userEmail, userPassword);

                editUserEmail.setText("");
                editUserPassword.setText("");
            }
        });


        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login_Activity.this, Password_Reset_Activity.class));
            }
        });
    }

    public void validation(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(Login_Activity.this, "Login Failed", Toast.LENGTH_SHORT).show();
        } else {
            fbAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        checkEmailVerification();
                    } else {
                        Toast.makeText(Login_Activity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void checkEmailVerification() {
        FirebaseUser fbUser = fbAuth.getCurrentUser();
        Boolean isEmailVerify = fbUser.isEmailVerified();
        if (isEmailVerify) {
            Toast.makeText(Login_Activity.this, "Login Successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Login_Activity.this, Main_Activity.class));
        } else {
            Toast.makeText(Login_Activity.this, "Please have your email Verify", Toast.LENGTH_SHORT).show();
            fbAuth.signOut();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("UserEmail", editUserEmail.getText().toString());
        outState.putString("UserPassword", editUserPassword.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        editUserEmail.setText(savedInstanceState.getString("UserEmail"));
        editUserPassword.setText(savedInstanceState.getString("UserPassword"));
    }
}