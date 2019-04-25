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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;

import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropNotification;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.util.ArrayList;

public class CropsNotificationsListRecyclerAdapter extends RecyclerView.Adapter<CropsNotificationsListRecyclerAdapter.CropsNotificationsViewHolder>  {
    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropNotification> cropsNotificationsList = new ArrayList<>();


    public CropsNotificationsListRecyclerAdapter(Context context, ArrayList<CropNotification> cropsNotifications){
        cropsNotificationsList.addAll(cropsNotifications);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);

    }


    @NonNull
    @Override
    public CropsNotificationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.notification_card,parent,false);

        CropsNotificationsViewHolder holder = new CropsNotificationsViewHolder(view);
        return holder;
    }
    public void appendList(ArrayList<CropNotification> cropsNotifications){

        this.cropsNotificationsList.addAll(cropsNotifications);
        notifyDataSetChanged();
    }
    public void addCropsNotifications(CropNotification cropsNotifications){
        this.cropsNotificationsList.add(cropsNotifications);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<CropNotification> cropsNotifications){

        this.cropsNotificationsList.clear();
        this.cropsNotificationsList.addAll(cropsNotifications);

        notifyDataSetChanged();


    }



    @Override
    public void onBindViewHolder(@NonNull CropsNotificationsViewHolder holder, int position) {
        CropNotification cropsNotifications = cropsNotificationsList.get(position);

        String date = CropSettingsSingleton.getInstance().convertToUserFormat(cropsNotifications.getActionDate());
        holder.messageTextView.setText(date+" : "+cropsNotifications.getMessage());

        if(cropsNotifications.getType().toLowerCase().equals(mContext.getString(R.string.notification_type_cultivation).toLowerCase())){
                holder.iconImage.setImageResource(R.drawable.cultivation);
        }
        else if(cropsNotifications.getType().toLowerCase().equals(mContext.getString(R.string.notification_type_irrigation).toLowerCase())){
            holder.iconImage.setImageResource(R.drawable.irrigation);
        }
        else if(cropsNotifications.getType().toLowerCase().equals(mContext.getString(R.string.notification_type_harvest).toLowerCase())){
            holder.iconImage.setImageResource(R.drawable.harvesting);
        }
        else if(cropsNotifications.getType().toLowerCase().equals(mContext.getString(R.string.notification_type_spraying).toLowerCase())){
            holder.iconImage.setImageResource(R.drawable.spraying);
        }
        else if(cropsNotifications.getType().toLowerCase().equals(mContext.getString(R.string.notification_type_scouting).toLowerCase())){
            holder.iconImage.setImageResource(R.drawable.scouting);
        }
        else if(cropsNotifications.getType().toLowerCase().equals(mContext.getString(R.string.notification_type_fertilizer_application).toLowerCase())){
            holder.iconImage.setImageResource(R.drawable.fertilizer_application);
        }
        else if(cropsNotifications.getType().toLowerCase().equals(mContext.getString(R.string.notification_type_transplanting).toLowerCase())){
            holder.iconImage.setImageResource(R.drawable.transplanting);
        }
        else if(cropsNotifications.getType().toLowerCase().equals(mContext.getString(R.string.notification_type_soil_analysis).toLowerCase())){
            holder.iconImage.setImageResource(R.drawable.event);
        }
        else if(cropsNotifications.getType().toLowerCase().equals(mContext.getString(R.string.notification_type_machine_task).toLowerCase())){
            holder.iconImage.setImageResource(R.drawable.event);
        }
        else if(cropsNotifications.getType().toLowerCase().equals(mContext.getString(R.string.notification_type_service).toLowerCase())){
            holder.iconImage.setImageResource(R.drawable.event);
        }
        else{
            holder.iconImage.setImageResource(R.drawable.event);

        }

    }

    @Override
    public int getItemCount() {
        return cropsNotificationsList.size();
    }

    public class CropsNotificationsViewHolder extends RecyclerView.ViewHolder{
        TextView notesDateTxt,messageTextView;
        ImageView iconImage, doneBtnImageView;
        public CropsNotificationsViewHolder(View itemView) {
            super(itemView);
            notesDateTxt = itemView.findViewById(R.id.txt_view_crop_notes_card_date);
            messageTextView = itemView.findViewById(R.id.text_view_notification_card_message);
            iconImage = itemView.findViewById(R.id.img_notification_card_icon);
            doneBtnImageView = itemView.findViewById(R.id.img_notification_card_done);
            doneBtnImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View alertLayout = layoutInflater.inflate(R.layout.notification_complete, null);
                    Button btn_mark_done = alertLayout.findViewById(R.id.btn_mark_done);
                    Button btn_cancel = alertLayout.findViewById(R.id.btn_cancel);
                    TextView tv_marktext = alertLayout.findViewById(R.id.tv_marktext);
                    try{

                        final CropNotification notification = cropsNotificationsList.get(getAdapterPosition());
                        tv_marktext.setText(notification.getMessage() );
                        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(mContext);
                        alert.setView(alertLayout);
                        // disallow cancel of AlertDialog on click of back button and outside touch
                        alert.setCancelable(false);

                        final android.app.AlertDialog dialog = alert.create();

                        btn_mark_done.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                notification.setStatus(mContext.getString(R.string.notification_status_done));
                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).updateCropNotification(notification);
                                cropsNotificationsList.remove(getAdapterPosition());
                                notifyItemRemoved(getAdapterPosition());
                                dialog.dismiss();
                            }
                        });

                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();

                            }
                        });

                        dialog.show();
                    }catch (ArrayIndexOutOfBoundsException e){

                    }


                }
            });


        }
    }
}
