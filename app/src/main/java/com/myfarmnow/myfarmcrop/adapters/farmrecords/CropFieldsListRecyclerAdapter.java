package com.myfarmnow.myfarmcrop.adapters.farmrecords;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
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
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.farmrecords.Crop;
import com.myfarmnow.myfarmcrop.models.farmrecords.CropField;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.util.ArrayList;
import java.util.List;

public class CropFieldsListRecyclerAdapter extends RecyclerView.Adapter<CropFieldsListRecyclerAdapter.FieldViewHolder> {

    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropField> cropFieldsList = new ArrayList<>();

    public CropFieldsListRecyclerAdapter(Context context, List<CropField> cropFields){
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
        holder.soilTypeTextView.setText(field.getFieldType());
        holder.soilCategoryTextView.setText(field.getStatus());
        holder.fieldNameTextView.setText(field.getFieldName());
        holder.fieldAreaTextView.setText(field.getTotalArea()+" "+field.getUnits().toLowerCase());

        ArrayList<Crop> crops = MyFarmDbHandlerSingleton.getHandlerInstance(mContext).getCropsInField(String.valueOf(field.getId()) );


        for(Crop crop : crops){
            TextView cropNameAreaTextView = new TextView(mContext);
            TextView datePlantedTextView = new TextView(mContext);
            Typeface face = Typeface.createFromAsset(mContext.getAssets(),  "fonts/JosefinSans-Regular.ttf");
            datePlantedTextView.setTextSize(16f);
            datePlantedTextView.setTextColor(Color.WHITE);
            cropNameAreaTextView.setTextSize(16f);
            cropNameAreaTextView.setTextColor(Color.WHITE);

            cropNameAreaTextView.setText(crop.getName()+" ("+crop.getArea()+field.getUnits().toLowerCase()+")");
            datePlantedTextView.setText("Date Planted : "+ CropSettingsSingleton.getInstance().convertToUserFormat(crop.getDateSown())+" ("+crop.computeAge()+")");
            datePlantedTextView.setTypeface(face );
            View view = new View(mContext);
            view.setMinimumHeight(20);
            cropNameAreaTextView.setTypeface(face, Typeface.BOLD);
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
            moreButton = itemView.findViewById(R.id.img_crop_fields_card_more);
            expandContentLayout= itemView.findViewById(R.id.layout_crop_field_expand);
            hideShowLayout = itemView.findViewById(R.id.layout_crop_field_card_show_hide);
            showHideCropsButton = itemView.findViewById(R.id.img_crop_field_card_show_crops);

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
                                final CropField cropField = cropFieldsList.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete "+cropField.getFieldName()+" field?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropField(""+cropField.getId());
                                                cropFieldsList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();
                            }else if (item.getTitle().toString().equals(mContext.getString(R.string.label_edit))){
                                CropField cropField = cropFieldsList.get(getAdapterPosition());
                                Bundle bundle=new Bundle();
                                bundle.putSerializable("cropField", cropField);
                                navController.navigate(R.id.action_fieldsListFragment_to_addFieldFragment,bundle);

                            }


                            return true;
                        }
                    });
                    //popup.getMenu().add(R.string.label_soil_analysis);
                    popup.getMenu().add(R.string.label_edit);
                    popup.getMenu().add(R.string.label_delete);
                    popup.show();

                }
            });

        }

    }
}
