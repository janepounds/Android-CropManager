package com.myfarmnow.myfarmcrop.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropFieldManagerActivity;
import com.myfarmnow.myfarmcrop.activities.CropSoilAnalysisListActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropField;
import com.myfarmnow.myfarmcrop.models.CropInventory;

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
    public void onBindViewHolder(@NonNull FieldViewHolder holder, int position) {

        CropField field = cropFieldsList.get(position);
        holder.croppableAreaTextView.setText(field.getCroppableArea()+field.getUnits());
        holder.soilTypeTextView.setText(field.getSoilType());
        holder.soilCategoryTextView.setText(field.getSoilCategory());
        holder.fieldNameTextView.setText(field.getFieldName());
        holder.fieldAreaTextView.setText(field.getTotalArea()+field.getUnits());
    }



    @Override
    public int getItemCount() {
        return cropFieldsList.size();
    }


    public class FieldViewHolder extends RecyclerView.ViewHolder{

        TextView fieldNameTextView, fieldAreaTextView, soilCategoryTextView, soilTypeTextView, croppableAreaTextView;
        ImageView editButton, deleteButton,soilAnalysisButton;
        public FieldViewHolder(View itemView) {
            super(itemView);
            fieldNameTextView = itemView.findViewById(R.id.txt_crop_field_card_name);
            fieldAreaTextView = itemView.findViewById(R.id.txt_crop_field_card_area);
            soilCategoryTextView = itemView.findViewById(R.id.txt_crop_field_card_soil_category);
            soilTypeTextView = itemView.findViewById(R.id.txt_crop_field_card_soil_type);
            croppableAreaTextView = itemView.findViewById(R.id.txt_crop_field_card_croppable_area);
            deleteButton = itemView.findViewById(R.id.img_crop_field_card_delete);
            editButton = itemView.findViewById(R.id.img_crop_field_card_edit);
            soilAnalysisButton = itemView.findViewById(R.id.img_crop_field_card_soil_analysis);

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CropField cropField = cropFieldsList.get(getAdapterPosition());
                    Intent editField = new Intent(mContext, CropFieldManagerActivity.class);
                    editField.putExtra("cropField",cropField);
                    mContext.startActivity(editField);
                }
            });
            soilAnalysisButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CropField cropField = cropFieldsList.get(getAdapterPosition());
                    Intent showSoilAnalysis = new Intent(mContext, CropSoilAnalysisListActivity.class);
                    showSoilAnalysis.putExtra("fieldId",cropField.getId());
                    mContext.startActivity(showSoilAnalysis);
                }
            });
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                }
            });
        }

    }
}
