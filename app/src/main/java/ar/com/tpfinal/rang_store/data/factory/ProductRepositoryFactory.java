package ar.com.tpfinal.rang_store.data.factory;

import android.content.Context;

import ar.com.tpfinal.rang_store.data.datasource.retrofit.ProductRetrofitDataSource;
import ar.com.tpfinal.rang_store.data.repository.ProductRepository;

public class ProductRepositoryFactory {

    public static ProductRepository create() {
        return new ProductRepository(new ProductRetrofitDataSource());
    }
}