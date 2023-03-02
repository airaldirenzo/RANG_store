package ar.com.tpfinal.rang_store.data.datasource.firebase;

import java.util.HashMap;
import java.util.List;

import ar.com.tpfinal.rang_store.model.Category;
import ar.com.tpfinal.rang_store.model.Product;

public class ProductMapper {
    public static Product entityProductToProduct(HashMap data){
        Category category = new Category((int)(long)((HashMap) data.get("category")).get("id"),
                (String)((HashMap) data.get("category")).get("name"),
                (String) ((HashMap) data.get("category")).get("slug"));

        Product product = new Product((int)(long) data.get("id"),
                (String) data.get("title"),
                (String) data.get("description"),
                (Double) data.get("price"),
                (List<String>) data.get("images"),
                category);

        return product;
    }
}
