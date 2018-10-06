package com.bobbylders.sms.smsieve;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bt1;
    Button bt2;

    public ArrayList<String> returnFullMsgs_service(List<String> allMsgsList, String service) {

        int index1, index2, index_body_end, index_body_start;
        String body;

        ArrayList<String> selectedFullMsgs = new ArrayList<String>();

        for (String msg: allMsgsList) {

            index_body_start = msg.indexOf("body:") + 5;
            index_body_end = msg.indexOf("service_center");
            body = msg.substring(index_body_start, index_body_end);

            index1 = body.indexOf(service);
            index2 = body.indexOf("code");

            if(index1 != -1 && index2 != -1) {

                selectedFullMsgs.add(body);

            } //if

        } //for

        return selectedFullMsgs;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED) {
//            LinearLayout tap = (LinearLayout)findViewById(R.id.cnt);
//            createLists(tap, returnFullMsgs_service(GetMessages(), "Uber"));
        }
    }
    public void createLists(LinearLayout container,List<String> toadd)
    {
        for(int i=0;i<toadd.size();i++)
        {
            LinearLayout temp = new LinearLayout(this);
            temp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            temp.setBackgroundColor(Color.RED);
            Button tempstr = new Button(this);
            tempstr.setText(toadd.get(i));
            Log.i("p",toadd.get(i));
            temp.addView(tempstr);
            container.addView(temp);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         bt1 = (Button)findViewById(R.id.pr);
         bt2 = (Button)findViewById(R.id.mc);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS}, 3004);
            Toast.makeText(this, "Permission Added Permanently", Toast.LENGTH_LONG).show();
        }
//        else{
//
////            LinearLayout tap = (LinearLayout)findViewById(R.id.cnt);
////            createLists(tap, returnFullMsgs_service(GetMessages(), "Uber"));
//        }


    }

    @Override
    public void onClick(View v){

        if(v == bt1) {
            Toast.makeText(this, "1", Toast.LENGTH_LONG).show();
            Intent it = new Intent(this,promos.class);
            startActivity(it);
        }
        else if(v==bt2)
        {
            Toast.makeText(this, "2", Toast.LENGTH_LONG).show();
            Intent it = new Intent(this,missed
                    .class);
            startActivity(it);
        }
    }
    protected List<String> GetMessages(){
            List Mes = new ArrayList();
            Uri message = Uri.parse("content://sms/");
            ContentResolver crd = getContentResolver();
            Cursor cr = crd.query(message,null,null,null,null);
            if (cr.moveToFirst()) {

                // must check the result to prevent exception
                do {
                    String msgData = "";
                    String cont ="";
                    for(int idx=0;idx<cr.getColumnCount();idx++)
                    {
                        msgData += " " + cr.getColumnName(idx) + ":" + cr.getString(idx);
                    }
                    cont = cont + msgData;
                    Mes.add(msgData);
                    // use msgData
                } while (cr.moveToNext());
                for (int i = 0; i < Mes.size(); i++) {
                    Log.i("p",Mes.get(i).toString());
                    Log.i("p","end");
                }
            } else {
                // empty box, no SMS
            }
        return Mes;
    }
}
