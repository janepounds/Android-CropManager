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
import com.myfarmnow.myfarmcrop.models.livestock_models.Mating;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MatingListAdapter extends  RecyclerView.Adapter<MatingListAdapter.MatingViewHolder> {
    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<Mating> matings = new ArrayList<>();

    public MatingListAdapter(Context context, List<Mating> matings){
        matings.addAll(matings);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);

        Log.d("MATINGS",matings.size()+" ");
    }
    @NonNull
    @Override
    public MatingListAdapter.MatingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.mating_list_item, parent,false);

        MatingViewHolder holder = new MatingViewHolder(view);
        return holder;
    }

    public void appendList(ArrayList<Mating> matings){

        this.matings.addAll(matings);
        notifyDataSetChanged();
    }

    public void addMating(Mating mating){
        this.matings.add(mating);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<Mating> matings){

        this.matings.clear();
        this.matings.addAll(matings);
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull final MatingListAdapter.MatingViewHolder holder, int position) {

        Mating mating = matings.get(position);
        holder.femaleNameTextView.setText(mating.getFemaleName());
        holder.maleNameTextView.setText(mating.getMaleName());
        holder.methodTextView.setText(mating.getMethod());

        computeDeliveryDate(mating.getMatingDate(),mating.getGestationPeriod(),holder);





    }



    @Override
    public int getItemCount() {
        return matings.size();
    }


    public class MatingViewHolder extends RecyclerView.ViewHolder{

        TextView femaleNameTextView,maleNameTextView,statusTextView,expectedDeliveryTextView,methodTextView;
        ImageView pictureImageView;

        public MatingViewHolder(View itemView) {
            super(itemView);
            femaleNameTextView = itemView.findViewById(R.id.mating_item_female);
            maleNameTextView = itemView.findViewById(R.id.mating_item_male);
            statusTextView = itemView.findViewById(R.id.mating_item_status);
            expectedDeliveryTextView = itemView.findViewById(R.id.mating_item_expected_delivery_date);
            methodTextView = itemView.findViewById(R.id.mating_item_method);






        }


    }

    public void computeDeliveryDate(String matingDate,float gestationPeriod,MatingViewHolder holder){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = new Date();
        try {

            convertedDate = dateFormat.parse(matingDate);
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH,((int)gestationPeriod));
            String newDate = dateFormat.format(c.getTime());
            holder.expectedDeliveryTextView.setText(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }



    }



}
