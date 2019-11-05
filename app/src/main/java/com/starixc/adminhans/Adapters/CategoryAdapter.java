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
import com.starixc.adminhans.Model.Category;
import com.starixc.adminhans.R;

public class CategoryAdapter extends FirestoreRecyclerAdapter<Category, CategoryAdapter.CategoryHolder> {
    private OnItemClickListener listener;
    private FragmentManager fm;
    private FragmentTransaction ft;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CategoryAdapter(@NonNull FirestoreRecyclerOptions<Category> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CategoryHolder holder, int position, @NonNull Category model) {
        holder.txtCategoryName.setText(model.getName());
        Picasso.get().load(model.getImage()).into(holder.imageView);
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category,parent,false);

        return new CategoryHolder(view);
    }



    class CategoryHolder extends RecyclerView.ViewHolder {
        public TextView txtCategoryName;
        public ImageView imageView;

         public CategoryHolder(@NonNull View itemView) {
             super(itemView);
             imageView=(ImageView)itemView.findViewById(R.id.category_image);
             txtCategoryName=(TextView)itemView.findViewById(R.id.category_view_name);
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
