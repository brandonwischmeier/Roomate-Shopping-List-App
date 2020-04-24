package edu.cs.uga.roommatesshopping.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.cs.uga.roommatesshopping.databinding.ListItemBinding;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.MyViewHolder> {

    private static final String TAG = "ShoppingListAdapter";

    private OnListListener onListListener;

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ListItemBinding listItemBinding;
        OnListListener onListListener;

        MyViewHolder(ListItemBinding binding, OnListListener onListListener) {
            super(binding.getRoot());
            listItemBinding = binding;
            this.onListListener = onListListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onListListener.onListClick(getAdapterPosition());
        }
    }

    private ArrayList<String> shoppingListNames;

    public ShoppingListAdapter(ArrayList<String> shoppingListNames, OnListListener onListListener) {
        this.shoppingListNames = shoppingListNames;
        this.onListListener = onListListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), onListListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.setIsRecyclable(false);
        for (int i = 0; i < shoppingListNames.size(); i++) {
            holder.listItemBinding.shoppingListName.setText(shoppingListNames.get(position));
        }

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + shoppingListNames.size());
        return shoppingListNames.size();
    }

    public interface OnListListener {
        void onListClick(int position);
    }
}
