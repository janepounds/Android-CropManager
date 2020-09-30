package com.myfarmnow.myfarmcrop.adapters.farmrecords;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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

import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropActivity;
import com.myfarmnow.myfarmcrop.models.CropCultivation;
import com.myfarmnow.myfarmcrop.models.CropFertilizerApplication;
import com.myfarmnow.myfarmcrop.models.CropHarvest;
import com.myfarmnow.myfarmcrop.models.CropSpraying;
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
            CropFertilizerApplication fertilizerApplication = (CropFertilizerApplication)cropsList.get(position);
            final FertilizerApplicationViewHolder fertilizerApplicationHolder = (FertilizerApplicationViewHolder)holder;
            fertilizerApplicationHolder.costTextView.setText(CropSettingsSingleton.getInstance().getCurrency()+" "+ NumberFormat.getInstance().format(fertilizerApplication.getCost())); //TODO replace currency
            fertilizerApplicationHolder.dateTextView.setText(fertilizerApplication.getDate());

            fertilizerApplicationHolder.operationTextView.setText(fertilizerApplication.getFertilizerName());
            fertilizerApplicationHolder.recurrenceTxt.setText(fertilizerApplication.getRecurrence());
            fertilizerApplicationHolder.rateTextView.setText(fertilizerApplication.getRate()+" kg");
            final ViewTreeObserver observer = fertilizerApplicationHolder.operationTextView.getViewTreeObserver();
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        fertilizerApplicationHolder.operationTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        fertilizerApplicationHolder.operationTextView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }

                    int containerHeight = (32+fertilizerApplicationHolder.rateTextView.getHeight())*7;
                    ViewGroup.LayoutParams params = fertilizerApplicationHolder.verticalLineView.getLayoutParams();
                    params.height = containerHeight;
                    fertilizerApplicationHolder.verticalLineView.requestLayout();
                }
            });
        }
        else if(cropsList.get(position).getType()==CropActivity.CROP_ACTIVITY_CULTIVATE){
            CropCultivation field = (CropCultivation)cropsList.get(position);
            ((CultivationViewHolder)holder).operatorTextView.setText(field.getOperator());
            ((CultivationViewHolder)holder).costTextView.setText(CropSettingsSingleton.getInstance().getCurrency()+" "+ NumberFormat.getInstance().format(field.getCost()));
            ((CultivationViewHolder)holder).operationTextView.setText(field.getOperation());
            //((CultivationViewHolder)holder).notesTextView.setText(field.getNotes());
            ((CultivationViewHolder)holder).recurrenceTxt.setText(field.getRecurrence());
            ((CultivationViewHolder)holder).dateTextView.setText(CropSettingsSingleton.getInstance().convertToUserFormat(field.getDate()));


            TextView notesTextView = new TextView(mContext);
            notesTextView.setText("Notes : "+field.getNotes());
            View view = new View(mContext);
            view.setMinimumHeight(20);
            ((CultivationViewHolder) holder).expandContentLayout.removeAllViews();
            ((CultivationViewHolder) holder).expandContentLayout.addView(notesTextView);

            ((CultivationViewHolder)holder).expandContentLayout.addView(view);
            ((CultivationViewHolder)holder).hideShowLayout.setVisibility(View.VISIBLE);



            ((CultivationViewHolder)holder).hideShowLayout.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    if (((CultivationViewHolder)holder).expandContentLayout.getVisibility()==View.VISIBLE){
                        ((CultivationViewHolder)holder).expandContentLayout.setVisibility(View.GONE);
                        ((CultivationViewHolder)holder).showHideRemarksButton.setImageDrawable(mContext.getDrawable(R.drawable.arrow_drop_down));

                    }else{
                        ((CultivationViewHolder)holder).expandContentLayout.setVisibility(View.VISIBLE);
                        ((CultivationViewHolder)holder).showHideRemarksButton.setImageDrawable(mContext.getDrawable(R.drawable.arrow_drop_up));

                    }
                }
            });


            final ViewTreeObserver observer =  ((CultivationViewHolder)holder).notesTextView.getViewTreeObserver();
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        ((CultivationViewHolder)holder).notesTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        ((CultivationViewHolder)holder).notesTextView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }

                    int containerHeight =  (32+((CultivationViewHolder)holder).operationTextView.getHeight())*7+((CultivationViewHolder)holder).notesTextView.getHeight();
                    ViewGroup.LayoutParams params =  ((CultivationViewHolder)holder).verticalLineView.getLayoutParams();
                    params.height = containerHeight;

                    ((CultivationViewHolder)holder).verticalLineView.requestLayout();


                }
            });
        }
        else if(cropsList.get(position).getType()==CropActivity.CROP_ACTIVITY_SPRAYING){
            final SprayingViewHolder sprayingViewHolder = (SprayingViewHolder)holder;
            CropSpraying field = (CropSpraying)cropsList.get(position);
            sprayingViewHolder.dateTextView.setText(field.getDate());
            sprayingViewHolder.sprayNameTextView.setText(field.getSprayName());
            sprayingViewHolder.sprayType.setText(field.getSprayType());
            sprayingViewHolder.recurrenceTextView.setText(field.getRecurrence());
            sprayingViewHolder.rateTextView.setText(field.getRate()+" lt");
            sprayingViewHolder.treatmentReasonTextView.setText(field.getTreatmentReason());


                TextView treatmentReasonTextView = new TextView(mContext);
                treatmentReasonTextView.setText("Reason: "+field.getTreatmentReason());
                View view = new View(mContext);
                view.setMinimumHeight(20);

            final ViewTreeObserver observer = sprayingViewHolder.treatmentReasonTextView.getViewTreeObserver();
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        sprayingViewHolder.treatmentReasonTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        sprayingViewHolder.treatmentReasonTextView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }

                    int lineHeight = (36+sprayingViewHolder.rateTextView.getHeight())*9+sprayingViewHolder.treatmentReasonTextView.getHeight();
                    ViewGroup.LayoutParams params = sprayingViewHolder.verticalLineView.getLayoutParams();
                    params.height = lineHeight;
                    sprayingViewHolder.verticalLineView.requestLayout();
                }
            });
        }
        else if(cropsList.get(position).getType()==CropActivity.CROP_ACTIVITY_HARVESTING){
            final HarvestViewHolder harvestViewHolder = (HarvestViewHolder)holder;
            CropHarvest harvest = (CropHarvest)cropsList.get(position);
            harvestViewHolder.harvestDateTxt.setText(CropSettingsSingleton.getInstance().convertToUserFormat(harvest.getDate()));
            if(harvest.getDateSold()!=null)
                harvestViewHolder.harvest_date_sold.setText(harvest.getDateSold());
            else
                harvestViewHolder.harvest_date_sold.setVisibility(View.GONE);

            harvestViewHolder.quantityTxt.setText(harvest.getQuantity()+" ");
            harvestViewHolder.unitsTxt.setText(harvest.getUnits());
            harvestViewHolder.statusTxt.setText(harvest.getStatus());
//            harvestViewHolder.costTxt.setText(CropSettingsSingleton.getInstance().getCurrency()+" "+ NumberFormat.getInstance().format(harvest.getCost()));
            harvestViewHolder.incomeGeneratedTxt.setText(CropSettingsSingleton.getInstance().getCurrency()+" "+ NumberFormat.getInstance().format(harvest.computeIncomeGenerated()));

            if(harvest.getStatus().toLowerCase().equals("sold")) {

                TextView dateSoldTxt = new TextView(mContext);
                dateSoldTxt.setText("Date Sold:   "+harvest.getDateSold());
                TextView customerTxt = new TextView(mContext);
                customerTxt.setText("Customer:   "+harvest.getCustomer());
                TextView quantitySoldTxt = new TextView(mContext);
                quantitySoldTxt.setText("Quantity Sold:   "+harvest.getQuantitySold()+" "+harvest.getUnits());
                View view = new View(mContext);
                view.setMinimumHeight(20);
                harvestViewHolder.expandContentLayout.removeAllViews();
                harvestViewHolder.expandContentLayout.addView(dateSoldTxt);
                harvestViewHolder.expandContentLayout.addView(customerTxt);
                harvestViewHolder.expandContentLayout.addView(quantitySoldTxt);
                harvestViewHolder.hideShowLayout.setVisibility(View.VISIBLE);
            }
            else if(harvest.getStatus().toLowerCase().equals("stored")){
                harvestViewHolder.incomeGeneratedLayout.setVisibility(View.GONE);

                TextView storageDateTxt = new TextView(mContext);
                storageDateTxt.setText("Storage Date:   " +harvest.getStorageDate());
                TextView quantityStoredTxt = new TextView(mContext);
                quantityStoredTxt.setText("Quantity Stored:   "+harvest.getQuantityStored()+" "+harvest.getUnits());

                View view = new View(mContext);
                view.setMinimumHeight(20);
                harvestViewHolder.expandContentLayout.removeAllViews();
                harvestViewHolder.expandContentLayout.addView(storageDateTxt);
                harvestViewHolder.expandContentLayout.addView(quantityStoredTxt);

                harvestViewHolder.hideShowLayout.setVisibility(View.VISIBLE);
            }

//            final ViewTreeObserver observer = harvestViewHolder.costTxt.getViewTreeObserver();
//            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                @Override
//                public void onGlobalLayout() {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                        harvestViewHolder.costTxt.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                    } else {
//                        harvestViewHolder.costTxt.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                    }
//
//                    int lineHeight = (int)((36+harvestViewHolder.costTxt.getHeight())*10);
//
//
//
//                    ViewGroup.LayoutParams params = harvestViewHolder.verticalLineView.getLayoutParams();
//                    params.height = lineHeight;
//                    harvestViewHolder.verticalLineView.requestLayout();
//
//
//                }
//            });


            harvestViewHolder.hideShowLayout.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    if (harvestViewHolder.expandContentLayout.getVisibility()==View.VISIBLE){
                        harvestViewHolder.expandContentLayout.setVisibility(View.GONE);
                        harvestViewHolder.showHideRemarksButton.setImageDrawable(mContext.getDrawable(R.drawable.arrow_drop_down));
                        ViewGroup.LayoutParams params = harvestViewHolder.verticalLineView.getLayoutParams();
                        params.height = params.height- harvestViewHolder.expandContentLayout.getHeight();
                        harvestViewHolder.verticalLineView.requestLayout();

                    }else{
                        harvestViewHolder.expandContentLayout.setVisibility(View.VISIBLE);
                        harvestViewHolder.showHideRemarksButton.setImageDrawable(mContext.getDrawable(R.drawable.arrow_drop_up));
                        ViewGroup.LayoutParams params = harvestViewHolder.verticalLineView.getLayoutParams();
                        params.height = params.height+(36+harvestViewHolder.expandContentLayout.getHeight());
                        harvestViewHolder.verticalLineView.requestLayout();

                    }
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

        TextView dateTextView, operationTextView, costTextView, rateTextView,recurrenceTxt;
        ImageView editButton, moreButton;
        View verticalLineView;
        public FertilizerApplicationViewHolder(View itemView) {
            super(itemView);
            operationTextView = itemView.findViewById(R.id.txt_view_crop_fertilizer_application_card_operation);
            dateTextView = itemView.findViewById(R.id.txt_view_crop_fertilizer_application_card_date);
            verticalLineView = itemView.findViewById(R.id.txt_view_crop_fertilizer_application_card_line);
            recurrenceTxt = itemView.findViewById(R.id.txt_view_crop_fertilizer_application_card_recurrence);

            costTextView = itemView.findViewById(R.id.txt_view_crop_fertilizer_application_card_cost);
            rateTextView = itemView.findViewById(R.id.txt_view_crop_fertilizer_application_card_rate);

            moreButton = itemView.findViewById(R.id.img_crop_fertilizer_application_card_more);


            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavController navController = Navigation.findNavController(v);
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
                                CropFertilizerApplication cropFertilizerApplication = (CropFertilizerApplication) cropsList.get(getAdapterPosition());
                                Bundle bundle=new Bundle();
                                bundle.putSerializable("fertilizerApplication", cropFertilizerApplication);
                                bundle.putString("cropId",cropFertilizerApplication.getCropId());
                                navController.navigate(R.id.action_cropActivitiesListFragment_to_fertilizerApplicationFragment,bundle);

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

        TextView dateTextView, notesTextView, operationTextView, operatorTextView, costTextView,recurrenceTxt;
        ImageView  moreButton,showHideRemarksButton;
        LinearLayout hideShowLayout,expandContentLayout;
        View verticalLineView;
        public CultivationViewHolder(View itemView) {
            super(itemView);
            notesTextView = itemView.findViewById(R.id.txt_view_crop_cultivation_card_notes);
            operationTextView = itemView.findViewById(R.id.txt_view_crop_cultivation_card_operation);
            dateTextView = itemView.findViewById(R.id.txt_view_crop_cultivation_card_date);
            verticalLineView = itemView.findViewById(R.id.txt_view_crop_cultivation_card_line);
            recurrenceTxt = itemView.findViewById(R.id.txt_view_crop_cultivation_card_recurrence);

            operatorTextView = itemView.findViewById(R.id.txt_view_crop_cultivation_card_operator);
            costTextView = itemView.findViewById(R.id.txt_view_crop_cultivation_card_cost);

            hideShowLayout = itemView.findViewById(R.id.layout_crop_scouting_card_show_hide);
            expandContentLayout = itemView.findViewById(R.id.layout_crop_scouting_expand);
            showHideRemarksButton = itemView.findViewById(R.id.img_crop_scouting_card_show_crops);
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
//                                CropCultivation cropCultivation = (CropCultivation)cropsList.get(getAdapterPosition());
//                                Intent editCropCultivation = new Intent(mContext, CropCultivationManagerActivity.class);
//                                editCropCultivation.putExtra("cropCultivation", cropCultivation);
//                                editCropCultivation.putExtra("cropId",cropCultivation.getCropId());
//
//                                mContext.startActivity(editCropCultivation);

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

        TextView dateTextView,treatmentReasonTextView, recurrenceTextView, sprayType, rateTextView, sprayNameTextView;
        ImageView moreButton, showHideCropsButton;
        View verticalLineView;

        public SprayingViewHolder(View itemView) {
            super(itemView);

            dateTextView = itemView.findViewById(R.id.txt_view_crop_spraying_card_date);
            verticalLineView = itemView.findViewById(R.id.txt_view_crop_spraying_card_line);
            treatmentReasonTextView = itemView.findViewById(R.id.txt_view_crop_spraying_card_reason);
            sprayType= itemView.findViewById(R.id.txt_view_crop_spraying_card_spray_type);
            recurrenceTextView = itemView.findViewById(R.id.txt_view_crop_spraying_recurrence);
            rateTextView = itemView.findViewById(R.id.txt_view_crop_spraying_card_rate);
            sprayNameTextView = itemView.findViewById(R.id.txt_view_crop_spraying_card_spray_name);


            moreButton = itemView.findViewById(R.id.img_crop_spraying_card_more);



            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    NavController navController = Navigation.findNavController(v);
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
                                Bundle bundle=new Bundle();
                                bundle.putSerializable("cropSpraying", cropSpraying);
                                bundle.putString("cropId",cropSpraying.getCropId());
                                navController.navigate(R.id.action_cropActivitiesListFragment_to_cropSprayingFragment,bundle);

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
        TextView harvestDateTxt,harvest_date_sold,quantityTxt,statusTxt,costTxt,unitsTxt, incomeGeneratedTxt;
        LinearLayout hideShowLayout,expandContentLayout, incomeGeneratedLayout;
        ImageView moreButton,showHideRemarksButton;
        View verticalLineView;

        public HarvestViewHolder(View itemView) {
            super(itemView);
            harvestDateTxt = itemView.findViewById(R.id.txt_view_crop_harvest_card_date);
            harvest_date_sold = itemView.findViewById(R.id.txt_view_crop_harvest_date_sold);
            quantityTxt = itemView.findViewById(R.id.txt_view_crop_harvest_card_quantity);
            statusTxt = itemView.findViewById(R.id.txt_view_crop_harvest_card_status);
            unitsTxt = itemView.findViewById(R.id.txt_view_crop_harvest_card_units);
            verticalLineView = itemView.findViewById(R.id.txt_view_crop_harvest_card_line);
            incomeGeneratedTxt = itemView.findViewById(R.id.txt_view_crop_harvest_card_income_generated);
            incomeGeneratedLayout = itemView.findViewById(R.id.layout_crop_harvest_card_income_generated);

            hideShowLayout = itemView.findViewById(R.id.layout_crop_harvest_card_show_hide);
            expandContentLayout = itemView.findViewById(R.id.layout_crop_harvest_expand);

            moreButton = itemView.findViewById(R.id.img_crop_harvest_card_more);
            showHideRemarksButton = itemView.findViewById(R.id.img_crop_harvest_card_show_crops);
            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    NavController navController = Navigation.findNavController(v);
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

                                Bundle bundle=new Bundle();
                                bundle.putSerializable("cropHarvest", cropHarvest);
                                bundle.putString("cropId",cropHarvest.getCropId());
                                navController.navigate(R.id.action_cropActivitiesListFragment_to_cropHarvestFragment,bundle);

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
