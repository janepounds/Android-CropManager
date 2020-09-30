package com.myfarmnow.myfarmcrop.adapters.livestockrecords;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.models.livestock_models.BreedingStock;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class BreedingStockListAdapter extends RecyclerView.Adapter<BreedingStockListAdapter.BreedingStockViewHolder> {
    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<BreedingStock> breedingStocks = new ArrayList<>();

    public BreedingStockListAdapter(Context context, List<BreedingStock> breedingStocks){
        breedingStocks.addAll(breedingStocks);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);

        Log.d("BREEDING STOCKS",breedingStocks.size()+" ");
    }
    @NonNull
    @Override
    public BreedingStockListAdapter.BreedingStockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.breeding_stock_list_item,parent,false);

        BreedingStockViewHolder holder = new BreedingStockViewHolder(view);
        return holder;
    }

    public void appendList(ArrayList<BreedingStock> breedingStocks){

        this.breedingStocks.addAll(breedingStocks);
        notifyDataSetChanged();
    }

    public void addBreedingStock(BreedingStock breedingStock){
        this.breedingStocks.add(breedingStock);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<BreedingStock> breedingStocks){

        this.breedingStocks.clear();
        this.breedingStocks.addAll(breedingStocks);

        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull final BreedingStockListAdapter.BreedingStockViewHolder holder, int position) {

        BreedingStock breedingStock = breedingStocks.get(position);
        holder.breedingStockNameTextView.setText(breedingStock.getName());
        holder.earTagtextView.setText(breedingStock.getEarTag());
        holder.colorTextView.setText(breedingStock.getColor());
        holder.breedTextView.setText(breedingStock.getBreed());
        holder.dobTextView.setText(breedingStock.getDateOfBirth());
        holder.weightTextView.setText(breedingStock.getWeight() + " KG");

        //set image
        byte[] decodedString = Base64.decode(breedingStock.getPhoto(),Base64.NO_WRAP);
        InputStream inputStream  = new ByteArrayInputStream(decodedString);
        Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
        holder.pictureImageView.setImageBitmap(bitmap);

        try {
            computeAge(breedingStock.getDateOfBirth(),holder);
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }



    @Override
    public int getItemCount() {
        return breedingStocks.size();
    }


    public class BreedingStockViewHolder extends RecyclerView.ViewHolder{

        TextView breedingStockNameTextView,earTagtextView,colorTextView,breedTextView,dobTextView,weightTextView,ageTextView;
        ImageView pictureImageView;

        public BreedingStockViewHolder(View itemView) {
            super(itemView);
            breedingStockNameTextView = itemView.findViewById(R.id.breeding_stock_item_name);
            earTagtextView = itemView.findViewById(R.id.breeding_stock_item_eartag);
            colorTextView = itemView.findViewById(R.id.breeding_stock_item_colour);
            breedTextView = itemView.findViewById(R.id.breeding_stock_item_breed);
            dobTextView = itemView.findViewById(R.id.breeding_stock_item_dob);
            pictureImageView = itemView.findViewById(R.id.breeding_stock_item_image);
            weightTextView = itemView.findViewById(R.id.breeding_stock_item_weight);
            ageTextView = itemView.findViewById(R.id.breeding_stock_item_age);





        }


    }

    public void computeAge(String dob, BreedingStockViewHolder holder) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = new Date();
        try {

            convertedDate = dateFormat.parse(dob);
            Calendar today = Calendar.getInstance();       // get calendar instance
            today.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
            today.set(Calendar.MINUTE, 0);                 // set minute in hour
            today.set(Calendar.SECOND, 0);                 // set second in minute
            today.set(Calendar.MILLISECOND, 0);

            long daysBetween = daysBetween(convertedDate,today.getTime());
            int years = (int)(daysBetween/365);
            int months = (int)(daysBetween/30);
            int days =(int)(daysBetween%30);
            Log.d("DATES",dob+" "+convertedDate.toString()+" - "+today.getTime().toString()+" days = "+daysBetween);
            String age = years+ "Y" + months+"M "+days+"D";
            holder.ageTextView.setText(age);


        } catch (ParseException e) {
            Log.d("DATe",dob);
            e.printStackTrace();


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
