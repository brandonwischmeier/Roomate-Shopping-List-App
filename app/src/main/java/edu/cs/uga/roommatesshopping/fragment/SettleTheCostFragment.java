package edu.cs.uga.roommatesshopping.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.cs.uga.roommatesshopping.R;
import edu.cs.uga.roommatesshopping.adapter.SettleTheCostAdapter;
import edu.cs.uga.roommatesshopping.pojo.ShoppingItem;
import edu.cs.uga.roommatesshopping.pojo.UserPair;


public class SettleTheCostFragment extends Fragment {
    ArrayList shoppingItemList;
    RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    public static final String DEBUG_TAG = "SettleTheCostFragment";
    public SettleTheCostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //final SettleTheCostAdapter[] recyclerAdapter;

        // use a linear layout manager for the recycler view

        DatabaseReference myRef = database.getReference("shoppingItems");
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        shoppingItemList = new ArrayList<ShoppingItem>();
        View v = inflater.inflate(R.layout.fragment_settle_the_cost, container, false);
        recyclerView = (RecyclerView) v.findViewById( R.id.recyclerView );
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager( layoutManager );
        final ArrayList<String> users = new ArrayList<String>();
        final ArrayList<UserPair> userPairs = new ArrayList<>();
        myRef.addListenerForSingleValueEvent( new ValueEventListener() {


            @Override
            public void onDataChange( DataSnapshot snapshot ) {
                // Once we have a DataSnapshot object, knowing that this is a list,
                // we need to iterate over the elements and place them on a List.
                for( DataSnapshot postSnapshot: snapshot.getChildren() ) {
                    ShoppingItem si = postSnapshot.getValue(ShoppingItem.class);
                    if (si.isPurchased()) {
                        shoppingItemList.add(si);
                        Log.d(DEBUG_TAG, "ReviewJobLeadsActivity.onCreate(): added: " + si);
                    }
                }
                Log.d( DEBUG_TAG, "ReviewJobLeadsActivity.onCreate(): setting recyclerAdapter" );
                boolean alreadyAdded = false;
                // Loop through shopping list, add prices
                for (int i= 0; i < shoppingItemList.size(); i++) {
                    ShoppingItem currentItem = (ShoppingItem) shoppingItemList.get(i);
                    alreadyAdded = false;
                    if (currentItem.isPurchased()) {
                        // If the ArrayList is empty, add the first user
                        if (users.size() == 0) {
                            users.add(currentItem.getPurchasedUser());
                            userPairs.add(new UserPair(currentItem.getPurchasedUser()));
                            userPairs.get(i).addToCost(currentItem.getPrice());
                            alreadyAdded = true;
                            System.out.println("put first user");
                        }
                        else
                            {
                            for (int j = 0; j < users.size(); j++)
                            {
                                // Check to make sure that we haven't added to total for the user yet
                                if (userPairs.get(j).getUser().equalsIgnoreCase(currentItem.getPurchasedUser()) && !alreadyAdded) {
                                    alreadyAdded = true;
                                    userPairs.get(j).addToCost(currentItem.getPrice());
                                    System.out.println("added to user");
                                }
                            }
                            // If the ArrayList didn't contain the user, add the user and add to their total
                            if (!alreadyAdded) {
                                users.add(currentItem.getPurchasedUser());
                                userPairs.add(new UserPair(currentItem.getPurchasedUser()));
                                userPairs.get(i).addToCost(currentItem.getPrice());
                                System.out.println("added new user");
                            }
                        }
                    }
                }
                // Now, create a SettleTheCostAdapter to populate a RecyclerView to display the job leads.
                recyclerAdapter = new SettleTheCostAdapter( userPairs );
                recyclerView.setAdapter(recyclerAdapter);
                //TODO: remove items from database

                for( DataSnapshot postSnapshot: snapshot.getChildren() ) {
                    ShoppingItem si = postSnapshot.getValue(ShoppingItem.class);
                    if (si.isPurchased()) {
                        postSnapshot.getRef().removeValue();
                        Log.d(DEBUG_TAG, "ReviewJobLeadsActivity.onCreate(): removed");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        } );
        return v;
    }
}