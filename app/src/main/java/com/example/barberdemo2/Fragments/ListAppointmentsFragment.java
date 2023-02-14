package com.example.barberdemo2.Fragments;

import static android.service.controls.ControlsProviderService.TAG;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.barberdemo2.Adapters.ListAppointmentsAdapter;
import com.example.barberdemo2.Models.Slots;
import com.example.barberdemo2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ListAppointmentsFragment extends Fragment {

    DatabaseReference databaseReference;
    DateTimeFormatter dtf;
    TextView startTimeView;
    TextView endTimeView;
    TextView availabilityView;
    ListView listView;
    ListAppointmentsAdapter listAppointmentsAdapter;


    //    Slots slots = new Slots();
    ArrayList<String> optionsSlotsOfBarberForAdapter;

    String cup = new String();
    String selectedDate = new String();
    String pathKey = new String();
    Context context;

    public ListAppointmentsFragment(String date, String pathKey) {
        // Required empty public constructor
        this.selectedDate = date;
        this.pathKey = pathKey;
    }


    public ListAppointmentsFragment() {
        // Required empty public constructor
    }

//    public static ListAppointmentsFragment newInstance() {
//        ListAppointmentsFragment fragment = new ListAppointmentsFragment();
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_appointments, container, false);
        listView = view.findViewById(R.id.time_slot_list);
        optionsSlotsOfBarberForAdapter = new Slots().getSlots();
        context = this.getContext();

        getListToListView(new UpdateSlotList() { // ← ← = implementInterfaceClassFromOnCreateView
            @Override
            public void updateSlots(ArrayList<String> storeUpdateSlotsOfBarberForAdapter) {
                listAppointmentsAdapter = new ListAppointmentsAdapter(context, storeUpdateSlotsOfBarberForAdapter,selectedDate, getParentFragment());
                listView.setAdapter(listAppointmentsAdapter);
                listAppointmentsAdapter.notifyDataSetChanged();
            }

        }, pathKey); // ← ← pathKey for DB path

        return view;
    }

    private void getListToListView(UpdateSlotList implementInterfaceClassFromOnCreateView, String uid){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("barberShops")
                .child(uid).child("myAppointments").child(selectedDate);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    optionsSlotsOfBarberForAdapter.remove(ds.getKey()); // remove a busy appointment
                }
                implementInterfaceClassFromOnCreateView.updateSlots(optionsSlotsOfBarberForAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, error.getMessage());
            }
        });
    }

    public interface UpdateSlotList {
        public void updateSlots(ArrayList<String> storeUpdateSlotsOfBarberForAdapter);
    }
}