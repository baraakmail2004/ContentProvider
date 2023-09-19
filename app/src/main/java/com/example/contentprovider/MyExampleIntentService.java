package com.example.contentprovider;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.widget.Toast;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyExampleIntentService extends IntentService {


    public MyExampleIntentService() {
        super("MyExapleIntentService");
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "on create", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        for (int i = 0; i < 1000000000; i++) {
            if (i%1000==0){
                Toast.makeText(this, "onHandleIntent"+i, Toast.LENGTH_SHORT).show();
            }


        }

    }


}