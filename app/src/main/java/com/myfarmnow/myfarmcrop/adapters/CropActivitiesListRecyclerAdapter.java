package com.myfarmnow.myfarmcrop.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropCultivationManagerActivity;
import com.myfarmnow.myfarmcrop.activities.CropCultivationsListActivity;
import com.myfarmnow.myfarmcrop.activities.CropFertilizerApplicationListActivity;
import com.myfarmnow.myfarmcrop.activities.CropFertilizerApplicationManagerActivity;
import com.myfarmnow.myfarmcrop.activities.CropSprayingListActivity;
import com.myfarmnow.myfarmcrop.activities.CropSprayingManagerActivity;
import com.myfarmnow.myfarmcrop.activities.CropsManagerActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.Crop;
import com.myfarmnow.myfarmcrop.models.CropActivity;
import com.myfarmnow.myfarmcrop.models.CropCultivation;
import com.myfarmnow.myfarmcrop.models.CropFertilizerApplication;
import com.myfarmnow.myfarmcrop.models.CropSpraying;

import java.text.NumberFormat;
import java.util.ArrayList;

public class CropActivitiesListRecyclerAdapter extends RecyclerView.Adapter< RecyclerView.ViewHolder> {
    ArrayList<CropActivity> cropsList;
    LayoutInflater mInflater;
    Context mContext;

