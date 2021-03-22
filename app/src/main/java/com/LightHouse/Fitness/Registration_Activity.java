package com.LightHouse.Fitness;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

import com.example.jb_fitnessapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration_Activity extends AppCompatActivity {

    private TextView editLogin;
    private EditText editName, editEmail, editPassword, editRetypePassword;
    private Button signUp_Button;

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
        signUp_Button = (Button) findViewById(R.id.button_Register_SignIn);
        editLogin = (TextView) findViewById(R.id.edit_register_login_button);


        signUp_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = editName.getText().toString().trim();
                String userEmail = editEmail.getText().toString().trim();
                String userPassword = editPassword.getText().toString().trim();
                String userRetypePassword = editRetypePassword.getText().toString().trim();

                if(validation(userName, userEmail, userPassword, userRetypePassword)) {
                    firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
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

        editLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration_Activity.this, Login_Activity.class));
            }
        });
    }
    private boolean validation(String userName, String userEmail, String userPassword, String userRetypePassword) {
        boolean result = false;

        if(userName.equals("") || userEmail.equals("") || userPassword.equals("") || userRetypePassword.equals("")) {
            Toast.makeText(getApplicationContext(), "All Fields must be Filled", Toast.LENGTH_LONG).show();
        } else if (!(userPassword.equals(userRetypePassword))) {
            Toast.makeText(getApplicationContext(),"Password does not match Retype Password, try again", Toast.LENGTH_LONG).show();
        } else if(userPassword.length() < 6) {
            Toast.makeText(getApplicationContext(),"Password much be greater than 5 characters", Toast.LENGTH_LONG).show();
        } else {
            result = true;
        }
        return result;
    }

    private void sendEmailVerification(FirebaseAuth firebaseAuth){
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user!=null) {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())  {
                        Toast.makeText(Registration_Activity.this, "Successfully Registered, Email Verification sent", Toast.LENGTH_SHORT).show();
                        FirebaseDatabase fbDataBase = FirebaseDatabase.getInstance();
                        DatabaseReference dataBaseRef = fbDataBase.getReference(firebaseAuth.getUid());
                        User_Profile_Activity tmpUser = new User_Profile_Activity();
                        dataBaseRef.setValue(tmpUser);
                        firebaseAuth.signOut();
                        startActivity(new Intent(Registration_Activity.this, Login_Activity.class));
                    } else {
                        Toast.makeText(Registration_Activity.this, "Email Verification Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}