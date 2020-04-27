package edu.cs.uga.roommatesshopping.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.cs.uga.roommatesshopping.databinding.ListItemBinding;
import edu.cs.uga.roommatesshopping.pojo.ShoppingItem;
import edu.cs.uga.roommatesshopping.pojo.UserPair;

public class PurchaseListAdapter extends RecyclerView.Adapter<PurchaseListAdapter.MyViewHolder> {
    private static final String TAG = "PurchaseListAdapter";

    static class MyViewHolder extends RecyclerView.ViewHolder {

        ListItemBinding listItemBinding;

        MyViewHolder(ListItemBinding binding) {
            super(binding.getRoot());
            listItemBinding = binding;
        }
    }

    private ArrayList<ShoppingItem> shoppingItems;

    public PurchaseListAdapter(ArrayList<ShoppingItem> shoppingItems) {
        this.shoppingItems = shoppingItems;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.setIsRecyclable(false);
        for (int i = 0; i < shoppingItems.size(); i++) {
                String text = shoppingItems.get(position).getName() + " " + shoppingItems.get(position).getPrice();
                System.out.println("adapter" + text);
                holder.listItemBinding.shoppingListName.setText(text);
        }
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + shoppingItems.size());
        return shoppingItems.size();
    }
}
