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
import com.myfarmnow.myfarmcrop.activities.CropProductManagerActivity;
import com.myfarmnow.myfarmcrop.activities.CropSprayingManagerActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropProduct;
import com.myfarmnow.myfarmcrop.models.CropSpraying;

import java.util.ArrayList;

public class CropSprayingsListRecyclerAdapter extends RecyclerView.Adapter<CropSprayingsListRecyclerAdapter.SprayingViewHolder> {

    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropSpraying> cropSprayingsList = new ArrayList<>();

    public CropSprayingsListRecyclerAdapter(Context context, ArrayList<CropSpraying> cropSprayings){
        cropSprayingsList.addAll(cropSprayings);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);

        Log.d("CROP FIELDS",cropSprayingsList.size()+" ");
    }
    @NonNull
    @Override
    public SprayingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.crop_spraying_list_card,parent,false);

        SprayingViewHolder holder = new SprayingViewHolder(view);
        return holder;
    }

    public void appendList(ArrayList<CropSpraying> cropSprayings){

        this.cropSprayingsList.addAll(cropSprayings);
        notifyDataSetChanged();
    }

    public void addCropSpraying(CropSpraying cropSpraying){
        this.cropSprayingsList.add(cropSpraying);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<CropSpraying> cropSprayings){

        this.cropSprayingsList.clear();
        this.cropSprayingsList.addAll(cropSprayings);

        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull final SprayingViewHolder holder, int position) {

        CropSpraying field = cropSprayingsList.get(position);
        holder.sprayNameTextView.setText(field.getSprayName());
        holder.operatorTextView.setText(field.getOperator());
        holder.rateTextView.setText(field.getRate()+"Kg/ha");
        holder.windDirectionTextView.setText(field.getWindDirection());
        holder.waterConditionTextView.setText(field.getWaterCondition());
        holder.treatmentReasonTextView.setText(field.getTreatmentReason());

        final ViewTreeObserver observer = holder.treatmentReasonTextView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.treatmentReasonTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    holder.treatmentReasonTextView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

                int containerHeight = holder.rateTextView.getHeight()+holder.treatmentReasonTextView.getHeight()+holder.waterConditionTextView.getHeight()+holder.operatorTextView.getHeight()+holder.operatorTextView.getHeight();
                ViewGroup.LayoutParams params = holder.verticalLineView.getLayoutParams();
                params.height = containerHeight;
                Log.d("LENGTH",containerHeight+"");
                holder.verticalLineView.requestLayout();


            }
        });
    }



    @Override
    public int getItemCount() {
        return cropSprayingsList.size();
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
                                final CropSpraying cropSpraying = cropSprayingsList.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete the spraying on "+cropSpraying.getDate()+"?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropSpraying(cropSpraying.getId());
                                                cropSprayingsList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();
                            }else if (item.getTitle().equals(mContext.getString(R.string.label_edit))){

                                CropSpraying cropSpraying = cropSprayingsList.get(getAdapterPosition());
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
