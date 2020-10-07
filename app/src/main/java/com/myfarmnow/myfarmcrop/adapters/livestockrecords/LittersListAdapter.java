package com.myfarmnow.myfarmcrop.adapters.livestockrecords;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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
import com.myfarmnow.myfarmcrop.models.livestock_models.Litter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LittersListAdapter extends RecyclerView.Adapter<LittersListAdapter.LitterViewHolder> {
    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<Litter> litters = new ArrayList<>();

    public LittersListAdapter(Context context, List<Litter> breedingStocks){
        breedingStocks.addAll(breedingStocks);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);

        Log.d("Litters",litters.size()+" ");
    }
    @NonNull
    @Override
    public LittersListAdapter.LitterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.litters_list_item,parent,false);

        return new LittersListAdapter.LitterViewHolder(view);
    }

    public void appendList(ArrayList<Litter> litters){

        this.litters.addAll(litters);
        notifyDataSetChanged();
    }

    public void addLitter(Litter litter){
        this.litters.add(litter);
    }

    public void addList(ArrayList<Litter> litters){
        this.litters.clear();
        this.litters.addAll(litters);
        notifyDataSetChanged();
    }

    public void clearLitterList(){
        litters.clear();
    }


    @Override
    public void onBindViewHolder(@NonNull final LittersListAdapter.LitterViewHolder holder, int position) {

        Litter litter = litters.get(position);
        holder.litterTextView.setText("00" +litter.getId());
        holder.damTextView.setText(litter.getMotherDam());
        holder.sireTextView.setText(litter.getFatherSire());
        holder.dobTextView.setText(litter.getDateOfBirth());
        holder.litterSizeTextView.setText(litter.getLitterSize() + "");
        holder.maleTextView.setText(litter.getNoOfMale() + "");
        holder.femalesTextView.setText(litter.getNoOfFemale() + "");

        try {
            computeAge(litter.getDateOfBirth(),holder);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }



    @Override
    public int getItemCount() {
        return litters.size();
    }


    public class LitterViewHolder extends RecyclerView.ViewHolder{

        TextView litterTextView,damTextView,sireTextView,dobTextView,litterSizeTextView,femalesTextView,maleTextView,ageTextView;
        ImageView pictureImageView,moreOpertions;


        public LitterViewHolder(View itemView) {
            super(itemView);
            litterTextView = itemView.findViewById(R.id.litter_item_id_number);
            damTextView = itemView.findViewById(R.id.litter_item_dam);
            sireTextView = itemView.findViewById(R.id.litter_item_sire);
            dobTextView = itemView.findViewById(R.id.litter_item_dob);
            litterSizeTextView = itemView.findViewById(R.id.litter_item_size);
            maleTextView = itemView.findViewById(R.id.litter_item_males);
            femalesTextView = itemView.findViewById(R.id.litter_item_females);
            ageTextView = itemView.findViewById(R.id.litter_item_age);
            moreOpertions = itemView.findViewById(R.id.litter_item_more);
            moreOpertions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                    PopupMenu popup = new PopupMenu(wrapper, v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            NavController navController = Navigation.findNavController(v);
                            if (item.getTitle().toString().equals(mContext.getString(R.string.label_delete))){
                                final Litter litter = litters.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete litter?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteLitter(""+litter.getId());
                                                litters.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();
                            }else if (item.getTitle().toString().equals(mContext.getString(R.string.label_edit))){
                                //edit functionality

                                Litter litter = litters.get(getAdapterPosition());
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("litter", litter);
                                navController.navigate(R.id.action_littersViewFragment_to_addLittersFragment,bundle);


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
    public void computeAge(String dob, LittersListAdapter.LitterViewHolder holder) throws ParseException {
        if (!dob.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date convertedDate = new Date();
            try {

                convertedDate = dateFormat.parse(dob);
                Calendar today = Calendar.getInstance();       // get calendar instance
                today.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
                today.set(Calendar.MINUTE, 0);                 // set minute in hour
                today.set(Calendar.SECOND, 0);                 // set second in minute
                today.set(Calendar.MILLISECOND, 0);

                long daysBetween = daysBetween(convertedDate, today.getTime());
                int years = (int) (daysBetween / 365);
                int months = (int) ((daysBetween - years * 365) / 30);
                int days = (int) (daysBetween % 30);
                Log.d("DATES", dob + " " + convertedDate.toString() + " - " + today.getTime().toString() + " days = " + daysBetween);
                String age = "";
                if (years > 0) {
                    age += years + "Y ";
                }
                if (months > 0)
                    age += months + "M ";
                age += days + "D";

                holder.ageTextView.setText(age);


            } catch (ParseException e) {
                Log.d("DATe", dob);
                e.printStackTrace();
                String age = "--";
                holder.ageTextView.setText(age);
            }
        }
    }

    public static long daysBetween(Date startDate, Date endDate) {
        Calendar sDate = getDatePart(startDate);
        Calendar eDate = getDatePart(endDate);

        long daysBetween = 0;
        while (sDate.before(eDate)) {
            sDate.add(Calendar.DAY_OF_MONTH, 1);
            //Log.d("Day "+daysBetween,sDate.getTime().toString());
            daysBetween++;
        }
        return daysBetween;
    }

    public static Calendar getDatePart(Date date) {
        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        cal.set(Calendar.MINUTE, 0);                 // set minute in hour
        cal.set(Calendar.SECOND, 0);                 // set second in minute
        cal.set(Calendar.MILLISECOND, 0);            // set millisecond in second

        return cal;                                  // return the date part
    }


}
