package com.myfarmnow.myfarmcrop.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropFieldManagerActivity;
import com.myfarmnow.myfarmcrop.activities.CropIrrigationListActivity;
import com.myfarmnow.myfarmcrop.activities.CropSoilAnalysisListActivity;
import com.myfarmnow.myfarmcrop.activities.CropTransplantingListActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.Crop;
import com.myfarmnow.myfarmcrop.models.CropField;

import java.util.ArrayList;

public class CropFieldsListRecyclerAdapter extends RecyclerView.Adapter<CropFieldsListRecyclerAdapter.FieldViewHolder> {

    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropField> cropFieldsList = new ArrayList<>();

    public CropFieldsListRecyclerAdapter(Context context, ArrayList<CropField> cropFields){
        cropFieldsList.addAll(cropFields);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);

        Log.d("CROP FIELDS",cropFieldsList.size()+" ");
    }
    @NonNull
    @Override
    public FieldViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.crop_field_list_card,parent,false);

        FieldViewHolder holder = new FieldViewHolder(view);
        return holder;
    }

    public void appendList(ArrayList<CropField> cropFields){

        this.cropFieldsList.addAll(cropFields);
        notifyDataSetChanged();
    }

    public void addCropField(CropField cropField){
        this.cropFieldsList.add(cropField);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<CropField> cropFields){

        this.cropFieldsList.clear();
        this.cropFieldsList.addAll(cropFields);

        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull final FieldViewHolder holder, int position) {

        CropField field = cropFieldsList.get(position);
        holder.croppableAreaTextView.setText(field.getCroppableArea()+" "+field.getUnits().toLowerCase());
        holder.soilTypeTextView.setText(field.getSoilType());
        holder.soilCategoryTextView.setText(field.getSoilCategory());
        holder.fieldNameTextView.setText(field.getFieldName());
        holder.fieldAreaTextView.setText("("+field.getTotalArea()+" "+field.getUnits().toLowerCase()+")");

        ArrayList<Crop> crops = MyFarmDbHandlerSingleton.getHandlerInstance(mContext).getCropsInField(field.getId());

        if(crops.size()==0){
            holder.hideShowLayout.setVisibility(View.GONE);
        }
        for(Crop crop : crops){
            TextView cropNameAreaTextView = new TextView(mContext);
            TextView datePlantedTextView = new TextView(mContext);
            cropNameAreaTextView.setText(crop.getName()+" ("+crop.getArea()+"ha)");
            datePlantedTextView.setText("Date Planted : "+crop.getDateSown()+" ("+crop.computeAge()+")");
            View view = new View(mContext);
            view.setMinimumHeight(20);
            cropNameAreaTextView.setTypeface(cropNameAreaTextView.getTypeface(), Typeface.BOLD);
            holder.expandContentLayout.addView(cropNameAreaTextView);
            holder.expandContentLayout.addView(datePlantedTextView);
            holder.expandContentLayout.addView(view);
        }
        holder.hideShowLayout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (holder.expandContentLayout.getVisibility()==View.VISIBLE){
                    holder.expandContentLayout.setVisibility(View.GONE);
                    holder.showHideCropsButton.setImageDrawable(mContext.getDrawable(R.drawable.arrow_drop_down));
                }else{
                    holder.expandContentLayout.setVisibility(View.VISIBLE);
                    holder.showHideCropsButton.setImageDrawable(mContext.getDrawable(R.drawable.arrow_drop_up));
                }
            }
        });

    }



    @Override
    public int getItemCount() {
        return cropFieldsList.size();
    }


    public class FieldViewHolder extends RecyclerView.ViewHolder{

        TextView fieldNameTextView, fieldAreaTextView, soilCategoryTextView, soilTypeTextView, croppableAreaTextView;
        ImageView moreButton, showHideCropsButton;
        LinearLayout expandContentLayout,hideShowLayout;
        public FieldViewHolder(View itemView) {
            super(itemView);
            fieldNameTextView = itemView.findViewById(R.id.txt_crop_field_card_name);
            fieldAreaTextView = itemView.findViewById(R.id.txt_crop_field_card_area);
            soilCategoryTextView = itemView.findViewById(R.id.txt_crop_field_card_soil_category);
            soilTypeTextView = itemView.findViewById(R.id.txt_crop_field_card_soil_type);
            croppableAreaTextView = itemView.findViewById(R.id.txt_crop_field_card_croppable_area);
            expandContentLayout = itemView.findViewById(R.id.layout_crop_field_expand);
            hideShowLayout = itemView.findViewById(R.id.layout_crop_field_card_show_hide);
            moreButton = itemView.findViewById(R.id.img_crop_fields_card_more);
            showHideCropsButton = itemView.findViewById(R.id.img_crop_fields_card_show_crops);
            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                    PopupMenu popup = new PopupMenu(wrapper, v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            if (item.getTitle().toString().equals(mContext.getString(R.string.label_delete))){
                                final CropField cropField = cropFieldsList.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete "+cropField.getFieldName()+" field?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropField(cropField.getId());
                                                cropFieldsList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();
                            }else if (item.getTitle().toString().equals(mContext.getString(R.string.label_edit))){
                                CropField cropField = cropFieldsList.get(getAdapterPosition());
                                Intent editField = new Intent(mContext, CropFieldManagerActivity.class);
                                editField.putExtra("cropField",cropField);
                                mContext.startActivity(editField);
                            }
                            else if (item.getTitle().toString().equals(mContext.getString(R.string.label_soil_analysis))){
                                CropField cropField = cropFieldsList.get(getAdapterPosition());
                                Intent showSoilAnalysis = new Intent(mContext, CropSoilAnalysisListActivity.class);
                                showSoilAnalysis.putExtra("fieldId",cropField.getId());
                                mContext.startActivity(showSoilAnalysis);
                            }
                            else if (item.getTitle().toString().equals(mContext.getString(R.string.label_irrigation))){
                                CropField cropField = cropFieldsList.get(getAdapterPosition());
                                Intent showIrrigation = new Intent(mContext, CropIrrigationListActivity.class);
                                showIrrigation.putExtra("fieldId",cropField.getId());
                                mContext.startActivity(showIrrigation);
                            }
                            else if (item.getTitle().toString().equals(mContext.getString(R.string.label_transplanting))){
                                CropField cropField = cropFieldsList.get(getAdapterPosition());
                                Intent showTransplanting = new Intent(mContext, CropTransplantingListActivity.class);
                                showTransplanting.putExtra("fieldId",cropField.getId());
                                mContext.startActivity(showTransplanting);
                            }

                            return true;
                        }
                    });
                    popup.getMenu().add(R.string.label_soil_analysis);
                    popup.getMenu().add(R.string.label_irrigation);
                    popup.getMenu().add(R.string.label_transplanting);
                    popup.getMenu().add(R.string.label_edit);
                    popup.getMenu().add(R.string.label_delete);
                    popup.show();

                }
            });

        }

    }
}
