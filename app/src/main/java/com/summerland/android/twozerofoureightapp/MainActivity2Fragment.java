package com.summerland.android.twozerofoureightapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivity2Fragment extends Fragment {


    public MainActivity2Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.i("Logm", "Inside onCreateView in MainActivity2Fragment");
        View view = inflater.inflate(R.layout.fragment_main_activity2, container, false);
        TextView tv = (TextView) view.findViewById(R.id.fragText);
        tv.setText("Hello duckie");

        // Inflate the layout for this fragment
        return view;

    }
}
