package edu.cs.uga.roommatesshopping.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.cs.uga.roommatesshopping.R;
import edu.cs.uga.roommatesshopping.adapter.ShoppingListAdapter;
import edu.cs.uga.roommatesshopping.databinding.FragmentHomeBinding;
import edu.cs.uga.roommatesshopping.pojo.ShoppingItem;


/**
 * Displays the current shopping lists
 */
public class HomeFragment extends Fragment implements ShoppingListAdapter.OnListListener, View.OnClickListener {

    private static final String TAG = "HomeFragment";
    private FragmentHomeBinding binding;
    private ArrayList<ShoppingItem> shoppingItemList = new ArrayList<>();
    private NavController navController = null;
    private ShoppingListAdapter adapter;
    private String itemID;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // inflate the layout
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        // get database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("shoppingItems");

        // add listener
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                shoppingItemList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    ShoppingItem shoppingItem = postSnapshot.getValue(ShoppingItem.class);
                    assert shoppingItem != null;
                    if (!shoppingItem.isPurchased()) {
                        itemID = postSnapshot.getKey();
                        shoppingItem.setItemID(itemID);
                        shoppingItemList.add(shoppingItem);
                    }
                }

                adapter = new ShoppingListAdapter(shoppingItemList, new ShoppingListAdapter.OnListListener() {
                    @Override
                    public void onListClick(int position) {
                        Bundle args = new Bundle();
                        args.putInt("index", position);
                        args.putParcelableArrayList("list", shoppingItemList);
                        navController.navigate(R.id.action_homeFragment_to_costEntryFragment, args);

                    }
                });
                binding.recyclerviewShoppingLists.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });


        binding.recyclerviewShoppingLists.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerviewShoppingLists.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // holds reference to navigation graph
        navController = Navigation.findNavController(view);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.new_shopping_list) {
            navController.navigate(R.id.action_homeFragment_to_itemEntryFragment);
        }
        if (item.getItemId() == R.id.settle_the_cost) {
            navController.navigate(R.id.action_homeFragment_to_settleTheCostFragment);
        }
        return true;
    }

    @Override
    public void onListClick(int position) {
        Log.d(TAG, "onListClick: clicked: " + position);

    }


    @Override
    public void onClick(View v) {

    }

}
