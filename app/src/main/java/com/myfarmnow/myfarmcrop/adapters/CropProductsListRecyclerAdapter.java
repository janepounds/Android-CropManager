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
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropProductManagerActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropProduct;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.text.NumberFormat;
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
        holder.taxTextView.setText(product.getTaxRate()+" % ");
        holder.rateTxt.setText(CropSettingsSingleton.getInstance().getCurrency()+"  " + NumberFormat.getInstance().format(product.getSellingPrice())+" / "+product.getUnits());

        holder.stockAtHandTextView.setText(product.computeStockAtHand()+" "+product.getUnits());
    }



    @Override
    public int getItemCount() {
        return cropProductsList.size();
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder{

        TextView stockAtHandTextView, rateTxt, taxTextView, nameTextView;
        ImageView editButton, moreButton;
        public ProductViewHolder(View itemView) {
            super(itemView);

            stockAtHandTextView = itemView.findViewById(R.id.txt_crop_product_card_quantity);
            rateTxt = itemView.findViewById(R.id.txt_crop_product_card_selling_price);
            taxTextView = itemView.findViewById(R.id.txt_crop_product_card_tax_rate);
            nameTextView = itemView.findViewById(R.id.txt_crop_product_card_name);
            moreButton = itemView.findViewById(R.id.img_crop_product_card_more);

            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                    PopupMenu popup = new PopupMenu(wrapper, v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getTitle().equals(mContext.getString(R.string.label_delete))){
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
                            }else if (item.getTitle().equals(mContext.getString(R.string.label_edit))){

                                CropProduct cropProduct = cropProductsList.get(getAdapterPosition());
                                Intent editProduct = new Intent(mContext, CropProductManagerActivity.class);
                                editProduct.putExtra("cropProduct",cropProduct);
                                mContext.startActivity(editProduct);
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
