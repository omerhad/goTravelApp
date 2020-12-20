package com.example.app1.Repository;

import androidx.lifecycle.MutableLiveData;

import com.example.app1.data.Travel;
import com.example.app1.data.TravelDataSource;

public class TravelRepository {


    private TravelDataSource travelDataSource;
    public TravelRepository()
    {
        travelDataSource =new TravelDataSource();
    }

    public MutableLiveData<Boolean> getIsSuccess() {
        return travelDataSource.getIsSuccess();
    }

    public void addTravel(Travel travel) {
        travelDataSource.addTravel(travel);
    }
}