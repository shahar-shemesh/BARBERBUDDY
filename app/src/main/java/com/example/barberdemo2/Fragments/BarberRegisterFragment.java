package com.example.barberdemo2.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.barberdemo2.Activitys.MainActivity;
import com.example.barberdemo2.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BarberRegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BarberRegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BarberRegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BarberRegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BarberRegisterFragment newInstance(String param1, String param2) {
        BarberRegisterFragment fragment = new BarberRegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_barber_register, container, false);


        EditText fullNameText = view.findViewById(R.id.editTextBarberFullName);
        EditText emailText = view.findViewById(R.id.editTextBarberEmail);
        EditText passwordText = view.findViewById(R.id.editTextBarberPassword);
        EditText phoneText = view.findViewById(R.id.editTextBarberPhone);
        EditText shopNameText = view.findViewById(R.id.editTextShopName);
        EditText locationText = view.findViewById(R.id.editTextBarberLocation);
        EditText priceText = view.findViewById(R.id.editTextBarberPrice);

        ArrayList<EditText> parameters = new ArrayList<EditText>();

        parameters.add(fullNameText);
        parameters.add(emailText);
        parameters.add(passwordText);
        parameters.add(phoneText);
        parameters.add(shopNameText);
        parameters.add(locationText);
        parameters.add(priceText);


        Button buttonRegister = view.findViewById(R.id.buttonBarberRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity)getActivity();
                //mainActivity.RegisterFunc(fullNameText,emailText,passwordText,phoneText);
                //mainActivity.RegisterBarberFunc(view, fullNameText,emailText,passwordText,phoneText, shopText, locationText, priceText);
                mainActivity.RegisterFunc(view, parameters);
            }
        });

        return view;
    }
}