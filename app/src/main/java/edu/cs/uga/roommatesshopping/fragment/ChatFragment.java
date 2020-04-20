package edu.cs.uga.roommatesshopping.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.cs.uga.roommatesshopping.pojo.Message;
import edu.cs.uga.roommatesshopping.databinding.FragmentChatBinding;


public class ChatFragment extends Fragment {

    private static final String TAG = "ChatFragment";

    private DatabaseReference databaseReference;
    private ChildEventListener listener;
    private FragmentChatBinding binding;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference("message");
        binding.buttonChatboxSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessageToDb();
            }
        });
        setListener();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setListener() {
        listener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Message message = dataSnapshot.getValue(Message.class);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databaseReference.addChildEventListener(listener);
    }

    private void sendMessageToDb() {
        // Pass in the contents of the EditText field and users display name to the message constructor
        Message message = new Message(
                FirebaseAuth.getInstance().getCurrentUser().getUid(),
                binding.edittextChatbox.getText().toString(),
                FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        Log.d(TAG, "onClick: " + binding.edittextChatbox.getText().toString() + "/n"
                + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        databaseReference.push().setValue(message);

        // Clear the text box after clicking send
        binding.edittextChatbox.setText("");
    }
}
