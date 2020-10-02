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
import com.myfarmnow.myfarmcrop.models.livestock_models.BreedingStock;
import com.myfarmnow.myfarmcrop.models.livestock_models.Litter;
import com.myfarmnow.myfarmcrop.models.livestock_models.Medication;

import java.util.ArrayList;
import java.util.List;

public class MedicationsListAdapter extends RecyclerView.Adapter<MedicationsListAdapter.MedicationViewHolder>{
    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<Medication> medications = new ArrayList<>();

    public MedicationsListAdapter(Context context, List<Medication> medications){
        medications.addAll(medications);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);

        Log.d("Medications",medications.size()+" ");
    }
    @NonNull
    @Override
    public MedicationsListAdapter.MedicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.medication_list_item,parent,false);

        return new MedicationsListAdapter.MedicationViewHolder(view);
    }

    public void appendList(ArrayList<Medication> medications){

        this.medications.addAll(medications);
        notifyDataSetChanged();
    }

    public void addMedication(Medication medication){
        this.medications.add(medication);
    }

    public void addList(ArrayList<Medication> medications){
        this.medications.clear();
        this.medications.addAll(medications);
        notifyDataSetChanged();
    }

    public void clearMedicationList(){
        medications.clear();
    }


    @Override
    public void onBindViewHolder(@NonNull final MedicationsListAdapter.MedicationViewHolder holder, int position) {

        Medication medication = medications.get(position);
        holder.medicationDateTextView.setText(medication.getMedicationDate());
        holder.medicationTypeTextView.setText(medication.getMedicationType());
        holder.animalTextView.setText(medication.getAnimal());
        holder.healthConditionTextView.setText(medication.getHealthCondition());
        holder.medicineTextView.setText(medication.getMedicationsName());
        holder.dosageTextView.setText(medication.getDosage() + "ml/day");
//        holder.litterTextView.setText(litter.getId());
//        holder.damTextView.setText(litter.getMotherDam());
//        holder.sireTextView.setText(litter.getFatherSire());
//        holder.dobTextView.setText(litter.getDateOfBirth());
//        holder.litterSizeTextView.setText(litter.getLitterSize());
//        holder.maleTextView.setText(litter.getNoOfMale());
//        holder.femalesTextView.setText(litter.getNoOfFemale());

    }



    @Override
    public int getItemCount() {
        return medications.size();
    }


    public class MedicationViewHolder extends RecyclerView.ViewHolder{

        TextView medicationDateTextView,medicationTypeTextView,animalTextView,healthConditionTextView,medicineTextView,dosageTextView;
        ImageView pictureImageView,moreOpertions;


        public MedicationViewHolder(View itemView) {
            super(itemView);
            medicationDateTextView = itemView.findViewById(R.id.medication_item_date);
            medicationTypeTextView = itemView.findViewById(R.id.medication_item_type);
            animalTextView = itemView.findViewById(R.id.medication_item_animal);
            healthConditionTextView = itemView.findViewById(R.id.medication_item_condition);
            medicineTextView = itemView.findViewById(R.id.medication_item_medicine);
            dosageTextView = itemView.findViewById(R.id.medication_item_dosage);
            moreOpertions = itemView.findViewById(R.id.medication_item_more);
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
                                final Medication medication = medications.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete litter?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteMedication(""+medication.getId());
                                                medications.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();
                            }else if (item.getTitle().toString().equals(mContext.getString(R.string.label_edit))){
                                //edit functionality
                                Medication medication = medications.get(getAdapterPosition());
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("medication", medication);
                                bundle.putString("title","EDIT MEDICATION");
                                navController.navigate(R.id.action_medicationsViewFragment_to_addMedicationFragment,bundle);



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
