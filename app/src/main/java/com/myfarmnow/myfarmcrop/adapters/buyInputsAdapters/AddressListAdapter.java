package com.myfarmnow.myfarmcrop.adapters.buyInputsAdapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.marketplace.BuyInputsActivity;
import com.myfarmnow.myfarmcrop.app.CropManagerApp;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.My_Addresses;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.Nearby_Merchants;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.Shipping_Address;
import com.myfarmnow.myfarmcrop.models.address_model.AddressDetails;

import java.util.List;


/**
 * AddressListAdapter is the adapter class of RecyclerView holding List of Addresses in My_Addresses
 **/

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.MyViewHolder> {

    Context context;
    String customerID;
    List<AddressDetails> addressList;
    My_Addresses my_addresses;

    private int selectedPosition;

    // To keep track of Checked Radio Button
    private RadioButton lastChecked_RB = null;
    My_Addresses parentFrag;



    public AddressListAdapter(My_Addresses my_addresses, Context context, String customerID, int defaultAddressPosition, List<AddressDetails> addressList, My_Addresses parentFrag) {
        this.my_addresses = my_addresses;
        this.context = context;
        this.customerID = customerID;
        this.addressList = addressList;
        this.selectedPosition = defaultAddressPosition;
        this.parentFrag = parentFrag;
    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.buy_inputs_card_addresses, parent, false);

        return new MyViewHolder(itemView);
    }



    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // Get the data model based on Position
        final AddressDetails addressDetails = addressList.get(position);

        final String addressID = String.valueOf(addressDetails.getAddressId());

        holder.address_title.setText( addressDetails.getLastname()!=null? addressDetails.getFirstname() +" "+addressDetails.getLastname() : addressDetails.getFirstname() );
        holder.address_details.setText(addressDetails.getStreet() +", "+ addressDetails.getCity() +", "+ addressDetails.getCountryName());
        

        // Toggle the Default Address RadioButton with Position
        if (addressDetails.getDefaultAddress() == 1) {
            holder.makeDefault_rb.setChecked(true);
            lastChecked_RB = holder.makeDefault_rb;
        } else {
            holder.makeDefault_rb.setChecked(false);
        }

        // Check the Clicked RadioButton
        View.OnClickListener onselctListener =new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (lastChecked_RB != null) {
                    lastChecked_RB.setChecked(false);
                }



                if(parentFrag.my_cart!=null) {
                    lastChecked_RB.setChecked(true);

                    ((CropManagerApp) context.getApplicationContext()).setShippingAddress(addressDetails);
                    Fragment fragment = new Nearby_Merchants(parentFrag.my_cart);
                    FragmentManager fragmentManager = parentFrag.getFragmentManager();
                    fragmentManager.beginTransaction().add(R.id.main_fragment, fragment)
                            .addToBackStack(context.getString(R.string.select_merchants_fragment)).commit();
                }else{
                    // Request the Server to Change Default Address
                    //buy_inputs_my__addresses.MakeAddressDefault(customerID, addressID, context, view);
                }


            }
        };
        holder.cardview_perant.setOnClickListener(onselctListener);
        holder.makeDefault_rb.setOnClickListener(onselctListener);

        holder.delete_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_addresses.DeleteAddress(customerID, addressID, context, holder.delete_address);
            }
        });

        // Edit relevant Address
        holder.edit_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set the current Address Info to Bundle
                Bundle addressInfo = new Bundle();
                addressInfo.putBoolean("isUpdate", true);
                addressInfo.putString("addressID", addressID);
                addressInfo.putString("addressFirstname", addressDetails.getFirstname());
                addressInfo.putString("addressLastname", addressDetails.getLastname());
                addressInfo.putString("addressCountryName", addressDetails.getCountryName());
                addressInfo.putString("addressCountryID", ""+addressDetails.getCountriesId());
                addressInfo.putString("addressZoneName", addressDetails.getZoneName());
                addressInfo.putString("addressZoneID", ""+addressDetails.getZoneId());
                addressInfo.putString("addressState", addressDetails.getState());
                addressInfo.putString("addressCity", addressDetails.getCity());
                addressInfo.putString("addressStreet", addressDetails.getStreet());
                addressInfo.putString("addressPostCode", addressDetails.getPostcode());
                addressInfo.putString("addressPhone", addressDetails.getPhone());
                addressInfo.putString("addressLocation", addressDetails.getLatitude()+", "+addressDetails.getLongitude());
                // Navigate to Add_Address Fragment with arguments to Edit Address

                ((CropManagerApp) context.getApplicationContext()).setShippingAddress(addressDetails);
                Fragment fragment = new Shipping_Address(null,parentFrag);
                fragment.setArguments(addressInfo);
                FragmentManager fragmentManager = ((BuyInputsActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();
            }
        });

    }



    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return addressList.size();
    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageButton edit_address, delete_address;
        RadioButton makeDefault_rb;
        TextView address_title, address_details;
        CardView cardview_perant;


        public MyViewHolder(final View itemView) {
            super(itemView);

            cardview_perant = itemView.findViewById(R.id.cardview_perant);
            address_title = itemView.findViewById(R.id.address_title);
            address_details = itemView.findViewById(R.id.address_details);
            edit_address = itemView.findViewById(R.id.edit_address);
            delete_address = itemView.findViewById(R.id.delete_address);
            makeDefault_rb = itemView.findViewById(R.id.default_address_rb);
        }
    }
}

