package com.example.mymodel.remote_data;


import com.example.mymodel.models.ModelM;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Api {
    public static final String BASE_URL= "https://myapi-8qmz.onrender.com/api/";
    @GET("products")
    Call<List<ModelM>> getStoreProducts();
    @Multipart
    @POST("products")
    Call<ModelM> addProduct(
            @Part("title") RequestBody title,
            @Part("price") RequestBody price,
            @Part("description") RequestBody description,
            @Part("count") RequestBody count,
            @Part MultipartBody.Part image
    );
    @DELETE("products/{id}")
    Call<Void> deleteProduct(@Path("id") int id);
}
