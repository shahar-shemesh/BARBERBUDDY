package com.example.barberdemo2.Activitys;


import static android.service.controls.ControlsProviderService.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.barberdemo2.Methods.getBundleUser;
import com.example.barberdemo2.Models.BarberShop;
import com.example.barberdemo2.Models.User;
import com.example.barberdemo2.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    User authUser = new User();
    Bundle bundleAuthUser = new Bundle();
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    private FirebaseAuth mAuth;
    @Override
    public void onStart() {
        super.onStart();
        //====================Check if user is signed in====================//
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            System.out.println(currentUser);
            Navigation.findNavController(this, R.id.fragmentContainerView).navigate(R.id.action_mainFragment_to_dashboardFragment);
        }
        //====================END Check if user is signed in====================//
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        //====================TOOLBAR====================//

        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout,
                toolbar,R.string.open_drawer, R.string.close_drawer);

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mySchedule:
                        NavController navController = Navigation.findNavController(MainActivity.this, R.id.fragmentContainerView);
                        navController.navigateUp();
                        navController.navigate(R.id.dashboardFragment);
                        break;

                    case R.id.profile:
                        new getBundleUser(new FirebaseCallback() {
                            @Override
                            public void onCallback(Bundle bundle) {
                                if (bundle == null){
                                    Toast.makeText(MainActivity.this, "You are not logged in", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                NavController navController = Navigation.findNavController(MainActivity.this, R.id.fragmentContainerView);
                                navController.navigateUp();
                                bundle.putString("getUid", mAuth.getUid());
                                if (bundle.getBoolean("isBarber")) {
                                    navController.navigate(R.id.profileBarberFragment, bundle);
                                }
                                else
                                    navController.navigate(R.id.profileUserFragment, bundle);
                            }
                        }, mAuth.getUid());
                        break;

                    case R.id.logout:
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        if(currentUser != null){
                            FirebaseAuth.getInstance().signOut();
                            mAuth = FirebaseAuth.getInstance();
                            MainActivity.this.finish();
                            MainActivity.this.startActivity(MainActivity.this.getIntent());
                            Toast.makeText(MainActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "You are not logged in", Toast.LENGTH_SHORT).show();
                        }
                        break;

                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    //====================END_TOOLBAR====================//

    public void LoginFunc(View view, EditText emailText, EditText passwordText) {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail: success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        helloUser(user.getUid());
                        Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_dashboardFragment);
                    }
                    else
                    {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void RegisterFunc(View view, ArrayList<EditText> parameters) {

        String fullName = parameters.get(0).getText().toString();
        String email = parameters.get(1).getText().toString();
        String password = parameters.get(2).getText().toString();
        String phone = parameters.get(3).getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("users").child(mAuth.getUid());
                        if (parameters.size() == 4) {
                            User newUser = new User(fullName, email, phone, false, mAuth.getUid());
                            myRef.setValue(newUser);
                            Toast.makeText(MainActivity.this, "Register OK", Toast.LENGTH_LONG).show();
                            Navigation.findNavController(view).navigate(R.id.action_userRegisterFragment_to_dashboardFragment);
                        }
                        else { // User&Barber
                            User newUser = new User(fullName, email, phone, true, mAuth.getUid());
                            myRef.setValue(newUser);
                            String shopName = parameters.get(4).getText().toString();
                            String location = parameters.get(5).getText().toString();
                            String price = parameters.get(6).getText().toString();
                            BarberShop newBarbarShop = new BarberShop(fullName, shopName, location, phone, Integer.parseInt(price), mAuth.getUid());
                            myRef = database.getReference("barberShops").child(mAuth.getUid());
                            myRef.setValue(newBarbarShop);
                            Toast.makeText(MainActivity.this, "Register OK", Toast.LENGTH_LONG).show();
                            Navigation.findNavController(view).navigate(R.id.action_barberRegisterFragment_to_dashboardFragment);
                        }

                    }
                    else{
                        Toast.makeText(MainActivity.this, "Register FAIL ", Toast.LENGTH_LONG).show();
                    }
                });
    }


//    public void writeData(EditText fullNameText, EditText emailText, EditText phoneText){
//
//        String fullName = fullNameText.getText().toString();
//        String email = emailText.getText().toString();
//        String phone = phoneText.getText().toString();
//
//        User newUser = new User(fullName, email, phone);
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("users").child(mAuth.getUid());
//        myRef.setValue(newUser);
//    }

    public void helloUser(String Uid){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(Uid);
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                User user = dataSnapshot.getValue(User.class);
                Log.d(TAG, "Value is: " + user.getFullName());
                Toast.makeText(MainActivity.this, "Welcome back, Dear " + user.getFullName() + "!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, error.getMessage());
            }

        });
    }

    public interface FirebaseCallback{
        void onCallback(Bundle bundle);
    }

//    public void getUserDetails() {
//        FirebaseUser userUid = mAuth.getCurrentUser();
//        if (userUid == null){
//            return;
//        }
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("users").child(userUid.getUid());
//        // Read from the database
//
//        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                User currUser = dataSnapshot.getValue(User.class);
//                assert currUser != null;
//                bundle.putString("getFullName", currUser.getFullName());
//                bundle.putString("getEmail", currUser.getEmail());
//                bundle.putString("getPhone", currUser.getPhone());
//                bundle.putBoolean("isBarber", currUser.isBarber());
//                System.out.println(bundle.getString("getEmail"));
//                myCallback.onCallback(bundle);
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//    }

//    private void getUserFromDB(FirebaseCallback firebaseCallback){
//        FirebaseUser userUid = mAuth.getCurrentUser();
//        if (userUid == null){
//            return;
//        }
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("users").child(userUid.getUid());
////        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                authUser = dataSnapshot.getValue(User.class);
//                firebaseCallback.onCallback(authUser);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.d(TAG, error.getMessage());
//            }
//        });
//    }
//
//    private interface FirebaseCallback{
//        void onCallback(User authUser);
//    }

}

//
//
//    private void getUserFromDB(FirebaseCallback firebaseCallback){
//        FirebaseUser userUid = mAuth.getCurrentUser();
//        if (userUid == null){
//            return;
//        }
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("users").child(userUid.getUid());
////        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                authUser = dataSnapshot.getValue(User.class);
//                firebaseCallback.onCallback(authUser);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.d(TAG, error.getMessage());
//            }
//        });
//    }