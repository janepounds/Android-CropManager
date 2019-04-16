package com.myfarmnow.myfarmcrop.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropCustomerManagerActivity;
import com.myfarmnow.myfarmcrop.activities.CropFertilizerApplicationManagerActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropCustomer;
import com.myfarmnow.myfarmcrop.models.CropFertilizerApplication;
import com.myfarmnow.myfarmcrop.models.CropInvoice;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CropCustomersListRecyclerAdapter extends RecyclerView.Adapter<CropCustomersListRecyclerAdapter.CustomerViewHolder> {

    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropCustomer> cropCustomersList = new ArrayList<>();

    public CropCustomersListRecyclerAdapter(Context context, ArrayList<CropCustomer> cropCustomers){
        cropCustomersList.addAll(cropCustomers);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);

        Log.d("CROP FIELDS",cropCustomersList.size()+" ");
    }
    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.crop_customer_list_card,parent,false);

        CustomerViewHolder holder = new CustomerViewHolder(view);
        return holder;
    }

    public void appendList(ArrayList<CropCustomer> cropCustomers){

        this.cropCustomersList.addAll(cropCustomers);
        notifyDataSetChanged();
    }

    public void addCropCustomer(CropCustomer cropCustomer){
        this.cropCustomersList.add(cropCustomer);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<CropCustomer> cropCustomers){

        this.cropCustomersList.clear();
        this.cropCustomersList.addAll(cropCustomers);

        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {

        CropCustomer customer = cropCustomersList.get(position);
        holder.nameTextView.setText(customer.getName());
        holder.taxTextView.setText(customer.getPhone()+"");
        holder.companyTxt.setText(customer.getCompany());
        holder.cityTextView.setText(customer.getShippingCityOrTown());
       //holder.dateTextView.setText(customer.getD());


        ArrayList<CropInvoice> invoices = MyFarmDbHandlerSingleton.getHandlerInstance(mContext).getCropInvoicesByCustomer(customer.getId());

        int unpaid =0;
        float balance = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<Date> dueDates = new ArrayList<>();
        for(CropInvoice x : invoices){
            if(!x.determineStatus(mContext).equals(mContext.getString(R.string.invoice_status_paid))){
                unpaid++;
                balance += x.computeBalance();
                holder.dateTextView.setText(CropSettingsSingleton.getInstance().convertToUserFormat(x.getDueDate()));
                holder.duePaymentLayout.setVisibility(View.VISIBLE);
            }
        }
        holder.quantityTextView.setText(CropSettingsSingleton.getInstance().getCurrency()+ NumberFormat.getInstance().format(customer.getOpeningBalance()+balance));
        holder.unpaidInvoicesTextView.setText(unpaid+"/"+invoices.size()+" Unpaid");


    }



    @Override
    public int getItemCount() {
        return cropCustomersList.size();
    }


    public class CustomerViewHolder extends RecyclerView.ViewHolder{

        TextView  quantityTextView, companyTxt, taxTextView, nameTextView,cityTextView, dateTextView,unpaidInvoicesTextView;
        ImageView  moreButton;
        LinearLayout duePaymentLayout;
        public CustomerViewHolder(View itemView) {
            super(itemView);

            quantityTextView = itemView.findViewById(R.id.txt_crop_customer_card_opening_balance);
            unpaidInvoicesTextView = itemView.findViewById(R.id.img_crop_customer_card_unpaid_invoices);
            cityTextView = itemView.findViewById(R.id.txt_crop_customer_card_city_town);
            dateTextView = itemView.findViewById(R.id.txt_crop_customer_card_due_payment_date);
            companyTxt = itemView.findViewById(R.id.txt_crop_customer_card_company);
            taxTextView = itemView.findViewById(R.id.txt_crop_customer_card_phone);
            nameTextView = itemView.findViewById(R.id.txt_crop_customer_card_name);
            moreButton = itemView.findViewById(R.id.img_crop_customer_card_more);
            duePaymentLayout = itemView.findViewById(R.id.layout_crop_customer_card_due_payment);

            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                    PopupMenu popup = new PopupMenu(wrapper, v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getTitle().equals(mContext.getString(R.string.label_delete))){
                                final CropCustomer cropCustomer = cropCustomersList.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete "+cropCustomer.getName()+" ?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropCustomer(cropCustomer.getId());
                                                cropCustomersList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();
                            }else if (item.getTitle().equals(mContext.getString(R.string.label_edit))){
                                CropCustomer cropCustomer = cropCustomersList.get(getAdapterPosition());
                                Intent editCustomer = new Intent(mContext, CropCustomerManagerActivity.class);
                                editCustomer.putExtra("cropCustomer",cropCustomer);
                                mContext.startActivity(editCustomer);
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
