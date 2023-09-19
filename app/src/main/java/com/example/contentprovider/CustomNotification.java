package com.example.contentprovider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.example.contentprovider.databinding.ActivityCustomNotifactionBinding;

import java.util.ArrayList;
import java.util.Collections;

public class CustomNotification extends AppCompatActivity {
    ActivityCustomNotifactionBinding binding;
    ArrayList<Products>arrayList;
    Context context=CustomNotification.this;
    AdapterProducts adapterProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCustomNotifactionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        arrayList=new ArrayList<>();
        arrayList.add(new Products(1,"Food",R.drawable.ic_baseline_icecream_24,15.5,"Drinks"));
        arrayList.add(new Products(2,"Food",R.drawable.ic_baseline_icecream_24,100,"Food"));
        arrayList.add(new Products(3,"Food",R.drawable.ic_baseline_icecream_24,20,"TEa"));
        arrayList.add(new Products(4,"Food",R.drawable.ic_launcher_background,80,"Cfe"));
        adapterProducts=new AdapterProducts(context, arrayList, new AdapterProducts.onClickItem() {
            @Override
            public void onUserClick(Products i) {
                showNotification(i);


            }
        });
        binding.recyclerview.setAdapter(adapterProducts);
        binding.recyclerview.setHasFixedSize(true);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(context));







    }

    private void showNotification(Products p) {
        String channelId="CH01";//لتمييز كل إشعار عن الآخر
        NotificationChannel channel=null;
        NotificationManager manager;
        manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent intent=new Intent(context,ViwPager2_TapLayout.class);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pn=PendingIntent.getActivities(context,0, new Intent[]{intent},PendingIntent.FLAG_MUTABLE);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,channelId);
        builder.setSmallIcon(p.getImage())
                .setOnlyAlertOnce(true)
//                .setOngoing(true)
                .setContentIntent(pn)
                .setContent(getCustomNotificationDesign(p));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel=new NotificationChannel(channelId,"Baraa", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannels(Collections.singletonList(channel));
        }
        manager.notify(1,builder.build());


    }
    private RemoteViews getCustomNotificationDesign(Products products){
     @SuppressLint("RemoteViewLayout") RemoteViews rv=new RemoteViews(getPackageName(),R.layout.category_design);
     rv.setImageViewResource(R.id.image_Product,products.getImage());
     rv.setTextViewText(R.id.price,String.valueOf(products.getPrice()));
     rv.setTextViewText(R.id.category,products.getCategory());
     rv.setTextViewText(R.id.name,products.getName());
     return rv;





    }
}