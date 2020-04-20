package edu.cs.uga.roommatesshopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

import edu.cs.uga.roommatesshopping.databinding.FragmentAccountBinding;


public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        binding.accountListView.setAdapter(getAccountListAdapter());
        setSignOut();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private ArrayAdapter<String> getAccountListAdapter() {
        ArrayList<String> accountList = new ArrayList<>();
        accountList.add("Sign out");

        return new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_list_item_1,
                accountList
        );
    }
    private void setSignOut() {
        binding.accountListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                signOut();
            }
        });
    }

    /**
     * Sign user out of Firebase Authentication and all social identity providers
     */
    public void signOut() {
        AuthUI.getInstance()
                .signOut(getContext())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        signOutIntent();
                    }
                });
    }

    /**
     * Helper method for signOut
     * All this function does is start the login activity
     */
    private void signOutIntent() {
        startActivity(new Intent(getContext(), LoginActivity.class));
        getActivity().finish();
    }
}
