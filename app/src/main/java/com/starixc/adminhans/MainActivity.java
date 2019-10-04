package com.starixc.adminhans;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.starixc.adminhans.Model.Products;
import com.starixc.adminhans.viewHolder.ProductViewHolder;

public class MainActivity extends AppCompatActivity {

private Toolbar toolbar;
private NavController navController;
private NavigationView nav_view;
private DrawerLayout drawerLayout;
private MenuInflater menuInflater;
//private AppBarConfiguration.OnNavigateUpListener navigateUp;
//    private DatabaseReference ProductsRef;
//    private RecyclerView recyclerView;
//    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setSupportActionBar(toolbar);
       // navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawer_layout);

        navController = Navigation.findNavController(this, R.id.fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);

        NavigationUI.setupWithNavController(nav_view, navController);

       // navController = Navigation.findNavController(this, R.id.fragment);
      //  nav_view = (NavigationView)findViewById(R.id.nav_view);

       // drawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
      //  NavigationUI.setupWithNavController(nav_view,navController);
//        NavigationUI.setupActionBarWithNavController(
//                this,
//                navController,drawerLayout
//        );



//        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
//
//        recyclerView = findViewById(R.id.recycler_menu);
//        recyclerView.setHasFixedSize(true);
//        layoutManager = new GridLayoutManager(this, 2);
//        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(
                Navigation.findNavController(this, R.id.fragment),
                drawerLayout

        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuInflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    //    @Override
//    protected void onStart() {
//        super.onStart();
//
//        FirebaseRecyclerOptions<Products> options =
//                new FirebaseRecyclerOptions.Builder<Products>()
//                        .setQuery(ProductsRef, Products.class)
//                        .build();
//
//
//        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
//                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
//                    @Override
//                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model) {
//                        holder.txtProductName.setText(model.getName());
//                        // holder.txtProductDescription.setText(model.getDescription());
//                        holder.txtProductPrice.setText("Ksh. " + model.getPrice() + "/=");
//                        Picasso.get().load(model.getImage()).into(holder.imageView);
//
//                        holder.itemView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Intent intent = new Intent(MainActivity.this, ProductDetailsActivity.class);
//                                intent.putExtra("pid", model.getPid());
//                                startActivity(intent);
//                            }
//                        });
//
//
//                    }
//
//                    @NonNull
//                    @Override
//                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
//                        ProductViewHolder holder = new ProductViewHolder(view);
//                        return holder;
//                    }
//                };
//        recyclerView.setAdapter(adapter);
//        adapter.startListening();
//    }
}
