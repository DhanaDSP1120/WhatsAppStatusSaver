package com.dhanadsp1120.whatsappstatusdownloader;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static org.apache.commons.io.FileUtils.copyFile;

public class image extends AppCompatActivity {
    ImageView vv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        vv=findViewById(R.id.image);
        Bitmap myBitmap = BitmapFactory.decodeFile(MainActivity.imagepath);
        vv.setImageBitmap(myBitmap);

    }
    public boolean appInstalledOrNot(String uri)
    {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try
        {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            app_installed = false;
        }
        return app_installed ;
    }
    public void others(View v)
    {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");// You Can set source type here like video, image text, etc.
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(MainActivity.imagepath));
        shareIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(shareIntent, "Share File Using!"));
    }
    public void whatsapp(View v)
    {
        boolean installed  =   appInstalledOrNot("com.whatsapp");
        if(installed)
        {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/*");// You Can set source type here like video, image text, etc.
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(MainActivity.imagepath));
            shareIntent.setPackage("com.whatsapp");
            startActivity(Intent.createChooser(shareIntent, "Share File Using!"));
        }
        else
        {
            Toast.makeText(this,"App Not Installed",Toast.LENGTH_LONG).show();
        }
    }
    public void fb(View v)
    {
        boolean installed  =   appInstalledOrNot("com.facebook.katana");
        if(installed)
        {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/*");// You Can set source type here like video, image text, etc.
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(MainActivity.imagepath));
            shareIntent.setPackage("com.facebook.katana");
            startActivity(Intent.createChooser(shareIntent, "Share File Using!"));

        }
        else
        {
            Toast.makeText(this,"App Not Installed",Toast.LENGTH_LONG).show();
        }
    }
    public void insta(View v)
    {
        boolean installed  =   appInstalledOrNot("com.instagram.android");
        if(installed)
        {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/*");// You Can set source type here like video, image text, etc.
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(MainActivity.imagepath));
            shareIntent.setPackage("com.instagram.android");
            startActivity(Intent.createChooser(shareIntent, "Share File Using!"));

        }
        else
        {
            Toast.makeText(this,"App Not Installed",Toast.LENGTH_LONG).show();
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void save(View v)
    {
        File src = new File(MainActivity.imagepath);
        File dst = new File(Environment.getExternalStorageDirectory()+"/StatusSaver/", src.getName());
        try {
            copyFile(src, dst);
            Toast.makeText(this,"Status Saved"+ System.lineSeparator()+"Location:"+Environment.getExternalStorageDirectory()+"/StatusSaver/",Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.rightmenu,menu);
        return true;
    }
    public void feed()
    {
        Intent intent =  new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","dhanadspprojects@gmail.com", null));

        intent.putExtra(Intent.EXTRA_SUBJECT, "FeedBack to Status Saver app");

        startActivity(Intent.createChooser(intent, "Send Email"));

    }
    public void exit()
    {
        AlertDialog.Builder dialog= new AlertDialog.Builder(this);
        dialog.setMessage("Are you sure want to Exit");
        dialog.setCancelable(true);
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);

            }
        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog ad=dialog.create();
        ad.show();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.feedback: feed();

                break;

            case R.id.exit:exit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}