package com.usinformatics.nytrip.helpers;

import android.content.Context;

import java.io.File;
import java.io.IOException;

/**
 * Created by D1m11n on 07.07.2015.
 */
public class FileNameGenerator {

    public static File getTempFile(Context context, String name) throws IOException {
        File outputDir = context.getCacheDir(); // context being the Activity pointer
        return  File.createTempFile(name, ".pcm", outputDir);
    }
}
