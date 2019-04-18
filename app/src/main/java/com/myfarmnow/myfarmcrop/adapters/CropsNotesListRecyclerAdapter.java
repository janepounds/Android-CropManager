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
import com.myfarmnow.myfarmcrop.activities.CropsNotesManagerActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropNote;

import java.util.ArrayList;

public class CropsNotesListRecyclerAdapter  extends RecyclerView.Adapter<CropsNotesListRecyclerAdapter.CropsNotesViewHolder>  {
    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropNote> cropsNotesList = new ArrayList<>();


    public CropsNotesListRecyclerAdapter(Context context, ArrayList<CropNote> cropsNotes){
        cropsNotesList.addAll(cropsNotes);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);

    }


    @NonNull
    @Override
    public CropsNotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.crop_notes_list_card,parent,false);

        CropsNotesViewHolder holder = new CropsNotesViewHolder(view);
        return holder;
    }
    public void appendList(ArrayList<CropNote> cropsNotes){

        this.cropsNotesList.addAll(cropsNotes);
        notifyDataSetChanged();
    }
    public void addCropsNotes(CropNote cropsNotes){
        this.cropsNotesList.add(cropsNotes);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<CropNote> cropsNotes){

        this.cropsNotesList.clear();
        this.cropsNotesList.addAll(cropsNotes);

        notifyDataSetChanged();


    }


    @Override
    public void onBindViewHolder(@NonNull CropsNotesListRecyclerAdapter.CropsNotesViewHolder holder, int position) {
        CropNote cropsNotes = cropsNotesList.get(position);

        holder.notesDateTxt.setText(cropsNotes.getDate());
        holder.notesTxt.setText(cropsNotes.getNotes());

    }

    @Override
    public int getItemCount() {
        return cropsNotesList.size();
    }

    public class CropsNotesViewHolder extends RecyclerView.ViewHolder{
        TextView notesDateTxt,notesTxt;
        ImageView moreButton;
        public CropsNotesViewHolder(View itemView) {
            super(itemView);
            notesDateTxt = itemView.findViewById(R.id.txt_view_crop_notes_card_date);
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
                                final CropNote cropsNotes = cropsNotesList.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete this note ?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropNote(cropsNotes.getId());
                                                cropsNotesList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();
                            }else if (item.getTitle().toString().equals(mContext.getString(R.string.label_edit))){
                                CropNote cropsNotes = cropsNotesList.get(getAdapterPosition());
                                Intent editCropsNotes = new Intent(mContext, CropsNotesManagerActivity.class);
                                editCropsNotes.putExtra("cropNote", cropsNotes);
                                editCropsNotes.putExtra("cropId",cropsNotes.getParentId());
                                mContext.startActivity(editCropsNotes);
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
