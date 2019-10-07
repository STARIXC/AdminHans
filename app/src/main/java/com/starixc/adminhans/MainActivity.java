package com.starixc.adminhans;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.starixc.adminhans.Model.Products;
import com.starixc.adminhans.viewHolder.ProductViewHolder;

public class MainActivity extends AppCompatActivity  {

//    private Toolbar toolbar;
//    private NavController navController;
//    private NavigationView nav_view;
//    private DrawerLayout drawerLayout;
//    private MenuInflater menuInflater;
//   private FragmentTransaction ft;
//    private boolean viewIsAtHome;

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set a tool bar to replace the actionbar
        toolbar =(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //display an Up icon(<-)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //find our drawer view
        drawerLayout =(DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        //setup toggle to display hamburger icon with nice animation
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        //tie drawer layout event to the ActionBarToggle
        drawerLayout.addDrawerListener(drawerToggle);

        navigationView=(NavigationView) findViewById(R.id.nav_view);
        setupDrawerContent(navigationView);

//        setupNavigation();
//        selectDrawerItem(MenuItem, R.id.homeFragment);
        Fragment fragment =new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment, fragment).addToBackStack(null).commit();
    }
    private ActionBarDrawerToggle setupDrawerToggle(){
        return new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                }
        );
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void selectDrawerItem(MenuItem menuItem) {
        //create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment =null;
        Class fragmentClass;
        switch (menuItem.getItemId()){

            case R.id.categoryFragment:
                fragmentClass = CategoryFragment.class;
                break;
            case R.id.productsFragment:
                fragmentClass = ProductsFragment.class;
                break;

            case R.id.profileFragment:
                fragmentClass = ProfileFragment.class;
                break;
            case R.id.ordersFragment:
                fragmentClass = OrdersFragment.class;
                break;
            case R.id.feedBackFragment:
                fragmentClass = FeedBackFragment.class;
                break;
            default:
                fragmentClass = HomeFragment.class;
        }
        try {
            fragment=(Fragment)fragmentClass.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        //insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment, fragment).addToBackStack(null).commit();

        //Highlight the selected item has been done by Navigation view
        menuItem.setChecked(true);

        //set actionbar title
        setTitle(menuItem.getTitle());
        //close the navigation drawer
        drawerLayout.closeDrawers();


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //the action bar  home/up action should open or close the drawer

//        switch (item.getItemId()){
//            case android.R.id.home:
//                drawerLayout.openDrawer(GravityCompat.START);
//                return true;
//        }
        if (drawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//        displayView(menuItem.getItemId());
//        return true;
//    }
//    private void setupNavigation() {
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        drawerLayout = findViewById(R.id.drawer_layout);
//
//        nav_view = findViewById(R.id.nav_view);
//        navController = Navigation.findNavController(this, R.id.fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
//        NavigationUI.setupWithNavController(nav_view, navController);
//
//    }

//    private void displayView(int itemId) {
//        Fragment fragment = null;
//        String title = getString(R.string.app_name);
//
//        switch (itemId) {
//            case R.id.homeFragment:
//                fragment = new HomeFragment();
//                ft = getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.fragment, fragment);
//                ft.commit();
//                title = getString(R.string.home);
//                viewIsAtHome = true;
//
//                break;
//            case R.id.categoryFragment:
//                fragment = new CategoryFragment();
//               ft = getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.fragment, fragment);
//                ft.commit();
//                title = getString(R.string.category);
//                viewIsAtHome = false;
//                break;
//
//            case R.id.productsFragment:
//                fragment = new ProductsFragment();
//                ft = getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.fragment, fragment);
//                ft.commit();
//                title = getString(R.string.products);
//                viewIsAtHome = false;
//                break;
//            case R.id.ordersFragment:
//                fragment = new OrdersFragment();
//                title = getString(R.string.orders);
//                ft = getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.fragment, fragment);
//                ft.commit();
//                viewIsAtHome = false;
//                break;
//        }
//        if (fragment != null) {
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.fragment, fragment);
//            ft.commit();
//        }
//
//        // set the toolbar title
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setTitle(title);
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//    }
//
//        @Override
//        public boolean onSupportNavigateUp () {
//            return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.fragment), drawerLayout);
//        }
//
//        @Override
//        public void onBackPressed () {
//            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//            if (drawer.isDrawerOpen(GravityCompat.START)) {
//                drawer.closeDrawer(GravityCompat.START);
//            }
//            if (!viewIsAtHome) { //if the current view is not the News fragment
//                displayView(R.id.homeFragment); //display the News fragment
//            } else {
//                moveTaskToBack(true);  //If view is in News fragment, exit application
//            }
//        }


}
