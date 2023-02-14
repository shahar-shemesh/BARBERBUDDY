package com.example.barberdemo2.Fragments;

import androidx.fragment.app.Fragment;

public class DayOffFragment extends Fragment {
//    ArrayList<String> fill = new ArrayList<>();
//    DatabaseReference databaseReference;
//    DateTimeFormatter dtf;
//    CalendarView calendarView;
//    TextView textViewDateChoosen;
//    ListView listView;
//    ArrayList<String> dataList;
//    String pathKey = new String();
//    String selectedDateCalendar = new String();
//    ArrayList<String> optionsSlotsOfBarber;
//    Context context;
//    Slots slots = new Slots();
//
//    public DayOffFragment() {
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_day_off, container, false);
//        TextView textViewShopTitle;
//        context = getContext();
//        optionsSlotsOfBarber = new Slots().getSlots();
//
//        SimpleDateFormat formatter = new SimpleDateFormat("dd\\MM\\yyyy");
//        SimpleDateFormat formatterShow = new SimpleDateFormat("dd/MM/yyyy");
//        Date today = new Date();
//
//        calendarView = view.findViewById(R.id.calendar_viewOff);
//        textViewDateChoosen = view.findViewById(R.id.textViewDateChoosenOff);
//        textViewDateChoosen.setText(formatterShow.format(today));
//
//        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
//                Calendar calendar = Calendar.getInstance();
//                calendar.set(year, month, dayOfMonth);
//                String sDate = formatter.format(calendar.getTime());
//                SimpleDateFormat show = new SimpleDateFormat("dd/MM/yyyy");
//                selectedDateCalendar = show.format(calendar.getTime());
//                textViewDateChoosen.setText(selectedDateCalendar);
//                onClickSelectedDayChange1(sDate);
//            }
//        });
//
//        return view;
//    }
//
//    public void onClickSelectedDayChange(String selectedDate) {
//        DatabaseReference mDatabase;
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//
//        String UID = FirebaseAuth.getInstance().getUid();
//
//        Appointment appointment = new Appointment();
//
//        Map<String, Object> dayOff = new HashMap<>();
//        for (int i = 0; i< optionsSlotsOfBarber.size(); i++){
//            dayOff.put(optionsSlotsOfBarber.get(i), optionsSlotsOfBarber.get(i));
//        }
//
//        Map<String, Object> childToUpdate = new HashMap<>();
//
////        childToUpdate.put("/barberShops/" + UID + "/myAppointments/" + selectedDate , dayOff);
////        mDatabase.updateChildren(childToUpdate);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setMessage("Day off booked successfully!")
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//
//                    }
//                });
//        AlertDialog dialog = builder.create();
//        dialog.show();
//
//    }
//
//    public interface onSelectedDayChange1 {
//        public void onClickSelectedDayChange1(String date);
//    }
}
