package com.example.contentprovider.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.contentprovider.R;
import com.example.contentprovider.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int REQ_PERMISSION_READ_CONTACT = 1;
    ActivityMainBinding binding;
    ArrayList<Contact> contacts;
    AdapterContact adapterContact;
    Context context = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, REQ_PERMISSION_READ_CONTACT);


        } else {
            new MyTask().execute();


        }


    }


    @SuppressLint("Range")
    private void readContact() {
        //سوف نقوم نبعمل لووب علي الأسماء الموجودة في داخل تطبيق الأسماءوبدي أعبيها في الأري وببعت الأري للadabter لكي تنعرض علي الرسايكل
        contacts = new ArrayList<>();
        Uri uri = ContactsContract.Contacts.CONTENT_URI;// عبارة عن عنوان الجدول تبع الأسماء إلي بدي أجيب منه البيانات
        Cursor cursor = getContentResolver().query(uri, null, null, null
                , ContactsContract.Contacts.DISPLAY_NAME + " ASC");
        do {
            if (cursor.moveToFirst()) {
                //قمنا بجلب الid من الجدول زلكل id يوجد بيانات سوف نقوم بجلبها بناء علي الid إلي عنا
                 long id = cursor.getLong(cursor.getColumnIndex("_ID"));
                Uri contactUri = ContactsContract.Contacts.CONTENT_URI;
                Cursor contactCursor = getContentResolver().query(contactUri, null, ContactsContract.Data.CONTACT_ID + " =?",
                        new String[]{String.valueOf(id)},null);
                //البيانات الموجودة في داخل برنامج الصوتبنقدر ناخذ منها إلي  بدنا إياه
                String displayName = "";
                String nickName = "";
                String homePhone = "";
                String workPhone = "";
                String mobilePhone = "";
                String photoPath = "" + R.drawable.ic_launcher_background;//photo Path;
                String homeEmail = "";
                String workEmail = "";
                String companyName = "";
                String title = "";
                byte[] photoByte = null;
                String contactNumber = "";
                String contactEmailAddress = "";
                String contactOtherDetails = "";
                if (contactCursor.moveToFirst()) {
                    @SuppressLint("Range") String name = cursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    displayName=name;
                    do {
                        //read nickName

                        if (contactCursor.getString(contactCursor.getColumnIndex("mimetype")).equals(ContactsContract
                                .CommonDataKinds.Nickname.CONTENT_ITEM_TYPE)) {
                            nickName = contactCursor.getString(contactCursor.getColumnIndex("data1"));
                            contactOtherDetails += nickName + "\n";
                        }
                        //read Phone
                        if (contactCursor.getString(contactCursor.getColumnIndex("mimetype")).equals(ContactsContract
                                .CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
                            switch (contactCursor.getInt(contactCursor.getColumnIndex("data2"))) {
                                case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                                    homePhone = contactCursor.getString(contactCursor.getColumnIndex("data1"));
                                    contactNumber += "Home Phone :" + homePhone + "\n";
                                    break;
                                case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                                    workPhone = contactCursor.getString(contactCursor.getColumnIndex("data1"));
                                    contactNumber += "Work Phone :" + workPhone + "\n";
                                    break;
                                case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                    mobilePhone = contactCursor.getString(contactCursor.getColumnIndex("data1"));
                                    contactNumber += "Mobile Phone :" + mobilePhone + "\n";
                                    break;

                            }

                        }
                        //read Email
                        if (contactCursor.getString(contactCursor.getColumnIndex("mimetype")).equals(ContactsContract
                                .CommonDataKinds.Email.CONTENT_ITEM_TYPE)) {
                            switch (contactCursor.getInt(contactCursor.getColumnIndex("data2"))){
                                case  ContactsContract.CommonDataKinds.Email.TYPE_HOME:
                                    homeEmail=contactCursor.getString(contactCursor.getColumnIndex("data1"));
                                    contactEmailAddress+="Home Email :"+homeEmail+"\n";
                                    break;
                                case  ContactsContract.CommonDataKinds.Email.TYPE_WORK:
                                    workEmail=contactCursor.getString(contactCursor.getColumnIndex("data1"));
                                    contactEmailAddress+="Work Email :"+workEmail+"\n";
                                    break;


                            }

                        }
                        if (contactCursor.getString(contactCursor.getColumnIndex("mimetype")).equals(ContactsContract
                                .CommonDataKinds.Organization.CONTENT_ITEM_TYPE)){
                            companyName=contactCursor.getString(contactCursor.getColumnIndex("data1"));
                            contactOtherDetails+="Company Name :"+companyName+"\n";
                            title=contactCursor.getString(contactCursor.getColumnIndex("data4"));
                            contactOtherDetails+="title:"+title+"\n";
                        }
                        //هذا الكود لتحخزين الصورة في cacheDirectory وأخذ الرابط تبعها وإرساله إلي الobjContact
                        if (contactCursor.getString(contactCursor.getColumnIndex("mimetype")).equals(ContactsContract
                                .CommonDataKinds.Photo.CONTENT_ITEM_TYPE)) {
                            photoByte = contactCursor.getBlob(contactCursor.getColumnIndex("data15"));
                            if (photoByte!=null){
                                Bitmap bitmap= BitmapFactory.decodeByteArray(photoByte,0,photoByte.length);
                                File cacheDirectory=getBaseContext().getCacheDir();
                                File tmp=new File(cacheDirectory.getPath()+"/_androhub"+id+".png");
                                try {
                                    FileOutputStream outputStream=new FileOutputStream(tmp);
                                    bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
                                    outputStream.flush();
                                    outputStream.close();

                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                photoPath=tmp.getPath();


                            }
                        }




                    } while (contactCursor.moveToNext());


                }
                contacts.add(new Contact(displayName,Long.toString(id),contactNumber,contactEmailAddress,contactOtherDetails,photoPath));


            }



        } while (cursor.moveToNext());


    }
    @SuppressLint("StaticFieldLeak")
    private class MyTask extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            binding.progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            readContact();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            binding.progressBar.setVisibility(View.GONE);
            populateDataIntoRecyclerView(contacts);
        }
    }

    private void populateDataIntoRecyclerView(ArrayList<Contact> arrayList) {
        adapterContact = new AdapterContact(context, arrayList);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(context));
        binding.recyclerview.setAdapter(adapterContact);
        binding.recyclerview.setHasFixedSize(true);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (REQ_PERMISSION_READ_CONTACT == 1 && grantResults.length > 0) {
            new MyTask().execute();

        }
    }
}