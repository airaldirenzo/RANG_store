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

    private Integer id;
    private String title;
    private String description;
    private Double price;
    private Integer stock;

    private Category category;

    //TODO ArrayList de imagenes

    //TODO private Categoria category;

    public Product(final Integer id, final String title, final String description, final Double price,
                      final Integer stock, final LocalDateTime creationAt, final LocalDateTime updatedAt) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id) && Objects.equals(title, product.title) && Objects.equals(description, product.description) && Objects.equals(price, product.price) && Objects.equals(stock, product.stock) && Objects.equals(category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, price, stock, category);
    }



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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeValue(this.price);
        dest.writeValue(this.stock);
        dest.writeParcelable(this.category, flags);
    }

    public void readFromParcel(Parcel source) {
        this.id = (Integer) source.readValue(Integer.class.getClassLoader());
        this.title = source.readString();
        this.description = source.readString();
        this.price = (Double) source.readValue(Double.class.getClassLoader());
        this.stock = (Integer) source.readValue(Integer.class.getClassLoader());
        this.category = source.readParcelable(Category.class.getClassLoader());
    }

    protected Product(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.title = in.readString();
        this.description = in.readString();
        this.price = (Double) in.readValue(Double.class.getClassLoader());
        this.stock = (Integer) in.readValue(Integer.class.getClassLoader());
        this.category = in.readParcelable(Category.class.getClassLoader());
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
