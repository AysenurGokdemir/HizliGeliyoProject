package com.example.hizligeliyoproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hizligeliyoproject.CustomItemClickListener;
import com.example.hizligeliyoproject.R;
import com.example.hizligeliyoproject.model.ProductList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> implements Filterable {

    private ArrayList<ProductList> productList;
    private ArrayList<ProductList> filteredProductList;
    private Context context;
    private CustomItemClickListener customItemClickListener;

    public CustomAdapter(ArrayList<ProductList> productList, Context context, CustomItemClickListener customItemClickListener) {
        this.productList = productList;
        this.filteredProductList=productList;
        this.context = context;
        this.customItemClickListener = customItemClickListener;
    }


    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup,false);

        final  MyViewHolder myViewHolder=new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // for click item listener
                customItemClickListener.onItemClick(filteredProductList.get(myViewHolder.getAdapterPosition()),myViewHolder.getAdapterPosition());
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.title.setText(filteredProductList.get(i).getTitle());
        myViewHolder.price.setText(String.valueOf(filteredProductList.get(i).getPrice())+ " TL");
        Picasso.with(context).load(filteredProductList.get(i).getImage()).into(myViewHolder.image);
    }

    @Override
    public int getItemCount() {
        return filteredProductList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String searchString = charSequence.toString();

                if(searchString.isEmpty()){
                    filteredProductList=productList;
                }else {
                    ArrayList<ProductList> tempFilteredList=new ArrayList<>();

                    for (ProductList products : productList){
                        if ((products.getTitle()).toLowerCase().contains(searchString)){
                            tempFilteredList.add(products);
                        }
                        if ((products.getCategory()).toLowerCase().contains(searchString)){
                            tempFilteredList.add(products);
                        }
                    }
                    filteredProductList=tempFilteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredProductList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredProductList = (ArrayList<ProductList>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView price;
        private ImageView image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.title);
            price= (TextView) itemView.findViewById(R.id.price);
            image= (ImageView) itemView.findViewById(R.id.product);


        }
    }
}
