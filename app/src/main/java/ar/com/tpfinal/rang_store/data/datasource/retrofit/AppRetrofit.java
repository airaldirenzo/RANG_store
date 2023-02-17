package ar.com.tpfinal.rang_store.data.datasource.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ar.com.tpfinal.rang_store.data.datasource.retrofit.entities.CategoryApiRest;
import ar.com.tpfinal.rang_store.data.datasource.retrofit.entities.ProductApiRest;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AppRetrofit {

    public static final ExecutorService EXECUTOR_API = Executors.newSingleThreadExecutor();
    private static AppRetrofit INSTANCE;

    public ProductApiRest productApiRest;
    public CategoryApiRest categoryApiRest;

    public static  AppRetrofit getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AppRetrofit();
        }
        return INSTANCE;
    }

    private AppRetrofit() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.escuelajs.co/api/v1/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        productApiRest = retrofit.create(ProductApiRest.class);
        categoryApiRest = retrofit.create(CategoryApiRest.class);
    }
}
