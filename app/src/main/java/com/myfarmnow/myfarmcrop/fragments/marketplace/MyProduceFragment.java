package com.myfarmnow.myfarmcrop.fragments.marketplace;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.marketplace.MyProduceListAdapter;
import com.myfarmnow.myfarmcrop.models.marketplace.MyProduce;
import com.myfarmnow.myfarmcrop.database.MyProduceDatabase;
import com.myfarmnow.myfarmcrop.databinding.FragmentMyProduceBinding;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MyProduceFragment extends Fragment {
    private static final String TAG = "MyProduceFragment";
    private FragmentMyProduceBinding binding;
    private Context context;

    private ArrayList<MyProduce> produceList = new ArrayList<>();

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    // image picker code
    private static final int IMAGE_PICK_CODE = 0;
    //permission code
    private static final int PERMISSION_CODE = 1;

    private Uri produceImageUri = null;
    private Bitmap produceImageBitmap = null;
    private ImageView produceImageView;

    private MyProduceDatabase myProduceDatabase;
    private MyProduce myProduce;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_produce, container, false);
        myProduceDatabase = MyProduceDatabase.getInstance(context);

        new getAllProduceTask(MyProduceFragment.this).execute();
        Log.d(TAG, "onCreateView: " + produceList);

//        binding.recyclerView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(context);
//        adapter = new MyProduceListAdapter(context, produceList);
//
//        binding.recyclerView.setLayoutManager(layoutManager);
//        binding.recyclerView.setAdapter(adapter);

        binding.addProduce.setOnClickListener(view -> addProduce());

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void addProduce() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View addProduceDialog = getLayoutInflater().inflate(R.layout.add_produce_dialog, null);

        EditText name = addProduceDialog.findViewById(R.id.produce_name);
        EditText variety = addProduceDialog.findViewById(R.id.produce_variety);
        EditText quantity = addProduceDialog.findViewById(R.id.produce_quantity);
        EditText price = addProduceDialog.findViewById(R.id.produce_price);
        ImageView image = addProduceDialog.findViewById(R.id.produce_image);
        Button submit = addProduceDialog.findViewById(R.id.produce_submit_button);

        image.setOnClickListener(v -> {
            produceImageView = image;
            //check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    //permission denied
                    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                } else {
                    //permission granted
                    chooseImage();
                }
            } else {
                //version is less than marshmallow
                chooseImage();
            }
        });

        submit.setOnClickListener(v -> {
            if (name.getText().toString().trim().isEmpty()) {
                name.setError("Name Required!!");
            } else if (variety.getText().toString().trim().isEmpty()) {
                variety.setError("Variety Required!!");
            } else if (quantity.getText().toString().trim().isEmpty()) {
                quantity.setError("Quantity Required!!");
            } else if (price.getText().toString().trim().isEmpty()) {
                price.setError("Price Required!!");
            } else if (produceImageUri == null) {
                Toast.makeText(context, "Image Required!!", Toast.LENGTH_SHORT).show();
            } else {
                // fetch data and create produce object
                myProduce = new MyProduce(name.getText().toString(),
                        variety.getText().toString(), quantity.getText().toString(),
                        price.getText().toString(), produceImageUri.toString()
                );

                // create worker thread to insert data into database
                new InsertProduceTask(MyProduceFragment.this, myProduce).execute();
            }

        });

        builder.setView(addProduceDialog);
        builder.setCancelable(true);

        Dialog dialog = builder.create();
        dialog.show();
    }

    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                chooseImage();
            } else {
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {

            assert data != null;
            if (data.getData() != null) {
                produceImageUri = data.getData();

                try {
                    produceImageBitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), produceImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Glide.with(context).load(produceImageUri).into(produceImageView);
        }

    }

    private static class getAllProduceTask extends AsyncTask<Void, Void, Boolean> {

        private WeakReference<MyProduceFragment> fragmentReference;
        private Context context;

        // only retain a weak reference to the activity
        getAllProduceTask(MyProduceFragment context) {
            fragmentReference = new WeakReference<>(context);
            this.context = context.context;
        }

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute: " + "Getting produce.");
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            ArrayList<MyProduce> produce = (ArrayList<MyProduce>) fragmentReference.get().myProduceDatabase.myProduceDao().getAll();
            fragmentReference.get().produceList = produce;

            Log.d(TAG, "doInBackground: " + produce);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                fragmentReference.get().requireActivity().runOnUiThread(() -> {
                    fragmentReference.get().binding.recyclerView.setHasFixedSize(true);
                    fragmentReference.get().layoutManager = new LinearLayoutManager(context);
                    fragmentReference.get().adapter = new MyProduceListAdapter(context, fragmentReference.get().produceList);

                    fragmentReference.get().binding.recyclerView.setLayoutManager(fragmentReference.get().layoutManager);
                    fragmentReference.get().binding.recyclerView.setAdapter(fragmentReference.get().adapter);
                });
                Log.d(TAG, "onPostExecute: Complete");
            }
        }
    }

    private static class InsertProduceTask extends AsyncTask<Void, Void, Boolean> {

        private WeakReference<MyProduceFragment> fragmentReference;
        private MyProduce myProduce;
        private ProgressDialog dialog;
        private Context context;

        // only retain a weak reference to the activity
        InsertProduceTask(MyProduceFragment context, MyProduce myProduce) {
            fragmentReference = new WeakReference<>(context);
            this.myProduce = myProduce;
            dialog = new ProgressDialog(context.context);
            this.context = context.context;
        }

        @Override
        protected void onPreExecute() {
            dialog.setIndeterminate(true);
            dialog.setMessage("Please Wait..");
            dialog.setCancelable(false);
            fragmentReference.get().requireActivity().runOnUiThread(() -> dialog.show());
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            List<MyProduce> produce = fragmentReference.get().myProduceDatabase.myProduceDao().getAll();

            Log.d(TAG, "doInBackground: " + fragmentReference.get().myProduceDatabase.myProduceDao().getAll());
            fragmentReference.get().myProduceDatabase.myProduceDao().insert(myProduce);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                fragmentReference.get().requireActivity().runOnUiThread(() -> {
                    Toast.makeText(context, "Produce Added", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                });

                Log.d(TAG, "onPostExecute: Complete");
            }
        }
    }
}