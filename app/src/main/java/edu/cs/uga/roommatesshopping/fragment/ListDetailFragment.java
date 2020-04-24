package edu.cs.uga.roommatesshopping.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import edu.cs.uga.roommatesshopping.R;


public class ListDetailFragment extends Fragment implements View.OnClickListener {

    private NavController navController;

    public ListDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        // TODO: set on click listeners for buttons
    }

    @Override
    public void onClick(View v) {

//        switch (v.getId()) {
//            R.id.settle_btn:
//                navController.navigate();
//            R.id.edit_btn:
//                navController.navigate();
//        }
    }
}
