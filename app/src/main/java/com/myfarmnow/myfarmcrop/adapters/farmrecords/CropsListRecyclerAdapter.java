package com.myfarmnow.myfarmcrop.adapters.farmrecords;

import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.farmrecords.Crop;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;


import java.text.NumberFormat;
import java.util.ArrayList;

public class CropsListRecyclerAdapter extends RecyclerView.Adapter<CropsListRecyclerAdapter.CropCardViewHolder> {
    private static final String TAG = "RecyclerAdapter";
    ArrayList<Crop> cropsList;
    LayoutInflater mInflater;
    Context mContext;
    NavController navController;

    public CropsListRecyclerAdapter(ArrayList<Crop> inventoryList, Context context) {
        this.cropsList = inventoryList;
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @NonNull
    @Override
    public CropCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.crops_list_card, parent, false);
        return new CropCardViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CropCardViewHolder holder, int position) {
        Crop curCrop = cropsList.get(position);
//        CropInventorySeeds seeds = MyFarmDbHandlerSingleton.getHandlerInstance(mContext).getCropInventorySeed(curCrop.getName());
//        String seedUnits ="";
//        if(seeds != null){
//            seedUnits = seeds.getUsageUnits();
//        }
        holder.cropNameTxtView.setText(curCrop.getName());
        holder.cropFieldTextView.setText(curCrop.getFieldName());
        holder.cropVarietyTextView.setText(""+""+"(" + curCrop.getVariety() + ")");
        holder.datePlantedTxt.setText(CropSettingsSingleton.getInstance().convertToUserFormat(curCrop.getDateSown()));
        holder.estimatedRevenueTxt.setText(CropSettingsSingleton.getInstance().getCurrency()+" "+ NumberFormat.getInstance().format(curCrop.computeEstimatedRevenueC()));

        Log.d(TAG, "onBindViewHolder: "+curCrop.getId());

    }

    public void clearCropList(){
        cropsList.clear();
    }

    @Override
    public int getItemCount() {
        return cropsList.size();
    }

    public void addList(ArrayList<Crop> inventoryListToAdd) {
        int size = getItemCount();
        this.cropsList.addAll(inventoryListToAdd);
        Log.d("INITIAL SIZE", size + " " + this.cropsList.size());
        notifyDataSetChanged();
    }


    public void changeList(ArrayList<Crop> filteredList) {
        this.cropsList.clear();
        this.cropsList.addAll(filteredList);
        notifyDataSetChanged();
    }


    public class CropCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView  moreButton;
        TextView cropNameTxtView, cropVarietyTextView,  cropAge, croppingYearTxt, seasonTxt;

        TextView datePlantedTxt,plantingMethodTxt,estimatedRevenueTxt, harvestUnitsTxt, cropFieldTextView;

        LinearLayout activitiesBtn, notesBtn,galleryBtn;

        public CropCardViewHolder(View itemView) {
            super(itemView);
            cropNameTxtView = itemView.findViewById(R.id.txt_crop_card_name);
            cropFieldTextView = itemView.findViewById(R.id.txt_crop_card_field);
            cropVarietyTextView = itemView.findViewById(R.id.txt_crop_card_variety);
            datePlantedTxt = itemView.findViewById(R.id.txt_crop_card_actual_date_planted);
            croppingYearTxt = itemView.findViewById(R.id.txt_crop_card_cropping_year);
            seasonTxt = itemView.findViewById(R.id.txt_crop_card_season);
            cropAge = itemView.findViewById(R.id.txt_crop_card_age);
            estimatedRevenueTxt = itemView.findViewById(R.id.txt_crop_card_estimated_revenue);

            activitiesBtn = itemView.findViewById(R.id.btn_crop_activities);
            notesBtn = itemView.findViewById(R.id.btn_crop_notes);
            galleryBtn = itemView.findViewById(R.id.btn_crop_gallery);

            galleryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavController navController = Navigation.findNavController(v);
                    Crop crop = cropsList.get(getAdapterPosition());
                    Bundle bundle = new Bundle();
                    String cropId = crop.getId();
                    bundle.putString("cropId", cropId);
                    Log.w("cropId",cropId);
                    navController.navigate(R.id.action_cropListFragment_to_galleryViewFragment, bundle);

                }
            });


            activitiesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //start crop activities
                    NavController navController = Navigation.findNavController(v);

                        Crop crop = cropsList.get(getAdapterPosition());
                        Bundle bundle = new Bundle();
                        String cropId = crop.getId();
                        bundle.putString("cropId", cropId);

                        navController.navigate(R.id.action_cropListFragment_to_cropActivitiesListFragment, bundle);
