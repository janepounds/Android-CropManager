 package com.myfarmnow.myfarmcrop.fragments.buyInputsFragments;

 import android.content.Context;
 import android.os.AsyncTask;
 import android.os.Bundle;
 import android.view.LayoutInflater;
 import android.view.Menu;
 import android.view.MenuInflater;
 import android.view.MenuItem;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.Toast;

 import androidx.annotation.Nullable;
 import androidx.appcompat.app.ActionBar;
 import androidx.appcompat.app.AppCompatActivity;
 import androidx.appcompat.widget.Toolbar;
 import androidx.fragment.app.Fragment;
 import androidx.fragment.app.FragmentManager;
 import androidx.viewpager.widget.ViewPager;

 import com.google.android.material.bottomnavigation.BottomNavigationView;
 import com.google.android.material.snackbar.Snackbar;
 import com.myfarmnow.myfarmcrop.R;
 import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
 import com.myfarmnow.myfarmcrop.app.CropManagerApp;
 import com.myfarmnow.myfarmcrop.adapters.buyInputsAdapters.ViewPagerCustomAdapter;
 import com.myfarmnow.myfarmcrop.constants.ConstantValues;
 import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
 import com.myfarmnow.myfarmcrop.fragments.AccountFragment;
 import com.myfarmnow.myfarmcrop.fragments.HomeFragment;
 import com.myfarmnow.myfarmcrop.fragments.OffersFragment;
 import com.myfarmnow.myfarmcrop.models.banner_model.BannerDetails;
 import com.myfarmnow.myfarmcrop.models.category_model.CategoryDetails;
 import com.myfarmnow.myfarmcrop.models.user_model.UserData;
 import com.myfarmnow.myfarmcrop.network.BuyInputsAPIClient;
 import com.myfarmnow.myfarmcrop.network.StartAppRequests;
 import com.myfarmnow.myfarmcrop.utils.Utilities;

 import java.util.ArrayList;
 import java.util.List;

 import am.appwise.components.ni.NoInternetDialog;
 import retrofit2.Call;
 import retrofit2.Callback;
 import retrofit2.Response;

 public class BuyInputsHomePage extends Fragment {
     View rootView;

     ViewPager viewPager;
     StartAppRequests startAppRequests;

     List<BannerDetails> bannerImages = new ArrayList<>();
     List<CategoryDetails> allCategoriesList = new ArrayList<>();
     FragmentManager fragmentManager;
     private Context context;
     private ActionBar actionBar;
     Toolbar toolbar;

     @Override
     public void onCreate(@Nullable Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
     }

     @Override
     public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         rootView = inflater.inflate(R.layout.buy_inputs_home, container, false);
         context=getContext();

         setHasOptionsMenu(true);
         ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.app_name));

         toolbar = rootView.findViewById(R.id.main_Toolbar);



         BottomNavigationView bottomNavigationView = rootView.findViewById(R.id.btm_navigation);
         bottomNavigationView.setItemIconTintList(null);
         bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

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


     @Override
     public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
         // Bind Menu Items
         MenuItem languageItem = menu.findItem(R.id.toolbar_ic_language);
         MenuItem currencyItem = menu.findItem(R.id.toolbar_ic_currency);
         MenuItem profileItem = menu.findItem(R.id.toolbar_edit_profile);
         MenuItem searchItem = menu.findItem(R.id.toolbar_ic_search);
         MenuItem cartItem = menu.findItem(R.id.toolbar_ic_cart);

         profileItem.setVisible(false);
         languageItem.setVisible(false);
         currencyItem.setVisible(false);
         searchItem.setVisible(true);
         cartItem.setVisible(true);

     }


     private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = item -> {
         Fragment selectedFragment = null;

         switch (item.getItemId()) {
             case R.id.page_1:
                 selectedFragment = new HomeFragment( context, getActivity().getSupportFragmentManager(),  MyFarmDbHandlerSingleton.getHandlerInstance(context));
                 break;
             case R.id.page_2:
                 selectedFragment = new OffersFragment();
                 break;
             case R.id.page_3:
                 selectedFragment = new AccountFragment();
                 break;
         }

         getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, selectedFragment).commit();
         return true;
     };


 }

