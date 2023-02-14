package com.example.barberdemo2.Models;

import android.os.Bundle;

public class BarberShop {
    private String ownerName;

    public String getBarberUid() {
        return barberUid;
    }

    private String barberUid;
    private String buisnessName;
    private String location;
    //private String picture;
    private String buisnessPhone;
    private int price;

    public BarberShop(){}
    public BarberShop(BarberShop barberShop){
        this.ownerName = barberShop.getOwnerName();
        this.buisnessName = barberShop.getBuisnessName();
        this.location = barberShop.getLocation();
        this.buisnessPhone = barberShop.getBuisnessPhone();
        this.price = barberShop.getPrice();
    }

    public BarberShop(String ownerName, String buisnessName, String location, String buisnessPhone, int price, String Uid) {
        this.ownerName = ownerName;
        this.buisnessName = buisnessName;
        this.location = location;
        this.buisnessPhone = buisnessPhone;
        this.price = price;
        this.barberUid = Uid;
    }

    public Bundle toBundle(){
        Bundle bundle = new Bundle();
        bundle.putString("ownerName", this.ownerName);
        bundle.putString("shopName", this.buisnessName);
        bundle.putString("location", this.location);
        bundle.putString("phone", this.buisnessPhone);
        bundle.putString("barberUid", this.barberUid);
        bundle.putString("price",  Integer.toString(this.price));

        return bundle;
    }


    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getBuisnessName() {
        return buisnessName;
    }

    public void setBuisnessName(String buisnessName) {
        this.buisnessName = buisnessName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBuisnessPhone() {
        return buisnessPhone;
    }

    public void setBuisnessPhone(String buisnessPhone) {
        this.buisnessPhone = buisnessPhone;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
