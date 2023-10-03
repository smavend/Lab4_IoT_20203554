package com.example.lab4_appiot_20203554.viewModel;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lab4_appiot_20203554.entity.Result;

public class ResultViewModel extends ViewModel {
    MutableLiveData<Result> resultMagnet = new MutableLiveData<>();
    MutableLiveData<Result> resultAcel = new MutableLiveData<>();

    public MutableLiveData<Result> getResultMagnet() {
        return resultMagnet;
    }

    public MutableLiveData<Result> getResultAcel() {
        return resultAcel;
    }
}