//                    Crop crop = cropsList.get(getAdapterPosition());
//                    Intent showSpray = new Intent(mContext, CropActivitiesListActivity.class);
//                    showSpray.putExtra("cropId", crop.getId());
//                    mContext.startActivity(showSpray);

                }
            });
            notesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //navigate to notes fragment.
                    NavController navController = Navigation.findNavController(v);
                    Crop crop = cropsList.get(getAdapterPosition());
                    Bundle bundle = new Bundle();
                    bundle.putString("cropId", crop.getId());
                    Log.e("CropID",crop.getId());
                    navController.navigate(R.id.action_cropListFragment_to_cropsNotesListFragment,bundle);
                }
            });

            moreButton = itemView.findViewById(R.id.img_crop_card_more);
            //layout_batch_number txt_view_batch_lbl
            moreButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    NavController navController = Navigation.findNavController(v);
                    final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                    PopupMenu popup = new PopupMenu(wrapper, v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            if (item.getTitle().toString().equals(mContext.getString(R.string.label_delete))) {
                                final Crop crop = cropsList.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete " + crop.getName() + " crop?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCrop(crop.getId());
                                                cropsList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, null).show();
                            } else if (item.getTitle().toString().equals(mContext.getString(R.string.label_edit))) {
                                Crop crop = cropsList.get(getAdapterPosition());
                                //editCrop.putExtra("crop", crop);
                                Bundle bundle=new Bundle();
                                bundle.putSerializable("crop", crop);
                                navController.navigate(R.id.action_cropListFragment_to_addCropFragment,bundle);

                            }  else if (item.getTitle().toString().equals(mContext.getString(R.string.fertilizer_application_title))) {
                                Crop crop = cropsList.get(getAdapterPosition());
                                //navigate to fertilizer dialog fragment
                                Bundle bundle = new Bundle();
                                bundle.putString("cropId", crop.getId());
                                navController.navigate(R.id.action_cropListFragment_to_fertilizerApplicationFragment,bundle);
                            } else if (item.getTitle().toString().equals(mContext.getString(R.string.label_spray))) {
                                Crop crop = cropsList.get(getAdapterPosition());
                                //navigate to spraying dialog fragment
                                Bundle bundle = new Bundle();
                                bundle.putString("cropId", crop.getId());
                                navController.navigate(R.id.action_cropListFragment_to_cropSprayingFragment,bundle);
                            }

                            else if (item.getTitle().toString().equals(mContext.getString(R.string.label_harvest))){
                                Crop crop = cropsList.get(getAdapterPosition());
                                //navigate to harvest dialog fragment
                                Bundle bundle = new Bundle();
                                bundle.putString("cropId", crop.getId());
                                navController.navigate(R.id.action_cropListFragment_to_cropHarvestFragment,bundle);

                            }

                            return true;
                        }
                    });

                    //
                    popup.getMenu().add(R.string.label_cultivate).setVisible(false);
                    popup.getMenu().add(R.string.label_irrigation).setVisible(false);
                    popup.getMenu().add(R.string.label_transplanting).setVisible(false);
                    popup.getMenu().add(R.string.label_scouting).setVisible(false);
                    popup.getMenu().add(R.string.fertilizer_application_title);
                    popup.getMenu().add(R.string.label_spray);
                    popup.getMenu().add(R.string.label_harvest);
                    popup.getMenu().add(R.string.label_edit);
                    popup.getMenu().add(R.string.label_delete);
                    popup.show();

                }
            });


        }

        @Override
        public void onClick(View view) {

        }
    }
}
