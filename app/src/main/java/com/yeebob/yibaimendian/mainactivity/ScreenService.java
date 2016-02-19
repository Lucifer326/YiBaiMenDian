package com.yeebob.yibaimendian.mainactivity;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * 屏保服务 service
 * Created by WGl on 2016-1-27.
 */
public class ScreenService extends Service {

    KeyguardManager mKeyguardManager = null;
    private KeyguardManager.KeyguardLock mKeyguardLock = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
       // Log.i("in Service", "in Service");
        mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        mKeyguardLock = mKeyguardManager.newKeyguardLock("");
        mKeyguardLock.disableKeyguard();
       // Log.i("in Service1","in Service1");
        BroadcastReceiver mMasterResetReciever = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {

                try {
                    Intent i = new Intent();
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setClass(context, CarouselImg.class);
                    context.startActivity(i);
                    // finish();
                   // Log.i("BroadcastReceiver","BroadcastReceiver");
                } catch (Exception e) {
                   // Log.i("Output:", e.toString());
                }
            }
        };
        registerReceiver(mMasterResetReciever, new IntentFilter(
                Intent.ACTION_SCREEN_OFF));

    }
}
