package com.example.barberdemo2.Fragments;

import static android.service.controls.ControlsProviderService.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.barberdemo2.Adapters.UserListAppointmentsAdapter;
import com.example.barberdemo2.Models.Appointment;
import com.example.barberdemo2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment implements UserListAppointmentsAdapter.CancelClickButton{
    ListView listView;
    ArrayList<Appointment> appointmentsLists;
    Boolean isBarber;
    Context context;
    String uid = new String();
    UserListAppointmentsAdapter userListAppointmentsAdapter;
    DatabaseReference databaseReference;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
// ============================================== when press back ================================================//
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavController navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView);
                navController.navigateUp();
                navController.navigate(R.id.dashboardFragment);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
// ============================================== ---------------- ================================================//
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        uid = getArguments().getString("getUid");
        appointmentsLists = new ArrayList<>();
        context = this.getContext();

// ============================================== barber account ================================================//
        if (getArguments().getBoolean("isBarber")){
            View view = inflater.inflate(R.layout.profile_barber_fragment, container, false);
            isBarber = true;
            listView = view.findViewById(R.id.listViewBarberAppointments);
            // ====================== set up profile layout items account ====================== //
            TextView yourName = view.findViewById(R.id.textViewFullNameProfileBarber);
            yourName.setText(getArguments().getString("fullName"));

            TextView yourEmail = view.findViewById(R.id.textViewEmailBarber);
            yourEmail.setText(getArguments().getString("email"));

            TextView yourPhone = view.findViewById(R.id.textViewBarberPhoneItem);
            yourPhone.setText(getArguments().getString("phone"));

            TextView shopName = view.findViewById(R.id.textViewShopNameProfileBarber);
            shopName.setText(getArguments().getString("buisnessName"));

            TextView location = view.findViewById(R.id.textViewLocationBarber);
            location.setText(getArguments().getString("location"));

            TextView price = view.findViewById(R.id.textViewBarberPrice);
            price.setText(getArguments().getString("price"));

            // ====================== ---------------------------------- ====================== //


            // ================== get appointments list barbar account ================== //
            getListToListView(new AppointmentsList() {
                @Override
                public void getAppointmentsInterface(ArrayList<Appointment> appointmentsLists) {
                    userListAppointmentsAdapter = new UserListAppointmentsAdapter(context, appointmentsLists, true);
                    listView.setAdapter(userListAppointmentsAdapter);
                }
            },uid, isBarber);
            // ================== ---------------------------------- ================== //
            return view;
        }
// ============================================== ---------------- ================================================//

// ============================================== regular user ================================================//
        else{
            View view = inflater.inflate(R.layout.profile_user_fragment, container, false);
            isBarber = false;
            listView = view.findViewById(R.id.listViewUserAppointments);
            // ================== set up profile layout items account ================== //
            TextView yourName = view.findViewById(R.id.textViewFullNameProfileUser);
            yourName.setText(getArguments().getString("fullName"));

            TextView yourEmail = view.findViewById(R.id.textViewEmail);
            yourEmail.setText(getArguments().getString("email"));

            TextView yourPhone = view.findViewById(R.id.textViewUserPhoneItem);
            yourPhone.setText(getArguments().getString("phone"));
            // ================== ---------------------------------- ================== //

            // ================== get appointments list regular user ================== //
            getListToListView(new AppointmentsList() {
                @Override
                public void getAppointmentsInterface(ArrayList<Appointment> appointmentsLists) {
                    userListAppointmentsAdapter = new UserListAppointmentsAdapter(context, appointmentsLists, false);
                    listView.setAdapter(userListAppointmentsAdapter);
                    userListAppointmentsAdapter.notifyDataSetChanged();
                }
            },uid, isBarber);
            // ================== ---------------------------------- ================== //
            return view;
        }
    }
// ============================================== ---------------- ================================================//



// ============================================ getListToListView function ==============================================//
    private void getListToListView(AppointmentsList storeAppointmentsOfAccountForAdapter, String uid, Boolean Barber){
        String pathAccount = Barber ? "barberShops" : "users" ;
        databaseReference = FirebaseDatabase.getInstance().getReference().child(pathAccount)
                .child(uid).child("myAppointments");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
// ============================================== Main FOR for dates ================================================//
                for (DataSnapshot datesRoot : snapshot.getChildren()){
                         // ============ Inner FOR for times (appointments) ============//
                    for (DataSnapshot fullAppointment : datesRoot.getChildren()){
                        appointmentsLists.add(fullAppointment.getValue(Appointment.class));
                    }
                        // ================== END Inner FOR ==================//
                }
// ============================================== END Main FOR ================================================//
                storeAppointmentsOfAccountForAdapter.getAppointmentsInterface(appointmentsLists);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, error.getMessage());
            }
        });
    }
// =============================== --------------END getListToListView func------------ ====================================//



// ========================================== Implement onClick Cancel Button ==============================================//
    @Override
    public void onClickCancelButton(Appointment appointment, View view, Boolean Barber) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Are you sure you want to cancel?").setTitle("Cancel");
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                DatabaseReference mDatabase;
                mDatabase = FirebaseDatabase.getInstance().getReference();

                String userPath = (String)appointment.getCustomerUID();
                String barberPath = (String)appointment.getBarberUID();
                String pathDateKey = "/" + (String)appointment.getDate() + "/";
                String pathHourKey = "/" + (String)appointment.getTime() + "/";

                Map<String, Object> childToUpdate = new HashMap<>();
                childToUpdate.put("/users/" + userPath + "/myAppointments/" + pathDateKey + pathHourKey , null);
                childToUpdate.put("/barberShops/" + barberPath + "/myAppointments/" + pathDateKey + pathHourKey , null);
                mDatabase.updateChildren(childToUpdate);


                Toast.makeText(view.getContext(), "The appointment has been canceled.", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {}});
        AlertDialog dialog = builder.create();dialog.show();


    }
// ============================================== ---------------- ================================================//


    public interface AppointmentsList {
        public void getAppointmentsInterface(ArrayList<Appointment> appointmentsLists);
    }

}