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
import com.myfarmnow.myfarmcrop.activities.CropFertilizerApplicationManagerActivity;

import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropFertilizerApplication;

import java.text.NumberFormat;
import java.util.ArrayList;

public class CropFertilizerApplicationsListRecyclerAdapter extends RecyclerView.Adapter<CropFertilizerApplicationsListRecyclerAdapter.FertilizerApplicationViewHolder> {

    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropFertilizerApplication> cropFertilizerApplicationsList = new ArrayList<>();

    public CropFertilizerApplicationsListRecyclerAdapter(Context context, ArrayList<CropFertilizerApplication> cropFertilizerApplications){
        cropFertilizerApplicationsList.addAll(cropFertilizerApplications);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);

        Log.d("CROP FIELDS",cropFertilizerApplicationsList.size()+" ");
    }
    @NonNull
    @Override
    public FertilizerApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.crop_fertilizer_application_list_card,parent,false);

        FertilizerApplicationViewHolder holder = new FertilizerApplicationViewHolder(view);
        return holder;
    }

    public void appendList(ArrayList<CropFertilizerApplication> cropFertilizerApplications){

        this.cropFertilizerApplicationsList.addAll(cropFertilizerApplications);
        notifyDataSetChanged();
    }

    public void addCropFertilizerApplication(CropFertilizerApplication cropFertilizerApplication){
        this.cropFertilizerApplicationsList.add(cropFertilizerApplication);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<CropFertilizerApplication> cropFertilizerApplications){

        this.cropFertilizerApplicationsList.clear();
        this.cropFertilizerApplicationsList.addAll(cropFertilizerApplications);

        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull final FertilizerApplicationViewHolder holder, int position) {

        CropFertilizerApplication field = cropFertilizerApplicationsList.get(position);

        holder.costTextView.setText("UGX "+NumberFormat.getInstance().format(field.getCost())); //TODO replace currency
        holder.rateTextView.setText(field.getRate()+"Kg/ha");
        holder.methodTextView.setText(field.getMethod());
        holder.operationTextView.setText(field.getFertilizerName());

        final ViewTreeObserver observer = holder.operationTextView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.operationTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    holder.operationTextView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

                int containerHeight = holder.rateTextView.getHeight()+holder.operationTextView.getHeight()+holder.methodTextView.getHeight();
                ViewGroup.LayoutParams params = holder.verticalLineView.getLayoutParams();
                params.height = containerHeight;

                holder.verticalLineView.requestLayout();


            }
        });

    }



    @Override
    public int getItemCount() {
        return cropFertilizerApplicationsList.size();
    }


    public class FertilizerApplicationViewHolder extends RecyclerView.ViewHolder{

        TextView dateTextView, operationTextView, methodTextView, costTextView, rateTextView;
        ImageView moreButton;
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
                                final CropFertilizerApplication cropFertilizerApplication = cropFertilizerApplicationsList.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete the fertilizer application done on "+cropFertilizerApplication.getDate()+"?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropFertilizerApplication(cropFertilizerApplication.getId());
                                                cropFertilizerApplicationsList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();
                            }else if (item.getTitle().equals(mContext.getString(R.string.label_edit))){
                                CropFertilizerApplication cropFertilizerApplication = cropFertilizerApplicationsList.get(getAdapterPosition());
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
}
