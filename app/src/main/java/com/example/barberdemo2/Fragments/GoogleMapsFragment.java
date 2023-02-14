package com.example.barberdemo2.Fragments;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.barberdemo2.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GoogleMapsFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    Context context;
//    Map barberShopDetails;
//    public GoogleMapsFragment(Map barberShopDetails){
//        this.barberShopDetails = barberShopDetails;
//        this.context = getContext();
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.google_maps_fragment, container, false);
        this.context = getContext();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.MY_MAP);
        mapFragment.getMapAsync(this);
    }

    //    32.109333    34.855499
//    LATITUDE,    LONGITUDE
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        LatLng barberShopLocation = new LatLng(32.109333, 34.855499);
//        mMap.addMarker(new MarkerOptions().position(barberShopLocation).title("Barber Shop"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(barberShopLocation, 16));
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName("Tel Aviv", 1);
            if (addresses.size() > 0) {
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();
                // Use the latitude and longitude to create a LatLng object and show it on the map
                LatLng location = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(location).title("Shop"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//
//    private void getLocationFromAddress(String address) {
//        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
//        try {
//            List<Address> addresses = geocoder.getFromLocationName(address, 1);
//            if (addresses.size() > 0) {
//                double latitude = addresses.get(0).getLatitude();
//                double longitude = addresses.get(0).getLongitude();
//                // Use the latitude and longitude to create a LatLng object and show it on the map
//                LatLng location = new LatLng(latitude, longitude);
//                mMap.addMarker(new MarkerOptions().position(location));
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}