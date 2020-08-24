package com.myfarmnow.myfarmcrop.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
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
import com.myfarmnow.myfarmcrop.activities.CropMachineNotesListActivity;
import com.myfarmnow.myfarmcrop.activities.CropMachineServicesListActivity;
import com.myfarmnow.myfarmcrop.activities.CropMachineTasksListActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropMachine;

import java.util.ArrayList;

public class CropMachinesListRecyclerAdapter extends RecyclerView.Adapter<CropMachinesListRecyclerAdapter.MachineViewHolder> {
    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropMachine> cropMachinesList = new ArrayList<>();
    public CropMachinesListRecyclerAdapter(Context context, ArrayList<CropMachine> cropMachines){
        cropMachinesList.addAll(cropMachines);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);

        Log.d("CROP MACHINES",cropMachinesList.size()+" ");
    }




    @NonNull
    @Override
    public MachineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.crop_machine_list_card,parent,false);
      MachineViewHolder holder = new MachineViewHolder(view);
        return holder;
    }
    public void appendList(ArrayList<CropMachine> cropMachines){

        this.cropMachinesList.addAll(cropMachines);
        notifyDataSetChanged();
    }
    public void addCropMachine(CropMachine cropMachine){
        this.cropMachinesList.add(cropMachine);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<CropMachine> cropMachines){

        this.cropMachinesList.clear();
        this.cropMachinesList.addAll(cropMachines);

        notifyDataSetChanged();
    }




    @Override
    public void onBindViewHolder(@NonNull MachineViewHolder holder, int position) {
        CropMachine machine = cropMachinesList.get(position);
        holder.machineNameTextView.setText(machine.getName());
        holder.categoryTextView.setText(machine.getCategory());
        holder.modelTextView.setText(machine.getModel());
        holder.registrationNumberTextView.setText(machine.getRegistrationNumber()+"");
        holder.locationTextView.setText(machine.getStorageLocation());


    }

    @Override
    public int getItemCount() {
        return cropMachinesList.size();
    }

    public class MachineViewHolder extends RecyclerView.ViewHolder {
        TextView machineNameTextView,categoryTextView, modelTextView, registrationNumberTextView,locationTextView;
        ImageView moreButton ;

        Button serviceBtn, notesBtn, tasksBtn;

        public MachineViewHolder(View itemView) {
            super(itemView);
            machineNameTextView = itemView.findViewById(R.id.txt_crop_machine_card_name);
            categoryTextView = itemView.findViewById(R.id.txt_crop_machine_card_category);
            modelTextView = itemView.findViewById(R.id.txt_crop_machine_card_model);
            registrationNumberTextView = itemView.findViewById(R.id.txt_crop_machine_card_registration_number);
            locationTextView = itemView.findViewById(R.id.txt_crop_machine_card_storage_location);
            moreButton = itemView.findViewById(R.id.img_crop_machine_card_more);
            tasksBtn = itemView.findViewById(R.id.btn_machine_tasks);
            serviceBtn = itemView.findViewById(R.id.btn_machine_service);
            notesBtn = itemView.findViewById(R.id.btn_machine_notes);

            serviceBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CropMachine cropMachine = cropMachinesList.get(getAdapterPosition());
                    Intent editMachine = new Intent(mContext, CropMachineServicesListActivity.class);
                    editMachine.putExtra("machineId",cropMachine.getId());
                    mContext.startActivity(editMachine);
                }
            });
            tasksBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CropMachine cropMachine = cropMachinesList.get(getAdapterPosition());
                    Intent editMachine = new Intent(mContext, CropMachineTasksListActivity.class);
                    editMachine.putExtra("machineId",cropMachine.getId());
                    mContext.startActivity(editMachine);
                }
            });
            notesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CropMachine cropMachine = cropMachinesList.get(getAdapterPosition());
                    Intent editMachine = new Intent(mContext, CropMachineNotesListActivity.class);
                    editMachine.putExtra("machineId",cropMachine.getId());
                    mContext.startActivity(editMachine);
                }
            });

            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                    PopupMenu popup = new PopupMenu(wrapper, v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getTitle().equals(mContext.getString(R.string.label_delete))){
                                final CropMachine cropMachine = cropMachinesList.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete "+cropMachine.getName()+" machine?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropMachine(cropMachine.getId());
                                                cropMachinesList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();

                            }else if (item.getTitle().equals(mContext.getString(R.string.label_edit))){
                                CropMachine cropMachine = cropMachinesList.get(getAdapterPosition());
                                Intent editMachine = new Intent(mContext, CropMachineManagerActivity.class);
                                editMachine.putExtra("cropMachine",cropMachine);
                                mContext.startActivity(editMachine);
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
