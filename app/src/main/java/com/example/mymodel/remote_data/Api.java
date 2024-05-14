package com.example.mymodel.remote_data;

import android.graphics.ColorSpace;

import com.example.mymodel.models.ModelM;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    String BASE_URL= "https://fakestoreapi.com/";
    @GET("products")
    Call<List<ModelM>> getStoreProducts();
}
