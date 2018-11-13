package com.example.srinivas.database_2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText e1, e2, e3, e4;
    Button b1, b2;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1 = (EditText) findViewById(R.id.editText);
        e2 = (EditText) findViewById(R.id.editText2);
        e3 = (EditText) findViewById(R.id.editText3);
        e4 = (EditText) findViewById(R.id.editText4);
        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        try {
            db = openOrCreateDatabase("Student.db", Context.MODE_PRIVATE, null);
            db.execSQL("create table if not exists student_2(name varchar(20),usn varchar(20),phone varchar(20))");
            //Toast.makeText(this,"Table Created !! ",Toast.LENGTH_SHORT).show();
            if (view == b1) {
                String name = e1.getText().toString();
                String usn = e2.getText().toString();
                String ph = e3.getText().toString();
                db.execSQL("insert into student_2 values('" + name + "','" + usn + "','" + ph + "')");
                Toast.makeText(this,"Inserted successfully !! ",Toast.LENGTH_SHORT).show();
            } else {
                String key = e4.getText().toString();
                Cursor c = db.rawQuery("select * from student_2 where usn='" + key + "'", null);
                if (c != null) {
                    c.moveToFirst();
                    String phone = c.getString(2);
                    Intent i = new Intent(Intent.ACTION_CALL);
                    i.setData(Uri.parse("tel:" + phone));
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this,"Premission not obtained",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        startActivity(i);
                    }

                }
            }
        }catch(SQLiteException ex)
        {
            ex.printStackTrace();
        }finally {
            if(db!=null)
                db.close();
        }
    }
}
