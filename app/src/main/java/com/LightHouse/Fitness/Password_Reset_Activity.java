package com.LightHouse.Fitness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.LightHouse_Fitness.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Password_Reset_Activity extends AppCompatActivity {

    private EditText userEmail;
    private Button button_Submit;
    private FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password__reset);

        button_Submit = (Button) findViewById(R.id.button_Reset_Submit);
        userEmail = (EditText) findViewById(R.id.editText_UserEmail);

        fbAuth = FirebaseAuth.getInstance();

        button_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = userEmail.getText().toString().trim();
                emailValidation(email);
            }
        });

    }

    public void emailValidation(String email) {
        if (email.equals("")) {
            Toast.makeText(Password_Reset_Activity.this, "Please enter the email associated to the account", Toast.LENGTH_SHORT).show();
        } else {
            fbAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Password_Reset_Activity.this, "Check Email to reset your password", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Password_Reset_Activity.this, Login_Activity.class));
                    } else {
                        Toast.makeText(Password_Reset_Activity.this, "Failed to send Password Reset via Email", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("UserEmail", userEmail.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        userEmail.setText(savedInstanceState.getString("UserEmail"));
    }

}