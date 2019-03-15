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
import com.myfarmnow.myfarmcrop.activities.CropProductManagerActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropProduct;

import java.util.ArrayList;

public class CropProductsListRecyclerAdapter extends RecyclerView.Adapter<CropProductsListRecyclerAdapter.ProductViewHolder> {

    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropProduct> cropProductsList = new ArrayList<>();

    public CropProductsListRecyclerAdapter(Context context, ArrayList<CropProduct> cropProducts){
        cropProductsList.addAll(cropProducts);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);

        Log.d("CROP FIELDS",cropProductsList.size()+" ");
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.crop_product_list_card,parent,false);

        ProductViewHolder holder = new ProductViewHolder(view);
        return holder;
    }

    public void appendList(ArrayList<CropProduct> cropProducts){

        this.cropProductsList.addAll(cropProducts);
        notifyDataSetChanged();
    }

    public void addCropProduct(CropProduct cropProduct){
        this.cropProductsList.add(cropProduct);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<CropProduct> cropProducts){

        this.cropProductsList.clear();
        this.cropProductsList.addAll(cropProducts);

        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        CropProduct product = cropProductsList.get(position);
        holder.nameTextView.setText(product.getName());
        holder.taxTextView.setText(product.getTaxRate()+"");
        holder.rateTxt.setText(product.getSellingPrice()+"");

        holder.quantityTextView.setText(product.getOpeningQuantity()+product.getUnits());
    }



    @Override
    public int getItemCount() {
        return cropProductsList.size();
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder{

        TextView  quantityTextView, rateTxt, taxTextView, nameTextView;
        ImageView editButton, deleteButton;
        public ProductViewHolder(View itemView) {
            super(itemView);

            quantityTextView = itemView.findViewById(R.id.txt_crop_product_card_quantity);
            rateTxt = itemView.findViewById(R.id.txt_crop_product_card_selling_price);
            taxTextView = itemView.findViewById(R.id.txt_crop_product_card_tax_rate);
            nameTextView = itemView.findViewById(R.id.txt_crop_product_card_name);
            deleteButton = itemView.findViewById(R.id.img_crop_product_card_delete);
            editButton = itemView.findViewById(R.id.img_crop_product_card_edit);


            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CropProduct cropProduct = cropProductsList.get(getAdapterPosition());
                    Intent editProduct = new Intent(mContext, CropProductManagerActivity.class);
                    editProduct.putExtra("cropProduct",cropProduct);
                    mContext.startActivity(editProduct);
                }
            });
       
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final CropProduct cropProduct = cropProductsList.get(getAdapterPosition());
                    new AlertDialog.Builder(mContext)
                            .setTitle("Confirm")
                            .setMessage("Do you really want to delete "+cropProduct.getName()+" ?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {

                                    MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropProduct(cropProduct.getId());
                                    cropProductsList.remove(getAdapterPosition());
                                    notifyItemRemoved(getAdapterPosition());

                                }})
                            .setNegativeButton(android.R.string.no, null).show();
                }
            });
        }

    }
}
