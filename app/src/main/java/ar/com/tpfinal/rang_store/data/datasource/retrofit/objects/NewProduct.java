package ar.com.tpfinal.rang_store.data.datasource.retrofit.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import ar.com.tpfinal.rang_store.model.Category;
import ar.com.tpfinal.rang_store.model.Product;

public class NewProduct {
    private String title;
    private String description;
    private Double price;
    private List<String> images;
    private Integer categoryId;
    //TODO ArrayList de imagenes


    public NewProduct(String title, String description, Double price, List<String> images, Integer categoryId) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.images = images;
        this.categoryId = categoryId;
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}