    public CropActivitiesListRecyclerAdapter(ArrayList<CropActivity> inventoryList, Context context) {
        this.cropsList = inventoryList;
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==CropActivity.CROP_ACTIVITY_FERTILIZER_APPLICATION){
            View view = mInflater.inflate(R.layout.crop_fertilizer_application_list_card,parent,false);
            FertilizerApplicationViewHolder holder = new FertilizerApplicationViewHolder(view);
            return holder;
        }
        else if(viewType==CropActivity.CROP_ACTIVITY_CULTIVATE){
            View view = mInflater.inflate(R.layout.crop_cultivation_list_card,parent,false);
            CultivationViewHolder holder = new CultivationViewHolder(view);
            return holder;
        }
        else if(viewType==CropActivity.CROP_ACTIVITY_SPRAYING){
            View view = mInflater.inflate(R.layout.crop_spraying_list_card,parent,false);

            SprayingViewHolder holder = new SprayingViewHolder(view);
            return holder;
        }
        else {
            return null;
        }


    }

    @Override
    public void onBindViewHolder(@NonNull  final RecyclerView.ViewHolder holder, int position) {
        if(cropsList.get(position).getType()==CropActivity.CROP_ACTIVITY_FERTILIZER_APPLICATION){
            CropFertilizerApplication field = (CropFertilizerApplication)cropsList.get(position);
            final FertilizerApplicationViewHolder fertilizerApplicationHolder = (FertilizerApplicationViewHolder)holder;
            fertilizerApplicationHolder.costTextView.setText("UGX "+ NumberFormat.getInstance().format(field.getCost())); //TODO replace currency
            fertilizerApplicationHolder.rateTextView.setText(field.getRate()+"Kg/ha");
            fertilizerApplicationHolder.methodTextView.setText(field.getMethod());
            fertilizerApplicationHolder.operationTextView.setText(field.getFertilizerName());

            final ViewTreeObserver observer = fertilizerApplicationHolder.operationTextView.getViewTreeObserver();
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        fertilizerApplicationHolder.operationTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        fertilizerApplicationHolder.operationTextView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }

                    int containerHeight = fertilizerApplicationHolder.rateTextView.getHeight()+fertilizerApplicationHolder.operationTextView.getHeight()+fertilizerApplicationHolder.methodTextView.getHeight();
                    ViewGroup.LayoutParams params = fertilizerApplicationHolder.verticalLineView.getLayoutParams();
                    params.height = 3*containerHeight;
                    fertilizerApplicationHolder.verticalLineView.requestLayout();
                }
            });
        }
        else if(cropsList.get(position).getType()==CropActivity.CROP_ACTIVITY_CULTIVATE){
            CropCultivation field = (CropCultivation)cropsList.get(position);
            ((CultivationViewHolder)holder).operatorTextView.setText(field.getOperator());
            ((CultivationViewHolder)holder).costTextView.setText("UGX "+ NumberFormat.getInstance().format(field.getCost()));
            ((CultivationViewHolder)holder).operationTextView.setText(field.getOperation());
            ((CultivationViewHolder)holder).notesTextView.setText(field.getNotes());
            ((CultivationViewHolder)holder).dateTextView.setText(field.getDate());

            final ViewTreeObserver observer =  ((CultivationViewHolder)holder).notesTextView.getViewTreeObserver();
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        ((CultivationViewHolder)holder).notesTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        ((CultivationViewHolder)holder).notesTextView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }

                    int containerHeight =  ((CultivationViewHolder)holder).notesTextView.getHeight()+ 3*(((CultivationViewHolder)holder).costTextView.getHeight()+ ((CultivationViewHolder)holder).operatorTextView.getHeight());
                    ViewGroup.LayoutParams params =  ((CultivationViewHolder)holder).verticalLineView.getLayoutParams();
                    params.height = containerHeight;
                    Log.d("LENGTH",containerHeight+"");
                    ((CultivationViewHolder)holder).verticalLineView.requestLayout();


                }
            });
        } else if(cropsList.get(position).getType()==CropActivity.CROP_ACTIVITY_SPRAYING){
            final SprayingViewHolder sprayingViewHolder = (SprayingViewHolder)holder;
            CropSpraying field = (CropSpraying)cropsList.get(position);
            sprayingViewHolder.sprayNameTextView.setText(field.getSprayName());
            sprayingViewHolder.operatorTextView.setText(field.getOperator());
            sprayingViewHolder.rateTextView.setText(field.getRate()+"Kg/ha");
            sprayingViewHolder.windDirectionTextView.setText(field.getWindDirection());
            sprayingViewHolder.waterConditionTextView.setText(field.getWaterCondition());
            sprayingViewHolder.treatmentReasonTextView.setText(field.getTreatmentReason());

            final ViewTreeObserver observer = sprayingViewHolder.treatmentReasonTextView.getViewTreeObserver();
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        sprayingViewHolder.treatmentReasonTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        sprayingViewHolder.treatmentReasonTextView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }

                    int containerHeight = sprayingViewHolder.rateTextView.getHeight()+sprayingViewHolder.treatmentReasonTextView.getHeight()+sprayingViewHolder.waterConditionTextView.getHeight()+sprayingViewHolder.operatorTextView.getHeight()+sprayingViewHolder.operatorTextView.getHeight();
                    ViewGroup.LayoutParams params = sprayingViewHolder.verticalLineView.getLayoutParams();
                    params.height = containerHeight;
                    Log.d("LENGTH",containerHeight+"");
                    sprayingViewHolder.verticalLineView.requestLayout();


                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return cropsList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return cropsList.size();
    }

    public void addList(ArrayList<CropActivity> inventoryListToAdd) {
        int size = getItemCount();
        this.cropsList.addAll(inventoryListToAdd);
        notifyDataSetChanged();
    }

    public void addInventory(CropActivity sheepInventory) {
        this.cropsList.add(sheepInventory);
        notifyItemChanged(getItemCount());
    }

    public ArrayList<CropActivity> getCropsList() {

        return cropsList;
    }

    public void changeList(ArrayList<CropActivity> filteredList) {
        this.cropsList.clear();
        this.cropsList.addAll(filteredList);
        notifyDataSetChanged();
    }

    public class FertilizerApplicationViewHolder extends RecyclerView.ViewHolder{

        TextView dateTextView, operationTextView, methodTextView, costTextView, rateTextView;
        ImageView editButton, moreButton;
        View verticalLineView;
        public FertilizerApplicationViewHolder(View itemView) {
            super(itemView);
            operationTextView = itemView.findViewById(R.id.txt_view_crop_fertilizer_application_card_operation);
            methodTextView = itemView.findViewById(R.id.txt_view_crop_fertilizer_application_card_method);
            dateTextView = itemView.findViewById(R.id.txt_view_crop_fertilizer_application_card_date);
            verticalLineView = itemView.findViewById(R.id.txt_view_crop_fertilizer_application_card_line);

            costTextView = itemView.findViewById(R.id.txt_view_crop_fertilizer_application_card_cost);
            rateTextView = itemView.findViewById(R.id.txt_view_crop_fertilizer_application_card_rate);

            moreButton = itemView.findViewById(R.id.img_crop_fertilizer_application_card_more);


            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                    PopupMenu popup = new PopupMenu(wrapper, v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getTitle().equals(mContext.getString(R.string.label_delete))){
                                final CropFertilizerApplication cropFertilizerApplication = (CropFertilizerApplication)cropsList.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete the fertilizer application done on "+cropFertilizerApplication.getDate()+"?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropFertilizerApplication(cropFertilizerApplication.getId());
                                                cropsList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();
                            }else if (item.getTitle().equals(mContext.getString(R.string.label_edit))){
                                CropFertilizerApplication cropFertilizerApplication = (CropFertilizerApplication)cropsList.get(getAdapterPosition());
                                Intent editFertilizerApplication = new Intent(mContext, CropFertilizerApplicationManagerActivity.class);
                                editFertilizerApplication.putExtra("fertilizerApplication",cropFertilizerApplication);
                                editFertilizerApplication.putExtra("cropId",cropFertilizerApplication.getCropId());
                                mContext.startActivity(editFertilizerApplication);
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

    public class CultivationViewHolder extends RecyclerView.ViewHolder{

        TextView dateTextView, notesTextView, operationTextView, methodTextView, operatorTextView, costTextView;
        ImageView editButton, deleteButton, moreButton;
        View verticalLineView;
        public CultivationViewHolder(View itemView) {
            super(itemView);
            notesTextView = itemView.findViewById(R.id.txt_view_crop_cultivation_card_notes);
            operationTextView = itemView.findViewById(R.id.txt_view_crop_cultivation_card_operation);
            dateTextView = itemView.findViewById(R.id.txt_view_crop_cultivation_card_date);
            verticalLineView = itemView.findViewById(R.id.txt_view_crop_cultivation_card_line);

            operatorTextView = itemView.findViewById(R.id.txt_view_crop_cultivation_card_operator);
            costTextView = itemView.findViewById(R.id.txt_view_crop_cultivation_card_cost);

            moreButton = itemView.findViewById(R.id.img_crop_cultivation_card_more);

            moreButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                    PopupMenu popup = new PopupMenu(wrapper, v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            if (item.getTitle().toString().equals(mContext.getString(R.string.label_delete))) {
                                final CropCultivation cropCultivation = (CropCultivation)cropsList.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete " + cropCultivation.getOperation() + " operation?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropCultivate(cropCultivation.getId());
                                                cropsList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, null).show();
                            } else if (item.getTitle().toString().equals(mContext.getString(R.string.label_edit))) {
                                CropCultivation cropCultivation = (CropCultivation)cropsList.get(getAdapterPosition());
                                Intent editCropCultivation = new Intent(mContext, CropCultivationManagerActivity.class);
                                editCropCultivation.putExtra("cropCultivation", cropCultivation);
                                mContext.startActivity(editCropCultivation);

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


    public class SprayingViewHolder extends RecyclerView.ViewHolder{

        TextView dateTextView,waterConditionTextView,windDirectionTextView, treatmentReasonTextView, operatorTextView, rateTextView, sprayNameTextView;
        ImageView moreButton, deleteButton,soilAnalysisButton;
        View verticalLineView;
        public SprayingViewHolder(View itemView) {
            super(itemView);
            waterConditionTextView = itemView.findViewById(R.id.txt_view_crop_spraying_card_water_condition);
            windDirectionTextView = itemView.findViewById(R.id.txt_view_crop_spraying_card_wind_direction);
            dateTextView = itemView.findViewById(R.id.txt_view_crop_spraying_card_date);
            verticalLineView = itemView.findViewById(R.id.txt_view_crop_spraying_card_line);
            treatmentReasonTextView = itemView.findViewById(R.id.txt_view_crop_spraying_card_reason);
            operatorTextView = itemView.findViewById(R.id.txt_view_crop_spraying_card_operator);
            rateTextView = itemView.findViewById(R.id.txt_view_crop_spraying_card_rate);
            sprayNameTextView = itemView.findViewById(R.id.txt_view_crop_spraying_card_spray_name);

            moreButton = itemView.findViewById(R.id.img_crop_spraying_card_more);



            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                    PopupMenu popup = new PopupMenu(wrapper, v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getTitle().equals(mContext.getString(R.string.label_delete))){
                                final CropSpraying cropSpraying = (CropSpraying)cropsList.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete the spraying on "+cropSpraying.getDate()+"?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropSpraying(cropSpraying.getId());
                                                cropsList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();
                            }else if (item.getTitle().equals(mContext.getString(R.string.label_edit))){

                                CropSpraying cropSpraying =(CropSpraying) cropsList.get(getAdapterPosition());
                                Intent editSpraying = new Intent(mContext, CropSprayingManagerActivity.class);
                                editSpraying.putExtra("cropSpraying",cropSpraying);
                                editSpraying.putExtra("cropId",cropSpraying.getCropId());
                                mContext.startActivity(editSpraying);
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
