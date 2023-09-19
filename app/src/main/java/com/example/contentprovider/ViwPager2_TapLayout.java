package com.example.contentprovider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.contentprovider.databinding.ActivityViwPager2TapLayoutBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class ViwPager2_TapLayout extends AppCompatActivity {
    ActivityViwPager2TapLayoutBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityViwPager2TapLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Utils.FillData();
        ArrayList<Fragment >fragments=new ArrayList<>();
        for (int i = 0; i < Utils.categoryArrayList.size(); i++) {
            fragments.add(CategoryFragment.newInstance(Utils.categoryArrayList.get(i)));
        }
        PagerAdapter pagerAdapter=new PagerAdapter(this,fragments);
        binding.view.setAdapter(pagerAdapter);
        new TabLayoutMediator(binding.tabLayout, binding.view, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText( Utils.categoryArrayList.get(position));



            }
        }).attach();





    }
}