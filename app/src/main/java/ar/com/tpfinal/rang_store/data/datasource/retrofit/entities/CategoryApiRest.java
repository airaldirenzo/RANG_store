package ar.com.tpfinal.rang_store.data.datasource.retrofit.entities;

import java.util.List;
import java.util.UUID;

import ar.com.tpfinal.rang_store.model.Category;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryApiRest {
    @GET("category")
    Call<List<Category>> listCategories();
}
