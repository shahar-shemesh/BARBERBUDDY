package com.example.barberdemo2.Models;


import java.util.HashMap;
import java.util.Map;

public class User {
    private String fullName;
    private String email;
    private String phone;

    public String getUid() {
        return Uid;
    }

    private String Uid;
    private boolean isBarber;

    public User(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String fullName, String email, String phone, boolean isBarber, String Uid) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.isBarber = isBarber;
        this.Uid = Uid;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("fullName", fullName);
        result.put("email", email);
        result.put("phone", phone);
        result.put("isBarber", isBarber);

        return result;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isBarber() {
        return isBarber;
    }

    public void setBarber(boolean barber) {
        isBarber = barber;
    }
}
