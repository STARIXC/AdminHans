package com.starixc.adminhans.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;
import com.starixc.adminhans.Model.Products;
import com.starixc.adminhans.R;
import com.starixc.adminhans.viewHolder.ProductViewHolder;


public class ProductAdapter extends FirestoreRecyclerAdapter<Products, ProductAdapter.ProductHolder> {
    private OnItemClickListener listener;
    private FragmentManager fm;
    private FragmentTransaction ft;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ProductAdapter(@NonNull FirestoreRecyclerOptions<Products> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProductHolder holder, int position, @NonNull Products model) {
        holder.txtProductName.setText(model.getName());
        // holder.txtProductDescription.setText(model.getDescription());
        holder.txtProductPrice.setText("Ksh. " + model.getPrice() + "/=");
        Picasso.get().load(model.getImage()).into(holder.imageView);
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout,
                parent, false);
        return new ProductHolder(v);
    }

    class ProductHolder extends RecyclerView.ViewHolder {
        public TextView txtProductName, txtProductPrice;
        public ImageView imageView;

        public ProductHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.product_image);
            txtProductName = (TextView) itemView.findViewById(R.id.product_name);
            //txtProductDescription = (TextView) itemView.findViewById(R.id.product_description);
            txtProductPrice = (TextView) itemView.findViewById(R.id.product_price);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position !=RecyclerView.NO_POSITION && listener !=null)
                    {
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }

                }
            });
        }
    }
    public interface  OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);

    }
    public void setOnClickListener(OnItemClickListener listener){
        this.listener= listener;

    }
}