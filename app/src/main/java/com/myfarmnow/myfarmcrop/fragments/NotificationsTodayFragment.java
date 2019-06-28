package com.myfarmnow.myfarmcrop.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropDashboardActivity;
import com.myfarmnow.myfarmcrop.adapters.CropsNotificationsListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropNotification;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link NotificationsTodayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationsTodayFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;



    public NotificationsTodayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationsTodayFragment.
     */

    public static NotificationsTodayFragment newInstance(String param1, String param2) {
        NotificationsTodayFragment fragment = new NotificationsTodayFragment();
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
        View view= inflater.inflate(R.layout.fragment_notifications_today, container, false);
        notificationsListRecyView = view.findViewById(R.id.recyc_view_notifications_list);
        notificationsNoDataTextView = view.findViewById(R.id.text_view_notifications_no_data);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(getActivity());
        notificationsListRecyclerAdapter = new CropsNotificationsListRecyclerAdapter(getActivity(),dbHandler.getCropNotifications(CropDashboardActivity.getPreferences("userId",getActivity()), CropNotification.QUERY_KEY_TODAY));
        notificationsListRecyView.setAdapter(notificationsListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        notificationsListRecyView.setLayoutManager(linearLayoutManager);

        if(notificationsListRecyclerAdapter.getItemCount()!=0){
            notificationsNoDataTextView.setVisibility(View.GONE);
        }

        return view;
    }






    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
