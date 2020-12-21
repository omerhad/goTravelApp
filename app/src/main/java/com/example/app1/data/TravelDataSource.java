package com.example.app1.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class TravelDataSource {


    private MutableLiveData<Boolean> isSuccess= new MutableLiveData<>();
    public MutableLiveData<Boolean> getIsSuccess() {
        return isSuccess;
    }





    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference travels = firebaseDatabase.getReference("ExistingTravels");

    public TravelDataSource() {
    }

//singelton
    private static TravelDataSource instance;

    public static TravelDataSource getInstance() {
        if (instance == null)
            instance = new TravelDataSource();
        return instance;
    }


    public void addTravel(Travel p) {
        p.setRequesType(Travel.RequestType.accepted);
        String id = travels.push().getKey();
        HashMap<String,Boolean> m=new HashMap<String, Boolean>();
        m.put("ww",true);
        p.setCompany(m);
        p.setTravelId(id);
        travels.child(id).setValue(p).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                isSuccess.setValue(true);
                //isSuccess.setValue(null);
            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                isSuccess.setValue(false);
                //isSuccess.setValue(null);
            }
        });
    }
}
