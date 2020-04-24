package edu.cs.uga.roommatesshopping.pojo;

import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseUser;

public class ShoppingItem {
    private String name;
    private double price;
    private FirebaseUser enteredUser;
    private UserPair purchasedUser;
    private boolean purchased;

    public ShoppingItem()
    {}
    public ShoppingItem(String name, double price, UserPair purchasedUser, boolean purchased)
    {
        this.name = name;
        this.price = price;
        this.purchasedUser = purchasedUser;
        this.purchased = purchased;
    }
    public String getName()
    {
        return name;
    }
    public double getPrice()
    {
        return price;
    }
    public UserPair getPurchasedUser()
    {
        return purchasedUser;
    }
    public boolean isPurchased()
    {
        return purchased;
    }
}
