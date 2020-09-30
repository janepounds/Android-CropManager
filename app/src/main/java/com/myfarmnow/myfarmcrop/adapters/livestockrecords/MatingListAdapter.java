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

        View view = layoutInflater.inflate(R.layout.breeding_stock_list_item, parent,false);

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




    }



    @Override
    public int getItemCount() {
        return matings.size();
    }


    public class MatingViewHolder extends RecyclerView.ViewHolder{

        TextView breedingStockNameTextView,earTagtextView,colorTextView,breedTextView,dobTextView,weightTextView,ageTextView;
        ImageView pictureImageView;

        public MatingViewHolder(View itemView) {
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



}
