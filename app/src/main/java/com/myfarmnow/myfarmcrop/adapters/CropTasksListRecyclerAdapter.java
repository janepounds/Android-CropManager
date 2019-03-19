package com.myfarmnow.myfarmcrop.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropTasksListActivity;
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

        holder.cropTextView.setText(task.getCropId());
        holder.personnelTextView.setText(task.getEmployeeId());
        holder.recurrenceTextView.setText(task.getRecurrence());
        holder.statusTextView.setText(task.getStatus());
        holder.dateTextView.setText(task.getDate());


    }

    @Override
    public int getItemCount() {
        return cropTasksList.size();
    }

    public class TaskViewHolder  extends RecyclerView.ViewHolder {
        TextView typeTextView, cropTextView, titleTextView,personnelTextView,recurrenceTextView, statusTextView,dateTextView;
        Button moreButton;
        public TaskViewHolder(View itemView) {
            super(itemView);


            typeTextView = itemView.findViewById(R.id.txt_crop_task_card_type);
            cropTextView = itemView.findViewById(R.id.txt_crop_task_card_crop);
            titleTextView = itemView.findViewById(R.id.txt_crop_task_card_title);
            personnelTextView = itemView.findViewById(R.id.txt_crop_task_card_personnel);
            recurrenceTextView = itemView.findViewById(R.id.txt_crop_task_card_recurrence);
            statusTextView = itemView.findViewById(R.id.txt_crop_task_card_status);

            dateTextView = itemView.findViewById(R.id.txt_crop_task_card_date);

            moreButton = itemView.findViewById(R.id.img_crop_task_card_edit);

        }

    }
}
