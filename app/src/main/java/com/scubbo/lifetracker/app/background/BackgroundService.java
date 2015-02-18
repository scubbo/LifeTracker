package com.scubbo.lifetracker.app.background;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import com.scubbo.lifetracker.app.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

//http://developer.android.com/reference/android/app/Service.html#LocalServiceSample
public class BackgroundService extends Service {

//    private final IBinder mBinder = new LocalBinder();
    private Runnable mR;
    private NotificationManager mNM;

    private static final int MILLIS_IN_A_SECOND = 1000;
    private static final int MILLIS_IN_A_MINUTE = 6 * MILLIS_IN_A_SECOND;

    private Set<Integer> activeNotifications = new HashSet<Integer>();

    @Override
    public IBinder onBind(Intent intent) {
//        return mBinder;
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        doWork();

        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        mR = new Runnable() {
            public void run() {
                for (Integer id: activeNotifications) {
                    mNM.cancel(id);
                }
                System.out.println("I'm done");
            }
        };
    }

    public void doWork() {
        final Handler handler = new Handler();

        mR = new Runnable() {
            public void run() {
                Date date = getDate();
                if (date.getSeconds() % 10 == 0) {
                    Integer notiID = (int) (Math.random() * 100);
                    activeNotifications.add(notiID);
                    Notification noti = buildNotification();
                    mNM.notify(notiID, noti);
                }
                handler.postDelayed(mR, MILLIS_IN_A_SECOND);
            }
        };

        handler.postDelayed(mR, MILLIS_IN_A_SECOND);
    }

    private Notification buildNotification() {
        return new Notification.Builder(this)
                .setContentTitle("YOU'VE GOT MAIL")
                .setContentText("THE SUBJECT TEXT")
                .setSmallIcon(R.drawable.interrobang)
                .build();
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
        void beginToDoWork() {getService().doWork();}
    }

    private Date getDate() {
        return GregorianCalendar.getInstance().getTime();
    }

}
