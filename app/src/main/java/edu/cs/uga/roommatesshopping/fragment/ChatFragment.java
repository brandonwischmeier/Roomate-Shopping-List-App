package edu.cs.uga.roommatesshopping.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.cs.uga.roommatesshopping.R;
import edu.cs.uga.roommatesshopping.adapter.MessageAdapter;
import edu.cs.uga.roommatesshopping.databinding.FragmentChatBinding;
import edu.cs.uga.roommatesshopping.pojo.Message;


/**
 * Fragment that controls chat functionality
 */
public class ChatFragment extends Fragment {

    private static final String TAG = "ChatFragment";

    private DatabaseReference databaseReference;
    private FragmentChatBinding binding;
    private ArrayList<Message> messageArrayList = new ArrayList<>();
    private MessageAdapter adapter;
    private RecyclerView recyclerView;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate layout
        binding = FragmentChatBinding.inflate(inflater, container, false);

        // store root view
        View view = binding.getRoot();

        recyclerView = view.findViewById(R.id.recyclerview_message_list);

        // get instance of firebase database node "message"
        databaseReference = FirebaseDatabase.getInstance().getReference("message");

        binding.buttonChatboxSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessageToDb();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setStackFromEnd(true);
        binding.recyclerviewMessageList.setLayoutManager(layoutManager);

        setChatListener();

        // return root view
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /*
    Sets listener for chat fragment
     */
    private void setChatListener() {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messageArrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Message message = snapshot.getValue(Message.class);
                    messageArrayList.add(message);
                }
                adapter = new MessageAdapter(messageArrayList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databaseReference.addValueEventListener(listener);
    }

    /*
    Records message in the database
     */
    private void sendMessageToDb() {
        // Pass in the contents of the EditText field and users display name to the message constructor
        Message message = new Message(
                getFirebaseUserId(),
                binding.edittextChatbox.getText().toString(),
                getFirebaseUserName());

        if (!binding.edittextChatbox.getText().toString().trim().isEmpty()) {
            databaseReference.push().setValue(message);
        }

        // Clear the text box after clicking send
        binding.edittextChatbox.setText("");
    }

    /*
    Returns the current user's id
     */
    private String getFirebaseUserId() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    /*
    Returns the current user's name
     */
    private String getFirebaseUserName() {
        return FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
    }
}
