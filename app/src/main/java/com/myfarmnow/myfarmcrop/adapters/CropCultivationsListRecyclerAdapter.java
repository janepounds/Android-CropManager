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

        //Log.d("CROP FIELDS",cropCultivationsList.size()+" ");
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
    public void onBindViewHolder(@NonNull final CultivationViewHolder holder, int position) {

        CropCultivation field = cropCultivationsList.get(position);
        holder.operatorTextView.setText(field.getOperator());
        holder.costTextView.setText(field.getCost()+"");
        holder.operationTextView.setText(field.getOperation());
        holder.notesTextView.setText(field.getNotes());
        holder.dateTextView.setText(field.getDate());
       // holder.verticalLineView.setMinimumHeight( holder.verticalLineView.getHeight()+holder.notesTextView.getHeight());
        //holder.verticalLineView.getLayoutParams();

        final ViewTreeObserver observer = holder.notesTextView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.notesTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    holder.notesTextView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

                int containerHeight = holder.notesTextView.getHeight()+holder.costTextView.getHeight()+holder.costTextView.getHeight()+holder.operatorTextView.getHeight();
                ViewGroup.LayoutParams params = holder.verticalLineView.getLayoutParams();
                params.height = containerHeight;
                Log.d("LENGTH",containerHeight+"");
                holder.verticalLineView.requestLayout();


            }
        });
    }



    @Override
    public int getItemCount() {
        return cropCultivationsList.size();
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
                                final CropCultivation cropCultivation = cropCultivationsList.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete " + cropCultivation.getOperation() + " operation?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropCultivate(cropCultivation.getId());
                                                cropCultivationsList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, null).show();
                            } else if (item.getTitle().toString().equals(mContext.getString(R.string.label_edit))) {
                                CropCultivation cropCultivation = cropCultivationsList.get(getAdapterPosition());
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
}
