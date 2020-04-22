package edu.cs.uga.roommatesshopping.pojo;

import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseUser;

public class ShoppingItem {
    private String name;
    private double price;
    private FirebaseUser enteredUser;
    private FirebaseUser purchasedUser;
    private boolean purchased;

    public ShoppingItem()
    {}
    public ShoppingItem(String name, double price, FirebaseUser enteredUser, FirebaseUser purchasedUser, boolean purchased)
    {
        this.name = name;
        this.price = price;
        this.enteredUser = enteredUser;
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
    public FirebaseUser getPurchasedUser()
    {
        return purchasedUser;
    }
    public FirebaseUser getEnteredUser()
    {
        return enteredUser;
    }
    public boolean isPurchased()
    {
        return purchased;
    }
}
