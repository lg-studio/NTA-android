package com.usinformatics.nytrip.helpers;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by D1m11n on 08.07.2015.
 */
public class FileHelper {

   public static void saveFromContentResolver(Context context, Uri uri) throws IOException {
       InputStream stream = context.getContentResolver().openInputStream(uri);
       File f= new File(Environment.getExternalStorageDirectory()+ "/output");
       f.createNewFile();
       FileOutputStream reader= new FileOutputStream(f);
       byte [] buffer= new byte[1024];
       int size;
       while((size=stream.read(buffer))>0){
           reader.write(buffer);
       }
       reader.flush();
       stream.close();
       reader.close();
   }

    public static void createDir(String path){
        if(TextUtils.isEmpty(path))
            return;
        File f= new File(path);
        if(f.exists()&&f.isDirectory())
            return;
        f.mkdirs();
    }

}
