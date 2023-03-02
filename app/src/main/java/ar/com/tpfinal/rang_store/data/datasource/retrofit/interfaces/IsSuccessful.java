package ar.com.tpfinal.rang_store.data.datasource.retrofit.interfaces;

import java.io.IOException;

import retrofit2.Response;

public interface IsSuccessful<T> {
    void isSuccessful(final Response<T> response) throws IOException;
}
