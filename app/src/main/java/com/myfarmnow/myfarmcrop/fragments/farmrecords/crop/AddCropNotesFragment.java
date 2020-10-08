package com.myfarmnow.myfarmcrop.fragments.farmrecords.crop;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;

import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropNote;

public class AddCropNotesFragment extends Fragment {
    private Context context;
    private NavController navController;
    private CropNote cropNote;
    private MyFarmDbHandlerSingleton dbHandler;
    private String cropId;

    private Toolbar toolbar;
    private EditText txtCropNoteDate, txtCropNoteDescription;
    private ImageView datePickerImage;
    private Button btnSave;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_crop_notes, container, false);

        toolbar = view.findViewById(R.id.toolbar_add_crop_notes_fragment);
        txtCropNoteDate = view.findViewById(R.id.txt_crop_note_date);
        datePickerImage = view.findViewById(R.id.date_picker_image);
        txtCropNoteDescription = view.findViewById(R.id.txt_crop_note_description);
        btnSave = view.findViewById(R.id.btn_save);

        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Notes");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        if (getArguments() != null) {
            cropId = getArguments().getString("cropId");
            Log.e("CropID", cropId);
            cropNote = (CropNote) getArguments().getSerializable("cropNote");
            btnSave.setText(getString(R.string.update_note));
        }
        initializeForm();
    }

    public void initializeForm() {
        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(context);
        DashboardActivity.addDatePicker(txtCropNoteDate, context);
        DashboardActivity.addDatePickerImageView(datePickerImage, txtCropNoteDate, context);

        btnSave.setOnClickListener((View.OnClickListener) v -> {
            if (validateEntries()) {
                if (cropNote == null) {
                    saveFields();
                } else {
                    updateField();
                }
                //pop back
                Bundle bundle = new Bundle();
                bundle.putString("cropId", cropNote.getParentId());
                navController.navigate(R.id.action_addCropNotesFragment_to_cropsNotesListFragment, bundle);
            }
        });

        fillViews();
    }

    public void saveFields() {
        cropNote = new CropNote();
        cropNote.setParentId(cropId);
        cropNote.setNotes(txtCropNoteDescription.getText().toString());
        cropNote.setDate(txtCropNoteDate.getText().toString());
        cropNote.setIsFor(CropNote.IS_FOR_CROP);
        dbHandler.insertCropNote(cropNote);
    }

    public void updateField() {
        if (cropNote != null) {
            cropNote.setNotes(txtCropNoteDescription.getText().toString());
            cropNote.setDate(txtCropNoteDate.getText().toString());
            cropNote.setIsFor(CropNote.IS_FOR_CROP);
            dbHandler.updateCropNote(cropNote);
        }
    }

    public void fillViews() {
        if (cropNote != null) {
            txtCropNoteDate.setText(cropNote.getDate());
            txtCropNoteDescription.setText(cropNote.getNotes());
        }
    }

    public boolean validateEntries() {
        String message = null;
        if (txtCropNoteDate.getText().toString().isEmpty()) {
            message = getString(R.string.date_not_entered_message);
            txtCropNoteDate.requestFocus();
        } else if (txtCropNoteDescription.getText().toString().isEmpty()) {
            message = getString(R.string.description_not_entered_message);
            txtCropNoteDescription.requestFocus();
        }

        if (message != null) {
            Toast.makeText(context, getString(R.string.missing_fields_message) + message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.crop_list_activitys_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        navController.navigate(R.id.action_addCropNotesFragment_to_cropsNotesListFragment);
        return super.onOptionsItemSelected(item);
    }
}