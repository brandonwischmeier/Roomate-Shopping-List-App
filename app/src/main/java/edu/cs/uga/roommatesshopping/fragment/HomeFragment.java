package edu.cs.uga.roommatesshopping.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import edu.cs.uga.roommatesshopping.R;
import edu.cs.uga.roommatesshopping.adapter.ShoppingListAdapter;
import edu.cs.uga.roommatesshopping.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.recyclerviewShoppingLists.setLayoutManager(new LinearLayoutManager(getContext()));
        ShoppingListAdapter adapter = new ShoppingListAdapter(generateFakeValues());
        binding.recyclerviewShoppingLists.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_home, menu);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private ArrayList<String> generateFakeValues() {
        ArrayList<String> values = new ArrayList<>();
        values.add("Shopping list 1");
        values.add("Shopping list 2");
        values.add("Shopping list 3");
        values.add("Shopping list 4");
        values.add("Shopping list 5");
        values.add("Shopping list 6");
        values.add("Shopping list 7");
        values.add("Shopping list 8");
        values.add("Shopping list 9");
        return values;
    }
}
