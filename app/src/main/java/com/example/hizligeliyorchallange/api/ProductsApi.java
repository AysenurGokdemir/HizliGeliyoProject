package com.example.hizligeliyorchallange.api;

import com.example.hizligeliyorchallange.model.ProductList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductsApi {

    @GET("products")
    Call<List<ProductList>> getProducts();
}
