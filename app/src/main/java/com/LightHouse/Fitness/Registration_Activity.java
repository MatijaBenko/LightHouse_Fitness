package com.LightHouse.Fitness;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.LightHouse_Fitness.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Vector;


public class Registration_Activity extends AppCompatActivity {

    private EditText editName, editEmail, editPassword, editRetypePassword;
    private Button button_SignUp;
    private String userName, userEmail, userPassword, userRetypePassword;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        //Initialize Values using specified ID calls
        editName = (EditText) findViewById(R.id.Text_Register_User_Name);
        editEmail = (EditText) findViewById(R.id.Text_Register_Email);
        editPassword = (EditText) findViewById(R.id.Text_Register_Password);
        editRetypePassword = (EditText) findViewById(R.id.Text_Register_Retyped_Password);
        button_SignUp = (Button) findViewById(R.id.button_Register_SignIn);


        button_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = editName.getText().toString().trim();
                userEmail = editEmail.getText().toString().trim();
                userPassword = editPassword.getText().toString().trim();
                userRetypePassword = editRetypePassword.getText().toString().trim();
                firebaseAuth = FirebaseAuth.getInstance();

                if (validation(userName, userEmail, userPassword, userRetypePassword)) {
                    firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sendEmailVerification(firebaseAuth);
                                startActivity(new Intent(Registration_Activity.this, Login_Activity.class));
                            } else {
                                Toast.makeText(Registration_Activity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                editName.setText("");
                editEmail.setText("");
                editPassword.setText("");
                editRetypePassword.setText("");
                editName.requestFocus();
            }
        });
    }

    private boolean validation(String userName, String userEmail, String userPassword, String userRetypePassword) {
        boolean result = false;

        if (userName.equals("") || userEmail.equals("") || userPassword.equals("") || userRetypePassword.equals("")) {
            Toast.makeText(getApplicationContext(), "All Fields must be Filled", Toast.LENGTH_LONG).show();
        } else if (!(userPassword.equals(userRetypePassword))) {
            Toast.makeText(getApplicationContext(), "Password does not match Retype Password, try again", Toast.LENGTH_LONG).show();
        } else if (userPassword.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password much be greater than 5 characters", Toast.LENGTH_LONG).show();
        } else {
            result = true;
        }
        return result;
    }

    private void sendEmailVerification(FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Vector<String> blankVector = new Vector<String>();
                        ArrayMap<String, Vector<String>> blankUserWorkout = new ArrayMap<>();
                        ArrayMap<String, Vector<String>> blankUserMealPlans = new ArrayMap<>();

                        blankVector.add("Test");
                        blankUserWorkout.put("Yoga", blankVector);
                        blankUserWorkout.put("Cardio", blankVector);
                        blankUserWorkout.put("Weightlifting", blankVector);
                        blankUserMealPlans.put("Breakfeast", blankVector);
                        blankUserMealPlans.put("Lunch", blankVector);
                        blankUserMealPlans.put("Dinner", blankVector);
                        
                        Toast.makeText(Registration_Activity.this, "Successfully Registered, Email Verification sent", Toast.LENGTH_SHORT).show();
                        FirebaseDatabase fbDataBase = FirebaseDatabase.getInstance();
                        DatabaseReference dataBaseRef = fbDataBase.getReference("Users");
                        User_Profile_Activity user = new User_Profile_Activity();
                        user.setUserEmail(userEmail);
                        user.setUserName(userName);
                        user.setUserNotes("");
                        user.setUserWeight(-1.0);
                        user.setUserAge(-1);
                        user.setUserID(firebaseAuth.getUid());
                        user.setUserBundle("FREE");
                        user.setUserGoals(blankVector);
                        user.setUserWorkout(blankUserWorkout);
                        user.setUserMealPlan(blankUserMealPlans);
                        dataBaseRef.child(user.getUserID()).setValue(user);
                        firebaseAuth.signOut();
                        finish();
                    } else {
                        Toast.makeText(Registration_Activity.this, "Email Verification Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("UserName", editName.getText().toString());
        outState.putString("UserEmail", editEmail.getText().toString());
        outState.putString("UserPassword", editPassword.getText().toString());
        outState.putString("UserReTypePassword", editRetypePassword.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        editName.setText(savedInstanceState.getString("UserName"));
        editEmail.setText(savedInstanceState.getString("UserEmail"));
        editPassword.setText(savedInstanceState.getString("UserPassword"));
        editRetypePassword.setText(savedInstanceState.getString("UserReTypePassword"));
    }
}