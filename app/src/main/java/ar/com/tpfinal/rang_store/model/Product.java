package ar.com.tpfinal.rang_store.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Product implements Parcelable {

    private String uid;
    private String title;
    private String description;
    private Double price;
    private Integer stock;
    //TODO ArrayList de imagenes
    private LocalDateTime creationAt;
    private LocalDateTime updatedAt;
    //TODO private Categoria category;

    public Product(final String uid, final String title, final String description, final Double price,
                      final Integer stock, final LocalDateTime creationAt, final LocalDateTime updatedAt) {

        this.uid = uid;
        this.title = title;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.creationAt = creationAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return uid.equals(product.uid) && Objects.equals(title, product.title) && Objects.equals(description, product.description) && Objects.equals(price, product.price) && Objects.equals(stock, product.stock) && Objects.equals(creationAt, product.creationAt) && Objects.equals(updatedAt, product.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, title, description, price, stock, creationAt, updatedAt);
    }

    public String getId() {
        return uid;
    }

    public void setId(String id) {
        this.uid = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public LocalDateTime getCreationAt() {
        return creationAt;
    }

    public void setCreationAt(LocalDateTime creationAt) {
        this.creationAt = creationAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.uid);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeValue(this.price);
        dest.writeValue(this.stock);
        dest.writeSerializable(this.creationAt);
        dest.writeSerializable(this.updatedAt);
    }

    public void readFromParcel(Parcel source) {
        this.uid = source.readString();
        this.title = source.readString();
        this.description = source.readString();
        this.price = (Double) source.readValue(Double.class.getClassLoader());
        this.stock = (Integer) source.readValue(Integer.class.getClassLoader());
        this.creationAt = (LocalDateTime) source.readSerializable();
        this.updatedAt = (LocalDateTime) source.readSerializable();
    }

    protected Product(Parcel in) {
        this.uid = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.price = (Double) in.readValue(Double.class.getClassLoader());
        this.stock = (Integer) in.readValue(Integer.class.getClassLoader());
        this.creationAt = (LocalDateTime) in.readSerializable();
        this.updatedAt = (LocalDateTime) in.readSerializable();
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public static final DiffUtil.ItemCallback<Product> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Product oldProduct, @NonNull Product newProduct) {
                    // Product properties may have changed if reloaded from the DB, but ID is fixed
                    return oldProduct.getId() == newProduct.getId();
                }

                @Override
                public boolean areContentsTheSame(
                        // NOTE: if you use equals, your object must properly override Object#equals()
                        @NonNull Product oldProduct, @NonNull Product newProduct) {
                    // Incorrectly returning false here will result in too many animations.
                    return oldProduct.equals(newProduct);
                }
            };
}
