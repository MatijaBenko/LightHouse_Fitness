package com.LightHouse.Fitness;

public class User_Profile_Activity {

    private String userName,userEmail,coach;
    private Integer userAge;
    private Double userWeight;

    public User_Profile_Activity() {}

    public User_Profile_Activity(String name, String email, Integer age, String trainer, Double weight){
        this.userName = name;
        this.userEmail = email;
        this.coach = trainer;
        this.userAge = age;
        this.userWeight = weight;
    }

    public void setUserName(String Name) {
        this.userName = Name;
    }

    public void setUserEmail(String Email) {
        this.userEmail = Email;
    }

    public void setCoach(String trainer) {
        this.coach = trainer;
    }

    public void setAge(Integer age) {
        this.userAge = age;
    }

    public void setUserWeight(Double weight) {
        this.userWeight = weight;
    }

    public String getUserName() {
        return this.userName;
    }

    public String setUserEmail() {
        return this.userEmail;
    }

    public String setCoach() {
        return this.coach;
    }

    public Integer setAge() {
        return this.userAge;
    }

    public Double getUserWeight() {
        return this.userWeight;
    }
}
