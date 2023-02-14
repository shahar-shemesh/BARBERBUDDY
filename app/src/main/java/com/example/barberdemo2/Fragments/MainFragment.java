package com.example.barberdemo2.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.barberdemo2.Activitys.MainActivity;
import com.example.barberdemo2.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        //=========================================================================================//
        EditText emailText = view.findViewById(R.id.editTextLoginEmail);
        EditText passwordText = view.findViewById(R.id.editTextLoginPassword);
        ImageView imageView = view.findViewById(R.id.imageViewBarberLogo);
        imageView.setImageResource(R.drawable.primary_logo);

        Button buttonLogin = view.findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.LoginFunc(view, emailText,passwordText);
            }
        });
        //=========================================================================================//
        Button buttonRegister = view.findViewById(R.id.buttonLoginRegister);
        Switch switchBarber = view.findViewById(R.id.switchBarber);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchBarber.isChecked()){
                    Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_barberRegisterFragment);
                }
                else{
                    Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_userRegisterFragment);
                }
            }
        });
        //=========================================================================================//
        Button buttonContinueAsGuest = view.findViewById(R.id.buttonContinueAsGuest);
        buttonContinueAsGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //System.out.println(view.getTransitionName());
//                Navigation.findNavController(view).navigate(R.id);
                Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_dashboardFragment);
//                Fragment fragment = DashboardFragment.newInstance();
//                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragmentContainerView, fragment, "recycleView");
//                fragmentTransaction.commit();



            }
        });
        //=========================================================================================//

        return view;
    }
}