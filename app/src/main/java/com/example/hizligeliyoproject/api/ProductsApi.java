package com.example.hizligeliyoproject.api;

import com.example.hizligeliyoproject.model.ProductList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductsApi {

    @GET("products")
    Call<List<ProductList>> getProducts();
}
