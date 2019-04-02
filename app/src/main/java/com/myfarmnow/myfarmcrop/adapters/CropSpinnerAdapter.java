package com.myfarmnow.myfarmcrop.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;

import java.util.ArrayList;

public class CropSpinnerAdapter extends ArrayAdapter<CropSpinnerItem> {
    ArrayList<CropSpinnerItem> values=new ArrayList<>();
    String fieldLabel;
    public CropSpinnerAdapter(ArrayList<CropSpinnerItem> items,final String fieldLabel, Context context) {
        super(context,  android.R.layout.simple_spinner_item);
        this.fieldLabel =fieldLabel;
        values.add(new CropSpinnerItem() {
            @Override
            public String getId() {
                return null;
            }
            public String toString(){
                return "Select "+fieldLabel;
            }
        });
        values.addAll(items);
    }
    public void add(CropSpinnerItem item){
        values.add(item);
        notifyDataSetChanged();
    }
    public void changeDefaultItem(CropSpinnerItem defaultItem){
        values.remove(0);
        values.add(0,defaultItem);
        notifyDataSetChanged();
    }
    @Override
    public int getCount(){
        return values.size();
    }

    @Override
    public CropSpinnerItem getItem(int position){
        return values.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }


    public void changeItems(ArrayList<CropSpinnerItem> items){
        values.clear();
        values.add(new CropSpinnerItem() {
            @Override
            public String getId() {
                return null;
            }
            public String toString(){
                return "Select "+fieldLabel;
            }
        });
        values.addAll(items);
        notifyDataSetChanged();
    }
    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (CropSpinnerItems array) and the current position
        // You can NOW reference each method you has created in your bean object (CropSpinnerItem class)
        label.setText(values.get(position).toString());

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(values.get(position).toString());

        return label;
    }
}
