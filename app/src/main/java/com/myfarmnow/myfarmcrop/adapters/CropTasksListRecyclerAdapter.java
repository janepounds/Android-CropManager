package com.myfarmnow.myfarmcrop.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropTaskManagerActivity;
import com.myfarmnow.myfarmcrop.activities.CropTasksListActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropTask;
import com.myfarmnow.myfarmcrop.models.CropTask;

import java.util.ArrayList;

public class CropTasksListRecyclerAdapter extends RecyclerView.Adapter<CropTasksListRecyclerAdapter.TaskViewHolder>  {
    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropTask> cropTasksList = new ArrayList<>();

    public CropTasksListRecyclerAdapter(Context context, ArrayList<CropTask> cropTasks) {
        cropTasksList.addAll(cropTasks);
        mContext = context;
        layoutInflater = LayoutInflater.from(mContext);

    }

    public void appendList(ArrayList<CropTask> cropTasks){

        this.cropTasksList.addAll(cropTasks);
        notifyDataSetChanged();
    }

    public void addCropTask(CropTask cropTask){
        this.cropTasksList.add(cropTask);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<CropTask> cropTasks){

        this.cropTasksList.clear();
        this.cropTasksList.addAll(cropTasks);

        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.crop_task_list_card,parent,false);

        TaskViewHolder holder = new TaskViewHolder(view);
        return holder;

    }


    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        CropTask task = cropTasksList.get(position);
        holder.typeTextView.setText(task.getType());
        holder.titleTextView.setText(task.getTitle());

        holder.cropTextView.setText(task.getCropName());
        holder.personnelTextView.setText(task.getEmployeeName());
        holder.recurrenceTextView.setText(task.getRecurrence());
        holder.statusTextView.setText(task.getStatus());
        holder.dateTextView.setText(task.getDate());

//        Log.d("DUE DATE",task.getEmployeeId());


    }

    @Override
    public int getItemCount() {
        return cropTasksList.size();
    }

    public class TaskViewHolder  extends RecyclerView.ViewHolder {
        TextView typeTextView, cropTextView, titleTextView,personnelTextView,recurrenceTextView, statusTextView,dateTextView;
        ImageView moreButton;
        public TaskViewHolder(View itemView) {
            super(itemView);
            typeTextView = itemView.findViewById(R.id.txt_crop_task_card_type);
            cropTextView = itemView.findViewById(R.id.txt_crop_task_card_crop);
            titleTextView = itemView.findViewById(R.id.txt_crop_task_card_title);
            personnelTextView = itemView.findViewById(R.id.txt_crop_task_card_personnel);
            recurrenceTextView = itemView.findViewById(R.id.txt_crop_task_card_recurrence);
            statusTextView = itemView.findViewById(R.id.txt_crop_task_card_status);

            dateTextView = itemView.findViewById(R.id.txt_crop_task_card_date);

            moreButton = itemView.findViewById(R.id.img_crop_task_card_more);
            
            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                    PopupMenu popup = new PopupMenu(wrapper, v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getTitle().equals(mContext.getString(R.string.label_delete))){
                                final CropTask cropTask = cropTasksList.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage(mContext.getString(R.string.delete_prompt_message)+cropTask.getTitle()+" ?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropTask(cropTask.getId());
                                                cropTasksList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();
                            }else if (item.getTitle().equals(mContext.getString(R.string.label_edit))){

                                CropTask cropTask = cropTasksList.get(getAdapterPosition());
                                Intent editTask = new Intent(mContext, CropTaskManagerActivity.class);
                                editTask.putExtra("cropTask",cropTask);
                                mContext.startActivity(editTask);
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
