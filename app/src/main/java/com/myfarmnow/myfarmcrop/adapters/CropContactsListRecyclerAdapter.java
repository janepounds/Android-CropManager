package com.myfarmnow.myfarmcrop.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropContactManagerActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropContact;

import java.util.ArrayList;

public class CropContactsListRecyclerAdapter extends RecyclerView.Adapter<CropContactsListRecyclerAdapter.ContactViewHolder> {
    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropContact> cropContactsList = new ArrayList<>();
    public CropContactsListRecyclerAdapter(Context context, ArrayList<CropContact> cropContacts){
        cropContactsList.addAll(cropContacts);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);

        Log.d("CROP CONTACTS",cropContactsList.size()+" ");
    }


    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.crop_contact_list_card,parent,false);
        ContactViewHolder holder = new ContactViewHolder(view);
        return holder;
    }
    public void appendList(ArrayList<CropContact> cropContacts){

        this.cropContactsList.addAll(cropContacts);
        notifyDataSetChanged();
    }
    public void addCropContact(CropContact cropContact){
        this.cropContactsList.add(cropContact);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<CropContact> cropContacts){

        this.cropContactsList.clear();
        this.cropContactsList.addAll(cropContacts);

        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        CropContact contact = cropContactsList.get(position);
        holder.typeTextView.setText(contact.getType());
        holder.nameTextView.setText(contact.getName());
        holder.businessNameTextView.setText(contact.getBusinessName());
        holder.phoneNumberTextView.setText(contact.getPhoneNumber());
        holder.emailTextView.setText(contact.getEmail());
    }

    @Override
    public int getItemCount() {
        return cropContactsList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder{
        TextView typeTextView,nameTextView,businessNameTextView,phoneNumberTextView,emailTextView;
        ImageView moreButton;

        public ContactViewHolder(View itemView) {
            super(itemView);
            typeTextView = itemView.findViewById(R.id.txt_crop_contact_card_type);
            nameTextView = itemView.findViewById(R.id.txt_crop_contact_card_name);
            businessNameTextView = itemView.findViewById(R.id.txt_crop_contact_card_business_name);
            phoneNumberTextView = itemView.findViewById(R.id.txt_crop_contact_card_phone_number);
            emailTextView = itemView.findViewById(R.id.txt_crop_contact_card_email);
            moreButton = itemView.findViewById(R.id.img_crop_contact_card_more);



            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                    PopupMenu popup = new PopupMenu(wrapper, v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getTitle().equals(mContext.getString(R.string.label_delete))){
                                final CropContact cropContact = cropContactsList.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete "+cropContact.getName()+" Contact?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropContact(cropContact.getId());
                                                cropContactsList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();

                            }else if (item.getTitle().equals(mContext.getString(R.string.label_edit))){
                                CropContact cropContact = cropContactsList.get(getAdapterPosition());
                                Intent editContact = new Intent(mContext, CropContactManagerActivity.class);
                                editContact.putExtra("cropContact",cropContact);
                                mContext.startActivity(editContact);
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
