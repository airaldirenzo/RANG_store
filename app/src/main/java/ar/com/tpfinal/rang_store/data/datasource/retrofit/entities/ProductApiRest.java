package ar.com.tpfinal.rang_store.data.datasource.retrofit.entities;

import java.util.List;
import java.util.UUID;

import ar.com.tpfinal.rang_store.model.Product;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ProductApiRest {
    @GET("products")
    Call<List<Product>> listProducts();

    @POST("products")
    Call<Product> createProduct(@Body Product p);
}
