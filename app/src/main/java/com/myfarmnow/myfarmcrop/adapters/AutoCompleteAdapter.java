package com.myfarmnow.myfarmcrop.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import com.myfarmnow.myfarmcrop.R;

import com.myfarmnow.myfarmcrop.models.livestock_models.BreedingStock;

public class AutoCompleteAdapter extends ArrayAdapter<BreedingStock> {

    Context context;
    int resource;
    int textViewResourceId;
    List<BreedingStock> mList, filteredAnimal, mListAll;

    public AutoCompleteAdapter(Context context, int resource, int textViewResourceId, List<BreedingStock> mList) {
        super(context, resource, textViewResourceId, mList);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.mList = mList;
        mListAll = mList;
        filteredAnimal = new ArrayList<BreedingStock>();
    }

    @Override
    public BreedingStock getItem(int position) {

        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_text, parent, false);
            TextView textView = (TextView) view.findViewById(R.id.label);
            textView.setText(mList.get(position).getName());
        }
        BreedingStock Animal = mList.get(position);
        if (Animal != null) {
            TextView textView = (TextView) view.findViewById(R.id.label);
            if (textView != null) {
                textView.setText(Animal.getName());
            }

        }
        return view;
    }

    @Override
    public Filter getFilter() {

        return nameFilter;
    }

    Filter nameFilter = new Filter() {

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            List<BreedingStock> filteredList = (List<BreedingStock>) results.values;

            if (results != null && results.count > 0) {
                clear();
                for (BreedingStock Animal : filteredList) {
                    add(Animal);
                }
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null) {
                filteredAnimal.clear();
                for (BreedingStock Animal : mListAll) {
                    if (Animal.getName().contains(constraint)) {
                        filteredAnimal.add(Animal);
                    }
                }
                filterResults.values = filteredAnimal;
                filterResults.count = filteredAnimal.size();
            }
            return filterResults;
        }
    };

}