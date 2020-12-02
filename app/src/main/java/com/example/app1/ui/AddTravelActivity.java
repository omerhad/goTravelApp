package com.example.app1.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProviders;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.app1.R;
import com.example.app1.data.Travel;
import com.example.app1.data.UserLocation;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Date;

public class AddTravelActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private TravelViewModel travelViewModel;

    private Travel travel;
    private TextInputLayout name;
    private TextInputLayout phone;
    private TextInputLayout email;
    private TextInputLayout let;
    private TextInputLayout lon;
    private Spinner status;
    //private TravelDataSource traveldatasource;
   //
    private Date start;
    private Date End;
    Button dateStart;
    Button dateStop;
    ImageButton goTravel;
    Calendar c1=Calendar.getInstance();
    Calendar c2=Calendar.getInstance();
    DatePickerDialog d1;
    DatePickerDialog d2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        //TODO its a diffrent version
        travelViewModel  = new TravelViewModel();
       String TAG = this.getClass().getSimpleName();
        final MutableLiveData<Boolean> success= (MutableLiveData<Boolean>) travelViewModel.getIsSuccess();
        success.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Toast.makeText(AddTravelActivity.this, "The location Added Successfully", Toast.LENGTH_LONG).show();
            }
                    });

//
//        status = (Spinner)findViewById(R.id.status);
//        status.setOnItemClickListener(this);
//        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item,Travel.RequestType.values());
//        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        status.setAdapter(aa);
        travel = new Travel();

        name= findViewById(R.id.name1);
        phone=findViewById(R.id.phone1);
        email=findViewById(R.id.email1);
        let=findViewById(R.id.let1);
        lon=findViewById(R.id.lon1);
        goTravel=(ImageButton)findViewById(R.id.gotravel);
        dateStart=(Button)findViewById(R.id.date_source);
        dateStop=(Button)findViewById(R.id.date_destination);
        dateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c1=Calendar.getInstance();
                int day =c1.get(Calendar.DAY_OF_MONTH);
                int month = c1.get(Calendar.MONTH);
                int year = c1.get(Calendar.YEAR);
                d1= new DatePickerDialog(AddTravelActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                        dateStart.setText(mDay+"/"+(mMonth+1)+"/"+(mYear));

                        start=new Date(mDay,(mMonth+1),mYear);
                    }
                }
                ,year,month,day);
                d1.show();
            }
        });
        dateStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c2=Calendar.getInstance();
                int day =c2.get(Calendar.DAY_OF_MONTH);
                int month = c2.get(Calendar.MONTH);
                int year = c2.get(Calendar.YEAR);
                d2= new DatePickerDialog(AddTravelActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                        dateStop.setText(mDay+"/"+(mMonth+1)+"/"+mYear);
                        End=new Date(mDay,(mMonth+1),mYear);
                    }
                }
                        ,year,month,day);
                d2.show();
            }
        });




        goTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validEmail() | !validLet() | !validLon() | !validName() | !validPhone()){
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        AddTravelActivity.this);
                builder.setTitle("Hello "+ name.getEditText().getText().toString());
                builder.setMessage("Are you sure you want this trip?");
                builder.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Toast.makeText(getApplicationContext(),"No is clicked",Toast.LENGTH_LONG).show();
                            }
                        });
                builder.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Toast.makeText(getApplicationContext(),"Yes is clicked",Toast.LENGTH_LONG).show();
                                travel.setClientName(name.getEditText().getText().toString().trim());
                                travel.setClientPhone(phone.getEditText().getText().toString().trim());
                                travel.setClientEmail(email.getEditText().getText().toString().trim());
                                travel.setTravelLocation(new UserLocation(Double.parseDouble(let.getEditText().getText().toString().trim()),Double.parseDouble(lon.getEditText().getText().toString().trim())));
                                // travel.setRequesType(1);
                                travel.setTravelDate(start);
                                travel.setArrivalDate(End);
                                travelViewModel.travelrepositorye.addTravel(travel);
                                name.getEditText().getText().clear();
                                phone.getEditText().getText().clear();
                                email.getEditText().getText().clear();
                                lon.getEditText().getText().clear();
                                let.getEditText().getText().clear();
////
                            }
                        });
                builder.setNeutralButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Toast.makeText(getApplicationContext(),"Cancel is clicked",Toast.LENGTH_LONG).show();
                            }
                        });

                builder.show();

            }
        });
    }

private boolean validEmail(){
        String emailInput=email.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()){
            email.setError("Field can't be empty");
            return false;
        }else {
            email.setError(null);
            return true;
        }
    }


    private boolean validName(){
        String nameInput=name.getEditText().getText().toString().trim();
        if (nameInput.isEmpty()){
            name.setError("Field can't be empty");
            return false;
        }else {
            name.setError(null);
            return true;
        }
    }

    private boolean validLon(){
        String lonInput=lon.getEditText().getText().toString().trim();
        if (lonInput.isEmpty()){
            lon.setError("Field can't be empty");
            return false;
        }else {
            lon.setError(null);
            return true;
        }
    }

    private boolean validLet(){
        String letInput=let.getEditText().getText().toString().trim();
        if (letInput.isEmpty()){
            let.setError("Field can't be empty");
            return false;
        }else {
            let.setError(null);
            return true;
        }
    }

    private boolean validPhone(){
        String phoneInput=phone.getEditText().getText().toString().trim();
        if (phoneInput.isEmpty()){
            phone.setError("Field can't be empty");
            return false;
        }else
            if(phoneInput.length()>10) {
                phone.setError("phone too long");
                return false;
            }
            else if(phoneInput.length()<10) {
                phone.setError("phone too short");
                return false;
            }else
                {
                phone.setError(null);
                return true;
            }
        }

    MutableLiveData<Boolean> getIsSuccess() {
        return travelViewModel.travelrepositorye.getIsSuccess();
    }
//
    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(),Travel.RequestType.getType(position).getCode(),Toast.LENGTH_LONG).show();
    }
}