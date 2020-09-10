package com.myfarmnow.myfarmcrop.models.farmrecords;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Crop implements Serializable, CropSpinnerItem {
    @PrimaryKey(autoGenerate = true)
   public int id;
   public String crop;
   public String variety;
   public String field;
   public String field_id;
   public  String season;
   public  String planting_date;
   public  String field_size;
   public String units;
   public String estimated_yield;
   public String estimated_revenue;
   public int userId;


    public Crop(JSONObject object) throws JSONException {
        setUserId(Integer.parseInt(object.getString("userId")) );
        setGlobalId(object.getString("id"));
        setField_id(object.getString("fieldId"));
        setPlanting_date(object.getString("dateSown"));
        setVariety(object.getString("variety"));
        setSeason(object.getString("season"));
        setFiels_size( object.getDouble("area")+"" );
        setUnits(object.getString("harvestUnits"));
        setEstimated_yield( object.getDouble("estimatedYield")+"" );
        setEstimated_revenue( object.getDouble("estimatedRevenue")+"" );
        setCrop(object.getString("name"));
        setSyncStatus("yes");
    }

    public Crop(String crop, String variety, String field, String field_id, String season, String planting_date, String field_size, String units, String estimated_yield, String estimated_revenue) {
        this.crop = crop;
        this.variety = variety;
        this.field = field;
        this.field_id = field_id;
        this.season = season;
        this.planting_date = planting_date;
        this.field_size = field_size;
        this.units = units;
        this.estimated_yield = estimated_yield;
        this.estimated_revenue = estimated_revenue;
    }

    public Crop() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCrop() {
        return crop;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getField_id() {
        return field_id;
    }

    public void setField_id(String field_id) {
        this.field_id = field_id;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getPlanting_date() {
        return planting_date;
    }

    public void setPlanting_date(String planting_date) {
        this.planting_date = planting_date;
    }

    public String getFiels_size() {
        return field_size;
    }

    public void setFiels_size(String field_size) {
        this.field_size = field_size;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getEstimated_yield() {
        return estimated_yield;
    }

    public void setEstimated_yield(String estimated_yield) {
        this.estimated_yield = estimated_yield;
    }

    public String getEstimated_revenue() {
        return estimated_revenue;
    }

    public void setEstimated_revenue(String estimated_revenue) {
        this.estimated_revenue = estimated_revenue;
    }


    @Override
    public String getId() {
        return this.id+"";
    }

    private String syncStatus="no";
    private String globalId;
    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    public String getGlobalId() {
        return globalId;
    }

    public float computeEstimatedRevenueC(){
        float estimatedRevenueC= (Float.parseFloat(estimated_yield) * Float.parseFloat(estimated_revenue));
        return  estimatedRevenueC;

    }

    public JSONObject toJSON(){

        JSONObject object = new JSONObject();

        try {
            object.put("id",id);
            object.put("globalId",globalId);
            object.put("userId",userId);
            object.put("fieldId",field_id);
            object.put("dateSown",planting_date);
            object.put("variety",variety);
            object.put("season",season);
//            object.put("growingCycle",growingCycle);
//            object.put("croppingYear",croppingYear);
//            object.put("cost",cost);
//            object.put("operator",operator);
//            object.put("seedId",seedId);
//            object.put("rate",rate);
//            object.put("plantingMethod",plantingMethod);
            object.put("area",field_size);
            object.put("estimatedYield",estimated_yield);
            object.put("estimatedRevenue",estimated_revenue);
            object.put("harvestUnits",units);
            object.put("name",crop);
            object.put("syncStatus",syncStatus);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public String computeAge(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = new Date();
        try {

            convertedDate = dateFormat.parse(getPlanting_date());
            Calendar today = Calendar.getInstance();       // get calendar instance
            today.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
            today.set(Calendar.MINUTE, 0);                 // set minute in hour
            today.set(Calendar.SECOND, 0);                 // set second in minute
            today.set(Calendar.MILLISECOND, 0);

            long daysBetween = daysBetween(convertedDate,today.getTime());

            int months = (int)(daysBetween/30);
            int days =(int)(daysBetween%30);
            Log.d("DATES",getPlanting_date()+" "+convertedDate.toString()+" - "+today.getTime().toString()+" days = "+daysBetween);
            return months+"m "+days+"d";

        } catch (ParseException e) {
            Log.d("DATe",getPlanting_date());
            e.printStackTrace();
            return "0m 0d";

        }

    }
    public static long daysBetween(Date startDate, Date endDate) {
        Calendar sDate = getDatePart(startDate);
        Calendar eDate = getDatePart(endDate);

        long daysBetween = 0;
        while (sDate.before(eDate)) {
            sDate.add(Calendar.DAY_OF_MONTH,1);
            //Log.d("Day "+daysBetween,sDate.getTime().toString());
            daysBetween++;
        }
        return daysBetween;
    }
    public static Calendar getDatePart(Date date){
        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        cal.set(Calendar.MINUTE, 0);                 // set minute in hour
        cal.set(Calendar.SECOND, 0);                 // set second in minute
        cal.set(Calendar.MILLISECOND, 0);            // set millisecond in second

        return cal;                                  // return the date part
    }


}
