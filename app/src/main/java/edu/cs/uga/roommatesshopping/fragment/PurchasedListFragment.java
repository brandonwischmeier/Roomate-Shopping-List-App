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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.cs.uga.roommatesshopping.R;
import edu.cs.uga.roommatesshopping.adapter.PurchaseListAdapter;
import edu.cs.uga.roommatesshopping.pojo.ShoppingItem;

/**
 * Fragment that displays the recently purchased items in a list
 */
public class PurchasedListFragment extends Fragment {

    private static final String DEBUG_TAG = "SettleTheCostFragment";
    private RecyclerView recyclerView;
    private ArrayList<ShoppingItem> purchasedItemList;
    private RecyclerView.Adapter recyclerAdapter;
    private NavController navController = null;

    public PurchasedListFragment() {
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

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("shoppingItems");


        View v = inflater.inflate(R.layout.fragment_purchased_list, container, false);

        recyclerView = v.findViewById(R.id.recyclerview_purchased_lists);

        purchasedItemList = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Once we have a DataSnapshot object, knowing that this is a list,
                // we need to iterate over the elements and place them on a List.
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    if (postSnapshot != null) {
                        ShoppingItem si = postSnapshot.getValue(ShoppingItem.class);
                        assert si != null;
                        if (si.isPurchased()) {
                            purchasedItemList.add(si);
                            System.out.println(si.getName());
                        }
                        Log.d(DEBUG_TAG, "PurchasedListFragment: added: " + si);
                    }
                }

                Log.d(DEBUG_TAG, "PurchasedListFragment.onCreate(): setting recyclerAdapter");

                // Now, create a SettleTheCostAdapter to populate a RecyclerView to display the job leads.
                recyclerAdapter = new PurchaseListAdapter(purchasedItemList);
                recyclerView.setAdapter(recyclerAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_purchased_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.settle_the_cost) {
            navController.navigate(R.id.action_purchasedListFragment_to_settleTheCostFragment);
        }
        return true;
    }
}
