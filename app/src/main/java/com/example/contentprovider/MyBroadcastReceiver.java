package com.example.contentprovider;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BATTERY_LOW)){
            Toast.makeText(context, "Battery low", Toast.LENGTH_SHORT).show();
        }else {
//            if (intent.getIntExtra("state",-1)==0){//  لأن الModeChanged بترجع قيمتين وللتحقق من القيمة المرجعة نقوم بعمل الشرط إذا الstate رجعة 0 في هذه الحالة بتكون false غير ذلك بتكون true
//
//            }else {
//                Toast.makeText(context, "MODE_CHANGED(true)", Toast.LENGTH_SHORT).show();
//            }
            //طؤيقة أخري
            int state=Settings.System.getInt(context.getContentResolver(),Settings.Global.AIRPLANE_MODE_ON,-1);
            if (state==0){
                Toast.makeText(context, "MODE_CHANGED(Off)", Toast.LENGTH_SHORT).show();
            }else
                Toast.makeText(context, "MODE_CHANGED(on)", Toast.LENGTH_SHORT).show();

        }
        PendingResult result=goAsync();//تستخدم لتنفيذ الأكواد التي تأخذ وقت تحتها بتخلي المثود تنتظر إلي أن تنهيها بأيدك
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    result.finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();





    }
}