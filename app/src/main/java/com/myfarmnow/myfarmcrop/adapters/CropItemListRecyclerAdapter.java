package com.myfarmnow.myfarmcrop.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropDashboardActivity;
import com.myfarmnow.myfarmcrop.models.CropEstimateItem;
import com.myfarmnow.myfarmcrop.models.CropProductItem;
import com.myfarmnow.myfarmcrop.models.CropProduct;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CropItemListRecyclerAdapter extends RecyclerView.Adapter<CropItemListRecyclerAdapter.ItemViewHolder> {

    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropProductItem> cropItemsList = new ArrayList<>();
    ArrayList<CropSpinnerItem> productArrayList = new ArrayList<>();



    ArrayList<String> deleteItemsId= new ArrayList<>();

    TextView subTotalTextView;

    public CropItemListRecyclerAdapter(Context context, ArrayList<CropProductItem> cropItems, ArrayList<CropProduct> productArrayList, TextView subTotalTextView){
        cropItemsList.addAll(cropItems);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);
        for(CropSpinnerItem x:productArrayList){
           this.productArrayList.add(x);
        }
        this.subTotalTextView = subTotalTextView;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.crop_estimate_item_row,parent,false);

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

        if(item.getProductId() != null){
            holder.qtyTxt.setText(item.getQuantity()+"");
            holder.taxTxt.setText(item.getTax()+"");
            holder.rateTxt.setText(item.getRate()+"");
            CropDashboardActivity.selectSpinnerItemById(holder.productSpinner,item.getProductId());

        }


      

      
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

        TextView amountTextView;
        ImageView  deleteButton;
        Spinner productSpinner;
        EditText qtyTxt,rateTxt,taxTxt;
        
        CropSpinnerAdapter spinnerAdapter;
     
        public ItemViewHolder(View itemView) {
            super(itemView);
            productSpinner = itemView.findViewById(R.id.spinner_crop_invoice_products);
            qtyTxt = itemView.findViewById(R.id.txt_crop_invoice_qty);
            rateTxt = itemView.findViewById(R.id.txt_crop_invoice_rate);
            taxTxt = itemView.findViewById(R.id.txt_crop_invoice_tax);
            amountTextView = itemView.findViewById(R.id.txt_view_crop_invoice_amount);
            deleteButton = itemView.findViewById(R.id.img_crop_invoice_delete);
            spinnerAdapter = new CropSpinnerAdapter(productArrayList,"Item",mContext);
            taxTxt.setEnabled(false);
            rateTxt.setEnabled(false);
            qtyTxt.setEnabled(false);
            productSpinner.setAdapter(spinnerAdapter);
            productSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position != 0){

                        CropSpinnerItem x= (CropSpinnerItem)productSpinner.getSelectedItem();


                        CropProduct product = (CropProduct) x;

                        CropProductItem currentItem = cropItemsList.get(getAdapterPosition());
                        if(!product.getId().equals(currentItem.getProductId())){
                            currentItem.setRate(product.getSellingPrice());
                            currentItem.setTax(product.getTaxRate());
                            currentItem.setProductId(product.getId());
                            currentItem.setQuantity(1);
                            taxTxt.setText(currentItem.getTax()+"");
                            rateTxt.setText(currentItem.getRate()+"");
                            qtyTxt.setText(currentItem.getQuantity()+"");

                            Log.d("RATE",product.getSellingPrice()+ "=="+currentItem.getRate());
                            updateAmount();
                        }


                        taxTxt.setEnabled(true);
                        rateTxt.setEnabled(true);
                        qtyTxt.setEnabled(true);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            TextWatcher watcher =new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    updateAmount();
                }

                @Override
                public void afterTextChanged(Editable s) {
                    updateAmount();
                }
            };
            qtyTxt.addTextChangedListener(watcher);
            rateTxt.addTextChangedListener(watcher);
            taxTxt.addTextChangedListener(watcher);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        deleteItemsId.add(cropItemsList.get(getAdapterPosition()).getId());
                        cropItemsList.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        updateSum();
                    }catch (Exception e){

                    }

                }
            });
        }

        public void updateAmount(){
            try{
                float tax = Float.parseFloat(taxTxt.getText().toString());
                float price = Float.parseFloat(rateTxt.getText().toString());
                float quantity = Float.parseFloat(qtyTxt.getText().toString());
                CropProductItem currentItem = cropItemsList.get(getAdapterPosition());
                currentItem.setTax(tax);
                currentItem.setRate(price);
                currentItem.setQuantity(quantity);
                amountTextView.setText(currentItem.computeAmount()+"");
                updateSum();

            }catch (Exception e){
                //do nothing
                e.printStackTrace();
            }


        }

    }
    public void updateSum(){
        float sum =0;
        for(CropProductItem x: cropItemsList){
            sum+=x.computeAmount();
        }
        subTotalTextView.setText(sum+"");
    }
}
