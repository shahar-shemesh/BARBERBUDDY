package com.example.barberdemo2.Methods;

import static android.service.controls.ControlsProviderService.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.barberdemo2.Activitys.MainActivity;
import com.example.barberdemo2.Models.BarberShop;
import com.example.barberdemo2.Models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class getBundleUser {
    User currentUser = new User();
    BarberShop barberShop = new BarberShop();
    Bundle bundle = new Bundle();
    public getBundleUser(MainActivity.FirebaseCallback firebaseCallback, String userUid){
        if (userUid == null)firebaseCallback.onCallback(null);
        else{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    currentUser = dataSnapshot.child("users").child(userUid).getValue(User.class);

                    bundle.putString("fullName", currentUser.getFullName());
                    bundle.putString("email", currentUser.getEmail());
                    bundle.putString("phone", currentUser.getPhone());
                    bundle.putString("Uid", currentUser.getUid());
                    bundle.putBoolean("isBarber", currentUser.isBarber());
                    if (currentUser.isBarber()){
                        barberShop = dataSnapshot.child("barberShops").child(userUid).getValue(BarberShop.class);

                        bundle.putString("ownerName", barberShop.getOwnerName());
                        bundle.putString("buisnessName", barberShop.getBuisnessName());
                        bundle.putString("location", barberShop.getLocation());
                        bundle.putString("buisnessPhone", barberShop.getBuisnessPhone());
                        bundle.putString("price", Integer.toString(barberShop.getPrice()));
                    }
                    firebaseCallback.onCallback(bundle);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d(TAG, error.getMessage());
                }
            });
        }
    }

}

