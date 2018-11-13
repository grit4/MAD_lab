package com.example.srinivas.database_1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    SQLiteDatabase db;
    EditText e1,e2,e3;
    Button b1,b2;
    ArrayAdapter adapter;
    List<String> records;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1=(EditText)findViewById(R.id.editText);
        e2=(EditText)findViewById(R.id.editText2);
        e3=(EditText)findViewById(R.id.editText3);
        b1=(Button)findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button2);
        lv=(ListView)findViewById(R.id.lv);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        try{
            db=openOrCreateDatabase("Student.db", Context.MODE_PRIVATE,null);
            db.execSQL("create table if not exists student(name varchar(20),usn varchar(10),phone varchar(10))");
            //db.execSQL("delete from student");
            if(view==b1){
                String name=e1.getText().toString();
                String usn=e2.getText().toString();
                String phone=e3.getText().toString();
                db.execSQL("insert into student values('"+name+"','"+usn+"','"+phone+"')");
                Toast.makeText(this,"Row inserted successfully!!",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Cursor c=db.rawQuery("select * from student",null);
                String student_rec;
                records=new ArrayList<>();
                if(c.getCount()!=0){
                    c.moveToFirst();
                    do{
                        student_rec="Name : "+c.getString(0)+"\nUSN : "+c.getString(1)+"\nPhone : "+c.getString(2);
                        //Toast.makeText(this,c.getString(0),Toast.LENGTH_SHORT).show();
                        records.add(student_rec);
                    }while(c.moveToNext());
                   adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,records);
                   lv.setAdapter(adapter);
                }
                else
                {
                    Toast.makeText(this,"ok "+c.getCount(),Toast.LENGTH_SHORT).show();
                }

            }

        }
        catch(SQLiteException ex){
            ex.printStackTrace();
        }
        finally {
            if(db!=null)
                db.close();
        }
    }
}
