package com.example.app1.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




@Entity
public class Travel {

    @NonNull
    @PrimaryKey
    private String travelId = "id";
    private String clientName;
    private String clientPhone;
    private String clientEmail;

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public void setTravelLocation(List<UserLocation> travelLocation) {
        this.travelLocation = travelLocation;
    }

    public void setRequesType(RequestType requesType) {
        this.requesType= requesType;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public void setCompany(HashMap<String, Boolean> company) {
        this.company = company;
    }

    @TypeConverters(UserLocationConverter.class)
    private   List<UserLocation> travelLocation;

    @TypeConverters(RequestType.class)
    private RequestType requesType;

    @TypeConverters(DateConverter.class)
    private Date travelDate;

    @TypeConverters(DateConverter.class)
    private Date arrivalDate;


    private HashMap<String, Boolean> company;


    @NonNull
    public String getTravelId() {
        return travelId;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public List<UserLocation> getTravelLocation() {
        return travelLocation;
    }

    public RequestType getRequesType() {
        return requesType;
    }

    public Date getTravelDate() {
        return travelDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public HashMap<String, Boolean> getCompany() {
        return company;
    }

    public Travel(List<UserLocation> dst) {
        this.arrivalDate=new Date(12/12/2020);
        this.clientEmail=" ";
        this.clientName=" ";
        this.clientPhone=" ";
        this.travelDate=new Date(12/12/2021);
        this.travelLocation= new ArrayList<>(dst);
    }

    public void setTravelId(String id) {
        this.travelId=id;
    }


    public static class DateConverter {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        @TypeConverter
        public Date fromTimestamp(String date) throws ParseException {
            return (date == null ? null : format.parse(date));
        }

        @TypeConverter
        public String dateToTimestamp(Date date) {
            return date == null ? null : format.format(date);
        }
    }



    public enum RequestType {
        sent("0"),
        accepted("1"),
        run(2),
        close(3),
        payed(4);

        public Integer code;
        private String str;

        private RequestType(String friendlyName){
            this.str = friendlyName;
        }

        RequestType(Integer value) {
            this.code = value;
        }
        public Integer getCode() {
            return code;
        }
        @TypeConverter
        public static RequestType getType(Integer numeral) {
            for (RequestType ds : values())
                if (ds.code.equals(numeral))
                    return ds;
            return null;
        }
        @TypeConverter
        public static Integer getTypeInt(RequestType requestType) {
            if (requestType != null)
                return requestType.code;
            return null;
        }

        @NonNull
        @Override
        public String toString() {
            return str;
        }
    }




    public static class CompanyConverter {
        @TypeConverter
        public HashMap<String, Boolean> fromString(String value) {
            if (value == null || value.isEmpty())
                return null;
            String[] mapString = value.split(","); //split map into array of (string,boolean) strings
            HashMap<String, Boolean> hashMap = new HashMap<>();
            for (String s1 : mapString) //for all (string,boolean) in the map string
            {
                if (!s1.isEmpty()) {//is empty maybe will needed because the last char in the string is ","
                    String[] s2 = s1.split(":"); //split (string,boolean) to company string and boolean string.
                    Boolean aBoolean = Boolean.parseBoolean(s2[1]);
                    hashMap.put(/*company string:*/s2[0], aBoolean);
                }
            }
            return hashMap;
        }

        @TypeConverter
        public String asString(HashMap<String, Boolean> map) {
            if (map == null)
                return null;
            StringBuilder mapString = new StringBuilder();
            for (Map.Entry<String, Boolean> entry : map.entrySet())
                mapString.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
            return mapString.toString();
        }
    }

    public static class UserLocationConverter {
        @TypeConverter
        public UserLocation fromString(String value) {
            if (value == null || value.equals(""))
                return null;
            double lat = Double.parseDouble(value.split(" ")[0]);
            double lang = Double.parseDouble(value.split(" ")[1]);
            return new UserLocation(lat, lang);
        }

        @TypeConverter
        public String asString(UserLocation warehouseUserLocation) {
            return warehouseUserLocation == null ? "" : warehouseUserLocation.getLat() + " " + warehouseUserLocation.getLon();
        }
    }
}
	
	