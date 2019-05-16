package com.myfarmnow.myfarmcrop.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropDashboardActivity;
import com.myfarmnow.myfarmcrop.adapters.CropProductsListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.adapters.CropsNotificationsListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropNotification;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link NotificationsOverDueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationsOverDueFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;



    public NotificationsOverDueFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationsOverDueFragment.
     */

    public static NotificationsOverDueFragment newInstance(String param1, String param2) {
        NotificationsOverDueFragment fragment = new NotificationsOverDueFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    RecyclerView notificationsListRecyView;
    TextView notificationsNoDataTextView;
    CropsNotificationsListRecyclerAdapter notificationsListRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    MyFarmDbHandlerSingleton dbHandler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view =inflater.inflate(R.layout.fragment_notifications_over_due, container, false);
         notificationsListRecyView = view.findViewById(R.id.recyc_view_notifications_list);
         notificationsNoDataTextView = view.findViewById(R.id.text_view_notifications_no_data);
         dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(getActivity());
         notificationsListRecyclerAdapter = new CropsNotificationsListRecyclerAdapter(getActivity(),dbHandler.getCropNotifications(CropDashboardActivity.getPreferences("userId",getActivity()), CropNotification.QUERY_KEY_OVER_DUE));
         notificationsListRecyView.setAdapter(notificationsListRecyclerAdapter);
         linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
         notificationsListRecyView.setLayoutManager(linearLayoutManager);

        if(notificationsListRecyclerAdapter.getItemCount()!=0){
            notificationsNoDataTextView.setVisibility(View.GONE);
        }
         return view;
    }





    @Override
    public void onDetach() {
        super.onDetach();

    }



}
