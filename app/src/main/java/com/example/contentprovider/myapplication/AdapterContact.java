package com.example.contentprovider.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.contentprovider.R;
import com.example.contentprovider.databinding.RvDesignContentBinding;

import java.util.ArrayList;

public class AdapterContact extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    RvDesignContentBinding binding;
    ArrayList<Contact> arrayList;

    public AdapterContact(Context context, ArrayList<Contact> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=RvDesignContentBinding.inflate(LayoutInflater.from(context),parent,false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder= (MyHolder) holder;
        Contact contactItem=arrayList.get(position);
        if (contactItem.getEmail()!=null){
            myHolder.binding.email.setText(arrayList.get(position).getEmail());
        }
        if (contactItem.getName()!=null){
            myHolder.binding.name.setText(arrayList.get(position).getName());
        }
        if (contactItem.getNumber()!=null){
            myHolder.binding.phone.setText(arrayList.get(position).getNumber());
        }
        if (contactItem.getOtherDetails()!=null){
            myHolder.binding.other.setText(arrayList.get(position).getOtherDetails());
        }

        Bitmap image=null;
        if (!contactItem.getPhoto().equals("")&&contactItem.getPhoto()!=null){
            image= BitmapFactory.decodeFile(contactItem.getPhoto());
            if (image!=null){
                myHolder.binding.image.setImageBitmap(image);

            }else {
                image=BitmapFactory.decodeResource(myHolder.binding.getRoot().getContext().getResources(), R.drawable.ic_launcher_background);
                myHolder.binding.image.setImageBitmap(image);

            }
        }else {
            image=BitmapFactory.decodeResource(myHolder.binding.getRoot().getContext().getResources(),R.drawable.ic_launcher_background);
            myHolder.binding.image.setImageBitmap(image);
        }



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class MyHolder extends RecyclerView.ViewHolder{
        RvDesignContentBinding binding;
        public MyHolder(RvDesignContentBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
