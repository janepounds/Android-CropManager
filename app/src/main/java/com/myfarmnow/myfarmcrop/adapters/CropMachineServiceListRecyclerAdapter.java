package com.myfarmnow.myfarmcrop.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
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
import com.myfarmnow.myfarmcrop.activities.CropMachineServiceManagerActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropMachineService;

import java.util.ArrayList;

public class CropMachineServiceListRecyclerAdapter extends RecyclerView.Adapter<CropMachineServiceListRecyclerAdapter.MachineServiceViewHolder> {
    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropMachineService> cropMachineServiceList = new ArrayList<>();


    public CropMachineServiceListRecyclerAdapter(Context context, ArrayList<CropMachineService> cropMachineServices){
        cropMachineServiceList.addAll(cropMachineServices);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);

    }

    @NonNull
    @Override
    public MachineServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.crop_machine_task_list_card,parent,false);

        MachineServiceViewHolder holder = new MachineServiceViewHolder(view);
        return holder;
    }
    public void appendList(ArrayList<CropMachineService> cropMachineServices){

        this.cropMachineServiceList.addAll(cropMachineServices);
        notifyDataSetChanged();
    }
    public void addCropMachineService(CropMachineService cropMachineService){
        this.cropMachineServiceList.add(cropMachineService);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<CropMachineService> cropMachineServices){

        this.cropMachineServiceList.clear();
        this.cropMachineServiceList.addAll(cropMachineServices);

        notifyDataSetChanged();


    }


    @Override
    public void onBindViewHolder(@NonNull final MachineServiceViewHolder holder, int position) {
        CropMachineService machineService = cropMachineServiceList.get(position);

        holder.serviceDateTxt.setText(machineService.getDate());
        holder.serviceTypeTxt.setText(machineService.getType());
        holder.personnelTxt.setText(machineService.getEmployeeName());
        holder.recurrenceTxt.setText(machineService.getRecurrence());
        holder.statusTxt.setVisibility(View.GONE);

        if(machineService.getDescription() != null){

            TextView descriptionTxt = new TextView(mContext);
            descriptionTxt.setText(machineService.getDescription());

            View view = new View(mContext);
            view.setMinimumHeight(20);

            holder.expandContentLayout.addView(descriptionTxt);

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
        return cropMachineServiceList.size();
    }


    public class MachineServiceViewHolder extends RecyclerView.ViewHolder{

        TextView serviceDateTxt,serviceTypeTxt,personnelTxt,recurrenceTxt,statusTxt;
        LinearLayout expandContentLayout,hideShowLayout;
        ImageView showHideRemarksButton,moreButton;
        public MachineServiceViewHolder(View itemView) {
            super(itemView);
            serviceDateTxt = itemView.findViewById(R.id.txt_view_crop_machine_task_card_start_date);
            serviceTypeTxt = itemView.findViewById(R.id.txt_crop_machine_task_card_title);
            personnelTxt = itemView.findViewById(R.id.txt_view_crop_machine_task_card_personnel);
            recurrenceTxt = itemView.findViewById(R.id.txt_view_crop_machine_task_card_recurring);
            hideShowLayout = itemView.findViewById(R.id.layout_crop_scouting_card_show_hide);
            expandContentLayout = itemView.findViewById(R.id.layout_crop_scouting_expand);
            moreButton = itemView.findViewById(R.id.img_crop_machine_task_card_more);


            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                    PopupMenu popup = new PopupMenu(wrapper, v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            if (item.getTitle().toString().equals(mContext.getString(R.string.label_delete))){
                                final CropMachineService cropMachineService = cropMachineServiceList.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete this "+cropMachineService.getType()+" service ?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropMachineService(cropMachineService.getId());
                                                cropMachineServiceList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();
                            }else if (item.getTitle().toString().equals(mContext.getString(R.string.label_edit))){
                                CropMachineService cropMachineService = cropMachineServiceList.get(getAdapterPosition());
                                Intent editMachineService = new Intent(mContext, CropMachineServiceManagerActivity.class);
                                editMachineService.putExtra("cropMachineService",cropMachineService);
                                editMachineService.putExtra("cropId",cropMachineService.getMachineId());
                                mContext.startActivity(editMachineService);
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
