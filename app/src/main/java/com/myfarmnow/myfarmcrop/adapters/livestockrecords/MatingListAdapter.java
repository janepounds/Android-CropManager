package com.myfarmnow.myfarmcrop.adapters.livestockrecords;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.fragments.farmrecords.liveStockRecords.BreedingStockViewFragment;
import com.myfarmnow.myfarmcrop.fragments.farmrecords.liveStockRecords.MatingViewFragment;
import com.myfarmnow.myfarmcrop.models.livestock_models.BreedingStock;
import com.myfarmnow.myfarmcrop.models.livestock_models.Mating;
import com.myfarmnow.myfarmcrop.models.livestock_models.Medication;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MatingListAdapter extends  RecyclerView.Adapter<MatingListAdapter.MatingViewHolder> {
    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<Mating> matings = new ArrayList<>();

    public MatingListAdapter(Context context, List<Mating> matings){
        matings.addAll(matings);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);

        Log.d("MATINGS",matings.size()+" ");
    }
    @NonNull
    @Override
    public MatingListAdapter.MatingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.mating_list_item, parent,false);

        MatingViewHolder holder = new MatingViewHolder(view);
        return holder;
    }



    public void addMating(Mating mating){
        this.matings.add(mating);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<Mating> matings){

        this.matings.clear();
        this.matings.addAll(matings);
        notifyDataSetChanged();
    }

    public void addList(ArrayList<Mating> matings){
        this.matings.clear();
        this.matings.addAll(matings);
        notifyDataSetChanged();
    }

    public void clearMatingList(){
        matings.clear();
    }



    @Override
    public void onBindViewHolder(@NonNull final MatingListAdapter.MatingViewHolder holder, int position) {

        Mating mating = matings.get(position);
        holder.femaleNameTextView.setText(mating.getFemaleName());
        holder.maleNameTextView.setText(mating.getMaleName());
        holder.methodTextView.setText(mating.getMethod());

        computeDeliveryDate(mating.getMatingDate(),mating.getGestationPeriod(),holder);





    }



    @Override
    public int getItemCount() {
        return matings.size();
    }


    public class MatingViewHolder extends RecyclerView.ViewHolder{

        TextView femaleNameTextView,maleNameTextView,statusTextView,expectedDeliveryTextView,methodTextView;
        ImageView moreOptions;

        public MatingViewHolder(View itemView) {
            super(itemView);
            femaleNameTextView = itemView.findViewById(R.id.mating_item_female);
            maleNameTextView = itemView.findViewById(R.id.mating_item_male);
            statusTextView = itemView.findViewById(R.id.mating_item_status);
            expectedDeliveryTextView = itemView.findViewById(R.id.mating_item_expected_delivery_date);
            methodTextView = itemView.findViewById(R.id.mating_item_method);
            moreOptions = itemView.findViewById(R.id.mating_item_more);
            moreOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                    PopupMenu popup = new PopupMenu(wrapper, v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            NavController navController = Navigation.findNavController(v);
                            if (item.getTitle().toString().equals(mContext.getString(R.string.label_delete))){
                                final Mating mating = matings.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete  this mating?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteMating(""+mating.getId());
                                                matings.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();
                            }else if (item.getTitle().toString().equals(mContext.getString(R.string.label_edit))){
                                //edit functionality
                                Mating mating = matings.get(getAdapterPosition());
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("mating", mating);
                                navController.navigate(R.id.action_matingViewFragment_to_addMatingsFragment,bundle);



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

    public void computeDeliveryDate(String matingDate,float gestationPeriod,MatingViewHolder holder){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = new Date();
        try {

            convertedDate = dateFormat.parse(matingDate);
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH,((int)gestationPeriod));
            String newDate = dateFormat.format(c.getTime());
            holder.expectedDeliveryTextView.setText(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }



    }



}
