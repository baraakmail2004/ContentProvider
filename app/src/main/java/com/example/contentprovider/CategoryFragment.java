package com.example.contentprovider;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.contentprovider.databinding.FragmentCategoreBinding;

import java.util.ArrayList;
import java.util.Locale;


public class CategoryFragment extends Fragment {
    ArrayList<Products> arrayList;
    AdapterProducts adapterProducts;

    private static final String CATEGORY = "category";

    private String category;


    public CategoryFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentCategoreBinding binding;
        binding=FragmentCategoreBinding.inflate(getLayoutInflater(),container,false);
        arrayList=Utils.GetProductsByCategory(category);
        adapterProducts=new AdapterProducts(getContext(), arrayList, new AdapterProducts.onClickItem() {
            @Override
            public void onUserClick(Products i) {

            }
        });
        binding.recyclerview.setAdapter(adapterProducts);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerview.setHasFixedSize(true);
        return binding.getRoot() ;
    }
}