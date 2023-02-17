package ar.com.tpfinal.rang_store.data.repository;

import java.util.List;

import ar.com.tpfinal.rang_store.data.OnResult;
import ar.com.tpfinal.rang_store.data.datasource.ProductDataSource;
import ar.com.tpfinal.rang_store.model.Product;

public class ProductRepository  implements ProductDataSource {

    private final ProductDataSource ds;

    public ProductRepository(final ProductDataSource ds) {
        this.ds = ds;
    }

    @Override
    public void createProduct(Product product, OnResult<Product> callback) {
        ds.createProduct(product, callback);
    }

    @Override
    public void listProducts(OnResult<List<Product>> callback) {
        ds.listProducts(callback);
    }

    @Override
    public void getProduct(Product product, OnResult<Product> callback) {
        ds.getProduct(product, callback);
    }
}
