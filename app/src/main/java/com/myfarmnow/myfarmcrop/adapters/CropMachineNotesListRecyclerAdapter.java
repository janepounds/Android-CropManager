package com.myfarmnow.myfarmcrop.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropMachineNotesManagerActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropNote;

import java.util.ArrayList;

public class CropMachineNotesListRecyclerAdapter extends RecyclerView.Adapter<CropMachineNotesListRecyclerAdapter.MachineNotesViewHolder>  {
    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropNote> cropMachineNotesList = new ArrayList<>();


    public CropMachineNotesListRecyclerAdapter(Context context, ArrayList<CropNote> cropMachineNotes){
        cropMachineNotesList.addAll(cropMachineNotes);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);

    }


    @NonNull
    @Override
    public MachineNotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.crop_notes_list_card,parent,false);

        MachineNotesViewHolder holder = new MachineNotesViewHolder(view);
        return holder;
    }
    public void appendList(ArrayList<CropNote> cropMachineNotes){

        this.cropMachineNotesList.addAll(cropMachineNotes);
        notifyDataSetChanged();
    }
    public void addCropMachineNotes(CropNote cropMachineNotes){
        this.cropMachineNotesList.add(cropMachineNotes);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<CropNote> cropMachineNotes){

        this.cropMachineNotesList.clear();
        this.cropMachineNotesList.addAll(cropMachineNotes);

        notifyDataSetChanged();


    }


    @Override
    public void onBindViewHolder(@NonNull CropMachineNotesListRecyclerAdapter.MachineNotesViewHolder holder, int position) {
        CropNote machineNotes = cropMachineNotesList.get(position);
        holder.categoryTxt.setVisibility(View.VISIBLE);
        holder.notesDateTxt.setText(machineNotes.getDate());
        holder.categoryTxt.setText(machineNotes.getNotes());

        holder.notesTxt.setText(machineNotes.getNotes());

    }

    @Override
    public int getItemCount() {
        return cropMachineNotesList.size();
    }

    public class MachineNotesViewHolder extends RecyclerView.ViewHolder{
        TextView categoryTxt,notesDateTxt,notesTxt;
        ImageView moreButton;
        public MachineNotesViewHolder(View itemView) {
            super(itemView);
            notesDateTxt = itemView.findViewById(R.id.txt_view_crop_notes_card_date);
            categoryTxt = itemView.findViewById(R.id.txt_view_crop_notes_card_category);
            notesTxt = itemView.findViewById(R.id.txt_view_crop_notes_card_notes);
            moreButton = itemView.findViewById(R.id.img_crop_notes_card_more);
            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                    PopupMenu popup = new PopupMenu(wrapper, v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            if (item.getTitle().toString().equals(mContext.getString(R.string.label_delete))){
                                final CropNote cropMachineNotes = cropMachineNotesList.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete this "+cropMachineNotes.getCategory()+" notes ?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropNote(cropMachineNotes.getId());
                                                cropMachineNotesList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();
                            }else if (item.getTitle().toString().equals(mContext.getString(R.string.label_edit))){
                                CropNote cropMachineNotes = cropMachineNotesList.get(getAdapterPosition());
                                Intent editMachineNotes = new Intent(mContext, CropMachineNotesManagerActivity.class);
                                editMachineNotes.putExtra("cropMachineNotes", String.valueOf(cropMachineNotes));
                                editMachineNotes.putExtra("machineId",cropMachineNotes.getIsFor());
                                mContext.startActivity(editMachineNotes);
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
