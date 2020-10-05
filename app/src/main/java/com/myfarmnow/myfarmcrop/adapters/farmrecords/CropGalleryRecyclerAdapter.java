package com.myfarmnow.myfarmcrop.adapters.farmrecords;

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
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropNote;
import com.myfarmnow.myfarmcrop.models.farmrecords.CropGallery;
import com.myfarmnow.myfarmcrop.models.livestock_models.Medication;

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
        layoutInflater = LayoutInflater.from(context);
        mContext =context;

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
    public void addList(ArrayList<CropGallery> cropGalleries){
        this.cropGalleries.clear();
        this.cropGalleries.addAll(cropGalleries);
        notifyDataSetChanged();
    }

    public void clearGalleryList(){
        cropGalleries.clear();
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
        ImageView photo,moreOptions;
        public CropGalleryViewHolder(View itemView) {
            super(itemView);

        caption = itemView.findViewById(R.id.card_view_image_caption);
        photo = itemView.findViewById(R.id.card_view_image);
        moreOptions = itemView.findViewById(R.id.gallery_item_more);
        moreOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                PopupMenu popup = new PopupMenu(wrapper, v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        NavController navController = Navigation.findNavController(v);
                        if (item.getTitle().toString().equals(mContext.getString(R.string.label_delete))){
                            final CropGallery cropGallery = cropGalleries.get(getAdapterPosition());
                            new AlertDialog.Builder(mContext)
                                    .setTitle("Confirm")
                                    .setMessage("Do you really want to delete this image?")
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int whichButton) {

                                            MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropGallery(""+cropGallery.getId());
                                            cropGalleries.remove(getAdapterPosition());
                                            notifyItemRemoved(getAdapterPosition());

                                        }})
                                    .setNegativeButton(android.R.string.no, null).show();
                        }else if (item.getTitle().toString().equals(mContext.getString(R.string.label_edit))){
                            //edit functionality
                            CropGallery cropGallery = cropGalleries.get(getAdapterPosition());
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("cropGallery", cropGallery);
                            bundle.putString("cropId",cropId);
                            navController.navigate(R.id.action_galleryViewFragment_to_addPhotoInGalleryFragment,bundle);



                        }


                        return true;
                    }
                });
                popup.getMenu().add(R.string.label_edit);
                popup.getMenu().add(R.string.label_delete);
                popup.show();

            }
        });



        }
    }
}
