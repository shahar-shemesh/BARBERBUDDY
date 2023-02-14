package com.example.barberdemo2.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barberdemo2.Adapters.CustomAdapter;
import com.example.barberdemo2.Models.BarberShop;
import com.example.barberdemo2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DashboardFragment extends Fragment implements CustomAdapter.ItemClickListener {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    CustomAdapter customAdapter;
    ArrayList<BarberShop> dataList;
    ArrayList<String> dataPathKeyList;

    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
// ============================================== when press back ================================================//
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure you want to exit?").setTitle("Exit");
                builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {getActivity().finish();}
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {}});
                AlertDialog dialog = builder.create();dialog.show();}
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
// ============================================== ---------------- ================================================//
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        recyclerView = view.findViewById(R.id.my_recycle_view);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        databaseReference = FirebaseDatabase.getInstance().getReference("barberShops");
//        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        dataList = new ArrayList<>();
        dataPathKeyList = new ArrayList<>();
        customAdapter = new CustomAdapter(dataList, dataPathKeyList, this.getContext(), this::onItemClick);
        recyclerView.setAdapter(customAdapter);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    BarberShop currentBarberShop = dataSnapshot.getValue(BarberShop.class);
                    String pathKey = dataSnapshot.getKey();
                    dataList.add(currentBarberShop);
                    dataPathKeyList.add(pathKey);
                }
                customAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return view;
    }


    @Override
    public void onItemClick(BarberShop currentBarberShop,String dataPathKey, View view) {
        Bundle bundle ;
        bundle = currentBarberShop.toBundle();
//        bundle.putString("shopName", currentBarberShop.getBuisnessName());
//        bundle.putString("location", currentBarberShop.getLocation());
//        bundle.putString("phone", currentBarberShop.getBuisnessPhone());
//        bundle.putString("price",  Integer.toString(currentBarberShop.getPrice()));
        bundle.putString("pathKey", dataPathKey);

        Navigation.findNavController(view).navigate(R.id.action_dashboardFragment_to_barberShopFragment, bundle);


    }
}