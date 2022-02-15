package com.example.mobileappimplementation.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mobileappimplementation.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragmentController#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragmentController extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //my variable(s)
    private String firstName;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public DashboardFragmentController() {
    }


    public static DashboardFragmentController newInstance(String param1, String param2) {
        DashboardFragmentController fragment = new DashboardFragmentController();
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
        View view = inflater.inflate(R.layout.dashboard, container, false);
        TextView nameText = (TextView) view.findViewById(R.id.nameText);
        nameText.setText("Welcome " + firstName);
        return view;

    }
}