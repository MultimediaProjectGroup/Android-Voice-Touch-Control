package ipc;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Vibrator;
import android.telephony.TelephonyManager;

import java.io.IOException;

/**
 * Created by lenovo on 2015/11/15.
 * 暂时无用
 */
public class AppAgent {
    private static final String TAG = "IPC";
    private Context mContext = null;
    private Handler mHandler = null;
    private MediaPlayer mPlayer = new MediaPlayer();
    private PowerManager.WakeLock mWakeLock = null;


   /* public String getCurrentIm()
    {
        return Settings.Secure.getString(this.mContext.getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD);
    }*/

    /*
    * Get IMEI information
    */
    public String getImei()
    {
        String str2 = ((TelephonyManager)this.mContext.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        String str1;
        if (str2 != null)
        {
            str1 = str2;
            if (!str2.equals("")) {}
        }
        else
        {
            str1 = getMacAddress();
        }
        return str1;
    }

    public String getMacAddress()
    {
        return ((WifiManager)this.mContext.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo().getMacAddress();
    }

    public boolean hasVibrator()
    {
        Vibrator localVibrator = (Vibrator)this.mContext.getSystemService(Context.VIBRATOR_SERVICE);
        try
        {
            boolean bool = localVibrator.hasVibrator();
            return bool;
        }
        catch (Throwable localThrowable) {}
        return false;
    }
    public void stopVibrate()
    {
        ((Vibrator)this.mContext.getSystemService(Context.VIBRATOR_SERVICE)).cancel();
    }

    public void vibrate(int paramInt)
    {
        if (hasVibrator())
        {
            stopVibrate();
            ((Vibrator)this.mContext.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(paramInt);
            return;
        }

    }

    public void playSound(String paramString)
    {
        if (this.mPlayer == null) {
            this.mPlayer = new MediaPlayer();
        }
        stopPlayingSound();
        try
        {
            this.mPlayer.reset();
            this.mPlayer.setDataSource(paramString);
            this.mPlayer.prepare();
            this.mPlayer.start();
            return;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            this.mPlayer.release();
            this.mPlayer = null;
        }
    }

    public void stopPlayingSound()
    {
        if ((this.mPlayer != null) && (this.mPlayer.isPlaying())) {
            this.mPlayer.stop();
        }
    }


}
