package com.scubbo.lifetracker.app.background;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.widget.Toast;
import com.scubbo.lifetracker.app.R;

public class BackgroundServiceInteraction {

    private Activity mActivity;
    private BackgroundService mBoundService;
    private Boolean mIsBound;

    public BackgroundServiceInteraction(Activity activity) {
        mActivity = activity;
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  Because we have bound to a explicit
            // service that we know is running in our own process, we can
            // cast its IBinder to a concrete class and directly access it.
            mBoundService = ((BackgroundService.LocalBinder)service).getService();
            System.out.println("Doing work now");
            ((BackgroundService.LocalBinder) service).beginToDoWork();
            // Tell the user about this for our demo.
//            Toast.makeText(mActivity, R.string.local_service_connected,
//                    Toast.LENGTH_SHORT).show();
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            // Because it is running in our same process, we should never
            // see this happen.
            mBoundService = null;
//            Toast.makeText(mActivity, R.string.local_service_disconnected,
//                    Toast.LENGTH_SHORT).show();
        }
    };

    public void startIt() {
        // Establish a connection with the service.  We use an explicit
        // class name because we want a specific service implementation that
        // we know will be running in our own process (and thus won't be
        // supporting component replacement by other applications).
        Toast.makeText(mActivity, "Starting it",
                Toast.LENGTH_SHORT).show();
        Context ctx = mActivity.getApplicationContext();
        Intent intent = new Intent(mActivity,
                BackgroundService.class);
//        ctx.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        ctx.startService(intent);
        mIsBound = true;
    }

    public void stopIt() {
        Toast.makeText(mActivity, "Stopping it",
                Toast.LENGTH_SHORT).show();
//        if (mIsBound) {
            // Detach our existing connection.
            Intent intent = new Intent(mActivity, BackgroundService.class);
            mActivity.getApplicationContext().stopService(intent);
            mIsBound = false;
//        }
    }

}
