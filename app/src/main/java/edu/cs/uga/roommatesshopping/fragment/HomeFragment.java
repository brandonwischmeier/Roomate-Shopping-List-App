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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("shoppingItems");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        shoppingItemList = new ArrayList<ShoppingItem>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    ShoppingItem si = postSnapshot.getValue(ShoppingItem.class);
                    shoppingItemList.add(si);
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
                new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recyclerviewShoppingLists);
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
        // TODO: set listeners
        navController = Navigation.findNavController(view);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // TODO
        if (item.getItemId() == R.id.new_shopping_list) {
            navController.navigate(R.id.action_homeFragment_to_itemEntryFragment);
        }
        return true;
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


    @Override
    public void onListClick(int position) {
        Log.d(TAG, "onListClick: clicked: " + position);

    }

    /**
     * Deletes items from the recyclerview and database with swiping to the left or right
     */
    private ItemTouchHelper.SimpleCallback itemTouchHelper =
            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    // TODO: Remove shopping list from database
                    shoppingItemList.remove(viewHolder.getAdapterPosition());
                    adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                }
            };

    @Override
    public void onClick(View v) {

    }

    public ArrayList<ShoppingItem> getShoppingItemList() {
        return shoppingItemList;
    }
}
