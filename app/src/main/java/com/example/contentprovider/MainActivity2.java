package com.example.contentprovider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.example.contentprovider.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {
    public static final int  REQUEST_READ_CODE_PERMISSION = 100;
    ActivityMain2Binding binding;
    MyBroadcastReceiver myBroadcastReceiver;
    Context context=MainActivity2.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//       myBroadcastReceiver=new MyBroadcastReceiver();
//        registerReceiver(myBroadcastReceiver,new IntentFilter(Intent.ACTION_BATTERY_LOW));
//        registerReceiver(myBroadcastReceiver,new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED));
        //content provider
        //1*use the Read contacts permission
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},REQUEST_READ_CODE_PERMISSION);
        }else {
            readContacts();
        }




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==REQUEST_READ_CODE_PERMISSION&&grantResults.length>0){
            Toast.makeText(context, "تم أخذ الصلاحية", Toast.LENGTH_SHORT).show();
        }
    }

    private void readContacts() {
        //*2 Access the Contact contract
        Uri uri= ContactsContract.Contacts.CONTENT_URI;//تقوم بإرجاع الuri إلي في داخلها الداتابيز إلي بدي أصلها
        Cursor cursor=getContentResolver().query(uri,null,null,null,ContactsContract.Contacts.DISPLAY_NAME+" ASC");
        if (cursor.moveToFirst()){
            do {
                long contactID=cursor.getLong(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                Uri uri1=ContactsContract.Data.CONTENT_URI;
                Cursor cursor1=getContentResolver().query(uri1,null,
                        ContactsContract.Data.CONTACT_ID+" =?",new String[]{String.valueOf(contactID)},null);//بجلب البيانات إلي الcontactID متساوي مع ال ContactId الموجود في الData
                if (cursor1.moveToFirst()){//بجيب بيانات row واحد من البيانات لذلك لا أحتاج إلي do while
                   String contractName=cursor1.getString(cursor1.getColumnIndexOrThrow(ContactsContract.Data.DISPLAY_NAME));





                }

            }while (cursor.moveToNext());
        }






    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }
}