package edu.cs.uga.roommatesshopping.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.cs.uga.roommatesshopping.databinding.ListItemBinding;
import edu.cs.uga.roommatesshopping.pojo.UserPair;

public class SettleTheCostAdapter extends RecyclerView.Adapter<SettleTheCostAdapter.MyViewHolder> {

    private static final String TAG = "SettleTheCostAdapter";

    static class MyViewHolder extends RecyclerView.ViewHolder {

        ListItemBinding listItemBinding;

        MyViewHolder(ListItemBinding binding) {
            super(binding.getRoot());
            listItemBinding = binding;
        }
    }

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
        // TODO
        Log.d(TAG, "onBindViewHolder: called");
        for (int i = 0; i < users.size(); i++) {
            String user = users.get(position).getUser();
            double price = users.get(position).getCost();
            String text = user+ " " + price;
            System.out.println(text);
            holder.listItemBinding.shoppingListName.setText(text);
        }
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + users.size());
        return users.size();
    }

}
