package com.example.contentprovider;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;
import android.widget.Toast;

public class ExampleJopService extends JobService {
    boolean isClick=false;
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d("jopTest", "onStartJob: ");
        doTaskInBackGround(params);
        return true;
    }



    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d("jopTest", "onStopJop: ");
        isClick=true;
        return false;
    }
    private void doTaskInBackGround(JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                    for (int i = 0; i < 10; i++) {
                        if (isClick) {
                            jobFinished(params,false);
                        }else {
                            try {
                                Thread.sleep(1000);
                                Log.d("jopTest", "run: " + i);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }



                }
            }
            }).start();

    }
}




