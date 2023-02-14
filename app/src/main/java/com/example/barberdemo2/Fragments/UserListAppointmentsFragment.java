package com.example.barberdemo2.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.barberdemo2.R;

public class UserListAppointmentsFragment extends Fragment {


    public UserListAppointmentsFragment() {
        // Required empty public constructor
    }


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_list_appointments, container, false);
    }
}



//
//public class ListAppointmentsFragment extends Fragment implements ListAppointmentsAdapter.BookClickButton {
//
//    DatabaseReference databaseReference;
//    DateTimeFormatter dtf;
//    TextView startTimeView;
//    TextView endTimeView;
//    TextView availabilityView;
//    ListView listView;
//    ListAppointmentsAdapter listAppointmentsAdapter;
//
//
//    //    Slots slots = new Slots();
//    ArrayList<String> slotsList;
//
//    String cup = new String();
//    String selectedDate = new String();
//    String pathKey = new String();
//    Context context;
//
//    public ListAppointmentsFragment(String date, String pathKey) {
//        // Required empty public constructor
//        this.selectedDate = date;
//        this.pathKey = pathKey;
//    }
//
//
//    public ListAppointmentsFragment() {
//        // Required empty public constructor
//    }
//
////    public static ListAppointmentsFragment newInstance() {
////        ListAppointmentsFragment fragment = new ListAppointmentsFragment();
////        return fragment;
////    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_list_appointments, container, false);
//        listView = view.findViewById(R.id.time_slot_list);
//        slotsList = new Slots().getSlots();
//        context = this.getContext();
//
//        updateList(new UpdateSlotList() {
//            @Override
//            public void updateSlots(ArrayList<String> stringArrayList) {
//                listAppointmentsAdapter = new ListAppointmentsAdapter(context, stringArrayList);
//                listView.setAdapter(listAppointmentsAdapter);
//            }
//        }, pathKey);
//
//        //listAppointmentsAdapter.notifyDataSetChanged();
//        return view;
//    }
//
//    private void updateList(UpdateSlotList updateSlotList, String pathKey){
//        databaseReference = FirebaseDatabase.getInstance().getReference().child("barberShops")
//                .child(pathKey).child("myWorkAppointments").child(selectedDate);
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot ds : snapshot.getChildren()){
//                    slotsList.remove(ds.getKey().replace("\"", ""));
//                }
//                updateSlotList.updateSlots(slotsList);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.d(TAG, error.getMessage());
//            }
//        });
//    }
//    public interface UpdateSlotList {
//        public void updateSlots(ArrayList<String> stringArrayList);
//    }
