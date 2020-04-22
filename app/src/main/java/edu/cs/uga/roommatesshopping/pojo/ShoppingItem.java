package edu.cs.uga.roommatesshopping.pojo;

import com.firebase.ui.auth.data.model.User;

public class ShoppingItem {
    private String name;
    private double price;
    private User enteredUser;
    private User purchasedUser;
    private boolean purchased;

    public ShoppingItem()
    {}
    public ShoppingItem(String name, double price, User enteredUser, User purchasedUser, boolean purchased)
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
    public User getPurchasedUser()
    {
        return purchasedUser;
    }
    public User getEnteredUser()
    {
        return enteredUser;
    }
    public boolean isPurchased()
    {
        return purchased;
    }
}
