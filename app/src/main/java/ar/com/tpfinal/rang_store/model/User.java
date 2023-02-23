package ar.com.tpfinal.rang_store.model;

import android.util.Pair;

import java.util.List;

public class User {

    private String uid;
    private String name;
    private String lastname;
    private String email;
    private List<ItemCart> cart;

    public User(String uid, String name, String lastname, String email, List<ItemCart> cart) {
        this.uid = uid;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.cart = cart;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ItemCart> getCart() {
        return cart;
    }

    public void setCart(List<ItemCart> cart) {
        this.cart = cart;
    }
}
