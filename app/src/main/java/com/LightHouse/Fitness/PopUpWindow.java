package com.LightHouse.Fitness;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.NumberPicker;

import com.LightHouse_Fitness.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;

public class PopUpWindow extends AppCompatActivity {

    private NumberPicker agePicker;
    private Button button_AgePicker;
    private int userAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_window);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.7), (int)(height*.5));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        // Show 2 digits in NumberPickers
        NumberPicker.Formatter numFormat = new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return new DecimalFormat("00").format(i);
            }
        };

        // Set min and max values for all NumberPickers
        agePicker = findViewById(R.id.agePicker);
        agePicker.setMinValue(18);
        agePicker.setMaxValue(100);
        agePicker.setFormatter(numFormat);

        button_AgePicker = (Button) findViewById(R.id.button_agePicker);
        button_AgePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userAge = agePicker.getValue();
                FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference dbReff = FirebaseDatabase.getInstance().getReference().child("Users").child(fbUser.getUid()).child("userAge");
                dbReff.setValue(userAge);
                finish();
            }
        });
    }
    public int getUserAge() {
        return this.userAge;
    }
}