package edu.cs.uga.roommatesshopping.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.cs.uga.roommatesshopping.R;


public class SettleTheCostFragment extends Fragment {

    public SettleTheCostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settle_the_cost, container, false);
    }
}
