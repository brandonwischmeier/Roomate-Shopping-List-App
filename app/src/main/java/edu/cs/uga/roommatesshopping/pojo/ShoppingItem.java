package edu.cs.uga.roommatesshopping.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseUser;

public class ShoppingItem implements Parcelable {
    private String name;
    private double price;
    private FirebaseUser enteredUser;
    private String purchasedUser;
    private boolean purchased;

    public ShoppingItem()
    {}
    public ShoppingItem(String name, double price, String purchasedUser, boolean purchased)
    {
        this.name = name;
        this.price = price;
        this.purchasedUser = purchasedUser;
        this.purchased = purchased;
    }

    protected ShoppingItem(Parcel in) {
        name = in.readString();
        price = in.readDouble();
        enteredUser = in.readParcelable(FirebaseUser.class.getClassLoader());
        purchased = in.readByte() != 0;
    }

    public static final Creator<ShoppingItem> CREATOR = new Creator<ShoppingItem>() {
        @Override
        public ShoppingItem createFromParcel(Parcel in) {
            return new ShoppingItem(in);
        }

        @Override
        public ShoppingItem[] newArray(int size) {
            return new ShoppingItem[size];
        }
    };

    public String getName()
    {
        return name;
    }
    public double getPrice()
    {
        return price;
    }
    public String getPurchasedUser()
    {
        return purchasedUser;
    }
    public boolean isPurchased()
    {
        return purchased;
    }
    public void setPrice(double price){this.price = price;}
    public void setPurchasedUser(String userPair){this.purchasedUser=userPair;}
    public void setPurchased(){purchased = true;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(price);
        dest.writeParcelable(enteredUser, flags);
        dest.writeByte((byte) (purchased ? 1 : 0));
    }
}
