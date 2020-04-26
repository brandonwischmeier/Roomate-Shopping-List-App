package edu.cs.uga.roommatesshopping.pojo;

import com.google.firebase.auth.FirebaseUser;

public class UserPair {
    private String user;
    private double cost;

    public UserPair(){}
    public UserPair(String user)
    {
        this.user = user;
        cost = 0;
    }
    public void addToCost(double cost)
    {
        this.cost += cost;
    }
    public double getCost()
    {
        return cost;
    }
    public String getUser()
    {
        return user;
    }
}

