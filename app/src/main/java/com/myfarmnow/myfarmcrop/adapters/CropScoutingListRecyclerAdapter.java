package com.myfarmnow.myfarmcrop.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropScoutingManagerActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropScouting;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;

import java.util.ArrayList;

public class CropScoutingListRecyclerAdapter extends RecyclerView.Adapter<CropScoutingListRecyclerAdapter.ScoutingViewHolder> {
    String remarks;
    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropScouting> cropScoutingList = new ArrayList<>();
    public CropScoutingListRecyclerAdapter(Context context, ArrayList<CropScouting> cropScoutings){
        cropScoutingList.addAll(cropScoutings);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);

    }
    @NonNull
    @Override
    public ScoutingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.crop_scouting_list_card,parent,false);

        ScoutingViewHolder holder = new ScoutingViewHolder(view);
        return holder;
    }
    public void appendList(ArrayList<CropScouting> cropScoutings){

        this.cropScoutingList.addAll(cropScoutings);
        notifyDataSetChanged();
    }
    public void addCropScouting(CropScouting cropScouting){
        this.cropScoutingList.add(cropScouting);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<CropScouting> cropScoutings){

        this.cropScoutingList.clear();
        this.cropScoutingList.addAll(cropScoutings);

        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull final ScoutingViewHolder holder, int position) {
        CropScouting scouting = cropScoutingList.get(position);
        holder.scoutingDateTxt.setText(scouting.getDate());
        holder.scoutingMethodTxt.setText("("+scouting.getMethod()+")");
        holder.infestationTypeTxt.setText(scouting.getInfestationType()+" : ");
       holder.infestationTxt.setText(scouting.getInfestation());
        holder.infestationLevelTxt.setText(scouting.getInfestationLevel());
        holder.costTxt.setText(scouting.getCost()+"");
       // holder.remarksTxt.setText(scouting.getRemarks());

        //TODO MAKING DROP DOWN GONE WHEN REMARKS NOT ENTERED
        //TODO MAKING infestationLayout and infestationLevelLayout GONE WHEN CROP IS NOT INFESTED
        //TODO ADJUSTING THE SIDE LINE OF THE CARD







        /*if(scouting.getInfestationType() != null) {

            TextView infestationTypeTxt = new TextView(mContext);
            infestationTypeTxt.setText(scouting.getInfestationType());

            TextView infestationTxt = new TextView(mContext);
            infestationTxt.setText(scouting.getInfestation());

            TextView infestationLevelTxt = new TextView(mContext);
            infestationLevelTxt.setText(scouting.getInfestationLevel());


           holder.infestationLayout.setVisibility(View.VISIBLE);
           holder.infestationLevelLayout.setVisibility(View.VISIBLE);


        }*/


        if(scouting.getRemarks() != null){
            //holder.hideShowLayout.setVisibility(View.GONE);
            TextView remarksTxt = new TextView(mContext);
            remarksTxt.setText(scouting.getRemarks());

            View view = new View(mContext);
            view.setMinimumHeight(20);

            holder.expandContentLayout.addView(remarksTxt);

            holder.expandContentLayout.addView(view);
            holder.hideShowLayout.setVisibility(View.VISIBLE);

        }

        holder.hideShowLayout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (holder.expandContentLayout.getVisibility()==View.VISIBLE){
                    holder.expandContentLayout.setVisibility(View.GONE);
                    holder.showHideRemarksButton.setImageDrawable(mContext.getDrawable(R.drawable.arrow_drop_down));
                }else{
                    holder.expandContentLayout.setVisibility(View.VISIBLE);
                    holder.showHideRemarksButton.setImageDrawable(mContext.getDrawable(R.drawable.arrow_drop_up));
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return cropScoutingList.size();
    }

    public class ScoutingViewHolder extends RecyclerView.ViewHolder{
        TextView scoutingDateTxt,scoutingMethodTxt,infestationTypeTxt,infestationTxt,infestationLevelTxt,costTxt,remarksTxt;
        ImageView moreButton,showHideRemarksButton;
        LinearLayout expandContentLayout,hideShowLayout, infestationLayout, infestationLevelLayout;


        public ScoutingViewHolder(View itemView) {
            super(itemView);
            scoutingDateTxt = itemView.findViewById(R.id.txt_view_crop_scouting_card_date);
            scoutingMethodTxt = itemView.findViewById(R.id.txt_view_crop_scouting_card_method);
            infestationTypeTxt = itemView.findViewById(R.id.txt_view_crop_scouting_card_infestation_type);
            infestationTxt = itemView.findViewById(R.id.txt_view_crop_scouting_card_infestation);
            infestationLevelTxt = itemView.findViewById(R.id.txt_view_crop_scouting_card_infestation_level);
            costTxt = itemView.findViewById(R.id.txt_view_crop_scouting_card_cost);

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
                                final CropScouting cropScouting = cropScoutingList.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete this Scouting Record ?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropScouting(cropScouting.getId());
                                                cropScoutingList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();
                            }else if (item.getTitle().toString().equals(mContext.getString(R.string.label_edit))){
                                CropScouting cropScouting = cropScoutingList.get(getAdapterPosition());
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
}
