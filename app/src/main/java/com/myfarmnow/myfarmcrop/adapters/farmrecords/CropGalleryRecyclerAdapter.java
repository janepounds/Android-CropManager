package com.myfarmnow.myfarmcrop.adapters.farmrecords;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropNote;
import com.myfarmnow.myfarmcrop.models.farmrecords.CropGallery;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class CropGalleryRecyclerAdapter extends RecyclerView.Adapter<CropGalleryRecyclerAdapter.CropGalleryViewHolder>{
    LayoutInflater layoutInflater;
    Context mContext;
    String cropId;
    ArrayList<CropGallery> cropGalleries = new ArrayList<>();


    public CropGalleryRecyclerAdapter(Context context, String cropId, ArrayList<CropGallery> cropGalleries){
        cropGalleries.addAll(cropGalleries);
        this.cropId=cropId;
        layoutInflater = LayoutInflater.from(mContext);

    }


    @NonNull
    @Override
    public CropGalleryRecyclerAdapter.CropGalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.gallery_list_card,parent,false);

        CropGalleryRecyclerAdapter.CropGalleryViewHolder holder = new CropGalleryRecyclerAdapter.CropGalleryViewHolder(view);
        return holder;
    }
    public void appendList(ArrayList<CropGallery> cropGalleries){

        this.cropGalleries.addAll(cropGalleries);
        notifyDataSetChanged();
    }
    public void addCropsGalleries(CropGallery cropGallery){
        this.cropGalleries.add(cropGallery);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<CropGallery> cropGalleries){

        this.cropGalleries.clear();
        this.cropGalleries.addAll(cropGalleries);

        notifyDataSetChanged();


    }


    @Override
    public void onBindViewHolder(@NonNull CropGalleryRecyclerAdapter.CropGalleryViewHolder holder, int position) {
        CropGallery cropGallery = cropGalleries.get(position);
        holder.caption.setText(cropGallery.getCaption());
        //set image
        if(cropGallery.getPhoto()!=null){
            byte[] decodedString = Base64.decode(cropGallery.getPhoto(),Base64.NO_WRAP);
            InputStream inputStream  = new ByteArrayInputStream(decodedString);
            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
            holder.photo.setImageBitmap(bitmap);
        }


    }

    @Override
    public int getItemCount() {
        return cropGalleries.size();
    }

    public class CropGalleryViewHolder extends RecyclerView.ViewHolder{
        TextView caption;
        ImageView photo;
        public CropGalleryViewHolder(View itemView) {
            super(itemView);

        caption = itemView.findViewById(R.id.card_view_image_caption);
        photo = itemView.findViewById(R.id.card_view_image);



        }
    }
}
