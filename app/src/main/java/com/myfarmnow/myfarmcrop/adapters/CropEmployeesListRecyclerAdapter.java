package com.myfarmnow.myfarmcrop.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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
import com.myfarmnow.myfarmcrop.activities.CropEmployeeManagerActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropEmployee;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;

import java.util.ArrayList;

public class CropEmployeesListRecyclerAdapter extends RecyclerView.Adapter<CropEmployeesListRecyclerAdapter.EmployeeViewHolder> {

    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropEmployee> cropEmployeesList = new ArrayList<>();



    public CropEmployeesListRecyclerAdapter(Context context, ArrayList<CropEmployee> cropEmployees){
        cropEmployeesList.addAll(cropEmployees);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);

    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.crop_employee_list_card,parent,false);
        EmployeeViewHolder holder = new EmployeeViewHolder(view);
        return holder;
    }
    public void appendList(ArrayList<CropEmployee> cropEmployees){

        this.cropEmployeesList.addAll(cropEmployees);
        notifyDataSetChanged();
    }
    public void addCropEmployee(CropEmployee cropEmployees){
        this.cropEmployeesList.add(cropEmployees);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<CropEmployee> cropEmployees){

        this.cropEmployeesList.clear();
        this.cropEmployeesList.addAll(cropEmployees);

        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull CropEmployeesListRecyclerAdapter.EmployeeViewHolder holder, int position) {
        CropEmployee employee = cropEmployeesList.get(position);
        holder.titleTextView.setText(employee.getTitle());
        holder.firstNameTextView.setText(employee.getFirstName());
        holder.lastNameTextView.setText(employee.getLastName());
        holder.employmentStatusTextView.setText(employee.getEmploymentStatus());
        holder.phoneTextView.setText(employee.getPhone());
        //holder.mobileTextView.setText(employee.getPhone());
        holder.payTypeTextView.setText(employee.getPayType());
        holder.payRateTextView.setText(employee.getPayAmount()+"");
        holder.perTextView.setText(employee.getPayRate());




    }



    @Override
    public int getItemCount() {
        return cropEmployeesList.size();
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView,firstNameTextView,lastNameTextView,employmentStatusTextView,phoneTextView,
        mobileTextView,payTypeTextView,payRateTextView,perTextView;
        ImageView moreButton;


        public EmployeeViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.txt_crop_employee_card_title);
            firstNameTextView = itemView.findViewById(R.id.txt_crop_employee_card_first_name);
            lastNameTextView= itemView.findViewById(R.id.txt_crop_employee_card_last_name);
            perTextView = itemView.findViewById(R.id.txt_crop_employee_card_pay_rate);
            employmentStatusTextView = itemView.findViewById(R.id.txt_crop_employee_card_employment_status);
            phoneTextView = itemView.findViewById(R.id.txt_crop_employee_card_phone);
            //mobileTextView = itemView.findViewById(R.id.txt_crop_employee_card_mobile);
            payTypeTextView = itemView.findViewById(R.id.txt_crop_employee_card_pay_type);
            payRateTextView = itemView.findViewById(R.id.txt_crop_employee_card_pay_amount);
            moreButton = itemView.findViewById(R.id.img_crop_employee_card_more);

            moreButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                    PopupMenu popup = new PopupMenu(wrapper, v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            if (item.getTitle().toString().equals(mContext.getString(R.string.label_delete))) {
                                final CropEmployee cropEmployee = cropEmployeesList.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete " + cropEmployee.getFirstName() + cropEmployee.getLastName()+ " employee?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropEmployee(cropEmployee.getId());
                                                cropEmployeesList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, null).show();
                            } else if (item.getTitle().toString().equals(mContext.getString(R.string.label_edit))) {
                                CropEmployee cropEmployee = cropEmployeesList.get(getAdapterPosition());
                                Intent editCropEmployee = new Intent(mContext, CropEmployeeManagerActivity.class);
                                editCropEmployee.putExtra("cropEmployee", cropEmployee);
                                mContext.startActivity(editCropEmployee);

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
