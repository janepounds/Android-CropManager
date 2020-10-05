package com.myfarmnow.myfarmcrop.fragments.farmrecords.liveStockRecords;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.adapters.livestockrecords.LittersListAdapter;
import com.myfarmnow.myfarmcrop.adapters.livestockrecords.MedicationsListAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.livestock_models.Medication;
import com.myfarmnow.myfarmcrop.utils.SharedPreferenceHelper;

import java.util.ArrayList;

public class MedicationsViewFragment extends Fragment {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private Context context;
    private NavController navController;
    private MyFarmDbHandlerSingleton dbHandler;
    private MedicationsListAdapter medicationsListAdapter;
    private ArrayList<Medication>medicationArrayList= new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_medications_view, container, false);
        toolbar = view.findViewById(R.id.toolbar_medication_view);
        recyclerView = view.findViewById(R.id.medication_recyclerView);

        setHasOptionsMenu(true);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Medication");
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(context);
        medicationsListAdapter = new MedicationsListAdapter(context, medicationArrayList);
        recyclerView.setAdapter(medicationsListAdapter);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        toolbar.setNavigationOnClickListener(view1->navController.popBackStack());
        loadMedication();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.medication_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void loadMedication(){
        medicationsListAdapter.clearMedicationList();

        SharedPreferenceHelper preferenceModel = new SharedPreferenceHelper(context);
        medicationsListAdapter.addList(dbHandler.getMedications(DashboardActivity.RETRIEVED_USER_ID,preferenceModel.getSelectedAnimal()));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add_medication:
                navController.navigate(R.id.action_medicationsViewFragment_to_addMedicationFragment);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}