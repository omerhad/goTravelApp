package com.example.app1.ui;


import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.app1.Repository.TravelDataSource;
import com.example.app1.Repository.TravelRepository;

public class TravelViewModel extends ViewModel {
    String TAG = "elyasaf";
    TravelRepository travelrepositorye;
    //Context r = getApplication();
    public TravelViewModel() {
        travelrepositorye=new TravelRepository();
    }

    LiveData<Boolean> getIsSuccess() {
        return travelrepositorye.getIsSuccess();
    }
}

