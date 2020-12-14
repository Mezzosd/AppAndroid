package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;

import static android.graphics.Bitmap.CompressFormat.JPEG;

public class Screenshot {


ActivityCompat.requestPermissions(activity:this,new
    String[] WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE, PackageManager.PERMISSION);

    String filePath = Environment.getExternalStorageDirectory() + "/Download/" + Calendar.getInstance().getTime().toString() + ".jpg";
    File fileScreenshot = new File(filePath);

    FileOutputStream fileOutputStream = null;

 try {
        fileOutputStream = new FileOutputStream(fileScreenshot);
        bitmap.compress(Bitmap.CompressFormat, JPEG, quality 100, fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
    } catch (Exception e){
        e.printStackTrace();
    }

    Intent intent = new Intent(.Action_view);
    Uri uri = Uri.fromFile(fileScreenshot);
    intent.setdataAndType(uri, type:"image/jpeg");
     intent.addFlag(intenet.FLAG_ACTIVITY_NEW_TASK);
     this.startActivity(intenet);
}
}
