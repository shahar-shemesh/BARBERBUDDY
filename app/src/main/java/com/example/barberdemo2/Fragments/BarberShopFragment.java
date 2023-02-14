package com.example.barberdemo2.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.example.barberdemo2.R;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.auth.FirebaseAuth;

public class BarberShopFragment extends Fragment {

    public BarberShopFragment() {
        // Required empty public constructor
    }
    String userUid = new String();

    SupportMapFragment mapFragment;

    public static BarberShopFragment newInstance() {
        BarberShopFragment fragment = new BarberShopFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_barber_shop, container, false);

// ============================================== Get fragment items ================================================//
        TextView textViewName = view.findViewById(R.id.textViewFragmentBarberShopName);
        TextView textViewLocation = view.findViewById(R.id.textViewFragmentBarberShopLocation);
        TextView textViewPhone = view.findViewById(R.id.textViewFragmentBarberShopPhone);
        TextView textViewPrice = view.findViewById(R.id.textViewFragmentBarberShopPrice);
// ============================================== ---------------- ================================================//


// ============================================== Get Maps Fragment ================================================//
        Bundle barberDetails = new Bundle();
        barberDetails.putString("barberLocation", getArguments().getString("location"));
        barberDetails.putString("barberName", getArguments().getString("shopName"));
        String a = new String();
        a = "aa";
        GoogleMapsFragment googleMapsFragment = new GoogleMapsFragment();
        googleMapsFragment.setArguments(barberDetails);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerForMapView, googleMapsFragment);
        transaction.commit();
// ============================================== ---------------- ================================================//

        textViewName.setText(getArguments().getString("shopName"));
        textViewLocation.setText(getArguments().getString("location"));
        textViewPhone.setText(getArguments().getString("phone"));
        textViewPrice.setText(getArguments().getString("price"));

        userUid = FirebaseAuth.getInstance().getUid();
        Button buttonSlots = view.findViewById(R.id.buttonToSlots);
        buttonSlots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("shopName" ,getArguments().getString("shopName"));
                bundle.putString("shopPhone" ,getArguments().getString("phone"));
                bundle.putString("pathKey" ,getArguments().getString("pathKey"));
                bundle.putString("barberShopUid" ,getArguments().getString("Uid"));
                bundle.putString("userUid" , userUid);

                Navigation.findNavController(view).navigate(R.id.action_barberShopFragment_to_bookingSlotFragment, bundle);


            }
        });

        return view;
    }
}