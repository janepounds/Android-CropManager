package com.myfarmnow.myfarmcrop.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropSoilAnalysisManagerActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropSoilAnalysis;

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
        holder.resultsTextView.setText(field.getResult());
        holder.organicMatterTextView.setText(field.getOrganicMatter()+"%");
        holder.phTextView.setText(field.getPh()+"");

        final ViewTreeObserver observer = holder.resultsTextView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.resultsTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    holder.resultsTextView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

                int containerHeight = holder.resultsTextView.getHeight()+holder.phTextView.getHeight()+holder.organicMatterTextView.getHeight()+holder.agronomistTextView.getHeight()+holder.agronomistTextView.getHeight();
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
        ImageView editButton, deleteButton;
        View verticalLineView;
        public SoilAnalysisViewHolder(View itemView) {
            super(itemView);
            phTextView = itemView.findViewById(R.id.txt_view_crop_soil_analysis_card_ph);
            organicMatterTextView = itemView.findViewById(R.id.txt_view_crop_soil_analysis_card_organic_matter);
            dateTextView = itemView.findViewById(R.id.txt_view_crop_soil_analysis_card_date);
            verticalLineView = itemView.findViewById(R.id.txt_view_crop_soil_analysis_card_line);

            agronomistTextView = itemView.findViewById(R.id.txt_view_crop_soil_analysis_card_agronomist);
            resultsTextView = itemView.findViewById(R.id.txt_view_crop_soil_analysis_card_results);

            deleteButton = itemView.findViewById(R.id.img_crop_soil_analysis_card_delete);
            editButton = itemView.findViewById(R.id.img_crop_soil_analysis_card_edit);
           

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CropSoilAnalysis cropSoilAnalysis = cropsoilAnalysisList.get(getAdapterPosition());
                    Intent editSoilAnalysis = new Intent(mContext, CropSoilAnalysisManagerActivity.class);
                    editSoilAnalysis.putExtra("cropSoilAnalysis",cropSoilAnalysis);
                    mContext.startActivity(editSoilAnalysis);
                }
            });
           
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final CropSoilAnalysis cropSoilAnalysis = cropsoilAnalysisList.get(getAdapterPosition());
                    new AlertDialog.Builder(mContext)
                            .setTitle("Confirm")
                            .setMessage("Do you really want to delete the soil_analysis on "+cropSoilAnalysis.getDate()+"?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {

                                    MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropSoilAnalysis(cropSoilAnalysis.getId());
                                    cropsoilAnalysisList.remove(getAdapterPosition());
                                    notifyItemRemoved(getAdapterPosition());

                                }})
                            .setNegativeButton(android.R.string.no, null).show();
                }
            });
        }

    }
}
