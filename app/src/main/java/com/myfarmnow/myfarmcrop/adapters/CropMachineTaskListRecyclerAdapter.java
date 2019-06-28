package com.myfarmnow.myfarmcrop.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
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
import com.myfarmnow.myfarmcrop.activities.CropMachineTaskManagerActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropMachineTask;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.text.NumberFormat;
import java.util.ArrayList;

public class CropMachineTaskListRecyclerAdapter extends RecyclerView.Adapter<CropMachineTaskListRecyclerAdapter.MachineTaskViewHolder> {
    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropMachineTask> cropMachineTaskList = new ArrayList<>();


    public CropMachineTaskListRecyclerAdapter(Context context, ArrayList<CropMachineTask> cropMachineTasks){
        cropMachineTaskList.addAll(cropMachineTasks);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);

    }



    @NonNull
    @Override
    public MachineTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.crop_machine_task_list_card,parent,false);

        MachineTaskViewHolder holder = new MachineTaskViewHolder(view);
        return holder;
    }

    public void appendList(ArrayList<CropMachineTask> cropMachineTasks){

        this.cropMachineTaskList.addAll(cropMachineTasks);
        notifyDataSetChanged();
    }
    public void addCropMachineTask(CropMachineTask cropMachineTask){
        this.cropMachineTaskList.add(cropMachineTask);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<CropMachineTask> cropMachineTasks){

        this.cropMachineTaskList.clear();
        this.cropMachineTaskList.addAll(cropMachineTasks);

        notifyDataSetChanged();


    }

    @Override
    public void onBindViewHolder(@NonNull final MachineTaskViewHolder holder, int position) {
        CropMachineTask machineTask = cropMachineTaskList.get(position);
        holder.costLayout.setVisibility(View.VISIBLE);
        holder.startDateTxt.setText(machineTask.getStartDate());
        holder.titleTxt.setText(machineTask.getTitle());
        holder.personnelTxt.setText(machineTask.getEmployeeName());
        holder.recurrenceTxt.setText(machineTask.getRecurrence());
        holder.statusTxt.setText(machineTask.getStatus());
        holder.costTxt.setText(CropSettingsSingleton.getInstance().getCurrency()+" " + NumberFormat.getInstance().format(machineTask.getCost())+"");

        if(machineTask.getDescription() != null){

            TextView descriptionTxt = new TextView(mContext);
            descriptionTxt.setText(machineTask.getDescription());

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
        return cropMachineTaskList.size();
    }


    public class MachineTaskViewHolder extends RecyclerView.ViewHolder{

        TextView startDateTxt,titleTxt,personnelTxt,recurrenceTxt,statusTxt,costTxt;
        LinearLayout hideShowLayout,expandContentLayout,costLayout;
        ImageView moreButton,showHideRemarksButton;
        public MachineTaskViewHolder(View itemView) {
            super(itemView);
            startDateTxt = itemView.findViewById(R.id.txt_view_crop_machine_task_card_start_date);
            titleTxt = itemView.findViewById(R.id.txt_crop_machine_task_card_title);
            personnelTxt = itemView.findViewById(R.id.txt_view_crop_machine_task_card_personnel);
            recurrenceTxt = itemView.findViewById(R.id.txt_view_crop_machine_task_card_recurring);
            statusTxt = itemView.findViewById(R.id.txt_view_crop_machine_task_card_status);
            costTxt = itemView.findViewById(R.id.txt_view_crop_machine_task_card_cost);

            costLayout = itemView.findViewById(R.id.layout_crop_service_card_cost);
            hideShowLayout = itemView.findViewById(R.id.layout_crop_scouting_card_show_hide);
            expandContentLayout = itemView.findViewById(R.id.layout_crop_machine_task_expand);
            moreButton = itemView.findViewById(R.id.img_crop_machine_task_card_more);
            showHideRemarksButton = itemView.findViewById(R.id.img_crop_machine_task_card_show_description);


            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                    PopupMenu popup = new PopupMenu(wrapper, v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            if (item.getTitle().toString().equals(mContext.getString(R.string.label_delete))){
                                final CropMachineTask cropMachineTask = cropMachineTaskList.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete this "+cropMachineTask.getTitle()+" task ?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropMachineTask(cropMachineTask.getId());
                                                cropMachineTaskList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();
                            }else if (item.getTitle().toString().equals(mContext.getString(R.string.label_edit))){
                                CropMachineTask cropMachineTask = cropMachineTaskList.get(getAdapterPosition());
                                Intent editMachineTask = new Intent(mContext, CropMachineTaskManagerActivity.class);
                                editMachineTask.putExtra("cropMachineTask",cropMachineTask);
                                editMachineTask.putExtra("machineId",cropMachineTask.getMachineId());
                                mContext.startActivity(editMachineTask);
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
