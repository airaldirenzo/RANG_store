package ar.com.tpfinal.rang_store.data.datasource.retrofit;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import ar.com.tpfinal.rang_store.data.OnResult;
import ar.com.tpfinal.rang_store.data.datasource.ProductDataSource;
import ar.com.tpfinal.rang_store.data.datasource.retrofit.entities.ProductApiRest;
import ar.com.tpfinal.rang_store.data.datasource.retrofit.interfaces.IsSuccessful;
import ar.com.tpfinal.rang_store.data.datasource.retrofit.objects.NewProduct;
import ar.com.tpfinal.rang_store.data.filter.FilterObject;
import ar.com.tpfinal.rang_store.model.Product;
import retrofit2.Response;

public class ProductRetrofitDataSource implements ProductDataSource {

    private final ProductApiRest productApiRest;

    public ProductRetrofitDataSource() {
        this(AppRetrofit.getInstance());
    }

    public ProductRetrofitDataSource(AppRetrofit retrofit) {
        productApiRest = retrofit.productApiRest;
    }
    @Override
    public void createProduct(Product product, OnResult<Product> callback) {
        try {
            NewProduct newProduct = new NewProduct(
                    product.getTitle(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getImages(),
                    product.getCategory().getId());

            Response<Product> response = productApiRest.createProduct(newProduct).execute();

            IsSuccessful<Product> responseStatus = new IsSuccessful<>() {
                @Override
                public void isSuccessful(Response<Product> response) throws IOException {
                    if (response.isSuccessful()) {
                        callback.onSuccess(response.body());
                    } else {
                        throw new IOException("No pudo completarse la solicitud a la API, Codigo de error: " + response.code());
                    }
                }
            };
            responseStatus.isSuccessful(response);

        } catch (IOException e) {
            callback.onError(e);
        }
    }

    @Override
    public void listProducts(OnResult<List<Product>> callback, FilterObject filterObject) {
        try {
            Response<List<Product>> response;
            response = productApiRest.
                    listProducts(
                            filterObject.getTitleFilter(),
                            filterObject.getCategoryId(),
                            filterObject.getMinPriceFilter(),
                            filterObject.getMaxPriceFilter())
                    .execute();


            IsSuccessful<List<Product>> responseStatus = new IsSuccessful<>() {
                @Override
                public void isSuccessful(Response<List<Product>> response) throws IOException {
                    if (response.isSuccessful()) {
                        callback.onSuccess(response.body());
                    } else {
                        throw new IOException("No pudo completarse la solicitud a la API, Codigo de error: " + response.code());
                    }
                }
            };
            responseStatus.isSuccessful(response);
        } catch (IOException e) {
            callback.onError(e);
        }
    }

    @Override
    public void updateProduct(Product product, OnResult<Product> callback) {
        try {
            NewProduct newProduct = new NewProduct(
                    product.getTitle(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getImages(),
                    product.getCategory().getId());

            Response<Product> response = productApiRest.updateProduct(product.getId(), newProduct).execute();

            IsSuccessful<Product> responseStatus = new IsSuccessful<>() {
                @Override
                public void isSuccessful(Response<Product> response) throws IOException {
                    if (response.isSuccessful()) {
                        callback.onSuccess(response.body());
                    } else {
                        throw new IOException("No pudo completarse la solicitud a la API, Codigo de error: " + response.code());
                    }
                }
            };
            responseStatus.isSuccessful(response);
        } catch (IOException e) {
            callback.onError(e);
        }
    }

    @Override
    public void deleteProduct(Product product, OnResult<Boolean> callback) {
        try {

            Response<Boolean> response = productApiRest.deleteProduct(product.getId()).execute();

            IsSuccessful<Boolean> responseStatus = new IsSuccessful<>() {
                @Override
                public void isSuccessful(Response<Boolean> response) throws IOException {
                    if (response.isSuccessful()) {
                        callback.onSuccess(response.body());
                    } else {
                        throw new IOException("No pudo completarse la solicitud a la API, Codigo de error: " + response.code());
                    }
                }
            };

            responseStatus.isSuccessful(response);
        } catch (IOException e) {
            callback.onError(e);
        }
    }
}
