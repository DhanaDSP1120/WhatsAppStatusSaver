package com.dhanadsp1120.whatsappstatusdownloader;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
public TextView t,t1;
    GridView gv;
    RelativeLayout r;
    TextView ff;
    LinearLayout l;
public static String vediopath,imagepath;
    List<String> pathv=new ArrayList<String>();
    List<String> pathim=new ArrayList<String>();
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t=findViewById(R.id.bt1);
        l=findViewById(R.id.bt);
        r=findViewById(R.id.v);
        ff=findViewById(R.id.show);
        gv = findViewById(R.id.gridview1);

        t1=findViewById(R.id.bt2);
             if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
                 l.setVisibility(View.INVISIBLE);
                 gv.setVisibility(View.INVISIBLE);
                 r.setVisibility(View.VISIBLE);
                 ff.setText("Storage Permission is Required");
                      }
             else {
                 r.setVisibility(View.INVISIBLE);
                 t1.setTextColor(Color.parseColor("#ffffff"));
                 t.setTextColor(Color.parseColor("#000000"));
                 ve();
                 File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
                 cal(f);
                 File op=new File (Environment.getExternalStorageDirectory(),"StatusSaver");
                 if(op.exists())
                 {
                  //   Toast.makeText(this,"Alread Exit",Toast.LENGTH_LONG).show();
                 }
                 else {
                     op.mkdirs();
                 }
             }
    }


    public void image(View v)
    {
        t1.setTextColor(Color.parseColor("#000000"));
        t.setTextColor(Color.parseColor("#ffffff"));
        im();
    }
    public void video(View v)
    {
        t1.setTextColor(Color.parseColor("#ffffff"));
        t.setTextColor(Color.parseColor("#000000"));
ve();
    }
    public void ve()
    {

        CustomAdapter Custom=new CustomAdapter();
        gv.setAdapter(Custom);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vediopath=pathv.get(position);
                startActivity(new Intent(MainActivity.this,vedio.class));
            }
        });
    }
    public void im()
    {
        CustomAdapter1 Custom=new CustomAdapter1();
        gv.setAdapter(Custom);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                imagepath=pathim.get(position);
                startActivity(new Intent(MainActivity.this,image.class));
            }
        });

    }

    public void cal(File k)
    {

        File[] files=k.listFiles();
        int what=0,sta=0;

        for(File file:files)
        {
            String g=file.getPath();
            String []p=g.split("/");
            String u=p[p.length-1];

            if(u.equals("WhatsApp"))
            {
                what++;
                File j=new File(g);
                File[] files1=j.listFiles();
                   for(File file1:files1) {
                    String g1 = file1.getPath();
                    String[] p1 = g1.split("/");
                    String u1 = p1[p1.length - 1];

                    if(u1.equals("Media"))
                    {

                        File fl =Environment.getExternalStorageDirectory();
                        File fp=new File(fl.getAbsolutePath()+"/d/");
                        fp.mkdir();
                        File j1=new File(g1);
                        File[] files11=j1.listFiles();

                        for(File file11:files11) {
                            String g11 = file11.getPath();
                            String[] p11 = g11.split("/");
                            String u11 = p11[p11.length - 1];

                            if(u11.equals(".Statuses"))
                            {
                                File j2=new File(g11);
                                File[] files12=j2.listFiles();
                                for(File file12:files12) {
                                    String g12 = file12.getPath();
                                    String[] p12 = g12.split("/");
                                    String u12 = p12[p12.length - 1];

                                    if (u12.contains(".")) {

                                        String[] namesplit = u12.split("\\.");
                                        String nameformat = namesplit[1];
                                        if (nameformat.equals("mp4"))
                                        {
                                            pathv.add(g12);
                                            sta++;

                                        }
                                        if(nameformat.equals("jpg"))
                                        {
                                            pathim.add(g12);
                                            sta++;

                                        }
                                    }
                                }
                                break ;
                            }
                        }
                        break;
                    }
                }
                break ;
            }

        }
        if(what==0)
        {
            l.setVisibility(View.INVISIBLE);
            gv.setVisibility(View.INVISIBLE);
            r.setVisibility(View.VISIBLE);
            ff.setText("Whats App is Not Installed");
        }
        else if (sta==0)
        {

            l.setVisibility(View.INVISIBLE);
            gv.setVisibility(View.INVISIBLE);
            r.setVisibility(View.VISIBLE);
            ff.setText("No Recent status found");
        }



    }

    private class CustomAdapter extends BaseAdapter {


        @Override
        public int getCount() {

            return pathv.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = getLayoutInflater().inflate(R.layout.ima, null);
            RelativeLayout image = view1.findViewById(R.id.gridviewdata);
            Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(pathv.get(i),
                    MediaStore.Images.Thumbnails.MINI_KIND);
            //iv.setImageBitmap(thumbnail);
            BitmapDrawable ob = new BitmapDrawable(getResources(), thumbnail);
           image.setBackground(ob);
            return view1;
        }
    }
    private class CustomAdapter1 extends BaseAdapter {


        @Override
        public int getCount() {

            return pathim.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = getLayoutInflater().inflate(R.layout.imagedis, null);
            ImageView image = view1.findViewById(R.id.gridviewdata);
            Bitmap myBitmap = BitmapFactory.decodeFile(pathim.get(i));
//Picasso.with(MainActivity.this).load(pathim.get(i)).into(image);
           image.setImageBitmap(myBitmap);
            return view1;
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