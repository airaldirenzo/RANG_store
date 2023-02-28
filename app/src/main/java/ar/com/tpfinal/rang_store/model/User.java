package ar.com.tpfinal.rang_store.model;

import java.util.List;

public class User {

    private String uid;
    private String name;
    private String lastname;
    private String email;
    private List<ItemCart> cart;

    private List<Product> favorites;

    public User(String uid, String name, String lastname, String email, List<ItemCart> cart, List<Product> favorites) {
        this.uid = uid;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.cart = cart;
        this.favorites = favorites;
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

    public List<Product> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Product> favorites) {
        this.favorites = favorites;
    }
}
