package com.starixc.adminhans.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.starixc.adminhans.Interface.ItemClickListner;
import com.starixc.adminhans.Model.OrderProduct;
import com.starixc.adminhans.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class OrderItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName, txtProductPrice, txtProductQuantity, txtProductTotal;
    private ItemClickListner itemClickListner;

    public OrderItemViewHolder(@NonNull View itemView) {
        super(itemView);
        txtProductName = itemView.findViewById(R.id.idProductName);
        txtProductPrice = itemView.findViewById(R.id.idProductPrice);
        txtProductQuantity = itemView.findViewById(R.id.idProductQty);
        // txtProductTotal=itemView.findViewById(R.id.idProductPriceTotal);
    }

    public void setTxtProductName(TextView txtProductName) {
        this.txtProductName = txtProductName;
    }

    @Override
    public void onClick(View view) {

    }
}

    public class  OrdersAdapter extends RecyclerView.Adapter<OrderItemViewHolder>{
        private List<OrderProduct> itemsList = new ArrayList<>();
        private Context context;

        public OrdersAdapter(List<OrderProduct> itemsList, Context context) {
            this.itemsList = itemsList;
            this.context = context;
        }


        @NonNull
        @Override
        public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            LayoutInflater inflater = LayoutInflater.from(context);
//            View view = inflater.inflate(R.layout.layout_order_item,parent,false);
//            return new OrderItemViewHolder(view);
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_order_item, parent, false);
                OrderItemViewHolder holder = new OrderItemViewHolder(view);
                return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
            holder.txtProductName.setText(itemsList.get(position).getProductName());
            holder.txtProductQuantity.setText(itemsList.get(position).getQuantity());
            //holder.txtProductPrice.setText(itemList.get(position).getPrice());
            Locale locale = new Locale("en","KE");
            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
            int price =(Integer.parseInt(itemsList.get(position).getPrice()))*(Integer.parseInt(itemsList.get(position).getQuantity()));
            holder.txtProductPrice.setText(fmt.format(price));
        }

        @Override
        public int getItemCount() {
            return itemsList.size();
        }

    }

