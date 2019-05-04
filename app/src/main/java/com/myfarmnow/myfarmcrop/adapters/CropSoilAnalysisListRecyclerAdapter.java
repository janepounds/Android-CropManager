package com.myfarmnow.myfarmcrop.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
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
import com.myfarmnow.myfarmcrop.activities.CropSoilAnalysisManagerActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropSoilAnalysis;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.util.ArrayList;

public class CropSoilAnalysisListRecyclerAdapter extends RecyclerView.Adapter<CropSoilAnalysisListRecyclerAdapter.SoilAnalysisViewHolder> {

    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropSoilAnalysis> cropsoilAnalysisList = new ArrayList<>();

    public CropSoilAnalysisListRecyclerAdapter(Context context, ArrayList<CropSoilAnalysis> cropsoilAnalysis){
        cropsoilAnalysisList.addAll(cropsoilAnalysis);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);

        Log.d("CROP FIELDS",cropsoilAnalysisList.size()+" ");
    }
    @NonNull
    @Override
    public SoilAnalysisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.crop_soil_analysis_list_card,parent,false);

        SoilAnalysisViewHolder holder = new SoilAnalysisViewHolder(view);
        return holder;
    }

    public void appendList(ArrayList<CropSoilAnalysis> cropsoilAnalysis){

        this.cropsoilAnalysisList.addAll(cropsoilAnalysis);
        notifyDataSetChanged();
    }

    public void addCropSoilAnalysis(CropSoilAnalysis cropSoilAnalysis){
        this.cropsoilAnalysisList.add(cropSoilAnalysis);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<CropSoilAnalysis> cropsoilAnalysis){

        this.cropsoilAnalysisList.clear();
        this.cropsoilAnalysisList.addAll(cropsoilAnalysis);

        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull final SoilAnalysisViewHolder holder, int position) {

        CropSoilAnalysis field = cropsoilAnalysisList.get(position);

        holder.agronomistTextView.setText(field.getAgronomist());
       // holder.resultsTextView.setText(field.getResult());
        holder.organicMatterTextView.setText(field.getOrganicMatter()+"%");
        holder.phTextView.setText(field.getPh()+"");
        holder.dateTextView.setText(CropSettingsSingleton.getInstance().convertToUserFormat(field.getDate()));

        TextView resultsTextView = new TextView(mContext);
        resultsTextView.setText("Results : "+field.getResult());
        View view = new View(mContext);
        view.setMinimumHeight(20);
        holder.expandContentLayout.addView(resultsTextView);

        holder.expandContentLayout.addView(view);
        holder.hideShowLayout.setVisibility(View.VISIBLE);



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



        final ViewTreeObserver observer = holder.resultsTextView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.resultsTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    holder.resultsTextView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

                int containerHeight = holder.resultsTextView.getHeight()+holder.organicMatterTextView.getHeight()+holder.agronomistTextView.getHeight()+holder.agronomistTextView.getHeight();
                ViewGroup.LayoutParams params = holder.verticalLineView.getLayoutParams();
                params.height = containerHeight;
                Log.d("LENGTH",containerHeight+"");
                holder.verticalLineView.requestLayout();


            }
        });

    }



    @Override
    public int getItemCount() {
        return cropsoilAnalysisList.size();
    }


    public class SoilAnalysisViewHolder extends RecyclerView.ViewHolder{

        TextView dateTextView, phTextView, organicMatterTextView, agronomistTextView, resultsTextView;
        LinearLayout hideShowLayout,expandContentLayout;
        ImageView moreButton,showHideRemarksButton;
        View verticalLineView;
        public SoilAnalysisViewHolder(View itemView) {
            super(itemView);
            phTextView = itemView.findViewById(R.id.txt_view_crop_soil_analysis_card_ph);
            organicMatterTextView = itemView.findViewById(R.id.txt_view_crop_soil_analysis_card_organic_matter);
            dateTextView = itemView.findViewById(R.id.txt_view_crop_soil_analysis_card_date);
            verticalLineView = itemView.findViewById(R.id.txt_view_crop_soil_analysis_card_line);

            agronomistTextView = itemView.findViewById(R.id.txt_view_crop_soil_analysis_card_agronomist);
            resultsTextView = itemView.findViewById(R.id.txt_view_crop_soil_analysis_card_results);


            moreButton = itemView.findViewById(R.id.img_crop_soil_analysis_card_more);

            hideShowLayout = itemView.findViewById(R.id.layout_crop_scouting_card_show_hide);
            expandContentLayout = itemView.findViewById(R.id.layout_crop_scouting_expand);
            showHideRemarksButton = itemView.findViewById(R.id.img_crop_scouting_card_show_crops);

            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                    PopupMenu popup = new PopupMenu(wrapper, v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getTitle().equals(mContext.getString(R.string.label_delete))){
                                final CropSoilAnalysis cropSoilAnalysis = cropsoilAnalysisList.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete the soil_analysis on "+ CropSettingsSingleton.getInstance().convertToUserFormat(cropSoilAnalysis.getDate())+"?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropSoilAnalysis(cropSoilAnalysis.getId());
                                                cropsoilAnalysisList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();
                            }else if (item.getTitle().equals(mContext.getString(R.string.label_edit))){

                                CropSoilAnalysis cropSoilAnalysis = cropsoilAnalysisList.get(getAdapterPosition());
                                Intent editSoilAnalysis = new Intent(mContext, CropSoilAnalysisManagerActivity.class);
                                editSoilAnalysis.putExtra("soilAnalysis",cropSoilAnalysis);
                                editSoilAnalysis.putExtra("fieldId",cropSoilAnalysis.getFieldId());
                                mContext.startActivity(editSoilAnalysis);
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
