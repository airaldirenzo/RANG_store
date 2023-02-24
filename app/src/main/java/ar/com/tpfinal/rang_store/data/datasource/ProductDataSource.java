package ar.com.tpfinal.rang_store.data.datasource;

import java.util.List;

import ar.com.tpfinal.rang_store.data.OnResult;
import ar.com.tpfinal.rang_store.data.filter.FilterObject;
import ar.com.tpfinal.rang_store.model.Product;

public interface ProductDataSource {
    void createProduct(Product product, OnResult<Product> callback);

    void listProducts(OnResult<List<Product>> callback, FilterObject filterObject);

    void updateProduct(Product product, OnResult<Product> callback);

    void deleteProduct(Product product, OnResult<Boolean> callback);
}
