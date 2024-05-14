package com.example.mymodel.repositories;
import android.util.Log;

import com.example.mymodel.remote_data.RetrofitClient;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mymodel.models.ModelM;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    public Repository(){}
    final MutableLiveData<List<ModelM>> data= new MutableLiveData<>();

    public Repository(FragmentActivity requireActivity, List<ModelM> modelMS) {
    }

    public LiveData<List<ModelM>>getDashJemList(){
        Call<List<ModelM>> apicall = RetrofitClient.getInstance().getApi().getStoreProducts();
        apicall.enqueue(new Callback<List<ModelM>>() {
            @Override
            public void onResponse(Call<List<ModelM>> call, Response<List<ModelM>> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<ModelM>> call, Throwable throwable) {
                Log.e("TAG","NO DATA"+throwable.getLocalizedMessage());
                data.postValue(null);
            }
        });
        return data;
    }
}
