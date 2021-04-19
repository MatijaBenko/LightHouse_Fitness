package com.LightHouse.Fitness;

import androidx.appcompat.app.AppCompatActivity;

import com.example.LightHouse_fitness.R;

public class User_Profile_Activity extends AppCompatActivity {

    private String userName,userEmail, assignedTrainer;
    private Integer userAge;
    private Double userWeight;

    public User_Profile_Activity() {}

    public User_Profile_Activity(String userName, String userEmail, Integer userAge, String userTrainer, Double userWeight){
        this.userName = userName;
        this.userEmail = userEmail;
        this.assignedTrainer = userTrainer;
        this.userAge = userAge;
        this.userWeight = userWeight;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setAssignedTrainer(String userTrainer) {
        this.assignedTrainer = userTrainer;
    }

    public void setAge(Integer userAge) {
        this.userAge = userAge;
    }

    public void setUserWeight(Double userWeight) {
        this.userWeight = userWeight;
    }

    public String getUserName() {
        return this.userName;
    }

    public String setUserEmail() {
        return this.userEmail;
    }

    public String setCoach() {
        return this.assignedTrainer;
    }

    public Integer setAge() {
        return this.userAge;
    }

    public Double getUserWeight() {
        return this.userWeight;
    }
}
