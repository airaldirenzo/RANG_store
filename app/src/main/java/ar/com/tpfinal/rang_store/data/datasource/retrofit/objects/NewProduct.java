package ar.com.tpfinal.rang_store.data.datasource.retrofit.objects;

import java.util.List;

public class NewProduct {
    private String title;
    private String description;
    private Double price;
    private List<String> images;
    private Integer categoryId;

    public NewProduct(String title, String description, Double price, List<String> images, Integer categoryId) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.images = images;
        this.categoryId = categoryId;
    }
}





