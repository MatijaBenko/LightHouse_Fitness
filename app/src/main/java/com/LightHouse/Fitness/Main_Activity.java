package com.LightHouse.Fitness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.LightHouse_Fitness.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Main_Activity extends AppCompatActivity {

    //private Button button_Logout;
    private FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bottom Navigation Bar
        BottomNavigationView btnNav = findViewById(R.id.bottomNavigationview);
        btnNav.setOnNavigationItemSelectedListener(navListener);

        // Setting Workout Fragment as Main Fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_layout, new WorkoutFragment()).commit();

        //button_Logout = (Button) findViewById(R.id.button_main_logout);

        fbAuth = FirebaseAuth.getInstance();

        /*button_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fbAuth.signOut();
                startActivity(new Intent(Main_Activity.this, Login_Activity.class));
            }
        });*/
    }

    // Listener Navigation Bar
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.workout:
                            selectedFragment = new WorkoutFragment();
                            break;
                        case R.id.meal:
                            selectedFragment = new MealFragment();
                            break;
                        case R.id.profile:
                            selectedFragment = new ProfileFragment();
                            break;
                    }

                    // Being Transaction
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_layout, selectedFragment).commit();

                    return true;
                }
            };
}