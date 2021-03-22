package com.LightHouse.Fitness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jb_fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class Main_Activity extends AppCompatActivity {

    private Button button_logOut;
    private FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_logOut = (Button) findViewById(R.id.button_main_logout);

        fbAuth = FirebaseAuth.getInstance();

        button_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fbAuth.signOut();
                startActivity(new Intent(Main_Activity.this, Login_Activity.class));
            }
        });
    }
}