package edu.cs.uga.roommatesshopping.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseUser;

/**
 * POJO representing a single shopping item
 */
public class ShoppingItem implements Parcelable {
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
    private String name;
    private double price;
    private FirebaseUser enteredUser;
    private String purchasedUser;
    private String itemID;
    private boolean purchased;

    public ShoppingItem() {
    }

    public ShoppingItem(String name, double price, String purchasedUser, boolean purchased) {
        this.name = name;
        this.price = price;
        this.purchasedUser = purchasedUser;
        this.purchased = purchased;
    }

    private ShoppingItem(Parcel in) {
        name = in.readString();
        price = in.readDouble();
        enteredUser = in.readParcelable(FirebaseUser.class.getClassLoader());
        purchased = in.readByte() != 0;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPurchasedUser() {
        return purchasedUser;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

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
