package com.example.barberdemo2.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.barberdemo2.Fragments.bookingSlotFragment;
import com.example.barberdemo2.R;

import java.util.ArrayList;

public class ListAppointmentsAdapter extends BaseAdapter {

    View viewAdapter;
    private Context context;
    ArrayList<String> slots;
    String selectedDate;
    bookingSlotFragment bookingSlotFragment;


    public ListAppointmentsAdapter(Context context, ArrayList<String> slots, String selectedDate, Fragment frag) {
        this.context = context;
        this.slots = slots;
        this.selectedDate = selectedDate;
        this.bookingSlotFragment = (com.example.barberdemo2.Fragments.bookingSlotFragment) frag;
    }
    public ListAppointmentsAdapter(Context context, ArrayList<String> slotss) {
        this.context = context;
        this.slots = slotss;
    }

    @Override
    public int getCount() {
        return slots.size();
    }

    @Override
    public Object getItem(int position) {
        return slots.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_booking_slot, parent, false);
            viewAdapter = view;
        }

        String currSlot = (String) getItem(position);
        int posTemp = position;
        TextView startTimeText = view.findViewById(R.id.textViewTime);
        startTimeText.setText(currSlot);

        Button bookButton = view.findViewById(R.id.book_button);
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new bookingSlotFragment().onClickBookButton((String) getItem(posTemp), selectedDate, bookingSlotFragment);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Appointment booked successfully!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Handle the OK button being clicked

                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
                Navigation.findNavController(view).navigate(R.id.action_bookingSlotFragment_to_dashboardFragment);
            }
        });

        return view;
    }

    public interface BookClickButton {
        public void onClickBookButton(String time, String date, bookingSlotFragment frag);
    }
}