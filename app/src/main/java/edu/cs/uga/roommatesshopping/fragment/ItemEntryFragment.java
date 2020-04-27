package edu.cs.uga.roommatesshopping.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.cs.uga.roommatesshopping.R;
import edu.cs.uga.roommatesshopping.pojo.ShoppingItem;

/**
 * Fragment to add an individual item to the shopping list
 */
public class ItemEntryFragment extends Fragment {

    private EditText editText;

    public ItemEntryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item_entry, container, false);
        editText = v.findViewById(R.id.editText);
        Button button = v.findViewById(R.id.button);
        button.setOnClickListener(new clickListener());
        return v;
    }

    private class clickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String itemText = editText.getText().toString();
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            String fb = mAuth.getCurrentUser().getEmail();
            final ShoppingItem shoppingItem = new ShoppingItem(itemText, 0.00, fb, false);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("shoppingItems");

            myRef.push().setValue(shoppingItem)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Show a quick confirmation
                            Toast.makeText(getActivity().getApplicationContext(), "Shopping List Item Created: " + shoppingItem.getName(),
                                    Toast.LENGTH_SHORT).show();

                            // Clear the EditTexts for next use.
                            editText.setText("");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity().getApplicationContext(), "Failed to create shopping list item " + shoppingItem.getName(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
