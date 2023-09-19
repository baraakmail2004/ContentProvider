package com.example.contentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;

import com.example.contentprovider.databinding.ActivityExampleJopServiceBinding;

public class Example_jop_service extends AppCompatActivity {
    private static final int JOP_ID = 1;
    ActivityExampleJopServiceBinding binding;
    JobScheduler jobScheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityExampleJopServiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnStart.setOnClickListener(v -> {
            //*1
            ComponentName componentName=new ComponentName(getBaseContext(),ExampleJopService.class);
            //*2
            JobInfo info= null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                info = new JobInfo.Builder(JOP_ID,componentName)
    //                    .setPeriodic(15*60*601000,5*60*1000)
                        .setMinimumLatency(5000)
                        .setRequiresCharging(false)
//                        .setRequiredNetwork(JobInfo.NETWORK_TYPE_ANY)
                        .setPersisted(true)
                        .build();
            }
            //*3
           jobScheduler= (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            jobScheduler.schedule(info);



        });
        binding.btnStop.setOnClickListener(v -> {
            jobScheduler.cancel(JOP_ID);


        });
    }
}