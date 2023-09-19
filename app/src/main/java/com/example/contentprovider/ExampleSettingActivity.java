package com.example.contentprovider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import android.os.Bundle;

import com.example.contentprovider.databinding.ActivityExampelSettingBinding;

public class ExampleSettingActivity extends AppCompatActivity {
    ActivityExampelSettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityExampelSettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new SettingsFragment()).commit();
        boolean isDarkMood= PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("testSwitch",false);
        if (isDarkMood){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        }


    }
}