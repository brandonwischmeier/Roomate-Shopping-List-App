package edu.cs.uga.roommatesshopping.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.cs.uga.roommatesshopping.databinding.ListItemBinding;
import edu.cs.uga.roommatesshopping.pojo.UserPair;

/**
 * Adapter for settle the cost
 */
public class SettleTheCostAdapter extends RecyclerView.Adapter<SettleTheCostAdapter.MyViewHolder> {

    private static final String TAG = "SettleTheCostAdapter";
    private ArrayList<UserPair> users;

    public SettleTheCostAdapter(ArrayList<UserPair> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        for (int i = 0; i < users.size(); i++) {
            String user = users.get(position).getUser();
            @SuppressLint("DefaultLocale") String price = String.format("$%,.2f", users.get(position).getCost());

            holder.listItemBinding.shoppingListName.setText(user);
            holder.listItemBinding.icon.setText(user.substring(0, 1));
            holder.listItemBinding.price.setText(price);
        }
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + users.size());
        return users.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        ListItemBinding listItemBinding;

        MyViewHolder(ListItemBinding binding) {
            super(binding.getRoot());
            listItemBinding = binding;
        }
    }

}
