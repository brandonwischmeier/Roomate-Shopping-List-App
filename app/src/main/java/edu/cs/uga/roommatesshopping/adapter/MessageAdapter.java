package edu.cs.uga.roommatesshopping.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import edu.cs.uga.roommatesshopping.databinding.ReceivedMsgBinding;
import edu.cs.uga.roommatesshopping.databinding.SentMsgBinding;
import edu.cs.uga.roommatesshopping.pojo.Message;

/**
 * Adapter class for messages
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private static final String TAG = "MessageAdapter";

    private static final int MSG_SENT = 0;
    private static final int MSG_RECEIVED = 1;
    private ArrayList<Message> message;

    public MessageAdapter(ArrayList<Message> message) {
        this.message = message;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_SENT) {
            Log.d(TAG, "onCreateViewHolder: SentMsgBinding called");
            return new MyViewHolder(SentMsgBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        } else {
            Log.d(TAG, "onCreateViewHolder: ReceivedMsgBinding called");
            return new MyViewHolder(ReceivedMsgBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        Message singleMsg = message.get(position);

        if (getItemViewType(position) == MSG_SENT) {
            holder.sentMsgBinding.msg.setText(singleMsg.getText());
        } else {
            holder.receivedMsgBinding.msg.setText(singleMsg.getText());
            holder.receivedMsgBinding.name.setText(singleMsg.getName());
        }
    }

    @Override
    public int getItemViewType(int position) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        if (message.get(position).getUserId().equals(user.getUid())) {
            return MSG_SENT;
        } else {
            return MSG_RECEIVED;
        }
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + message.size());
        return message.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        SentMsgBinding sentMsgBinding;
        ReceivedMsgBinding receivedMsgBinding;

        MyViewHolder(SentMsgBinding binding) {
            super(binding.getRoot());
            sentMsgBinding = binding;
        }

        MyViewHolder(ReceivedMsgBinding binding) {
            super(binding.getRoot());
            receivedMsgBinding = binding;
        }
    }

}
