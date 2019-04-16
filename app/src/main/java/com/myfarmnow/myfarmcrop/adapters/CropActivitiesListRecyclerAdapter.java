package com.myfarmnow.myfarmcrop.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropCultivationManagerActivity;

import com.myfarmnow.myfarmcrop.activities.CropFertilizerApplicationManagerActivity;
import com.myfarmnow.myfarmcrop.activities.CropHarvestManagerActivity;
import com.myfarmnow.myfarmcrop.activities.CropIrrigationManagerActivity;
import com.myfarmnow.myfarmcrop.activities.CropScoutingManagerActivity;

import com.myfarmnow.myfarmcrop.activities.CropSprayingManagerActivity;
import com.myfarmnow.myfarmcrop.activities.CropTransplantingManagerActivity;

import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropActivity;
import com.myfarmnow.myfarmcrop.models.CropCultivation;
import com.myfarmnow.myfarmcrop.models.CropFertilizerApplication;
import com.myfarmnow.myfarmcrop.models.CropHarvest;
import com.myfarmnow.myfarmcrop.models.CropIrrigation;
import com.myfarmnow.myfarmcrop.models.CropScouting;
import com.myfarmnow.myfarmcrop.models.CropSpraying;
import com.myfarmnow.myfarmcrop.models.CropTransplanting;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

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
        else if(viewType==CropActivity.CROP_ACTIVITY_SCOUTING){
            View view = mInflater.inflate(R.layout.crop_scouting_list_card,parent,false);

            ScoutingViewHolder holder = new ScoutingViewHolder(view);
            return holder;
        }else if(viewType==CropActivity.CROP_ACTIVITY_IRRIGATION){
            View view = mInflater.inflate(R.layout.crop_irrigation_list_card,parent,false);
            IrrigationViewHolder holder = new IrrigationViewHolder(view);
            return holder;
        }else if(viewType==CropActivity.CROP_ACTIVITY_TRANSPLANTING){
            View view = mInflater.inflate(R.layout.crop_transplanting_list_card,parent,false);
            TransplantingViewHolder holder = new TransplantingViewHolder(view);
            return holder;
        }else if(viewType==CropActivity.CROP_ACTIVITY_HARVESTING){
            View view = mInflater.inflate(R.layout.crop_harvest_list_card,parent,false);
            HarvestViewHolder holder = new HarvestViewHolder(view);
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
            fertilizerApplicationHolder.costTextView.setText(CropSettingsSingleton.getInstance().getCurrency()+ NumberFormat.getInstance().format(field.getCost())); //TODO replace currency
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

                    int containerHeight = (16+fertilizerApplicationHolder.rateTextView.getHeight())*7;
                    ViewGroup.LayoutParams params = fertilizerApplicationHolder.verticalLineView.getLayoutParams();
                    params.height = containerHeight;
                    fertilizerApplicationHolder.verticalLineView.requestLayout();
                }
            });
        }
        else if(cropsList.get(position).getType()==CropActivity.CROP_ACTIVITY_CULTIVATE){
            CropCultivation field = (CropCultivation)cropsList.get(position);
            ((CultivationViewHolder)holder).operatorTextView.setText(field.getOperator());
            ((CultivationViewHolder)holder).costTextView.setText(CropSettingsSingleton.getInstance().getCurrency()+ NumberFormat.getInstance().format(field.getCost()));
            ((CultivationViewHolder)holder).operationTextView.setText(field.getOperation());
            ((CultivationViewHolder)holder).notesTextView.setText(field.getNotes());
            ((CultivationViewHolder)holder).dateTextView.setText(CropSettingsSingleton.getInstance().convertToUserFormat(field.getDate()));

            final ViewTreeObserver observer =  ((CultivationViewHolder)holder).notesTextView.getViewTreeObserver();
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        ((CultivationViewHolder)holder).notesTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        ((CultivationViewHolder)holder).notesTextView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }

                    int containerHeight =  (16+((CultivationViewHolder)holder).operationTextView.getHeight())*7+((CultivationViewHolder)holder).notesTextView.getHeight();
                    ViewGroup.LayoutParams params =  ((CultivationViewHolder)holder).verticalLineView.getLayoutParams();
                    params.height = containerHeight;

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

                    int lineHeight = (16+sprayingViewHolder.rateTextView.getHeight())*8+sprayingViewHolder.treatmentReasonTextView.getHeight();
                    ViewGroup.LayoutParams params = sprayingViewHolder.verticalLineView.getLayoutParams();
                    params.height = lineHeight;
                    sprayingViewHolder.verticalLineView.requestLayout();
                }
            });
        } 
        else if(cropsList.get(position).getType()==CropActivity.CROP_ACTIVITY_SCOUTING){
            final ScoutingViewHolder scoutingViewHolder = (ScoutingViewHolder)holder;
            final CropScouting scouting = (CropScouting)cropsList.get(position);
            scoutingViewHolder.scoutingDateTxt.setText(CropSettingsSingleton.getInstance().convertToUserFormat(scouting.getDate()));
            scoutingViewHolder.scoutingMethodTxt.setText("("+scouting.getMethod()+")");
            scoutingViewHolder.infestationTypeTxt.setText(scouting.getInfestationType()+" : ");
            scoutingViewHolder.infestationTxt.setText(scouting.getInfestation());
            scoutingViewHolder.infestationLevelTxt.setText(scouting.getInfestationLevel());
            scoutingViewHolder.costTxt.setText(scouting.getCost()+"");
            // holder.remarksTxt.setText(scouting.getRemarks());

            //TODO MAKING DROP DOWN GONE WHEN REMARKS NOT ENTERED
            //TODO MAKING infestationLayout and infestationLevelLayout GONE WHEN CROP IS NOT INFESTED
            //TODO ADJUSTING THE SIDE LINE OF THE CARD
        
            if(scouting.getInfested().toLowerCase().equals("yes")) {

                TextView infestationTypeTxt = new TextView(mContext);
                infestationTypeTxt.setText(scouting.getInfestationType());
                TextView infestationTxt = new TextView(mContext);
                infestationTxt.setText(scouting.getInfestation());
                TextView infestationLevelTxt = new TextView(mContext);
                infestationLevelTxt.setText(scouting.getInfestationLevel());
                scoutingViewHolder.infestationLayout.setVisibility(View.VISIBLE);
                scoutingViewHolder.infestationLevelLayout.setVisibility(View.VISIBLE);
            }
            else{
                TextView infestationTypeTxt = new TextView(mContext);
                infestationTypeTxt.setText("Infested : ");
                TextView infestationTxt = new TextView(mContext);
                infestationTxt.setText("No");
                scoutingViewHolder.infestationLayout.setVisibility(View.VISIBLE);
            }


            if(scouting.getRemarks() != null){
                //holder.hideShowLayout.setVisibility(View.GONE);
                TextView remarksTxt = new TextView(mContext);
                remarksTxt.setText(scouting.getRemarks());
                View view = new View(mContext);
                view.setMinimumHeight(20);
                scoutingViewHolder.expandContentLayout.addView(remarksTxt);

                scoutingViewHolder.expandContentLayout.addView(view);
                scoutingViewHolder.hideShowLayout.setVisibility(View.VISIBLE);

            }

            scoutingViewHolder.hideShowLayout.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    if (scoutingViewHolder.expandContentLayout.getVisibility()==View.VISIBLE){
                        scoutingViewHolder.expandContentLayout.setVisibility(View.GONE);
                        scoutingViewHolder.showHideRemarksButton.setImageDrawable(mContext.getDrawable(R.drawable.arrow_drop_down));
                        ViewGroup.LayoutParams params = scoutingViewHolder.verticalLineView.getLayoutParams();
                        params.height = scoutingViewHolder.verticalLineView.getHeight()-scoutingViewHolder.expandContentLayout.getHeight();
                        scoutingViewHolder.verticalLineView.requestLayout();
                    }else{
                        scoutingViewHolder.expandContentLayout.setVisibility(View.VISIBLE);
                        scoutingViewHolder.showHideRemarksButton.setImageDrawable(mContext.getDrawable(R.drawable.arrow_drop_up));
                        ViewGroup.LayoutParams params = scoutingViewHolder.verticalLineView.getLayoutParams();
                        params.height = scoutingViewHolder.verticalLineView.getHeight()+scoutingViewHolder.expandContentLayout.getHeight();
                        scoutingViewHolder.verticalLineView.requestLayout();
                    }
                }
            });

            final ViewTreeObserver observer = scoutingViewHolder.costTxt.getViewTreeObserver();
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        scoutingViewHolder.costTxt.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        scoutingViewHolder.costTxt.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }

                    int lineHeight = (int)((18+scoutingViewHolder.costTxt.getHeight())*8.5);
                    if(scouting.getInfested().toLowerCase().equals("no")){
                        lineHeight=-(16+scoutingViewHolder.costTxt.getHeight());
                    }
                    ViewGroup.LayoutParams params = scoutingViewHolder.verticalLineView.getLayoutParams();
                    params.height = lineHeight;
                    scoutingViewHolder.verticalLineView.requestLayout();
                }
            });



        } else if(cropsList.get(position).getType()==CropActivity.CROP_ACTIVITY_IRRIGATION){
            final IrrigationViewHolder irrigationViewHolder = (IrrigationViewHolder)holder;
            CropIrrigation irrigation = (CropIrrigation)cropsList.get(position);
            irrigationViewHolder.operationDateTxt.setText(CropSettingsSingleton.getInstance().convertToUserFormat(irrigation.getOperationDate()));
            irrigationViewHolder.areaIrrigationTxt.setText(irrigation.getAreaIrrigated()+"");
            irrigationViewHolder.systemRateTxt.setText(irrigation.getSystemRate()+" l/hr");
            irrigationViewHolder.durationTxt.setText(irrigation.getDuration()+" hrs");
            irrigationViewHolder.totalWaterQuantity.setText(irrigation.computeWaterQuantity()+" ltrs");
            irrigationViewHolder.recurrenceTxt.setText(irrigation.getRecurrence());
            irrigationViewHolder.totalCostTxt.setText(irrigation.getTotalCost()+"");
        }else if(cropsList.get(position).getType()==CropActivity.CROP_ACTIVITY_TRANSPLANTING){
            final TransplantingViewHolder transplantingViewHolder = (TransplantingViewHolder)holder;
            CropTransplanting transplanting = (CropTransplanting)cropsList.get(position);
            transplantingViewHolder.operationDateTxt.setText(CropSettingsSingleton.getInstance().convertToUserFormat(transplanting.getOperationDate()));
            transplantingViewHolder.totalSeedlingsTxt.setText(transplanting.getTotalSeedling()+"");
            transplantingViewHolder.seedlingsPerHaTxt.setText(transplanting.getSeedlingPerHa()+"");
            transplantingViewHolder.varietyEarlinessTxt.setText(transplanting.getVarietyEarliness());
            transplantingViewHolder.harvestDueDateTxt.setText(CropSettingsSingleton.getInstance().convertToUserFormat(transplanting.getOperationDate()));
            transplantingViewHolder.expectedYieldTxt.setText(transplanting.getExpectedYield()+"");
            transplantingViewHolder.totalCostTxt.setText(transplanting.getTotalCost()+"");
        }else if(cropsList.get(position).getType()==CropActivity.CROP_ACTIVITY_HARVESTING){
            final HarvestViewHolder harvestViewHolder = (HarvestViewHolder)holder;
            CropHarvest harvest = (CropHarvest)cropsList.get(position);
            harvestViewHolder.harvestDateTxt.setText(CropSettingsSingleton.getInstance().convertToUserFormat(harvest.getDate()));
            harvestViewHolder.harvestMethodTxt.setText(harvest.getMethod());
            harvestViewHolder.quantityTxt.setText(harvest.getQuantity()+" ");
            harvestViewHolder.unitsTxt.setText(harvest.getUnits());
            harvestViewHolder.statusTxt.setText(harvest.getStatus());
            harvestViewHolder.costTxt.setText(harvest.getCost()+"");

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
                                        .setMessage("Do you really want to delete the fertilizer application done on "+CropSettingsSingleton.getInstance().convertToUserFormat(cropFertilizerApplication.getDate())+"?")
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
                                        .setMessage("Do you really want to delete the spraying on "+CropSettingsSingleton.getInstance().convertToUserFormat(cropSpraying.getDate())+"?")
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
    public class ScoutingViewHolder extends RecyclerView.ViewHolder{
        TextView scoutingDateTxt,scoutingMethodTxt,infestationTypeTxt,infestationTxt,infestationLevelTxt,costTxt,remarksTxt;
        ImageView moreButton,showHideRemarksButton;
        LinearLayout expandContentLayout,hideShowLayout, infestationLayout, infestationLevelLayout;
        View verticalLineView;

        public ScoutingViewHolder(View itemView) {
            super(itemView);
            scoutingDateTxt = itemView.findViewById(R.id.txt_view_crop_scouting_card_date);
            scoutingMethodTxt = itemView.findViewById(R.id.txt_view_crop_scouting_card_method);
            infestationTypeTxt = itemView.findViewById(R.id.txt_view_crop_scouting_card_infestation_type);
            infestationTxt = itemView.findViewById(R.id.txt_view_crop_scouting_card_infestation);
            infestationLevelTxt = itemView.findViewById(R.id.txt_view_crop_scouting_card_infestation_level);
            costTxt = itemView.findViewById(R.id.txt_view_crop_scouting_card_cost);
            verticalLineView = itemView.findViewById(R.id.txt_view_crop_scouting_card_line);

            hideShowLayout = itemView.findViewById(R.id.layout_crop_scouting_card_show_hide);
            expandContentLayout = itemView.findViewById(R.id.layout_crop_scouting_expand);
            infestationLayout = itemView.findViewById(R.id.layout_crop_scouting_card_infestation);
            infestationLevelLayout = itemView.findViewById(R.id.layout_crop_scouting_card_infestation_level);

            moreButton = itemView.findViewById(R.id.img_crop_scouting_card_more);
            showHideRemarksButton = itemView.findViewById(R.id.img_crop_scouting_card_show_crops);
            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                    PopupMenu popup = new PopupMenu(wrapper, v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            if (item.getTitle().toString().equals(mContext.getString(R.string.label_delete))){
                                final CropScouting cropScouting = (CropScouting)cropsList.get(getAdapterPosition());
                                new android.app.AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete this Scouting Record ?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropScouting(cropScouting.getId());
                                                cropsList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();
                            }else if (item.getTitle().toString().equals(mContext.getString(R.string.label_edit))){
                                CropScouting cropScouting = (CropScouting)cropsList.get(getAdapterPosition());
                                Intent editScouting = new Intent(mContext, CropScoutingManagerActivity.class);
                                editScouting.putExtra("cropScouting",cropScouting);
                                editScouting.putExtra("cropId",cropScouting.getCropId());
                                mContext.startActivity(editScouting);
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

    public class IrrigationViewHolder extends RecyclerView.ViewHolder {
        TextView operationDateTxt,areaIrrigationTxt,systemRateTxt,durationTxt,totalWaterQuantity,recurrenceTxt,totalCostTxt;
        ImageView moreButton ;

        public IrrigationViewHolder(View itemView) {
            super(itemView);
            operationDateTxt = itemView.findViewById(R.id.txt_view_crop_irrigation_card_operation_date);
            areaIrrigationTxt = itemView.findViewById(R.id.txt_crop_irrigation_card_irrigated_area);
            systemRateTxt = itemView.findViewById(R.id.txt_view_crop_irrigation_card_system_rate);
            durationTxt = itemView.findViewById(R.id.txt_view_crop_irrigation_card_duration);
            totalWaterQuantity = itemView.findViewById(R.id.txt_view_crop_irrigation_card_total_water_quantity);
            recurrenceTxt = itemView.findViewById(R.id.txt_view_crop_irrigation_card_recurrence);
            totalCostTxt = itemView.findViewById(R.id.txt_view_crop_irrigation_card_total_cost);


            moreButton = itemView.findViewById(R.id.img_crop_irrigation_card_more);



            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                    PopupMenu popup = new PopupMenu(wrapper, v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getTitle().equals(mContext.getString(R.string.label_delete))){
                                final CropIrrigation cropIrrigation = (CropIrrigation)cropsList.get(getAdapterPosition());
                                new android.app.AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete this irrigation card?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropIrrigation(cropIrrigation.getId());
                                                cropsList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();

                            }else if (item.getTitle().equals(mContext.getString(R.string.label_edit))){
                                CropIrrigation cropIrrigation = (CropIrrigation)cropsList.get(getAdapterPosition());
                                Intent editIrrigation = new Intent(mContext, CropIrrigationManagerActivity.class);
                                editIrrigation.putExtra("cropIrrigation",cropIrrigation);
                                mContext.startActivity(editIrrigation);
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


    public class TransplantingViewHolder extends RecyclerView.ViewHolder {
        TextView operationDateTxt,totalSeedlingsTxt,seedlingsPerHaTxt,expectedYieldTxt,totalCostTxt,varietyEarlinessTxt,harvestDueDateTxt;
        ImageView moreButton ;

        public TransplantingViewHolder(View itemView) {
            super(itemView);
            operationDateTxt = itemView.findViewById(R.id.txt_view_crop_transplanting_card_operation_date);
            totalSeedlingsTxt = itemView.findViewById(R.id.txt_view_crop_transplanting_card_total_seedlings);
            seedlingsPerHaTxt = itemView.findViewById(R.id.txt_view_crop_transplanting_card_seedlings_per_ha);
            varietyEarlinessTxt = itemView.findViewById(R.id.txt_view_crop_transplanting_card_variety_earliness);
            harvestDueDateTxt = itemView.findViewById(R.id.txt_view_crop_transplanting_card_operation_date);
            expectedYieldTxt = itemView.findViewById(R.id.txt_view_crop_transplanting_card_expected_yield);
            totalCostTxt = itemView.findViewById(R.id.txt_view_crop_transplanting_card_total_cost);
            moreButton = itemView.findViewById(R.id.img_crop_irrigation_card_more);



            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                    PopupMenu popup = new PopupMenu(wrapper, v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getTitle().equals(mContext.getString(R.string.label_delete))){
                                final CropTransplanting cropTransplanting =(CropTransplanting) cropsList.get(getAdapterPosition());
                                new android.app.AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete this Transplanting card?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropTransplanting(cropTransplanting.getId());
                                                cropsList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();

                            }else if (item.getTitle().equals(mContext.getString(R.string.label_edit))){
                                CropTransplanting cropTransplanting = (CropTransplanting)cropsList.get(getAdapterPosition());
                                Intent editTransplanting = new Intent(mContext, CropTransplantingManagerActivity.class);
                                editTransplanting.putExtra("cropTransplanting",cropTransplanting);
                                mContext.startActivity(editTransplanting);
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

    public class HarvestViewHolder extends RecyclerView.ViewHolder{
        TextView harvestDateTxt,harvestMethodTxt,quantityTxt,statusTxt,costTxt,unitsTxt;
        LinearLayout hideShowLayout,expandContentLayout;
        ImageView moreButton,showHideRemarksButton;

        public HarvestViewHolder(View itemView) {
            super(itemView);
            harvestDateTxt = itemView.findViewById(R.id.txt_view_crop_harvest_card_date);
            harvestMethodTxt = itemView.findViewById(R.id.txt_view_crop_harvest_card_method);
            quantityTxt = itemView.findViewById(R.id.txt_view_crop_harvest_card_quantity);
            statusTxt = itemView.findViewById(R.id.txt_view_crop_harvest_card_status);
            unitsTxt = itemView.findViewById(R.id.txt_view_crop_harvest_card_units);

            costTxt = itemView.findViewById(R.id.txt_view_crop_harvest_card_cost);

            hideShowLayout = itemView.findViewById(R.id.layout_crop_harvest_card_show_hide);
            expandContentLayout = itemView.findViewById(R.id.layout_crop_harvest_expand);

            moreButton = itemView.findViewById(R.id.img_crop_harvest_card_more);
            showHideRemarksButton = itemView.findViewById(R.id.img_crop_harvest_card_show_crops);
            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                    PopupMenu popup = new PopupMenu(wrapper, v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            if (item.getTitle().toString().equals(mContext.getString(R.string.label_delete))){
                                final CropHarvest cropHarvest = (CropHarvest)cropsList.get(getAdapterPosition());
                                new android.app.AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete this Harvest Record ?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropHarvest(cropHarvest.getId());
                                                cropsList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();
                            }else if (item.getTitle().toString().equals(mContext.getString(R.string.label_edit))){
                                CropHarvest cropHarvest = (CropHarvest)cropsList.get(getAdapterPosition());
                                Intent editHarvest = new Intent(mContext, CropHarvestManagerActivity.class);
                                editHarvest.putExtra("cropHarvest",cropHarvest);
                                editHarvest.putExtra("cropId",cropHarvest.getCropId());
                                mContext.startActivity(editHarvest);
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
