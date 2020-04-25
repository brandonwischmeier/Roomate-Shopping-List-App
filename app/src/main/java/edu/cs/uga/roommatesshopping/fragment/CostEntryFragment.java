package edu.cs.uga.roommatesshopping.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.cs.uga.roommatesshopping.R;
import edu.cs.uga.roommatesshopping.pojo.ShoppingItem;

public class CostEntryFragment extends Fragment {

    private static final String TAG = "CostEntryFragment";
    //private HomeFragment homeFragment = new HomeFragment();

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
//        Bundle args = getArguments();
//        int index = args.getInt("index", 0);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("shoppingItems");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        ShoppingItem currentItem = (ShoppingItem) getArguments().getParcelableArrayList("list").get(getArguments().getInt("position"));
        EditText editText = v.findViewById(R.id.cost);
//        double price = Double.parseDouble(editText.getText().toString());
//        currentItem.setPrice(price);
//        currentItem.setPurchasedUser(new UserPair(mAuth.getCurrentUser()));
        return v;
    }
}
