package com.starixc.adminhans.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.starixc.adminhans.Interface.ItemClickListner;
import com.starixc.adminhans.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtCategoryName;
    public ImageView imageView;
    private ItemClickListner listner;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        txtCategoryName=(TextView)itemView.findViewById(R.id.category_view_name);
        imageView=(ImageView)itemView.findViewById(R.id.category_image);
        itemView.setOnClickListener(this);


    }

    public void setItemClickListner(ItemClickListner listner) {
        this.listner= listner;
    }

    @Override
    public void onClick(View view) {
        listner.onClick(view,getAdapterPosition(),false);
    }
}
