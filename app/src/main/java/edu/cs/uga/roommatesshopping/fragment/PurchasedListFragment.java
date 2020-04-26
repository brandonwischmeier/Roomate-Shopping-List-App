package edu.cs.uga.roommatesshopping.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import edu.cs.uga.roommatesshopping.adapter.PurchaseListAdapter;
import edu.cs.uga.roommatesshopping.adapter.SettleTheCostAdapter;
import edu.cs.uga.roommatesshopping.adapter.ShoppingListAdapter;
import edu.cs.uga.roommatesshopping.databinding.FragmentHomeBinding;
import edu.cs.uga.roommatesshopping.pojo.ShoppingItem;
import edu.cs.uga.roommatesshopping.pojo.UserPair;

public class PurchasedListFragment extends Fragment {
    ArrayList shoppingItemList;
    RecyclerView recyclerView;
    ArrayList<ShoppingItem> purchasedItemList;
    private RecyclerView.Adapter recyclerAdapter;
    private FragmentHomeBinding binding;
    RecyclerView.LayoutManager layoutManager;
    public static final String DEBUG_TAG = "SettleTheCostFragment";
    public PurchasedListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("shoppingItems");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        purchasedItemList = new ArrayList<ShoppingItem>();
        View v = inflater.inflate(R.layout.fragment_purchased_list, container, false);
        recyclerView = (RecyclerView) v.findViewById( R.id.recyclerview_purchased_lists );
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager( layoutManager );
        final ArrayList<UserPair> users = new ArrayList<UserPair>();
        myRef.addListenerForSingleValueEvent( new ValueEventListener() {


            @Override
            public void onDataChange( DataSnapshot snapshot ) {
                // Once we have a DataSnapshot object, knowing that this is a list,
                // we need to iterate over the elements and place them on a List.
                for( DataSnapshot postSnapshot: snapshot.getChildren() ) {
                    if(postSnapshot != null) {
                        ShoppingItem si = postSnapshot.getValue(ShoppingItem.class);
                        if(si.isPurchased()) {
                            purchasedItemList.add(si);
                            System.out.println(si.getName());
                        }
                        Log.d(DEBUG_TAG, "PurchasedListFragment: added: " + si);
                    }
                }
                Log.d( DEBUG_TAG, "PurchasedListFragment.onCreate(): setting recyclerAdapter" );
                // Now, create a SettleTheCostAdapter to populate a RecyclerView to display the job leads.
                recyclerAdapter = new PurchaseListAdapter( purchasedItemList );
                recyclerView.setAdapter( recyclerAdapter );

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        } );
        return v;
    }
}
