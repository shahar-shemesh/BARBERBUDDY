package com.example.barberdemo2.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.barberdemo2.Activitys.MainActivity;
import com.example.barberdemo2.Adapters.ListAppointmentsAdapter;
import com.example.barberdemo2.Methods.getBundleUser;
import com.example.barberdemo2.Models.Slots;
import com.example.barberdemo2.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class bookingSlotFragment extends Fragment implements ListAppointmentsAdapter.BookClickButton {
    ArrayList<String> fill = new ArrayList<>();
    DatabaseReference databaseReference;
    DateTimeFormatter dtf;
    CalendarView calendarView;
    TextView textViewDateChoosen;
    ListView listView;
    ArrayList<String> dataList;
    String pathKey = new String();
    String selectedDateCalendar = new String();

    Slots slots = new Slots();

    public bookingSlotFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.booking_slot_fragment, container, false);
        TextView textViewShopTitle;

        pathKey = getArguments().getString("pathKey");

        SimpleDateFormat formatter = new SimpleDateFormat("dd\\MM\\yyyy");
        SimpleDateFormat formatterShow = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();

        calendarView = view.findViewById(R.id.calendar_view);
        textViewDateChoosen = view.findViewById(R.id.textViewDateChoosen);
        textViewDateChoosen.setText(formatterShow.format(today));
        textViewShopTitle = view.findViewById(R.id.textViewShopTitle);
        textViewShopTitle.setText(getArguments().getString("shopName"));

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                String sDate = formatter.format(calendar.getTime());
                SimpleDateFormat show = new SimpleDateFormat("dd/MM/yyyy");
                selectedDateCalendar = show.format(calendar.getTime());
                textViewDateChoosen.setText(selectedDateCalendar);
                onClickSelectedDayChange(sDate);
            }
        });

        ListAppointmentsFragment listAppointmentsFragment = new ListAppointmentsFragment(formatter.format(today), pathKey);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerListView, listAppointmentsFragment);
        transaction.commit();

        return view;
    }

    public void onClickSelectedDayChange(String selectedDate) {
        ListAppointmentsFragment listAppointmentsFragment = new ListAppointmentsFragment(selectedDate, pathKey);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerListView, listAppointmentsFragment);
        transaction.commit();
    }

    public void onClickBookButton(String selectedTime, String date, bookingSlotFragment frag) {

        String barberUID = frag.getArguments().getString("pathKey");
        String customerUID = frag.getArguments().getString("userUid");

/// ========================== user details ==========================
        new getBundleUser(new MainActivity.FirebaseCallback() {
            Map<String, String> fillFieldsAppointments = new HashMap<String, String>();
            @Override
            public void onCallback(Bundle bundle) {
                fillFieldsAppointments.put("date", date);
                fillFieldsAppointments.put("time", selectedTime);
                fillFieldsAppointments.put("customerUID", customerUID);
                fillFieldsAppointments.put("barberUID", barberUID);
                fillFieldsAppointments.put("fullNameCustomer", bundle.getString("fullName"));
                fillFieldsAppointments.put("customerPhone", bundle.getString("phone"));
       /// ========================== barber details ==========================
                new getBundleUser(new MainActivity.FirebaseCallback() {
                    @Override
                    public void onCallback(Bundle bundleBarber) {
                        fillFieldsAppointments.put("barberName", bundleBarber.getString("buisnessName"));
                        fillFieldsAppointments.put("barberPhone", bundleBarber.getString("buisnessPhone"));
                        fillFieldsAppointments.put("barberLocation", bundleBarber.getString("location"));
                        writeAppointmentInDB(fillFieldsAppointments);
                    }
                },barberUID);
            }
        },customerUID);

    }

    public void writeAppointmentInDB(Map appointmentMap){

        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        String userPath = (String)appointmentMap.get("customerUID");
        String barberPath = (String)appointmentMap.get("barberUID");
        String pathDateKey = "/" + (String)appointmentMap.get("date") + "/";
        String pathHourKey = "/" + (String)appointmentMap.get("time") + "/";

        Map<String, Object> childToUpdate = new HashMap<>();
        childToUpdate.put("/users/" + userPath + "/myAppointments/" + pathDateKey + pathHourKey , appointmentMap);
        childToUpdate.put("/barberShops/" + barberPath + "/myAppointments/" + pathDateKey + pathHourKey , appointmentMap);
        mDatabase.updateChildren(childToUpdate);

    }


    public interface onSelectedDayChange {
        public void onClickSelectedDayChange(String date);
    }
}