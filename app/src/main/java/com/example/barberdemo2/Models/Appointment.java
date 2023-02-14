package com.example.barberdemo2.Models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Appointment {

    // only for DB
    String customerUID;
    String barberUID;

// for both view
     String date;
     String time;

//for barber:
     String fullNameCustomer;
     String customerPhone;

 //for customer:
     String barberName;
     String barberLocation;
     String barberPhone;

    public Appointment(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Appointment(String customerUID, String barberUID, String date, String time, String fullNameCustomer,
                       String customerPhone, String barberShopName, String barberShopLocation, String barberPhone) {
        this.customerUID = customerUID;
        this.barberUID = barberUID;
        this.date = date;
        this.time = time;
        this.fullNameCustomer = fullNameCustomer;
        this.customerPhone = customerPhone;
        this.barberName = barberShopName;
        this.barberLocation = barberShopLocation;
        this.barberPhone = barberPhone;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("customerUID", customerUID);
        result.put("barberUID", barberUID);
        result.put("date", date);
        result.put("time", time);
        result.put("fullNameCustomer", fullNameCustomer);
        result.put("customerPhone", customerPhone);
        result.put("barberName", barberName);
        result.put("barberLocation", barberLocation);
        result.put("barberPhone", barberPhone);

        return result;
    }

    public Appointment mapToAppointment(HashMap map){
        Appointment newAppointment = new Appointment(
               (String)map.get("customerUID"),
               (String)map.get("barberUID"),
               (String)map.get("date"),
               (String)map.get("time"),
               (String)map.get("fullNameCustomer"),
               (String)map.get("customerPhone"),
               (String)map.get("barberName"),
               (String)map.get("barberLocation"),
               (String)map.get("barberPhone"));

        return newAppointment;
    }

    public String getCustomerUID() {
        return customerUID;
    }

    public void setCustomerUID(String customerUID) {
        this.customerUID = customerUID;
    }

    public String getBarberUID() {
        return barberUID;
    }

    public void setBarberUID(String barberUID) {
        this.barberUID = barberUID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFullNameCustomer() {
        return fullNameCustomer;
    }

    public void setFullNameCustomer(String fullNameCustomer) {
        this.fullNameCustomer = fullNameCustomer;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getBarberName() {
        return barberName;
    }

    public void setBarberName(String barberName) {
        this.barberName = barberName;
    }

    public String getBarberLocation() {
        return barberLocation;
    }

    public void setBarberLocation(String barberLocation) {
        this.barberLocation = barberLocation;
    }

    public String getBarberPhone() {
        return barberPhone;
    }

    public void setBarberPhone(String barberPhone) {
        this.barberPhone = barberPhone;
    }
}

