package com.example.app1.ui;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.app1.Repository.TravelRepository;
import com.example.app1.data.Travel;

public class TravelViewModel extends ViewModel {
    String TAG = "elyasaf";
    private TravelRepository travelrepositorye;
    //Context r = getApplication();
    public TravelViewModel() {
        travelrepositorye=new TravelRepository();
    }
    public void addTravel(Travel travel){
        travelrepositorye.addTravel(travel);
    }

    LiveData<Boolean> getIsSuccess() {
        return travelrepositorye.getIsSuccess();
    }
}

