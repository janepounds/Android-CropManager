package com.myfarmnow.myfarmcrop.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropDashboardActivity;
import com.myfarmnow.myfarmcrop.models.CropProduct;
import com.myfarmnow.myfarmcrop.models.CropProductItem;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;

import java.text.NumberFormat;
import java.util.ArrayList;

public class CropPreviewItemListRecyclerAdapter extends RecyclerView.Adapter<CropPreviewItemListRecyclerAdapter.ItemViewHolder> {

    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropProductItem> cropItemsList = new ArrayList<>();




    ArrayList<String> deleteItemsId= new ArrayList<>();


    public CropPreviewItemListRecyclerAdapter(Context context, ArrayList<CropProductItem> cropItems){
        cropItemsList.addAll(cropItems);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.crop_estimate_invoice_preview_item_row,parent,false);

        ItemViewHolder holder = new ItemViewHolder(view);
        return holder;
    }

    public void appendList(ArrayList<CropProductItem> cropItems){

        this.cropItemsList.addAll(cropItems);
        notifyDataSetChanged();
    }

    public void addCropProductItem(CropProductItem cropItem){
        this.cropItemsList.add(cropItem);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<CropProductItem> cropItems){

        this.cropItemsList.clear();
        this.cropItemsList.addAll(cropItems);

        notifyDataSetChanged();
    }

    public ArrayList<String> getDeleteItemsId() {
        return deleteItemsId;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, int position) {

        CropProductItem item = cropItemsList.get(position);

        holder.qtyTxt.setText(item.getQuantity()+"");
        holder.taxTxt.setText(item.getTax()+"");
        holder.rateTxt.setText(NumberFormat.getInstance().format(item.getRate()));
        holder.numbertTxtView.setText((position+1)+"");
        holder.productTextView.setText(item.gettProductName());
    }



    @Override
    public int getItemCount() {
        return cropItemsList.size();
    }

    public ArrayList<CropProductItem> getItems() {
        ArrayList<CropProductItem> items = new ArrayList<>();
        for(CropProductItem y:cropItemsList){
            if(y.getProductId() != null){
                items.add(y);
            }
        }
        return items;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder{

        TextView amountTextView, numbertTxtView;
        ImageView  deleteButton;
        TextView productTextView;
        TextView qtyTxt,rateTxt,taxTxt;
        
        CropSpinnerAdapter spinnerAdapter;
     
        public ItemViewHolder(View itemView) {
            super(itemView);
            productTextView = itemView.findViewById(R.id.text_view_crop_invoice_products);
            qtyTxt = itemView.findViewById(R.id.text_view_crop_invoice_qty);
            rateTxt = itemView.findViewById(R.id.text_view_crop_invoice_rate);
            taxTxt = itemView.findViewById(R.id.text_view_crop_invoice_tax);
            amountTextView = itemView.findViewById(R.id.text_view_view_crop_invoice_amount);
            numbertTxtView = itemView.findViewById(R.id.text_view_crop_invoice_items_number);

            TextWatcher watcher =new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    updateAmount();
                }
            };
            qtyTxt.addTextChangedListener(watcher);
            rateTxt.addTextChangedListener(watcher);
            taxTxt.addTextChangedListener(watcher);

        }

        public void updateAmount(){
            try{
                CropProductItem currentItem = cropItemsList.get(getAdapterPosition());
                amountTextView.setText(NumberFormat.getInstance().format(currentItem.computeAmount()));

            }catch (Exception e){
                //do nothing
                e.printStackTrace();
            }


        }

    }
}
