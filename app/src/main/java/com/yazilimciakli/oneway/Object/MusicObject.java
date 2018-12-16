package com.yazilimciakli.oneway.Object;

import android.content.Context;

public class MusicObject {
    private MusicObject musicObject=null;

    private MusicObject(Context context)
    {
        synchronized (context){
            if(musicObject==null)
            {
                musicObject=new MusicObject(context);
            }
        }
    }
    public MusicObject newInstance()
    {
        return musicObject;
    }
}
