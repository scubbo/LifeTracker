package com.scubbo.lifetracker.app.background;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

//http://developer.android.com/reference/android/app/Service.html#LocalServiceSample
public class BackgroundService extends Service {

//    private final IBinder mBinder = new LocalBinder();
    private Runnable mR;

    private CallbackService callbackService;

    private static final int MILLIS_IN_A_SECOND = 1000;
    private static final int MILLIS_IN_A_MINUTE = 6 * MILLIS_IN_A_SECOND;

    private static final int CALLBACK_INTERVAL = MILLIS_IN_A_SECOND;

    @Override
    public IBinder onBind(Intent intent) {
//        return mBinder;
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        callbackService = new CallbackService(this);
        doWork();

        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        mR = new Runnable() {
            public void run() {
                //Do nothing
            }
        };
    }

    public void doWork() {
        final Handler handler = new Handler();

        mR = new Runnable() {
            public void run() {
                callbackService.call();
                handler.postDelayed(mR, CALLBACK_INTERVAL);
            }
        };

        handler.postDelayed(mR, CALLBACK_INTERVAL);
    }

    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder {
        BackgroundService getService() {
            return BackgroundService.this;
        }
    }

}
