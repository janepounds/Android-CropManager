 package com.myfarmnow.myfarmcrop.fragments.buyInputsFragments;

 import android.os.AsyncTask;
 import android.os.Bundle;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;

 import androidx.annotation.Nullable;
 import androidx.appcompat.app.AppCompatActivity;
 import androidx.fragment.app.Fragment;
 import androidx.fragment.app.FragmentManager;
 import androidx.viewpager.widget.ViewPager;

 import com.myfarmnow.myfarmcrop.R;
 import com.myfarmnow.myfarmcrop.app.CropManagerApp;
 import com.myfarmnow.myfarmcrop.adapters.buyInputsAdapters.ViewPagerCustomAdapter;
 import com.myfarmnow.myfarmcrop.constants.ConstantValues;
 import com.myfarmnow.myfarmcrop.models.banner_model.BannerDetails;
 import com.myfarmnow.myfarmcrop.models.category_model.CategoryDetails;
 import com.myfarmnow.myfarmcrop.network.StartAppRequests;
 import com.myfarmnow.myfarmcrop.utils.Utilities;
 import com.google.android.material.tabs.TabLayout;

 import java.util.ArrayList;
 import java.util.List;

 import am.appwise.components.ni.NoInternetDialog;

 public class HomePage extends Fragment {
     View rootView;

     ViewPager viewPager;
     StartAppRequests startAppRequests;

     List<BannerDetails> bannerImages = new ArrayList<>();
     List<CategoryDetails> allCategoriesList = new ArrayList<>();
     FragmentManager fragmentManager;

     @Override
     public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         rootView = inflater.inflate(R.layout.buy_inputs_homepage_9, container, false);

         // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
         //MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
         ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(ConstantValues.APP_HEADER);

         NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
         //noInternetDialog.show();

         startAppRequests = new StartAppRequests(getContext());

         // Get BannersList from ApplicationContext
         bannerImages = ((CropManagerApp) getContext().getApplicationContext()).getBannersList();
         allCategoriesList = ((CropManagerApp) getContext().getApplicationContext()).getCategoriesList();

         // Binding Layout View

         if (bannerImages.isEmpty() || allCategoriesList.isEmpty())
             new MyTask().execute();
         else
             continueSetup();

         return rootView;

     }


     public void continueSetup() {

         bannerImages = ((CropManagerApp) getContext().getApplicationContext()).getBannersList();
         allCategoriesList = ((CropManagerApp) getContext().getApplicationContext()).getCategoriesList();
         // Setup BannerSlider
         setupBannerSlider(bannerImages);

         // Add corresponding ViewPagers to TabLayouts
         fragmentManager = getFragmentManager();

         Bundle categoryBundle = new Bundle();
         categoryBundle.putBoolean("isHeaderVisible", false);
         categoryBundle.putBoolean("isMenuItem", false);
         categoryBundle.putBoolean("home_9", true);
         Fragment categories = new Categories_3();
         categories.setArguments(categoryBundle);
         fragmentManager.beginTransaction().replace(R.id.home9_categories, categories).commit();

         Bundle bundleInfo = new Bundle();
         bundleInfo.putString("sortBy", "Newest");

     }


     //*********** Setup the BannerSlider with the given List of BannerImages ********//
     private void setupBannerSlider(final List<BannerDetails> bannerImages) {
         FragmentManager fragmentManager = getFragmentManager();
         Fragment bannerStyle = null;

         switch (ConstantValues.DEFAULT_BANNER_STYLE) {
             case 0:
                 bannerStyle = new BannerStyle1(bannerImages, allCategoriesList);
                 break;
             case 1:
                 bannerStyle = new BannerStyle1(bannerImages, allCategoriesList);
                 break;

         }

         if (bannerStyle != null)
             fragmentManager.beginTransaction().replace(R.id.bannerFrameHome9, bannerStyle).commit();
     }


     private class MyTask extends AsyncTask<String, Void, String> {

         @Override
         protected void onPreExecute() {
             super.onPreExecute();
         }

         @Override
         protected String doInBackground(String... params) {

             // Check for Internet Connection from the static method of Helper class
             if (Utilities.hasActiveInternetConnection(getContext())) {

                 // Call the method of StartAppRequests class to process App Startup Requests
                 startAppRequests.RequestBanners();
                 startAppRequests.RequestAllCategories();

                 return "1";
             } else {

                 return "0";
             }
         }

         @Override
         protected void onPostExecute(String result) {
             super.onPostExecute(result);

             if (result.equalsIgnoreCase("1")) {
                 continueSetup();
             }
         }

     }
 }

