package com.way.sound;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Handler;
import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by tchl on 2016-11-09.
 */
public class MediaManager
{
    private ConcurrentHashMap<String, Integer> mMediaManagerMap;
    static private MediaManager _instance;
    private Context mContext;
    private MediaPlayer mediaPlayer;
    private long seperateTime = 3000;
    private long delay = 1000;
    private Vector<Integer> mKillSoundQueue = new Vector<Integer>();
    private Handler mHandler = new Handler();


    public void initSounds(Context theContext) {
        if(mContext==null) {
            mContext = theContext;
        }
        if(mMediaManagerMap == null) {
            mMediaManagerMap = new ConcurrentHashMap<String, Integer>();
        }

    }

    public boolean isMediaManagerMapNull(){
        if(mMediaManagerMap==null || mMediaManagerMap.size() == 0){
            return  false;
        }
        return true;
    }

    static synchronized public MediaManager getInstance() {
        if (_instance == null)
            _instance = new MediaManager();
        return _instance;
    }

    private int PatientCall(String message){
        return mContext.getResources().getIdentifier("call"+message,"raw",mContext.getPackageName());
    }

    public void addSound(String key) {
        //int temp  = Integer.parseInt(key);
        if(!key.equals("hello")){
            synchronized (this) {
                if (!mMediaManagerMap.containsKey(key)) {
                    mMediaManagerMap.put(key, PatientCall(key));
                    Log.d("Map size:","addSound size:"+mMediaManagerMap.size());
                }
            }
        }

    }

    public void playMutilSounds()
            throws InterruptedException {
        Log.d("thread", "MediaManager thread:" + Thread.currentThread().getId());
        if (mMediaManagerMap != null) {
            Log.d("Map size:", "size:" + mMediaManagerMap.size());
            Iterator iter = mMediaManagerMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String key = String.valueOf(entry.getKey());
                Log.d("playMutilSounds", key);
                int temp  = Integer.parseInt(String.valueOf(entry.getValue()));
                if (mMediaManagerMap.containsKey(key)) {
                    //if(temp>=1 && temp<=69) {
                    Log.d("playMutilSounds","MediaPlay create");
                        mediaPlayer = MediaPlayer.create(mContext, Integer.parseInt(String.valueOf(entry.getValue())));
                        mediaPlayer.start();
                    //}

                }
                Thread.sleep(seperateTime);
                if (mediaPlayer != null ) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                    Log.d("playMutilSounds","MediaPlay release");
                }
            }
        }
    }

    public void cleanup() {
        if(mMediaManagerMap != null){
            mMediaManagerMap.clear();
        }
       // _instance = null;
       // _instance = null;
    }

    public void PrintSound(){
        Iterator iter = mMediaManagerMap.entrySet().iterator();
        while  (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = String.valueOf(entry.getKey());
            String val = String.valueOf(entry.getValue());
            Log.d("PrintSound:","MediaManager: key="+key+"  val="+val);
        }
    }
}