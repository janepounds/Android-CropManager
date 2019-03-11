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
import com.myfarmnow.myfarmcrop.activities.CropCultivationManagerActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropCultivation;

import java.util.ArrayList;

public class CropCultivationsListRecyclerAdapter extends RecyclerView.Adapter<CropCultivationsListRecyclerAdapter.CultivationViewHolder> {

    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropCultivation> cropCultivationsList = new ArrayList<>();

    public CropCultivationsListRecyclerAdapter(Context context, ArrayList<CropCultivation> cropCultivations){
        cropCultivationsList.addAll(cropCultivations);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);

        Log.d("CROP FIELDS",cropCultivationsList.size()+" ");
    }
    @NonNull
    @Override
    public CultivationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.crop_cultivation_list_card,parent,false);

        CultivationViewHolder holder = new CultivationViewHolder(view);
        return holder;
    }

    public void appendList(ArrayList<CropCultivation> cropCultivations){

        this.cropCultivationsList.addAll(cropCultivations);
        notifyDataSetChanged();
    }

    public void addCropCultivation(CropCultivation cropCultivation){
        this.cropCultivationsList.add(cropCultivation);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<CropCultivation> cropCultivations){

        this.cropCultivationsList.clear();
        this.cropCultivationsList.addAll(cropCultivations);

        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull CultivationViewHolder holder, int position) {

        CropCultivation field = cropCultivationsList.get(position);
        holder.operatorTextView.setText(field.getOperator());
        holder.costTextView.setText(field.getCost()+"");
        holder.operationTextView.setText(field.getOperation());
        holder.notesTextView.setText(field.getNotes());
        holder.dateTextView.setText(field.getDate());
       // holder.verticalLineView.setMinimumHeight( holder.verticalLineView.getHeight()+holder.notesTextView.getHeight());
        //holder.verticalLineView.getLayoutParams();
        ViewGroup.LayoutParams params = holder.verticalLineView.getLayoutParams();
        params.height = holder.verticalLineView.getHeight()+holder.notesTextView.getHeight();
        holder.verticalLineView.requestLayout();
    }



    @Override
    public int getItemCount() {
        return cropCultivationsList.size();
    }


    public class CultivationViewHolder extends RecyclerView.ViewHolder{

        TextView dateTextView, notesTextView, operationTextView, methodTextView, operatorTextView, costTextView;
        ImageView editButton, deleteButton;
        View verticalLineView;
        public CultivationViewHolder(View itemView) {
            super(itemView);
            notesTextView = itemView.findViewById(R.id.txt_view_crop_cultivation_card_notes);
            operationTextView = itemView.findViewById(R.id.txt_view_crop_cultivation_card_operation);
            dateTextView = itemView.findViewById(R.id.txt_view_crop_cultivation_card_date);
            verticalLineView = itemView.findViewById(R.id.txt_view_crop_cultivation_card_line);

            operatorTextView = itemView.findViewById(R.id.txt_view_crop_cultivation_card_operator);
            costTextView = itemView.findViewById(R.id.txt_view_crop_cultivation_card_cost);
            deleteButton = itemView.findViewById(R.id.img_crop_cultivation_card_delete);
            editButton = itemView.findViewById(R.id.img_crop_cultivation_card_edit);
          

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CropCultivation cropCultivation = cropCultivationsList.get(getAdapterPosition());
                    Intent editCultivation = new Intent(mContext, CropCultivationManagerActivity.class);
                    editCultivation.putExtra("cropCultivation",cropCultivation);
                    mContext.startActivity(editCultivation);
                }
            });
           
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final CropCultivation cropCultivation = cropCultivationsList.get(getAdapterPosition());
                    new AlertDialog.Builder(mContext)
                            .setTitle("Confirm")
                            .setMessage("Do you really want to delete the cultivation on "+cropCultivation.getDate()+"?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {

                                    MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropCultivate(cropCultivation.getId());
                                    cropCultivationsList.remove(getAdapterPosition());
                                    notifyItemRemoved(getAdapterPosition());

                                }})
                            .setNegativeButton(android.R.string.no, null).show();
                }
            });
        }

    }
}
