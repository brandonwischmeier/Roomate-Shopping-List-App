package edu.cs.uga.roommatesshopping.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.cs.uga.roommatesshopping.R;
import edu.cs.uga.roommatesshopping.pojo.ShoppingItem;
import edu.cs.uga.roommatesshopping.pojo.UserPair;

public class CostEntryFragment extends Fragment {

    private static final String TAG = "CostEntryFragment";
    private EditText editText;
    private Button button;
    private ShoppingItem currentItem;
    private ArrayList<ShoppingItem> shoppingItemList;

    public CostEntryFragment() {
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cost_entry, container, false);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("shoppingItems");
        shoppingItemList = getArguments().getParcelableArrayList("list");
        int index = getArguments().getInt("index");
        myRef.addListenerForSingleValueEvent( new ValueEventListener() {

            @Override
            public void onDataChange( DataSnapshot snapshot ) {
                // Once we have a DataSnapshot object, knowing that this is a list,
                // we need to iterate over the elements and place them on a List.
                for( DataSnapshot postSnapshot: snapshot.getChildren() ) {
                    ShoppingItem si = postSnapshot.getValue(ShoppingItem.class);
                    shoppingItemList.add(si);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        } );


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentItem = shoppingItemList.get(index);
        editText = v.findViewById(R.id.cost);
        TextView textView = v.findViewById(R.id.textView3);
        textView.setText(currentItem.getName());
        button = v.findViewById(R.id.enterBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // prevent user from entering in a non-value
                if (!editText.getText().toString().trim().isEmpty()) {
                    double price = Double.parseDouble(editText.getText().toString());
                    currentItem.setPrice(price);
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    DatabaseReference hopperRef = myRef.child(currentItem.getName());
                    DatabaseReference userUpdateRef = hopperRef.child("purchasedUser");
                    Map<String, Object> hopperUpdates = new HashMap<>();
                    hopperUpdates.put("price", currentItem.getPrice());
                    hopperUpdates.put("purchasedUser", mAuth.getCurrentUser().getEmail());
                    try {
                        hopperRef.updateChildren(hopperUpdates);
                        Toast.makeText(getActivity(),
                                "You've set the price to $" + currentItem.getName() + price, Toast.LENGTH_SHORT).show();
                    }
                    catch(Exception e)
                    {
                        System.out.println(e.getCause());
                    }
                } else {
                    editText.setText("");
                    Toast.makeText(getActivity(),
                            "Please enter a price", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //currentItem.setPurchasedUser(new UserPair(mAuth.getCurrentUser()));
        return v;
    }

}
