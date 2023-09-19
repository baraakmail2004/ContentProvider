package com.example.contentprovider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.contentprovider.databinding.CategoryDesignBinding;
import com.example.contentprovider.databinding.RvDesignContentBinding;

import java.util.ArrayList;

public class AdapterProducts extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<Products> arrayList;
    CategoryDesignBinding binding;
    public onClickItem clickItem;




    public AdapterProducts(Context context, ArrayList<Products> arrayList,onClickItem clickItem) {
        this.clickItem=clickItem;
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=CategoryDesignBinding.inflate(LayoutInflater.from(context),parent,false);
        return new MyHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder= (MyHolder) holder;
        Products products=arrayList.get(position);
        myHolder.bind(products);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class MyHolder extends RecyclerView.ViewHolder{
        Products products;
        CategoryDesignBinding binding;
        public MyHolder(CategoryDesignBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
        public void bind(Products p){
            this.products=p;
            binding.name.setText(p.getName());
            binding.category.setText(p.getCategory());
            binding.price.setText(String.valueOf(p.getPrice()));
            binding.imageProduct.setImageResource(p.getImage());
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickItem.onUserClick(products);

                }
            });

        }
    }
    interface onClickItem{
        void onUserClick(Products p);
    }

}
