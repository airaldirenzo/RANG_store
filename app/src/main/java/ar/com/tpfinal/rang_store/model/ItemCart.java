package ar.com.tpfinal.rang_store.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemCart implements Parcelable {

    private Product product;
    private Integer quantity;

    public ItemCart(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.product, flags);
        dest.writeValue(this.quantity);
    }

    public void readFromParcel(Parcel source) {
        this.product = source.readParcelable(Product.class.getClassLoader());
        this.quantity = (Integer) source.readValue(Integer.class.getClassLoader());
    }

    protected ItemCart(Parcel in) {
        this.product = in.readParcelable(Product.class.getClassLoader());
        this.quantity = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<ItemCart> CREATOR = new Parcelable.Creator<ItemCart>() {
        @Override
        public ItemCart createFromParcel(Parcel source) {
            return new ItemCart(source);
        }

        @Override
        public ItemCart[] newArray(int size) {
            return new ItemCart[size];
        }
    };
}
