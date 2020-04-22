package edu.cs.uga.roommatesshopping.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.cs.uga.roommatesshopping.R;

public class ItemEntryFragment extends Fragment {
    EditText editText;
    Button button;
    public ItemEntryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item_entry, container, false);
        editText = v.findViewById(R.id.editText);
        button = v.findViewById(R.id.button);
        button.setOnClickListener(new clickListener());
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("shoppingItems");
        return v;

    }

    private class clickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            String itemText = editText.getText().toString();
        }
    }
}
