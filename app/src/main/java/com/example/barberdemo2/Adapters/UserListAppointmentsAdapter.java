package com.example.barberdemo2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.barberdemo2.Fragments.ProfileFragment;
import com.example.barberdemo2.Models.Appointment;
import com.example.barberdemo2.R;

import java.util.ArrayList;

public class UserListAppointmentsAdapter extends BaseAdapter{
    private Context context;
    ArrayList<Appointment> appointments;
    Boolean isBarber;

    public UserListAppointmentsAdapter(Context context, ArrayList<Appointment> appointments, boolean isBarber) {
        this.context = context;
        this.appointments = appointments;
        this.isBarber = isBarber;
    }

    @Override
    public int getCount() {
        return appointments.size();
    }

    @Override
    public Object getItem(int position) {
        return appointments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_appointment, parent, false);
        }

// ============================================== the current item ================================================//
        Appointment appointment = (Appointment) getItem(position);
// ============================================== ---------------- ================================================//

// ============================================== get layout items ================================================//
        if(isBarber){

        }
        TextView textViewNameUserOrBarber = view.findViewById(R.id.textViewNameUserOrBarber);
        TextView textViewContactPhone = view.findViewById(R.id.textViewContactPhone);
        TextView textViewDate = view.findViewById(R.id.textViewDate);
        TextView textViewTime = view.findViewById(R.id.textViewTime);
        TextView textViewBarberShopLocation = view.findViewById(R.id.textViewBarberShopLocation);
        Button cancelButton = view.findViewById(R.id.cancel_button);
// ============================================== ---------------- ================================================//


// ============================================== set up layout items ================================================//
        if(isBarber){
            textViewNameUserOrBarber.setText(appointment.getFullNameCustomer());
            textViewContactPhone.setText(appointment.getCustomerPhone());
            textViewBarberShopLocation.setVisibility(View.GONE);
        }
        else{
            textViewNameUserOrBarber.setText(appointment.getBarberName());
            textViewContactPhone.setText(appointment.getBarberPhone());
            textViewBarberShopLocation.setText(appointment.getBarberLocation());
        }
        textViewDate.setText(appointment.getDate());
        textViewTime.setText(appointment.getTime());
// ============================================== ---------------- ================================================//

// ============================================== set up cancel button ================================================//
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ProfileFragment().onClickCancelButton((Appointment) getItem(position), view, isBarber);

            }
        });

// ============================================== ---------------- ================================================//

        return view;
    }

    public interface CancelClickButton {
        public void onClickCancelButton(Appointment appointment, View view, Boolean Barber);
    }
}

