package ar.com.tpfinal.rang_store.data.datasource.retrofit.entities;

import java.util.List;
import java.util.UUID;

import ar.com.tpfinal.rang_store.data.datasource.retrofit.objects.NewProduct;
import ar.com.tpfinal.rang_store.model.Product;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductApiRest {
    @GET("products")
    Call<List<Product>> listProducts(
            @Query("title") String title,
            @Query("categoryId") Integer categoryId,
            @Query("price_min") String price_min,
            @Query("price_max") String price_max);

    @GET("products")
    Call<List<Product>> listProducts();

    @POST("products")
    Call<Product> createProduct(@Body NewProduct p);

    @PUT("products/{productId}")
    Call<Product> updateProduct(@Path("productId") Integer productId, @Body NewProduct p);

    @DELETE("products/{productId}")
    Call<Boolean> deleteProduct(@Path("productId") Integer productId);
}
