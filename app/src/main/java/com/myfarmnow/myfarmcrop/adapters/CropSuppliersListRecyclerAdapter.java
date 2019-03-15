package com.myfarmnow.myfarmcrop.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropSupplierManagerActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropSupplier;

import java.util.ArrayList;

public class CropSuppliersListRecyclerAdapter extends RecyclerView.Adapter<CropSuppliersListRecyclerAdapter.SupplierViewHolder> {

    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropSupplier> cropSuppliersList = new ArrayList<>();

    public CropSuppliersListRecyclerAdapter(Context context, ArrayList<CropSupplier> cropSuppliers){
        cropSuppliersList.addAll(cropSuppliers);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);

        Log.d("CROP FIELDS",cropSuppliersList.size()+" ");
    }
    @NonNull
    @Override
    public SupplierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.crop_customer_list_card,parent,false);

        SupplierViewHolder holder = new SupplierViewHolder(view);
        return holder;
    }

    public void appendList(ArrayList<CropSupplier> cropSuppliers){

        this.cropSuppliersList.addAll(cropSuppliers);
        notifyDataSetChanged();
    }

    public void addCropSupplier(CropSupplier cropSupplier){
        this.cropSuppliersList.add(cropSupplier);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<CropSupplier> cropSuppliers){

        this.cropSuppliersList.clear();
        this.cropSuppliersList.addAll(cropSuppliers);

        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull SupplierViewHolder holder, int position) {

        CropSupplier supplier = cropSuppliersList.get(position);
        holder.nameTextView.setText(supplier.getName());
        holder.taxTextView.setText(supplier.getPhone()+"");
        holder.companyTxt.setText(supplier.getCompany());
        holder.cityTextView.setText(supplier.getInvoiceCityOrTown());
        //holder.dateTextView.setText(supplier.getD());

        holder.quantityTextView.setText(supplier.getOpeningBalance()+"");
    }



    @Override
    public int getItemCount() {
        return cropSuppliersList.size();
    }


    public class SupplierViewHolder extends RecyclerView.ViewHolder{

        TextView  quantityTextView, companyTxt, taxTextView, nameTextView,cityTextView, dateTextView;
        ImageView editButton, deleteButton;
        public SupplierViewHolder(View itemView) {
            super(itemView);

            quantityTextView = itemView.findViewById(R.id.txt_crop_customer_card_opening_balance);
            cityTextView = itemView.findViewById(R.id.txt_crop_customer_card_city_town);
            dateTextView = itemView.findViewById(R.id.txt_crop_customer_card_opening_balance);
            companyTxt = itemView.findViewById(R.id.txt_crop_customer_card_company);
            taxTextView = itemView.findViewById(R.id.txt_crop_customer_card_phone);
            nameTextView = itemView.findViewById(R.id.txt_crop_customer_card_name);
            deleteButton = itemView.findViewById(R.id.img_crop_customer_card_delete);
            editButton = itemView.findViewById(R.id.img_crop_customer_card_edit);


            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CropSupplier cropSupplier = cropSuppliersList.get(getAdapterPosition());
                    Intent editSupplier = new Intent(mContext, CropSupplierManagerActivity.class);
                    editSupplier.putExtra("cropSupplier",cropSupplier);
                    mContext.startActivity(editSupplier);
                }
            });
       
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final CropSupplier cropSupplier = cropSuppliersList.get(getAdapterPosition());
                    new AlertDialog.Builder(mContext)
                            .setTitle("Confirm")
                            .setMessage("Do you really want to delete "+cropSupplier.getName()+" ?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {

                                    MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropSupplier(cropSupplier.getId());
                                    cropSuppliersList.remove(getAdapterPosition());
                                    notifyItemRemoved(getAdapterPosition());

                                }})
                            .setNegativeButton(android.R.string.no, null).show();
                }
            });
        }

    }
}
