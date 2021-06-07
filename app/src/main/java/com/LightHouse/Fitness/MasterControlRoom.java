package com.LightHouse.Fitness;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.LightHouse_Fitness.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Vector;

public class MasterControlRoom extends AppCompatActivity {

    private DatabaseReference dbReff;
    private ListView userList;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    private EditText tvStretching, tvCardio, tvWeightLifting, tvBreakfeast, tvLunch, tvDinner;
    private Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_control_room);

        tvStretching = (EditText) findViewById(R.id.editText_mastercontrol_stretching);
        tvCardio = (EditText) findViewById(R.id.editText_mastercontrol_cardio);
        tvWeightLifting = (EditText) findViewById(R.id.editText_mastercontrol_weightlifting);
        tvBreakfeast = (EditText) findViewById(R.id.editText_mastercontrol_breakfeast);
        tvLunch = (EditText) findViewById(R.id.editText_mastercontrol_lunch);
        tvDinner = (EditText) findViewById(R.id.editText_mastercontrol_dinner);
        updateButton = (Button) findViewById(R.id.button_masterControlUpdate);

        dbReff = FirebaseDatabase.getInstance().getReference().child("Users");
        userList = (ListView) findViewById(R.id.listView_UserList);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        userList.setAdapter(arrayAdapter);
        dbReff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                String userID = snapshot.child("userID").getValue().toString();
                String userName = snapshot.child("userName").getValue().toString();
                String userEmail = snapshot.child("userEmail").getValue().toString();
                String userLayout =  userName + "\n" + userEmail + "\n" + userID;

                arrayList.add(userLayout);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String  itemValue  = (String) userList.getItemAtPosition(position);

                String[] split = itemValue.split("\n");
                String userID = split[2];
                dbReff = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
                String breakfeast = tvBreakfeast.getText().toString().trim();
                String lunch = tvLunch.getText().toString().trim();
                String dinner = tvDinner.getText().toString().trim();

                String stretching = tvStretching.getText().toString().trim();
                String cardio = tvCardio.getText().toString().trim();
                String weightlifting = tvWeightLifting.getText().toString().trim();

                String[] splitBreakfeast = breakfeast.split("\n");
                String[] splitLunch = lunch.split("\n");
                String[] splitDinner = dinner.split("\n");
                String[] splitStretching = stretching.split("\n");
                String[] splitCardio = cardio.split("\n");
                String[] splitWeightLifting = weightlifting.split("\n");

                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dbReff.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Vector<String> reusableVector = new Vector<>();
                                if(snapshot != null) {
                                    DatabaseReference userReff = dbReff.child("userMealPlan").child("Breakfeast");
                                    for (String breakfeastItem: splitBreakfeast) {
                                        reusableVector.add(breakfeastItem);
                                    }
                                    userReff.setValue(reusableVector);

                                    userReff = dbReff.child("userMealPlan").child("Lunch");
                                    reusableVector.clear();
                                    for (String lunchItem: splitLunch) {
                                        reusableVector.add(lunchItem);
                                    }
                                    userReff.setValue(reusableVector);

                                    userReff = dbReff.child("userMealPlan").child("Dinner");
                                    reusableVector.clear();
                                    for (String dinnerItem: splitDinner) {
                                        reusableVector.add(dinnerItem);
                                    }
                                    userReff.setValue(reusableVector);

                                    userReff = dbReff.child("userWorkout").child("Yoga");
                                    reusableVector.clear();
                                    for (String stretchingItem: splitStretching) {
                                        reusableVector.add(stretchingItem);
                                    }
                                    userReff.setValue(reusableVector);

                                    userReff = dbReff.child("userWorkout").child("Cardio");
                                    reusableVector.clear();
                                    for (String cardioItem: splitCardio) {
                                        reusableVector.add(cardioItem);
                                    }
                                    userReff.setValue(reusableVector);

                                    userReff = dbReff.child("userWorkout").child("Weightlifting");
                                    reusableVector.clear();
                                    for (String weightliftingItem: splitWeightLifting) {
                                        reusableVector.add(weightliftingItem);
                                    }
                                    userReff.setValue(reusableVector);
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        tvBreakfeast.setText("");
                        tvLunch.setText("");
                        tvDinner.setText("");
                        tvStretching.setText("");
                        tvCardio.setText("");
                        tvWeightLifting.setText("");
                    }
                });

            }
        });
    }



}