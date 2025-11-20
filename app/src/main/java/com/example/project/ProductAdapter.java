package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private Context context;
    private List<Product> products;

    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_product, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.tvProductName);
        TextView tvDescription = convertView.findViewById(R.id.tvProductDescription);
        TextView tvPrice = convertView.findViewById(R.id.tvProductPrice);


        Product product = products.get(position);
        tvName.setText(product.getName());
        tvDescription.setText(product.getDescription());
        tvPrice.setText("Rs." + product.getPrice());


        return convertView;
    }

    // Method to get the products list
    public List<Product> getProducts() {
        return products;
    }
}
