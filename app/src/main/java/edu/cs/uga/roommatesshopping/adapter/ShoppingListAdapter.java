package edu.cs.uga.roommatesshopping;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.cs.uga.roommatesshopping.databinding.ListItemBinding;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.MyViewHolder> {

    private static final String TAG = "ShoppingListAdapter";


    class MyViewHolder extends RecyclerView.ViewHolder {

        ListItemBinding listItemBinding;


        MyViewHolder(ListItemBinding binding) {
            super(binding.getRoot());
            listItemBinding = binding;
        }
    }

    private ArrayList<String> shoppingListNames;

    ShoppingListAdapter(ArrayList<String> shoppingListNames) {
        this.shoppingListNames = shoppingListNames;
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
        for (int i = 0; i < shoppingListNames.size(); i++) {
            holder.listItemBinding.shoppingListName.setText(shoppingListNames.get(position));
        }
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + shoppingListNames.size());
        return shoppingListNames.size();
    }

}
