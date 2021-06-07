package com.LightHouse.Fitness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.LightHouse_Fitness.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main_Activity extends AppCompatActivity {

    private FirebaseAuth fbAuth;
    private FirebaseUser fbUser;
    private DatabaseReference dbReff;

    private int userDataAge;

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

        main();
    }

    private void main() {
        fbAuth = FirebaseAuth.getInstance();
        fbUser = FirebaseAuth.getInstance().getCurrentUser();
        dbReff = FirebaseDatabase.getInstance().getReference().child("Users").child(fbUser.getUid());
        dbReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot != null){
                    userDataAge =  Integer.parseInt(snapshot.child("userAge").getValue().toString());
                    if(userDataAge == -1) {
                        openPopUpWindow();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void openPopUpWindow(){
        Intent popupwindow = new Intent(Main_Activity.this, PopUpWindow.class);
        startActivity(popupwindow);
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