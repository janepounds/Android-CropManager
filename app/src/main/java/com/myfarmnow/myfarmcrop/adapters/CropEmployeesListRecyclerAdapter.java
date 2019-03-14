package com.myfarmnow.myfarmcrop.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
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
        holder.mobileTextView.setText(employee.getPhone());
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

        public EmployeeViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.txt_crop_employee_card_title);
            firstNameTextView = itemView.findViewById(R.id.txt_crop_employee_card_first_name);
            lastNameTextView= itemView.findViewById(R.id.txt_crop_employee_card_last_name);
            perTextView = itemView.findViewById(R.id.txt_crop_employee_card_pay_rate);
            employmentStatusTextView = itemView.findViewById(R.id.txt_crop_employee_card_employment_status);
            phoneTextView = itemView.findViewById(R.id.txt_crop_employee_card_phone);
            mobileTextView = itemView.findViewById(R.id.txt_crop_employee_card_mobile);
            payTypeTextView = itemView.findViewById(R.id.txt_crop_employee_card_pay_type);
            payRateTextView = itemView.findViewById(R.id.txt_crop_employee_card_pay_amount);
        }
    }
}
