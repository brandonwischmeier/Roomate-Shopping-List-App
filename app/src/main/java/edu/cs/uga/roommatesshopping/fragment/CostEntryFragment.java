package edu.cs.uga.roommatesshopping.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.cs.uga.roommatesshopping.R;
import edu.cs.uga.roommatesshopping.pojo.ShoppingItem;
import edu.cs.uga.roommatesshopping.pojo.UserPair;

public class CostEntryFragment extends Fragment {
<<<<<<< HEAD
    EditText editText;
    Button button;
    ShoppingItem currentItem;
=======

    private static final String TAG = "CostEntryFragment";
    private EditText editText;
    private Button button;
    private ShoppingItem currentItem;
    //private HomeFragment homeFragment = new HomeFragment();

>>>>>>> 68e4e33fc53de8959a41fbaf59e3a3ad080cef4b
    public CostEntryFragment() {
    }

    //
//    public static CostEntryFragment newInstance(int index) {
//        CostEntryFragment f = new CostEntryFragment();
//        // Supply index input as an argument.
//        Bundle args = new Bundle();
//        args.putInt("index", index);
//        f.setArguments(args);
//        return f;
//    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cost_entry, container, false);
<<<<<<< HEAD
        Bundle args = getArguments();
        assert args != null;
        int index = args.getInt("index", 0);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("shoppingItems");
        final ArrayList<ShoppingItem> shoppingItemList = new ArrayList<ShoppingItem>();
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
        currentItem = (ShoppingItem) shoppingItemList.get(index);
        editText = v.findViewById(R.id.cost);
        button = v.findViewById(R.id.button2);
        button.setOnClickListener(new CostEntryFragment.clickListener());
=======
//        Bundle args = getArguments();
//        int index = args.getInt("index", 0);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("shoppingItems");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentItem = (ShoppingItem) getArguments().getParcelableArrayList("list").get(getArguments().getInt("position"));
        editText = v.findViewById(R.id.cost);
        button = v.findViewById(R.id.enterBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // prevent user from entering in a non-value
                if (!editText.getText().toString().trim().isEmpty()) {
                    double price = Double.parseDouble(editText.getText().toString());
                    currentItem.setPrice(price);
                    Toast.makeText(getActivity(),
                            "You've set the price to $" + price, Toast.LENGTH_SHORT).show();
                } else {
                    editText.setText("");
                    Toast.makeText(getActivity(),
                            "Please enter a price", Toast.LENGTH_SHORT).show();
                }
            }
        });

        currentItem.setPurchasedUser(new UserPair(mAuth.getCurrentUser()));
        return v;
    }
    private class clickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("shoppingItems");
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            double price = Double.parseDouble(editText.getText().toString());
            currentItem.setPrice(price);
            currentItem.setPurchasedUser(new UserPair(mAuth.getCurrentUser()));
            //TODO: update in database

        }
    }
}
