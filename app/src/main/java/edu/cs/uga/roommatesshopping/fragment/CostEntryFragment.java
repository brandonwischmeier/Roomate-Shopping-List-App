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

import java.util.ArrayList;

import edu.cs.uga.roommatesshopping.R;
import edu.cs.uga.roommatesshopping.pojo.ShoppingItem;
import edu.cs.uga.roommatesshopping.pojo.UserPair;

public class CostEntryFragment extends Fragment {
    EditText editText;
    Button button;
    ShoppingItem currentItem;
    public CostEntryFragment() {
    }
    public static CostEntryFragment newInstance(int index) {
        CostEntryFragment f = new CostEntryFragment();
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);
        return f;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cost_entry, container, false);
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
