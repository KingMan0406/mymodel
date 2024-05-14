package com.example.mymodel.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymodel.models.ModelM;
import com.example.mymodel.repositories.Repository;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private Repository repository;
    private LiveData<List<ModelM>> modelMResponseLiveData;
    public HomeViewModel() {
      repository =new Repository();
      modelMResponseLiveData = repository.getDashJemList();
    }

    public LiveData<List<ModelM>> getModelMResponseLiveData() {
        return modelMResponseLiveData;
    }
}