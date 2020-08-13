package com.example.indee;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.net.URLEncoder;

import static android.os.Environment.MEDIA_MOUNTED;
import static android.os.Environment.getExternalStorageDirectory;
import static android.os.Environment.getExternalStorageState;

public class FileCache {
    private File cacheDir;

    public FileCache(Context context){
        if (getExternalStorageState().equals(MEDIA_MOUNTED))
            cacheDir=new File(getExternalStorageDirectory().toString(),"/Images");
        else
            cacheDir=context.getCacheDir();
        if(!cacheDir.exists())
            cacheDir.mkdirs();
    }

    public File getFile(String url){
        String filename=String.valueOf(url.hashCode());
//        String filename = URLEncoder.encode(url);
        File f = new File(cacheDir, filename);
        return f;

    }

    public void clear(){
        File[] files=cacheDir.listFiles();
        if(files==null)
            return;
        for(File f:files)
            f.delete();
    }
}
