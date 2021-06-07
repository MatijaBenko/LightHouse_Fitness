package com.LightHouse.Fitness;

import android.util.ArrayMap;

import java.util.List;
import java.util.Vector;

public class User_Profile_Activity {

    private String userName, userEmail, userNotes, userID, userBundle;
    private Vector<String> userGoals;
    private Integer userAge;
    private Double userWeight;
    private ArrayMap<String, Vector<String>> userWorkout;
    private ArrayMap<String, Vector<String>> userMealPlan;

    public User_Profile_Activity() {
    }

    // Setters
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public void setUserWeight(Double userWeight) {
        this.userWeight = userWeight;
    }

    public void setUserNotes(String userNotes) {
        this.userNotes = userNotes;
    }

    public void setUserGoals(Vector<String> userGoals) {
        this.userGoals = userGoals;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUserBundle(String userBundle) { this.userBundle = userBundle;}

    public void setUserWorkout (ArrayMap<String, Vector<String>> userWorkout) {
        this.userWorkout = userWorkout;
    }

    public void setUserMealPlan(ArrayMap<String, Vector<String>> userMealPlan) {
        this.userMealPlan = userMealPlan;
    }

    // Getters
    public String getUserName() {
        return this.userName;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public Integer getUserAge() {
        return this.userAge;
    }

    public Double getUserWeight() {
        return this.userWeight;
    }

    public String getUserNotes() {
        return this.userNotes;
    }

    public List<String> getUserGoals() {
        return userGoals;
    }

    public String getUserID() {
        return this.userID;
    }

    public String getUserBundle() { return this.userBundle;}

    public ArrayMap<String, Vector<String>> getUserWorkout() {return this.userWorkout;}

    public ArrayMap<String, Vector<String>> getUserMealPlan() {return  this.userMealPlan;}
}
